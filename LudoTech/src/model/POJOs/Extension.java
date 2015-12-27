package model.POJOs;

public class Extension {
/** Identifiant de l'extension **/
	
	private int extensionID;
	
	/**Nom de l'extension *
	 * 
	 */
	private String name;
	/**Construire l'extension 
	 * 
	 * @param extensionID
	 * @param name
	 */
	public Extension(int extensionID, String name) {
		this.extensionID = extensionID;
		this.name = name;
	}
	

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
	
}
