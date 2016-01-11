package backend.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import backend.DAOs.BookDAO;
import backend.DAOs.BorrowDAO;
import backend.DAOs.GameCategoryDAO;
import backend.DAOs.GameDAO;
import backend.DAOs.GameEditorDAO;
import backend.DAOs.ItemDAO;
import backend.POJOs.Game;
import backend.POJOs.Item;

/**
 * Propose des fonctionnalités de traitement sur des jeux
 */
public class GameServices {

	private ParametersServices parametersServices;

	/**
	 * Objet de manipulation de la base de données pour les jeux
	 */
	private final GameDAO gameDAO;

	/**
	 * Objet de manipulation de la base de données pour les catégories de jeux
	 */
	private final GameCategoryDAO gameCategoryDAO;

	/**
	 * Objet de manipulation de la base de données pour les éditeurs de jeux
	 */
	private final GameEditorDAO gameEditorDAO;

	/**
	 * Objet de manipulation de la base de données pour les exemplaires de jeux
	 */
	private final ItemDAO itemDAO;

	/**
	 * Objet de manipulation de la base de données pour les réservations
	 */
	private final BookDAO bookDAO;

	/**
	 * Objet de manipulation de la base de données pour les prêts
	 */
	private final BorrowDAO borrowDAO;

	/**
	 * Construit un nouveau service pour les jeux
	 */
	public GameServices() {
		this.gameDAO = new GameDAO();
		this.gameCategoryDAO = new GameCategoryDAO();
		this.gameEditorDAO = new GameEditorDAO();
		this.itemDAO = new ItemDAO();
		this.borrowDAO = new BorrowDAO();
		this.bookDAO = new BookDAO();
		this.parametersServices = new ParametersServices();
	}

	/**
	 * Trouve un jeu existant
	 * 
	 * @param gameID
	 *            L'identifiant du jeu
	 * @return Un objet de type Game si le jeu a bien été trouvé, sinon null
	 */
	public Game getGame(int gameID) {
		return this.gameDAO.get(gameID);
	}

	/**
	 * Liste les jeux existant à l'aide d'un filtre
	 * 
	 * @param filter
	 *            Ensemble de couples <clef(String), valeur(String)> où la clef
	 *            est incluse dans la liste : name, publishing_year, nb_players,
	 *            minimum_age, category, editor.
	 * @return La liste des jeux respectant les conditions
	 */
	public List<Game> getGames(HashMap<String, String> filter) {
		List<Game> games = this.gameDAO.getAll(filter);
		if (filter.containsKey("is_available") && Boolean.parseBoolean(filter.get("is_available"))) {
			List<Game> availableGames = new ArrayList<Game>();
			for (Game game : games) {
				if (this.isAvailableNow(game.getGameID())) {
					availableGames.add(game);
				}
			}
			return availableGames;
		} else {
			return games;
		}
	}

	/**
	 * Liste toutes les catégories de jeu existantes
	 * 
	 * @param sorted
	 *            Vrai si la liste doit être triée par ordre alphabétique
	 * @return La liste des catégories de jeu existantes
	 */
	public List<String> getGameCategories(boolean sorted) {
		return this.gameCategoryDAO.list(sorted);
	}

	/**
	 * Liste tous les éditeurs de jeu existants
	 * 
	 * @param sorted
	 *            Vrai si la liste doit être triée par ordre alphabétique
	 * @return La liste des éditeurs de jeu existantes
	 */
	public List<String> getGameEditors(boolean sorted) {
		return this.gameEditorDAO.list(sorted);
	}

	/**
	 * Détermine si le jeu est disponible maintenant (s'il a au moins deux
	 * exemplaires disponibles : un en stock + un disponible pour le
	 * prêt/réservation)
	 * 
	 * @param gameId
	 *            L'identifiant du jeu à vérifier
	 * @return True si le jeu est disponible maintenant, False sinon
	 */
	public boolean isAvailableNow(int gameId) {
		Calendar calendar = Calendar.getInstance();
		Date startDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 7 * parametersServices.getDurationOfBorrowingsInWeeks());
		Date endDate = calendar.getTime();
		return (getOneAvailableItem(gameId, startDate, endDate) != null);
	}

	/**
	 * Trouve un exemplaire disponible pour un jeu sur une période donnée Il est
	 * nécessaire qu'il y est au moins 2 exemplaires disponibles : un qui reste
	 * en stock et un qui est peut être utilisé pour le prêt ou la réservation
	 * 
	 * @param gameId
	 *            L'identifiant du jeu pour lequelle il faut trouver un
	 *            exemplaire disponible
	 * @param startDate
	 *            La date de début de disponibilité
	 * @param endDate
	 *            La date de fin de disponibilité
	 * @return Un exemplaire disponible si trouvé, sinon null
	 */
	public Item getOneAvailableItem(int gameId, Date startDate, Date endDate) {
		int i = 0;
		List<Item> itemsOfGame = itemDAO.getAllHavingGameID(gameId);
		List<Item> availableItems = new ArrayList<Item>();
		while ((i < itemsOfGame.size()) && (availableItems.size() <= 1)) {
			if (!bookDAO.itemUsedDuringPeriod(itemsOfGame.get(i).getItemID(), startDate, endDate)
					&& (!borrowDAO.itemUsedDuringPeriod(itemsOfGame.get(i).getItemID(), startDate, endDate))) {
				availableItems.add(itemsOfGame.get(i));
			}
			i++;
		}
		if (availableItems.size() > 1) {
			return availableItems.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Détermine s'il est possible de diminuer le nombre d'exemplaire d'un jeu
	 * sans pour autant supprimer ceux utiliser dans des prêts ou réservations
	 * 
	 * @param gameID
	 *            Identifiant unique du jeu à vérifier
	 * @param newItemAmount
	 *            Nouvelle quantité voulue d'exemplaires
	 * @return True s'il est possible de diminuer la quantité d'exemplaires,
	 *         False sinon
	 */
	public boolean canDecreaseItemAmount(int gameID, int newItemAmount) {
		List<Item> itemsOfGame = itemDAO.getAllHavingGameID(gameID);
		List<Item> availableItems = new ArrayList<Item>();
		for (Item item : itemsOfGame) {
			if (!bookDAO.itemUsed(item.getItemID()) && (!borrowDAO.itemUsed(item.getItemID()))) {
				availableItems.add(item);
			}
		}
		if (newItemAmount < itemsOfGame.size()) {
			return (availableItems.size() - (itemsOfGame.size() - newItemAmount) >= 1);
		} else {
			return true;
		}
	}

	/**
	 * Détermine s'il est possible de supprimer une extension d'un jeu en
	 * vérifiant si elle ne fait pas partie d'un prêt ou d'une réservation
	 * 
	 * @param extensionID
	 *            Identifiant unique de l'extension à vérifier
	 * @return True s'il est possible de supprimer l'extension, False sinon
	 */
	public boolean canRemoveExtension(int extensionID) {
		return (!bookDAO.extensionUsed(extensionID) && !borrowDAO.extensionUsed(extensionID));
	}

	/**
	 * Créé un nouveau jeu
	 * 
	 * @param name
	 *            Le nom du jeu
	 * @param description
	 *            La description du jeu
	 * @param publishingYear
	 *            L'année d'édition du jeu
	 * @param minimumAge
	 *            L'age minimum recommandé pour y jouer
	 * @param minimumPlayers
	 *            Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers
	 *            Le nombre maximum de joueurs necessaires
	 * @param category
	 *            La catégorie du jeu (jeu de cartes, jeu de dés, ...)
	 * @param editor
	 *            L'éditeur du jeu
	 * @return Un objet de type Game si le jeu a bien été ajouté, sinon null
	 */
	public Game addGame(String name, String description, int publishingYear, int minimumAge, int minimumPlayers,
			int maximumPlayers, String category, String editor) {
		Game game = new Game(name, description, publishingYear, minimumAge, minimumPlayers, maximumPlayers, category,
				editor);
		int categoryID = this.gameCategoryDAO.getID(category);
		int editorID = this.gameEditorDAO.getID(editor);
		return this.gameDAO.add(game, categoryID, editorID) ? game : null;
	}

	/**
	 * Modifie un jeu déjà existant
	 * 
	 * @param gameID
	 *            L'identifiant du jeu
	 * @param name
	 *            Le nom du jeu
	 * @param description
	 *            La description du jeu
	 * @param publishingYear
	 *            L'année d'édition du jeu
	 * @param minimumAge
	 *            L'age minimum recommandé pour y jouer
	 * @param minimumPlayers
	 *            Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers
	 *            Le nombre maximum de joueurs necessaires
	 * @param category
	 *            La catégorie du jeu (jeu de cartes, jeu de dés, ...)
	 * @param editor
	 *            L'éditeur du jeu
	 * @return Un objet de type Game si le jeu a bien été modifié, sinon null
	 */
	public Game editGame(int gameID, String name, String description, int publishingYear, int minimumAge,
			int minimumPlayers, int maximumPlayers, String category, String editor) {
		Game game = new Game(gameID, name, description, publishingYear, minimumAge, minimumPlayers, maximumPlayers,
				category, editor);
		int categoryID = this.gameCategoryDAO.getID(category);
		int editorID = this.gameEditorDAO.getID(editor);
		return this.gameDAO.edit(game, categoryID, editorID) ? game : null;
	}

	/**
	 * Supprime un jeu déjà existant
	 * 
	 * @param gameID
	 *            L'identifiant du jeu
	 * @return True si le jeu a bien été supprimé, sinon False
	 */
	public boolean removeGame(int gameID) {
		return this.gameDAO.remove(gameID);
	}

}