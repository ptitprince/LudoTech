package model.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.DAOs.BookDAO;
import model.DAOs.BorrowDAO;
import model.POJOs.Borrow;
import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Item;
import model.POJOs.Member;
import model.exceptions.BorrowAlreadyExistException;
import model.exceptions.DatesOrderException;
import model.exceptions.ExtensionNotAvailableException;
import model.exceptions.IntervalBetweenStartDateAndEndDateException;
import model.exceptions.MemberCantBorrowException;
import model.exceptions.MemberNbBorrowsException;
import model.exceptions.NoneItemAvailableException;

public class BorrowServices {

	private final BookDAO bookDAO;
	private final BorrowDAO borrowDAO;
	private final ParametersServices parametersServices;
	private final MemberContextServices memberContextServices;
	private final MemberServices memberServices;
	private final GameServices gameServices;

	public BorrowServices() {
		this.bookDAO = new BookDAO();
		this.borrowDAO = new BorrowDAO();
		this.parametersServices = new ParametersServices();
		this.memberContextServices = new MemberContextServices();
		this.memberServices = new MemberServices();
		this.gameServices = new GameServices();
	}

	/**
	 * Méthode pour avoir tous les emprunts existants.
	 * 
	 * @return une liste d'emprunts, qui sont de type Borrow.
	 */
	public List<Borrow> getAll() {
		return this.borrowDAO.getBorrows(-1);
	}

	/**
	 * Méthode pour avoir tous les emprunts d'un membre.
	 * 
	 * @param memberID
	 *            l'identifiant du membre.
	 * @return une Liste d'emprunts.
	 */
	public List<Borrow> getAllAccordingToUserID(int memberID) {
		return this.borrowDAO.getBorrows(memberID);
	}
	
	public Borrow addBorrow(Game game, Member member, Date beginningDate, Date endingDate, Extension extension) throws NoneItemAvailableException, MemberCantBorrowException, MemberNbBorrowsException, DatesOrderException, IntervalBetweenStartDateAndEndDateException, ExtensionNotAvailableException, BorrowAlreadyExistException {
		Item potentialItem = gameServices.getOneAvailableItem(game.getGameID(), beginningDate, endingDate);
		if (potentialItem != null) {
			return addBorrowKnowingItem(potentialItem, member, beginningDate, endingDate, extension);
		} else {
			throw new NoneItemAvailableException(game.getName(), beginningDate, endingDate);
		}
	}
	
	public Borrow addBorrowKnowingItem(Item potentialItem, Member member, Date beginningDate, Date endingDate, Extension extension) throws MemberCantBorrowException, MemberNbBorrowsException, DatesOrderException, IntervalBetweenStartDateAndEndDateException, ExtensionNotAvailableException, BorrowAlreadyExistException  {
		checkMemberCanBorrow(member);
		checkMemberNbBorrows(member);
		checkDatesOrder(beginningDate, endingDate);
		checkIntervalBetweenStartDateAndEndDateIsValid(beginningDate, endingDate);
		checkExtensionIsAvailable(extension, beginningDate, endingDate);
		checkBorrowNotAlreadyExist(potentialItem, member, beginningDate);

		Borrow acceptedBorrow = new Borrow(potentialItem, member, beginningDate, endingDate, extension);
		this.borrowDAO.add(acceptedBorrow);
		return acceptedBorrow;
	}
	
	private void checkMemberCanBorrow(Member member) throws MemberCantBorrowException {
		if (!member.getMemberContext().canBorrow()) {
			throw new MemberCantBorrowException(member.getFirstName() + " " + member.getLastName());
		}
	}

	private void checkMemberNbBorrows(Member member) throws MemberNbBorrowsException {
		int memberNbBorrows = borrowDAO.getBorrows(member.getMemberID()).size();
		int maxNbBorrows = parametersServices.getNumberOfBorrowings();
		if (memberNbBorrows >= maxNbBorrows) {
			throw new MemberNbBorrowsException(member.getFirstName() + " " + member.getLastName(), memberNbBorrows,
					maxNbBorrows);
		}
	}

	private void checkDatesOrder(Date beginningDate, Date endingDate) throws DatesOrderException {
		if (beginningDate.before(getTodayDateWithoutTime()) || beginningDate.after(endingDate)) {
			throw new DatesOrderException();
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
	
	private void checkExtensionIsAvailable(Extension extension, Date beginningDate, Date endingDate)
			throws ExtensionNotAvailableException {
		if (extension != null) {
			if (bookDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate)
					|| borrowDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate)) {
				throw new ExtensionNotAvailableException(extension.getName());
			}
		}
	}

	private void checkBorrowNotAlreadyExist(Item potentialItem, Member member, Date beginningDate)
			throws BorrowAlreadyExistException {
		Borrow canBeExistingBorrow = this.borrowDAO.get(potentialItem.getItemID(), member.getMemberID(), beginningDate);
		if (canBeExistingBorrow != null) {
			throw new BorrowAlreadyExistException(canBeExistingBorrow);
		}

	}
	
	private Date getTodayDateWithoutTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public boolean removeBorrow(int itemID, int memberID, Date beginningDate, Date endingDate) {
		if (endingDate.before(new Date())) {
			this.memberContextServices.addNbDelays(this.memberServices.getMemberContext(memberID));
		}
		return this.borrowDAO.remove(itemID, memberID, beginningDate);
	}

}
