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
import model.exceptions.BookAlreadyExistException;
import model.exceptions.BorrowAlreadyExistException;
import model.exceptions.DatesOrderException;
import model.exceptions.ExtensionNotAvailableException;
import model.exceptions.IntervalBetweenNowAndStartDateException;
import model.exceptions.IntervalBetweenStartDateAndEndDateException;
import model.exceptions.MemberCantBookException;
import model.exceptions.MemberCantBorrowException;
import model.exceptions.MemberNbBooksException;
import model.exceptions.MemberNbBorrowsException;
import model.exceptions.NoneItemAvailableException;

public class BookServices {
	private final BookDAO bookDAO;
	private final BorrowDAO borrowDAO;
	private final ItemDAO itemDAO;
	private final MemberDAO memberDAO;
	private final ExtensionDAO extensionDAO;
	private final ParametersServices parametersServices;
	private final GameServices gameServices;
	private final BorrowServices borrowServices;

	private final MemberContextServices memberContextServices;

	public BookServices() {
		this.gameServices = new GameServices();
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

	public Book addBook(Game game, Member member, Date beginningDate, Date endingDate, Extension extension)
			throws MemberCantBookException, MemberNbBooksException, DatesOrderException,
			IntervalBetweenNowAndStartDateException, IntervalBetweenStartDateAndEndDateException,
			NoneItemAvailableException, ExtensionNotAvailableException, BookAlreadyExistException {
		checkMemberCanBook(member);
		checkMemberNbBooks(member);
		checkDatesOrder(beginningDate, endingDate);
		checkIntervalBetweenNowAndStartDateIsValid(beginningDate);
		checkIntervalBetweenStartDateAndEndDateIsValid(beginningDate, endingDate);
		Item potentialItem = checkAndGetAvailableItem(game, beginningDate, endingDate);
		if (potentialItem != null) {
			checkExtensionIsAvailable(extension, beginningDate, endingDate);
			checkBookNotAlreadyExist(potentialItem, member, beginningDate);
	
			Book acceptedBook = new Book(potentialItem, member, beginningDate, endingDate, extension);
			this.bookDAO.add(acceptedBook);
			return acceptedBook;
		} else {
			return null;
		}
	}
	
	private void checkMemberCanBook(Member member) throws MemberCantBookException {
		if (!member.getMemberContext().canBook()) {
			throw new MemberCantBookException(member.getFirstName() + " " + member.getLastName());
		}
	}

	private void checkMemberNbBooks(Member member) throws MemberNbBooksException {
		int memberNbBooks = bookDAO.getBooksNb(member.getMemberID());
		int maxNbBooks = parametersServices.getNumberOfBookings();
		if (memberNbBooks >= maxNbBooks) {
			throw new MemberNbBooksException(member.getFirstName() + " " + member.getLastName(), memberNbBooks,
					maxNbBooks);
		}
	}

	private void checkDatesOrder(Date beginningDate, Date endingDate) throws DatesOrderException {
		if (beginningDate.before(getTodayDateWithoutTime()) || beginningDate.after(endingDate)) {
			throw new DatesOrderException();
		}
	}

	private void checkIntervalBetweenNowAndStartDateIsValid(Date startDate)
			throws IntervalBetweenNowAndStartDateException {
		long diffValue = startDate.getTime() - new Date().getTime();
		long diffDays = TimeUnit.DAYS.convert(diffValue, TimeUnit.MILLISECONDS) + 1;
		int minIntervalBetweenNowAndStartDateInDays = 7
				* this.parametersServices.getDurationBetweenBookingAndBorrowingInWeeks();
		if (diffDays < minIntervalBetweenNowAndStartDateInDays) {
			throw new IntervalBetweenNowAndStartDateException(startDate, (int) diffDays,
					minIntervalBetweenNowAndStartDateInDays);
		}
	}

	private void checkIntervalBetweenStartDateAndEndDateIsValid(Date startDate, Date endDate)
			throws IntervalBetweenStartDateAndEndDateException {
		long diffValue = endDate.getTime() - startDate.getTime();
		long diffDays = TimeUnit.DAYS.convert(diffValue, TimeUnit.MILLISECONDS);
		int expectedIntervalBetweenStartDateAndEndDateInDays = 7
				* this.parametersServices.getDurationOfBorrowingsInWeeks();
		if (diffDays != expectedIntervalBetweenStartDateAndEndDateInDays) {
			throw new IntervalBetweenStartDateAndEndDateException(startDate, endDate, (int) diffDays,
					expectedIntervalBetweenStartDateAndEndDateInDays);
		}
	}
	
	private Item checkAndGetAvailableItem(Game game, Date beginningDate, Date endingDate)
			throws NoneItemAvailableException {
		Item potentialItem = gameServices.getOneAvailableItem(game.getGameID(), beginningDate, endingDate);
		if (potentialItem == null) {
			throw new NoneItemAvailableException(game.getName(), beginningDate, endingDate);
		}
		return potentialItem;
	}
	
	private void checkExtensionIsAvailable(Extension extension, Date beginningDate, Date endingDate)
			throws ExtensionNotAvailableException {
		if (extension != null) {
			if (bookDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate)
					|| borrowDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate)) {
				throw new ExtensionNotAvailableException(extension.getName());
			}
		}
	}

	private void checkBookNotAlreadyExist(Item potentialItem, Member member, Date beginningDate)
			throws BookAlreadyExistException {
		Book canBeExistingBook = this.bookDAO.get(potentialItem.getItemID(), member.getMemberID(), beginningDate);
		if (canBeExistingBook != null) {
			throw new BookAlreadyExistException(canBeExistingBook);
		}

	}

	public boolean cancelBook(int itemID, int memberID, Date beginningDate) {
		memberContextServices.oneMoreNbFakeBooking(memberDAO.getMemberContextID(memberID));
		return bookDAO.remove(itemID, memberID, beginningDate);
	}

	public boolean convertBookIntoBorrow(int itemID, int memberID, Date beginningDate, Date endingDate,
			Integer extensionID) throws MemberCantBorrowException, MemberNbBorrowsException, DatesOrderException, IntervalBetweenStartDateAndEndDateException, ExtensionNotAvailableException, BorrowAlreadyExistException {

		/* Recupération de la réservation localement puis suppression.
		 * Si le prêt n'a pas pû être créé (exception), réajout de la réservation. 
		 * (BorrowServices vérifie dans les tables Borrow et Book pour vérifier 
		 * si l'exemplaire n'est pas utilisé pour ces dates, si le Book est laissé 
		 * la vérification ne pourra pas être valide) */
		
		Book mayflyBook = bookDAO.get(itemID, memberID, beginningDate);
		bookDAO.remove(itemID, memberID, beginningDate);

		Extension extension = null;
		if (extensionID != null) {
			extension = extensionDAO.get(extensionID);
		}

		Borrow createdBorrow = null;
		try {
			createdBorrow = borrowServices.addBorrowKnowingItem(itemDAO.get(itemID), memberDAO.get(memberID),
					beginningDate, endingDate, extension);
		} catch (MemberCantBorrowException | MemberNbBorrowsException | DatesOrderException
				| IntervalBetweenStartDateAndEndDateException | ExtensionNotAvailableException
				| BorrowAlreadyExistException exception) {
			bookDAO.add(mayflyBook);
			throw exception;
		}
		
		return (createdBorrow != null);
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
