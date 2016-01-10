package backend.POJOs;

/**
 * Représentation d'un jeu (informations communes pour tous les exemplaires de
 * ce jeu)
 */
public class Game {

	/**
	 * L'identifiant unique du jeu, un nombre entier strictement positif
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private int gameID;

	/**
	 * Le nom du jeu
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private String name;

	/**
	 * La description du jeu
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private String description;

	/**
	 * L'année d'édition du jeu, un nombre entier positif ou nul
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private int publishingYear;

	/**
	 * L'âge minimum recommandé pour y jouer, un nombre entier positif ou nul
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private int minimumAge;

	/**
	 * Le nombre minimum de joueurs pour y jouer, un nombre entier positif ou
	 * nul
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private int minimumPlayers;

	/**
	 * Le nombre maximum de joueurs pour y jouer, un nombre entier positif ou
	 * nul
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private int maximumPlayers;

	/**
	 * La catégorie du jeu
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private String category;

	/**
	 * La société éditrice du jeu
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private String editor;

	/**
	 * Construit un jeu avec un identifiant connu
	 * 
	 * @param gameID
	 *            L'identifiant unique du jeu, un nombre entier strictement positif
	 * @param name
	 *            Le nom du jeu
	 * @param description
	 *            La description du jeu
	 * @param publishingYear
	 *            L'année d'édition du jeu, un nombre entier positif ou nul
	 * @param minimumAge
	 *            L'age minimum recommandé pour y jouer, un nombre entier positif ou nul
	 * @param minimumPlayers
	 *            Le nombre minimum de joueurs pour y jouer, un nombre entier positif ou nul
	 * @param maximumPlayers
	 *            Le nombre maximum de joueurs pour y jouer, un nombre entier positif ou nul
	 * @param category
	 *            La catégorie du jeu
	 * @param editor
	 *            La société éditrice du jeu
	 */
	public Game(int gameID, String name, String description, int publishingYear, int minimumAge, int minimumPlayers,
			int maximumPlayers, String category, String editor) {
		this(name, description, publishingYear, minimumAge, minimumPlayers, maximumPlayers, category, editor);
		this.gameID = gameID;
	}

	/**
	 * Construit un jeu sans identifiant
	 * 
	 * @param name
	 *            Le nom du jeu
	 * @param description
	 *            La description du jeu
	 * @param publishingYear
	 *            L'année d'édition du jeu, un nombre entier positif ou nul
	 * @param minimumAge
	 *            L'age minimum recommandé pour y jouer, un nombre entier positif ou nul
	 * @param minimumPlayers
	 *            Le nombre minimum de joueurs pour y jouer, un nombre entier positif ou nul
	 * @param maximumPlayers
	 *            Le nombre maximum de joueurs pour y jouer, un nombre entier positif ou nul
	 * @param category
	 *            La catégorie du jeu
	 * @param editor
	 *            La société éditrice du jeu
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
	
	public String toString() {
		return this.name;
	}
}