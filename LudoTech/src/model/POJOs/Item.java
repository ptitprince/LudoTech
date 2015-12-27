package model.POJOs;

/**
 * Représentation d'un exemplaire de jeu
 */
public class Item {

	/**
	 * L'identifiant unique de l'exemplaire, un nombre entier strictement
	 * positif
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private int itemID;

	/**
	 * Les informations diverses sur l'exemplaire (ex : son état, s'il manque des pièces)
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private String comments;

	/**
	 * Construit un exemplaire de jeu avec un identifiant connu
	 * @param itemID L'identifiant unique de l'exemplaire, un nombre entier strictement positif
	 * @param comments Les informations diverses sur l'exemplaire (ex : son état, s'il manque des pièces)
	 */
	public Item(int itemID, String comments) {
		this.itemID = itemID;
		this.comments = comments;
	}

	/**
	 * Construit un exemplaire de jeu sans données
	 */
	public Item() {
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
