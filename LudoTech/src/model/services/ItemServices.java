package model.services;

import java.util.List;

import model.DAOs.ItemDAO;
import model.POJOs.Item;

public class ItemServices {

	private ItemDAO itemDAO;

	public ItemServices() {
		this.itemDAO = new ItemDAO();
	}

	public int countItemsOfGame(int gameID) {
		return this.itemDAO.getAllHavingGameID(gameID).size();
	}

	public boolean setItemsNumberOfGame(int gameID, int nbItems) {
		boolean noProblem = true;
		int currentNbItems = this.countItemsOfGame(gameID);
		while (currentNbItems < nbItems) {
			noProblem &= this.increaseItemsNumberOfGame(gameID);
			currentNbItems++;
		}
		while (currentNbItems > nbItems) {
			noProblem &= this.decreaseItemsNumberOfGame(gameID);
			currentNbItems--;
		}
		return noProblem;
	}
	
	private boolean increaseItemsNumberOfGame(int gameID) {
		return this.itemDAO.add(new Item(), gameID);
	}

	private boolean decreaseItemsNumberOfGame(int gameID) {
		List<Item> items = this.itemDAO.getAllHavingGameID(gameID);
		if (items.size() > 0) {
			int lastItemID = items.get(items.size() - 1).getItemID();
			return this.itemDAO.remove(lastItemID);
		} else {
			return false;
		}
	}
}
