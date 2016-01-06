package model.services;
import java.sql.Date;

import java.util.List;
import model.DAOs.BookDAO;
import model.DAOs.ExtensionDAO;
import model.DAOs.ItemDAO;
import model.DAOs.MemberDAO;
import model.POJOs.*;
import model.DAOs.*;
public class BookServices {

private BookDAO bookDAO;

private ExtensionDAO extensionDAO;
private ItemDAO itemDAO;
//
//	
//	
	public BookServices() {
		this.bookDAO = new BookDAO();
		this.extensionDAO= new ExtensionDAO();
		this.itemDAO= new ItemDAO();
	}
	public List<Book> getAll() {
		return this.bookDAO.getBooks();
	}
	public List<String> getExtension(boolean sorted) {
		return this.extensionDAO.list(sorted);
	}

	public List<String> getItem(boolean sorted) {
		return this.itemDAO.list(sorted);
	}
//	
//	
//	
//	/**Ojet d'acces a book 
//	**/
//	private final BookDAO bookDAO;
//	/**Ojet d'acces a Member 
//	**/
//
//	private final MemberDAO memberDAO;
//	
//	public BookServices(BookDAO bookDAO, MemberDAO memberDAO) {
//		super();
//		this.bookDAO = bookDAO;
//		this.memberDAO = memberDAO;
//	}	
//	/** Ajouter une ligne de reservation 
//	 * 
//	 * @param Member
//	 * @param Item
//	 * @param Extension
//	 * @param StartDate
//	 * @param EndDate
//	 * @return La reservation
//	 */
//	public Book addBook(int memberID,int itemID, int extensionID, Date StartDate,Date EndDate) {
//		Item item;
//		Member member;
//		Date StartDate;
//		Date EndDate;
//		Extension extension;
//		Book book = new Book( item,member,StartDate ,EndDate,extension);
//		if (this.bookDAO.add(book.getItem().getItemID(), book.getMember().getMemberID(),book.getStartDate().getTime(), book.getEndDate().getTime(), book.getExtension().getExtensionID()) {
//			return book;
//		}
//			else {
//				return null;
//			}
//	}
//	
//	/**
//	 * 
//	 * @param Member le membre qui a reservé 
//	 * @param StartDate la date de debut de reservations
//	 * @return Supprime la reservation
//	 */
//	public boolean removeBook(Member Member, java.sql.Date StartDate)	{
//		return this.bookDAO.remove(Member.getMemberID(),StartDate);
//	}
//	/** Edit une table de reservation 
//	 * 
//	 * @param member 
//	 * @param item
//	 * @param extension
//	 * @param StartDate
//	 * @param EndDate
//	 * @return Changer la ligne d'une base de données
//	 */
//	
//	public Book editBook( int memberID,int itemID,int extensionID, Date StartDate, Date EndDate)	{
//		Book book = new Book( membeID,itemID, extensionID, StartDate, EndDate);
//		return this.bookDAO.edit(book.getItem().getItemID(), book.getMember().getMemberID(),book.getStartDate().getTime(), book.getEndDate().getTime(), book.getExtension().getExtensionID()) ? book : null;
//	}
//	public MemberDAO getMemberDAO() {
//		return memberDAO;
//	}
//
//	
//	/**
//	 * 
//	 * @param Member
//	 * @return Le nombre de jours avant 
//	 */
//	public long DaysBeforeBorrow(Member Member, Date StartDate)
//	
//	{
//		Date Today= new Date();
//		  long dureest= (StartDate.getTime()- Today.getTime())/86400000;
//		  return dureest;
//		  
//	}
//	
//	
//	
//	public boolean GetMyBooks(Member Member)
//	
//	{
//		Book book = new Book(member
//	}
//	
//	
//	
//	public boolean GetAllBooks()
//	{
//		
//	}
//	
//	public boolean FromBookToBorrow(Member member,Item item, Date StartDate)
//	{
//		
//		
//	}
//}
}