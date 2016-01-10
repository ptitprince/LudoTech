package model.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.DAOs.BookDAO;
import model.DAOs.BorrowDAO;
import model.DAOs.ExtensionDAO;
import model.DAOs.ItemDAO;
import model.DAOs.MemberDAO;
import model.POJOs.Book;
import model.POJOs.Borrow;
import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Item;
import model.POJOs.Member;

public class BookServices {
	private final BookDAO bookDAO;
	private final BorrowDAO borrowDAO;
	private final ItemDAO itemDAO;
	private final MemberDAO memberDAO;
	private final ExtensionDAO extensionDAO;
	private final ParametersServices parametersServices;
	private final GameServices gamesServices;
	private final BorrowServices borrowServices;

	private final MemberContextServices memberContextServices;

	public BookServices() {
		this.gamesServices = new GameServices();
		this.borrowDAO = new BorrowDAO();
		this.memberDAO = new MemberDAO();
		this.extensionDAO = new ExtensionDAO();
		this.bookDAO = new BookDAO();
		this.itemDAO = new ItemDAO();
		this.parametersServices = new ParametersServices();
		this.memberContextServices = new MemberContextServices();
		this.borrowServices = new BorrowServices();
	}

	public List<Book> getAll() {
		return this.bookDAO.getBooks(-1);
	}

	public List<Book> getAllAccordingToUserID(int userID) {
		return this.bookDAO.getBooks(userID);
	}

	public Book addBook(Game game, Member member, Date beginningDate, Date endingDate, Extension extension) {
		boolean canAddBook = true;
		canAddBook &= (member.getMemberContext().canBook());
		canAddBook &= (canAddBook) && (bookDAO.getBooksNb(member.getMemberID()) < parametersServices.getNumberOfBookings());
		canAddBook &= (canAddBook) && (beginningDate.after(getTodayDateWithoutTime()) && beginningDate.before(endingDate));
		canAddBook &= (canAddBook) && (checkIntervalBetweenNowAndStartDateIsValid(beginningDate));
		canAddBook &= (canAddBook) && (checkIntervalBetweenStartDateAndEndDateIsValid(beginningDate, endingDate));
		Item potentialItem = gamesServices.getOneAvailableItem(game.getGameID(), beginningDate, endingDate);
		canAddBook &= (canAddBook) && (potentialItem != null);
		if (extension != null) {
			canAddBook &= (canAddBook) 
				&& (!bookDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate))
				&& (!borrowDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate));
		}
		if (canAddBook && this.bookDAO.get(potentialItem.getItemID(), member.getMemberID(), beginningDate) == null) {
			Book acceptedBook = new Book(potentialItem, member, beginningDate, endingDate, extension);
			this.bookDAO.add(acceptedBook);
			return acceptedBook;
		} else {
			return null;
		}
	}	

	public boolean cancelBook(int itemID, int memberID, Date beginningDate) {
		memberContextServices.oneMoreNbFakeBooking(memberDAO.getMemberContextID(memberID));
		return bookDAO.remove(itemID, memberID, beginningDate);
	}

	public boolean convertBookIntoBorrow(int itemID, int memberID, Date beginningDate, Date endingDate,
			Integer extensionID) {

		bookDAO.remove(itemID, memberID, beginningDate);
		
		Extension extension = null;
		if (extensionID != null) {
			extension = extensionDAO.get(extensionID);
		}
		
		Borrow createdBorrow = borrowServices.addBorrowKnowingItem(itemDAO.get(itemID), memberDAO.get(memberID), beginningDate, endingDate, extension);

		return (createdBorrow != null);
	}
	
	private boolean checkIntervalBetweenNowAndStartDateIsValid(Date startDate) {
		long diffValue = startDate.getTime() - new Date().getTime();
		long diffDays = TimeUnit.DAYS.convert(diffValue, TimeUnit.MILLISECONDS) + 1;
		int minIntervalBetweenNowAndStartDateInDays = 7*this.parametersServices.getDurationBetweenBookingAndBorrowingInWeeks();
		return (diffDays >= minIntervalBetweenNowAndStartDateInDays);
	}
	
	private boolean checkIntervalBetweenStartDateAndEndDateIsValid(Date startDate, Date endDate) {
		long diffValue = endDate.getTime() - startDate.getTime();
		long diffDays = TimeUnit.DAYS.convert(diffValue, TimeUnit.MILLISECONDS);
		int expectedIntervalBetweenStartDateAndEndDateInDays = 7*this.parametersServices.getDurationOfBorrowingsInWeeks();
		return (diffDays == expectedIntervalBetweenStartDateAndEndDateInDays);
	}
	
	private Date getTodayDateWithoutTime() {
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	
}
