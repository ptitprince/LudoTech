package model.services;

import java.util.HashMap;
import java.util.List;

import model.DAOs.GameCategoryDAO;
import model.DAOs.GameDAO;
import model.DAOs.GameEditorDAO;
import model.POJOs.Game;

/**
 * Expose des services de traitement sur des jeux
 */
public class GameServices {

	/**
	 * Objet d'accés aux données de type Game
	 */
	private final GameDAO gameDAO;
	
	/**
	 * Objet d'accés aux données de type GameCategory
	 */
	private final GameCategoryDAO gameCategoryDAO;
	
	/**
	 * Objet d'accés aux données de type GameEditor
	 */
	private final GameEditorDAO gameEditorDAO;

	public GameServices() {
		this.gameDAO = new GameDAO();
		this.gameCategoryDAO = new GameCategoryDAO();
		this.gameEditorDAO = new GameEditorDAO();
	}

	/**
	 * Création et ajout en base de données d'un nouveau jeu
	 * @param name Le nom du jeu
	 * @param description La description du jeu
	 * @param publishingYear L'année d'édition
	 * @param minimumAge L'age minimum recommandé pour jouer
	 * @param minimumPlayers Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers Le nombre maximum de joueurs necessaires
	 * @param category La catégorie du jeu (jeu de cartes, jeu de dés)
	 * @param editor L'éditeur du jeu
	 * @return Un objet de type Game s'il le jeu a pu être ajouté à la base de
	 *         données, sinon Null
	 */
	public Game addGame(String name, String description, int publishingYear, int minimumAge, int minimumPlayers, int maximumPlayers, String category, String editor) {
		Game game = new Game(name, description, publishingYear, minimumAge, minimumPlayers, maximumPlayers, category, editor);
		int categoryID = this.gameCategoryDAO.getID(category);
		int editorID = this.gameEditorDAO.getID(editor);
		return this.gameDAO.add(game, categoryID, editorID) ? game : null;
	}

	/**
	 * Modification d'un jeu existant
	 * 
	 * @param id L'identifiant du jeu
	 * @param name Le nom du jeu
	 * @param description La description du jeu
	 * @param publishingYear L'année d'édition
	 * @param minimumAge L'age minimum recommandé pour jouer
	 * @param minimumPlayers Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers Le nombre maximum de joueurs necessaires
	 * @param category La catégorie du jeu (jeu de cartes, jeu de dés)
	 * @param editor L'éditeur du jeu
	 * @return Un objet de type Game s'il le jeu a pu être modifié dans la base de
	 *         données, sinon Null
	 */
	public Game editGame(int id, String name, String description, int publishingYear, int minimumAge, int minimumPlayers, int maximumPlayers, String category, String editor) {
		Game game = new Game(id, name, description, publishingYear, minimumAge, minimumPlayers, maximumPlayers, category, editor);
		int categoryID = this.gameCategoryDAO.getID(category);
		int editorID = this.gameEditorDAO.getID(editor);
		return this.gameDAO.edit(game, categoryID, editorID) ? game : null;
	}
	
	/**
	 * Suppression d'un jeu existant
	 * @param id L'identifiant du jeu
	 * @return True si le jeu a pu être supprimé de la base de données, sinon False
	 */
	public boolean removeGame(int id) {
		return this.gameDAO.remove(id);
	}
	
	/**
	 * Trouve un jeu existant
	 * @param gameID L'identifiant du jeu
	 * @return Un objet de type game ou null s'il n'existe pas en base de données
	 */
	public Game getGame(int gameID) {
		return this.gameDAO.get(gameID);
	}
	
	public List<Game> getGames(HashMap<String, String> filter) {
		return this.gameDAO.getGames(filter);
	}
	
	public List<String> getGameCategories(boolean sorted) {
		return this.gameCategoryDAO.list(sorted);
	}
	
	public List<String> getGameEditors(boolean sorted) {
		return this.gameEditorDAO.list(sorted);
	}

}