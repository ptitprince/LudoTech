package model.pojos;

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
	 * Ann�e d'�dition du jeu
	 */
	private int publishingYear;
	
	/**
	 * Age minimum recommand� pour ce jeu
	 */
	private int minimumAge;

	/**
	 * Nombre minimum de joueurs recommand� pour ce jeu
	 */
	private int minimumPlayers;
	
	/**
	 * Nombre maximum de joueurs recommand� pour ce jeu
	 */
	private int maximumPlayers;
	
	/**
	 * La cat�gorie du jeu
	 */
	private String category;

	/**
	 * L'�diteur du jeu
	 */
	private String editor;

	/**
	 * Construit et retourne un nouveau jeu avec un identifiant connu
	 * @param gameID L'identifiant du jeu
	 * @param name Le nom du jeu
	 * @param description La description du jeu
	 * @param publishingYear L'ann�e d'�dition
	 * @param minimumAge L'age minimum recommand� pour jouer
	 * @param minimumPlayers Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers Le nombre maximum de joueurs necessaires
	 * @param category La cat�gorie du jeu (jeu de cartes, jeu de d�s)
	 * @param editor L'�diteur du jeu
	 */
	public Game(int gameID, String name, String description, int publishingYear, int minimumAge, int minimumPlayers,
			int maximumPlayers, String category, String editor) {
		this.gameID = gameID;
		this.name = name;
		this.description = description;
		this.publishingYear = publishingYear;
		this.minimumAge = minimumAge;
		this.minimumPlayers = minimumPlayers;
		this.maximumPlayers = maximumPlayers;
		this.category = category;
		this.editor = editor;
	}

	/**
	 * Construit et retourne un nouveau jeu avec un identifiant inconnu
	 * @param name Le nom du jeu
	 * @param description La description du jeu
	 * @param publishingYear L'ann�e d'�dition
	 * @param minimumAge L'age minimum recommand� pour jouer
	 * @param minimumPlayers Le nombre minimum de joueurs necessaires
	 * @param maximumPlayers Le nombre maximum de joueurs necessaires
	 * @param category La cat�gorie du jeu (jeu de cartes, jeu de d�s)
	 * @param editor L'�diteur du jeu
	 */
	public Game(String name, String description, int publishingYear, int minimumAge, int minimumPlayers,
			int maximumPlayers, String category, String editor) {
		this.name = name;
		this.description = description;
		this.publishingYear = publishingYear;
		this.minimumAge = minimumAge;
		this.minimumPlayers = minimumPlayers;
		this.maximumPlayers = maximumPlayers;
		this.category = category;
		this.editor = editor;
	}

	public int getGameID() {
		return this.gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPublishingYear() {
		return this.publishingYear;
	}

	public void setPublishingYear(int publishingYear) {
		this.publishingYear = publishingYear;
	}

	public int getMinimumAge() {
		return this.minimumAge;
	}

	public void setMinimumAge(int minimumAge) {
		this.minimumAge = minimumAge;
	}

	public int getMinimumPlayers() {
		return this.minimumPlayers;
	}

	public void setMinimumPlayers(int minimumPlayers) {
		this.minimumPlayers = minimumPlayers;
	}

	public int getMaximumPlayers() {
		return this.maximumPlayers;
	}

	public void setMaximumPlayers(int maximumPlayers) {
		this.maximumPlayers = maximumPlayers;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

}