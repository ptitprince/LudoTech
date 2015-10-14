package model.pojos;

import model.enums.GameCategory;

/**
 * Repr�sentation d'un jeu (conforme � la table Game)
 * @author Th�o Gauchoux
 *
 */
public class Game {

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
