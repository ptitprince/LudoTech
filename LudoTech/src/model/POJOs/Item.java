package model.POJOs;

public class Item {

	private int itemID;
	private String comments;
	
	public Item(int itemID, String comments) {
		this.itemID = itemID;
		this.comments = comments;
	}

	public Item() {
		// TODO Auto-generated constructor stub
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
