package model.services;

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
	
	public Borrow addBorrow(Game game, Member member, Date beginningDate, Date endingDate, Extension extension) {
		Item potentialItem = gameServices.getOneAvailableItem(game.getGameID(), beginningDate, endingDate);
		if (potentialItem != null) {
			return addBorrowKnowingItem(potentialItem, member, beginningDate, endingDate, extension);
		} else {
			return null;
		}
	}
	
	public Borrow addBorrowKnowingItem(Item item, Member member, Date beginningDate, Date endingDate, Extension extension) {
		boolean canAddBorrow = true;
		canAddBorrow &= (member.getMemberContext().canBorrow());
		canAddBorrow &= (canAddBorrow) && (borrowDAO.getBorrows(member.getMemberID()).size() < parametersServices.getNumberOfBorrowings());
		canAddBorrow &= (canAddBorrow) && (beginningDate.after(new Date()) && beginningDate.before(endingDate));
		canAddBorrow &= (canAddBorrow) && (checkIntervalBetweenStartDateAndEndDateIsValid(beginningDate, endingDate));
		canAddBorrow &= (canAddBorrow) && (item != null);
		if (extension != null) {
			canAddBorrow &= (canAddBorrow) && (extension != null) 
					&& (!bookDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate))
					&& (!borrowDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate));
			}
		if (canAddBorrow) {
			Borrow acceptedBorrow = new Borrow(item, member, beginningDate, endingDate,
					extension);
			this.borrowDAO.add(acceptedBorrow);
			return acceptedBorrow;
		} else {
			return null;
		}
	}

	private boolean checkIntervalBetweenStartDateAndEndDateIsValid(Date startDate, Date endDate) {
		long diffValue = endDate.getTime() - startDate.getTime();
		long diffDays = TimeUnit.DAYS.convert(diffValue, TimeUnit.MILLISECONDS);
		int expectedIntervalBetweenStartDateAndEndDateInDays = 7*this.parametersServices.getDurationOfBorrowingsInWeeks();
		return (diffDays == expectedIntervalBetweenStartDateAndEndDateInDays);
	}

	public boolean removeBorrow(int itemID, int memberID, Date beginningDate, Date endingDate) {
		if (endingDate.before(new Date())) {
			this.memberContextServices.addNbDelays(this.memberServices.getMemberContext(memberID));
		}
		return this.borrowDAO.remove(itemID, memberID, beginningDate);
	}

}
