package backend.services;

import java.util.List;

import backend.DAOs.ItemDAO;
import backend.POJOs.Item;

/**
 * Propose des fonctionnalités de traitement sur des exemplaires de jeu
 */
public class ItemServices {

	/**
	 * Objet de manipulation de la base de données pour les exemplaires de jeux
	 */
	private ItemDAO itemDAO;

	/**
	 * Construit un nouveau service pour les exemplaires de jeux
	 */
	public ItemServices() {
		this.itemDAO = new ItemDAO();
	}

	/**
	 * Trouve et retourne le nom du jeu auquel est attaché l'exemplaire dont
	 * l'identifiant est passé en paramètre
	 * 
	 * @param itemID
	 *            L'identifiant d'un exemplaire de jeu existant
	 * @return Le nom du jeu de l'exemplaire si trouvé, sinon une chaine de
	 *         caractères vide
	 */
	public String getGameName(int itemID) {
		return this.itemDAO.getGameName(itemID);

	}

	/**
	 * Compte le nombre d'exemplaires que possède un jeu
	 * 
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @return Le nombre d'exemplaires du jeu
	 */
	public int countItemsOfGame(int gameID) {
		return this.itemDAO.getAllHavingGameID(gameID).size();
	}

	/**
	 * Définit le nombre d'exemplaires que doit avoir un jeu
	 * 
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @param nbItems
	 *            Le nombre d'exemplaires que le jeu doit avoir
	 * @return True si le nombre d'exemplaires du jeu a bien été modifié, sinon
	 *         False;
	 */
	public boolean setItemsNumberOfGame(int gameID, int nbItems) {
		boolean noProblem = true;
		int currentNbItems = this.countItemsOfGame(gameID);
		while (currentNbItems < nbItems) {
			noProblem &= this.addOneItem(gameID);
			currentNbItems++;
		}
		while (currentNbItems > nbItems) {
			noProblem &= this.deleteOneItem(gameID);
			currentNbItems--;
		}
		return noProblem;
	}

	/**
	 * Ajoute un nouvel exemplaire à un jeu
	 * 
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @return True si un exemplaire a bien été ajouté au jeu, sinon False
	 */
	private boolean addOneItem(int gameID) {
		return this.itemDAO.add(new Item(), gameID);
	}

	/**
	 * Supprime un exemplaire d'un jeu ayant au moins un exemplaire
	 * 
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @return True si un exemplaire a bien été supprimé du jeu, sinon False
	 */
	private boolean deleteOneItem(int gameID) {
		List<Item> items = this.itemDAO.getAllHavingGameID(gameID);
		if (items.size() > 0) {
			int lastItemID = items.get(items.size() - 1).getItemID();
			return this.itemDAO.remove(lastItemID);
		} else {
			return false;
		}
	}

}
