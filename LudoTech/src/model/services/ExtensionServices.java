package model.services;

import java.util.List;

import model.DAOs.ExtensionDAO;
import model.POJOs.Extension;

/**
 * Propose des fonctionnalités de traitement sur des extensions de jeu
 */
public class ExtensionServices {

	/**
	 * Objet d'accés aux données de type Extension (extensions de jeux)
	 */
	private ExtensionDAO extensionDAO;

	/**
	 * Construit un nouveau service pour les extensions de jeux
	 */
	public ExtensionServices() {
		this.extensionDAO = new ExtensionDAO();
	}

	/**
	 * Ajoute une extension à un jeu
	 * 
	 * @param name
	 *            Le nom de la nouvelle extension
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @return L'extension ajoutée au jeu si cela a réussit, sinon null
	 */
	public Extension addExtension(String name, int gameID) {
		Extension extension = new Extension(name);
		if (this.extensionDAO.add(extension, gameID)) {
			return extension;
		} else {
			return null;
		}
	}

	/**
	 * Supprime une extension
	 * 
	 * @param extensionID
	 *            L'identifiant d'une extension de jeu existante
	 * @return True si l'extension a bien été supprimée, sinon False
	 */
	public boolean deleteExtension(int extensionID) {
		return this.extensionDAO.remove(extensionID);
	}
	
	/**
	 * Compte le nombre d'extensions que possède un jeu
	 * 
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @return Le nombre d'extensions du jeu
	 */
	public int countExtensions(int gameID) {
		return this.extensionDAO.getAll(gameID).size();
	}

	/**
	 * Liste toutes les extensions possédées par un jeu
	 * 
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @return La liste des extenstion d'un jeu
	 */
	public List<Extension> getExtensions(int gameID) {
		return this.extensionDAO.getAll(gameID);
	}

	
}
