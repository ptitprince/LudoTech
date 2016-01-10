package backend.POJOs;

/**
 * Représentation d'une extension de jeu
 */
public class Extension {

	/**
	 * L'identifiant unique de l'extension, un nombre entier strictement positif
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private int extensionID;

	/**
	 * Le nom de l'extension
	 * 
	 * @HasGetter
	 * @HasSetter
	 */
	private String name;

	/**
	 * Construit une extension de jeu avec un identifiant connu
	 * 
	 * @param extensionID
	 *            L'identifiant unique de l'extension, un nombre entier
	 *            strictement positif
	 * @param name
	 *            Le nom de l'extension
	 */
	public Extension(int extensionID, String name) {
		this(name);
		this.extensionID = extensionID;
	}

	/**
	 * Construit une extension de jeu sans identifiant
	 * 
	 * @param name
	 *            Le nom de l'extension
	 */
	public Extension(String name) {
		this.name = name;
	}

	public int getExtensionID() {
		return extensionID;
	}

	public void setExtensionID(int extensionID) {
		this.extensionID = extensionID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}

}
