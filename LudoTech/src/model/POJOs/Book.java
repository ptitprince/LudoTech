package model.POJOs;
import java.util.Date;


/**Representation de reservation *
 */


public class Book {
	
	/** Identifiant de la reservation **/
	
	int IdBook;
	/**Date de debut de reservation *
	 */
	private static	Date StartDate; 
	/**Date de fin de reservation *
	 */
	private static	Date EndDate;
	
	/**Membre qui reserve *
	 */
	private	Member Member;
	/** L'exemplaire a reserver
	 *  */
	private	Item item;
	/** L'extensiona a reserver
	 * 
	 */
	private	Extension Extension;
	
	public Book(int idBook, Date startDate, Date endDate, model.POJOs.Member member, Item item,
			model.POJOs.Extension extension) {
		super();
		this.IdBook = idBook;
		this.StartDate = startDate;
		this.EndDate = endDate;
		this.Member = member;
		this.item = item;
		this.Extension = extension;
	}
	/**
	 * @return the startDate
	 */
	public static Date getStartDate() {
		return StartDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public static Date getEndDate() {
		return EndDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	/**
	 * @return the member
	 */
	public Member getMember() {
		return Member;
	}
	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		Member = member;
	}
	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	/**
	 * @return the extension
	 */
	public Extension getExtension() {
		return Extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(Extension extension) {
		Extension = extension;
	}
	/**
	 * @return the idBook
	 */
	public int getIdBook() {
		return IdBook;
	}
	/**
	 * @param idBook the idBook to set
	 */
	public void setIdBook(int idBook) {
		IdBook = idBook;
	} 
	
	

	

}


	
