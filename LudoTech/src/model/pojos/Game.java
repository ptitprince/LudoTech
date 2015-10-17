package model.pojos;

import model.enums.GameCategory;

/**
 * Repr�sentation d'un jeu (conforme � la table Game)
 * 
 * @author Th�o Gauchoux
 *
 */
public class Game {

	/**
	 * L'identifiant du jeu
	 */
	private int gameID;

	/**
	 * Le nom du jeu
	 */
	private String name;

	/**
	 * La description du jeu
	 */
	private String description;

	/**
	 * La cat�gorie du jeu
	 */
	private GameCategory category;

	/**
	 * L'�diteur du jeu (l'entreprise le vendant)
	 */
	private String editor;

	/**
	 * L'auteur du jeu (la personne l'ayant cr��)
	 */
	private String author;

	/**
	 * Ann�e d'�dition du jeu
	 */
	private int publishingYear;

	/**
	 * Nombre de joueurs recommand� pour ce jeu
	 */
	private int nbPlayers;

	/**
	 * Constructeur d'un nouveau jeu sans conna�tre son identifiant (utilis�
	 * lorsque le jeu n'existe pas encore en BDD)
	 * 
	 * @param name
	 *            Le nom du nouveau jeu
	 * @param description
	 *            La description du nouveau jeu
	 * @param category
	 *            La cat�gorie du nouveau jeu
	 * @param editor
	 *            L'�diteur du nouveau jeu
	 * @param author
	 *            L'auteur du nouveau jeu
	 * @param publishingYear
	 *            L'ann�e d'�dition du nouveau jeu
	 * @param nbPlayers
	 *            Le nombre de joueurs recommand� du nouveau jeu
	 */
	public Game(String name, String description, GameCategory category, String editor, String author,
			int publishingYear, int nbPlayers) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.editor = editor;
		this.author = author;
		this.publishingYear = publishingYear;
		this.nbPlayers = nbPlayers;
	}

	/**
	 * Constructeur d'un jeu en connaissant son identifiant (utilis� lorsque le
	 * jeu existe d�j� en BDD)
	 * 
	 * @param gameID
	 *            L'identifiant du jeu
	 * @param name
	 *            Le nom du jeu
	 * @param description
	 *            La description du jeu
	 * @param category
	 *            La cat�gorie du jeu
	 * @param editor
	 *            L'�diteur du jeu
	 * @param author
	 *            L'auteur du jeu
	 * @param publishingYear
	 *            L'ann�e d'�dition du jeu
	 * @param nbPlayers
	 *            Le nombre de joueurs recommand� du jeu
	 */
	public Game(int gameID, String name, String description, GameCategory category, String editor, String author,
			int publishingYear, int nbPlayers) {
		this.gameID = gameID;
		this.name = name;
		this.description = description;
		this.category = category;
		this.editor = editor;
		this.author = author;
		this.publishingYear = publishingYear;
		this.nbPlayers = nbPlayers;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GameCategory getCategory() {
		return category;
	}

	public void setCategory(GameCategory category) {
		this.category = category;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPublishingYear() {
		return publishingYear;
	}

	public void setPublishingYear(int publishingYear) {
		this.publishingYear = publishingYear;
	}

	public int getNbPlayers() {
		return nbPlayers;
	}

	public void setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}

}