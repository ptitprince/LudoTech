package model.services;

import java.util.List;

import model.DAOs.ExtensionDAO;
import model.POJOs.Extension;

public class ExtensionServices {
	
	private ExtensionDAO extensionDAO;
	
	public ExtensionServices() {
		this.extensionDAO = new ExtensionDAO();
	}
	
	public int countExtensionOfGame(int gameID) {
		return this.extensionDAO.getAllHavingGameID(gameID).size();
	}

	public Extension addExtensionToGame(String name, int gameID) {
		Extension extension = new Extension(name);
		boolean result = this.extensionDAO.add(extension, gameID);
		if (result) {
			return extension;
		} else {
			return null;
		}
	}

	public boolean deleteExtensionFromGame(int extensionID) {
		return this.extensionDAO.remove(extensionID);
	}
	
	public List<Extension> getExtensionList(int gameID) {
		return this.extensionDAO.getAllHavingGameID(gameID);
	}

}
