package model.services;

import model.DAOs.GameCategoryDAO;
import model.DAOs.GameDAO;
import model.DAOs.GameEditorDAO;
import model.pojos.Game;

/**
 * Expose des services de traitement sur des jeux
 * 
 * @author Th�o Gauchoux
 *
 */
public class GameServices {

	/**
	 * Objet d'acc�s aux donn�es de type Game
	 */
	private final GameDAO gameDAO;
	
	/**
	 * Objet d'acc�s aux donn�es de type GameCategory
	 */
	private final GameCategoryDAO gameCategoryDAO;
	
	/**
	 * Objet d'acc�s aux donn�es de type GameEditor
	 */
	private final GameEditorDAO gameEditorDAO;

	public GameServices() {
		this.gameDAO = new GameDAO();
		this.gameCategoryDAO = new GameCategoryDAO();
		this.gameEditorDAO = new GameEditorDAO();
	}

	/**
	 * Cr�ation et ajout en base de donn�es d'un nouveau jeu
	 * 
	 * @param name Le nom du jeu
	 * @param description La description du jeu
	 * @param publishingYear L'ann�e d'�dition
	 * @param minimumAge L'age minimum recommand� pour jouer
	 * @param minimumPlayers Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers Le nombre maximum de joueurs necessaires
	 * @param category La cat�gorie du jeu (jeu de cartes, jeu de d�s)
	 * @param editor L'�diteur du jeu
	 * @return Un objet de type Game s'il le jeu a pu �tre ajout� � la base de
	 *         donn�es, sinon Null
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
	 * @param publishingYear L'ann�e d'�dition
	 * @param minimumAge L'age minimum recommand� pour jouer
	 * @param minimumPlayers Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers Le nombre maximum de joueurs necessaires
	 * @param category La cat�gorie du jeu (jeu de cartes, jeu de d�s)
	 * @param editor L'�diteur du jeu
	 * @return Un objet de type Game s'il le jeu a pu �tre ajout� � la base de
	 *         donn�es, sinon Null
	 */
	public Game editGame(int id, String name, String description, int publishingYear, int minimumAge, int minimumPlayers, int maximumPlayers, String category, String editor) {
		Game game = new Game(id, name, description, publishingYear, minimumAge, minimumPlayers, maximumPlayers, category, editor);
		int categoryID = this.gameCategoryDAO.getID(category);
		int editorID = this.gameEditorDAO.getID(editor);
		return this.gameDAO.edit(game, categoryID, editorID) ? game : null;
	}
	
	/**
	 * Suppression d'un jeu existant
	 * 
	 * @param id L'identifiant du jeu
	 * @param name Le nom du jeu
	 * @param description La description du jeu
	 * @param publishingYear L'ann�e d'�dition
	 * @param minimumAge L'age minimum recommand� pour jouer
	 * @param minimumPlayers Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers Le nombre maximum de joueurs necessaires
	 * @return Un objet de type null s'il le jeu a pu �tre supprim� de la base de
	 *         donn�es, sinon Game qui �tait en param�tre
	 */
	public Game removeGame(int id, String name, String description, int publishingYear, int minimumAge, int minimumPlayers, int maximumPlayers, String category, String editor) {
		Game game = new Game(id, name, description, publishingYear, minimumAge, minimumPlayers, maximumPlayers, category, editor);
		
		return this.gameDAO.remove(game) ? null : game;
	}

}
