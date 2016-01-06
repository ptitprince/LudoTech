package model.services;
import java.util.List;

import model.DAOs.BookDAO;
import model.POJOs.*;


public class BookServices {

	private BookDAO bookDAO;
	
	public BookServices() {
		this.bookDAO = new BookDAO();
	}
	
	public List<Book> getAll() {
		return this.bookDAO.getBooks();
	}
	
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
//		Book book = new Book(Member, Item, extensionID, StartDate,EndDate);
//		if (this.bookDAO.add(book, Item.getItemID(),Extension.getExtensionID(),Member.getMemberID())) {
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
//	public Book editBook( Member member,Item item,Extension extension, Date StartDate, Date EndDate)	{
//		Book book = new Book( member,item, extension, StartDate, EndDate);
//		return this.bookDAO.edit(book.getItem().getItemID(), book.getMember().getMemberID(),book.getExtension().getExtensionID(), StartDate, EndDate) ? book : null;
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
//		Book book = new Book(member,
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
}
