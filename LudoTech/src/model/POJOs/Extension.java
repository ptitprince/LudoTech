package model.POJOs;

public class Extension {

	private int extensionID;
	private String name;
	
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
