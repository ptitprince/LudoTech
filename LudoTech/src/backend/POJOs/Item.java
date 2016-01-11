package backend.POJOs;

/**
 * Représentation d'un exemplaire de jeu
 */
public class Item {

	/**
	 * L'identifiant unique de l'exemplaire, un nombre entier strictement
	 * positif
	 */
	private int itemID;

	/**
	 * Construit un exemplaire de jeu avec un identifiant connu
	 * 
	 * @param itemID
	 *            L'identifiant unique de l'exemplaire, un nombre entier
	 *            strictement positif
	 */
	public Item(int itemID) {
		this.itemID = itemID;
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

}
