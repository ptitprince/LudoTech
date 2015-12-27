package model.services;

import java.util.HashMap;
import java.util.List;

import model.DAOs.GameCategoryDAO;
import model.DAOs.GameDAO;
import model.DAOs.GameEditorDAO;
import model.POJOs.Game;

/**
 * Propose des fonctionnalités de traitement sur des jeux
 */
public class GameServices {

	/**
	 * Objet d'accés aux données de type Game (jeux)
	 */
	private final GameDAO gameDAO;

	/**
	 * Objet d'accés aux données de type GameCategory (catégories de jeux)
	 */
	private final GameCategoryDAO gameCategoryDAO;

	/**
	 * Objet d'accés aux données de type GameEditor (éditeur de jeux)
	 */
	private final GameEditorDAO gameEditorDAO;

	/**
	 * Construit un nouveau service pour les jeux
	 */
	public GameServices() {
		this.gameDAO = new GameDAO();
		this.gameCategoryDAO = new GameCategoryDAO();
		this.gameEditorDAO = new GameEditorDAO();
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
	 *            Un filtre indiquant quelles conditions doivent respecter les jeux,
	 *            où chaque condition est au format texte et de la forme
	 *            <nomCondition, valeurARespecter> parmis : 
	 *            - name (Nom du jeu, accepte une partie du nom du nom du jeu)
	 *            - publishing_year (Année d'édition, accepte la valeur exacte du jeu)
	 *            - nb_players (Nombre de joueurs, accepte une valeur comprise entre les deux bornes incluses du jeu)
	 *            - minimum_age (Age minimum, accepte une valeur égale ou supérieure à celle du jeu)
	 *            - category (Catégorie du jeu, accepte la valeur exacte du jeu)
	 *            - editor (Editeur du jeu, accepte la valeur exacte du jeu)
	 * @return La liste des jeux respectant les conditions
	 */
	public List<Game> getGames(HashMap<String, String> filter) {
		return this.gameDAO.getGames(filter);
	}

	/**
	 * Liste toutes les catégories de jeu existantes
	 * @param sorted Vrai si la liste doit être triée par ordre alphabétique
	 * @return La liste des catégories de jeu existantes
	 */
	public List<String> getGameCategories(boolean sorted) {
		return this.gameCategoryDAO.list(sorted);
	}

	/**
	 * Liste tous les éditeurs de jeu existants
	 * @param sorted Vrai si la liste doit être triée par ordre alphabétique
	 * @return La liste des éditeurs de jeu existantes
	 */
	public List<String> getGameEditors(boolean sorted) {
		return this.gameEditorDAO.list(sorted);
	}

}