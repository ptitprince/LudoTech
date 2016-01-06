package model.POJOs;
import java.util.Date;


/**Representation de reservation *
 */


public class Book {
	/**
	 * Identifiant de book
	 */
	int bookId;
	
	/** L'exemplaire a reserver
	 *  */
	private	Item item;
	
	/**Membre qui reserve *
	 */
	private	Member member;
	
	/**Date de debut de reservation *
	 */
	private	Date startDate; 
	/**Date de fin de reservation *
	 */
	private	Date endDate;
	
	/** L'extensiona a reserver
	 * 
	 */
	private	Extension extension;
	
	public Book(int bookID, Item item, Member member, Date startDate, Date endDate,
			Extension extension) {
		this.bookId = bookID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.member = member;
		this.item = item;
		this.extension = extension;
	}
	
	public Book(Item item, Member member, Date startDate, Date endDate,
			Extension extension) {		
		this.startDate = startDate;
		this.endDate = endDate;
		this.member = member;
		this.item = item;
		this.extension = extension;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookID(int bookId) {
		this.bookId = bookId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}	

}
