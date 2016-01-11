package backend.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import backend.DAOs.BookDAO;
import backend.DAOs.BorrowDAO;
import backend.DAOs.ExtensionDAO;
import backend.DAOs.ItemDAO;
import backend.DAOs.MemberDAO;
import backend.POJOs.Book;
import backend.POJOs.Borrow;
import backend.POJOs.Extension;
import backend.POJOs.Game;
import backend.POJOs.Item;
import backend.POJOs.Member;
import backend.exceptions.BookAlreadyExistException;
import backend.exceptions.BorrowAlreadyExistException;
import backend.exceptions.DatesOrderException;
import backend.exceptions.ExtensionNotAvailableException;
import backend.exceptions.IntervalBetweenNowAndStartDateException;
import backend.exceptions.IntervalBetweenStartDateAndEndDateException;
import backend.exceptions.MemberCantBookException;
import backend.exceptions.MemberCantBorrowException;
import backend.exceptions.MemberNbBooksException;
import backend.exceptions.MemberNbBorrowsException;
import backend.exceptions.NoneItemAvailableException;

/**
 * Propose des fonctionnalités de traitement sur des réservations
 */
public class BookServices {

	/**
	 * Services associés aux jeux
	 */
	private final GameServices gameServices;

	/**
	 * Services associés aux contextes d'adhérents
	 */
	private final MemberContextServices memberContextServices;

	/**
	 * Services associés aux paramètres de l'application
	 */
	private final ParametersServices parametersServices;

	/**
	 * Services associés aux prêts
	 */
	private final BorrowServices borrowServices;

	/**
	 * Objet de manipulation de la base de données pour des réservations
	 */
	private final BookDAO bookDAO;

	/**
	 * Objet de manipulation de la base de données pour des prêts
	 */
	private final BorrowDAO borrowDAO;

	/**
	 * Objet de manipulation de la base de données pour des exemplaires
	 */
	private final ItemDAO itemDAO;

	/**
	 * Objet de manipulation de la base de données pour des adhérents
	 */
	private final MemberDAO memberDAO;

	/**
	 * Objet de manipulation de la base de données pour des extensions
	 */
	private final ExtensionDAO extensionDAO;

	/**
	 * Construit des services pour les réservations
	 */
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

	/**
	 * Liste toutes les réservations
	 * 
	 * @return Une liste de réservations potentiellement vide
	 */
	public List<Book> getAll() {
		return this.bookDAO.getAll(-1);
	}

	/**
	 * Liste toutes les réservations d'un adhérent dont l'identifiant est passé
	 * en paramètre
	 * 
	 * @param userID
	 *            L'identifiant d'un adhérent
	 * @return Une liste de réservations de l'adhérent potentiellement vide
	 */
	public List<Book> getAllAccordingToUserID(int userID) {
		return this.bookDAO.getAll(userID);
	}

	/**
	 * Ajoute un nouvelle réservation en vérifiant si c'est possible
	 * 
	 * @param game
	 *            Le jeu non null à réserver
	 * @param member
	 *            L'adhérent non null qui réserver
	 * @param beginningDate
	 *            La date de début de réservation
	 * @param endingDate
	 *            La date de fin de réservation
	 * @param extension
	 *            L'extension qui peut être réservée en supplément (peut être
	 *            null)
	 * @return La réservation si elle a été acceptée, sinon null
	 * @throws MemberCantBookException
	 *             Si l'adhérent n'a pas le droit de réserver
	 * @throws MemberNbBooksException
	 *             Si l'adhérent a atteint la limite maximale de réservations
	 *             définie dans les paramètres de l'application
	 * @throws DatesOrderException
	 *             Si une réservation, la date de début se situe
	 *             chronologiquement après celle de fin
	 * @throws IntervalBetweenNowAndStartDateException
	 *             Si l'intervalle entre la date du système et la date de début
	 *             de la réservation ne respecte pas les paramètres de
	 *             l'application
	 * @throws IntervalBetweenStartDateAndEndDateException
	 *             Si l'intervalle entre la date de début et la date de fin de
	 *             la réservation ne respecte pas les paramètres de
	 *             l'application
	 * @throws NoneItemAvailableException
	 *             Si pour un certain jeu il n'existe pas d'exemplaires
	 *             disponible (en comptant un qui restera toujours en un stock)
	 * @throws ExtensionNotAvailableException
	 *             Si l'extension n'est pas disponible pour une réservation
	 * @throws BookAlreadyExistException
	 *             Si la réservation existe déjà
	 */
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

	/**
	 * Annule une réservation identifiée par le couple {itemID, memberID,
	 * startDate} en incrémentant le nombre de réservations annulées de
	 * l'adhérent
	 * 
	 * @param itemID
	 *            L'identifiant unique d'un exemplaire
	 * @param memberID
	 *            L'identifiant unique d'un adhérent
	 * @param startDate
	 *            La date de début de la réservation
	 * @return True si la réservation a été supprimée, sinon False
	 */
	public boolean cancelBook(int itemID, int memberID, Date startDate) {
		memberContextServices.incrementNbFakeBookings(memberDAO.getMemberContextID(memberID));
		return bookDAO.remove(itemID, memberID, startDate);
	}

	/**
	 * Convertie une réservation en prêt. Si le prêt est autorisé, supprime la
	 * réservation, sinon la concerve.
	 * 
	 * @param game
	 *            Le jeu non null à réserver
	 * @param member
	 *            L'adhérent non null qui réserver
	 * @param beginningDate
	 *            La date de début de réservation
	 * @param endingDate
	 *            La date de fin de réservation
	 * @param extension
	 *            L'extension qui peut être réservée en supplément (peut être
	 *            null)
	 * @return True si la conversion a réussie, sinon False
	 * @throws MemberCantBorrowException
	 *             Si l'adhérent n'a pas le droit d'emprunter
	 * @throws MemberNbBorrowsException
	 *             Si l'adhérent a atteint la limite maximale d'emprunts définie
	 *             dans les paramètres de l'application
	 * @throws DatesOrderException
	 *             Si pour un emprunt, la date de début se situe
	 *             chronologiquement après celle de fin
	 * @throws IntervalBetweenStartDateAndEndDateException
	 *             Si l'intervalle entre la date de début et la date de fin du
	 *             prêt ne respecte pas les paramètres de l'application
	 * @throws ExtensionNotAvailableException
	 *             Si l'extension n'est pas disponible pour un prêt
	 * @throws BorrowAlreadyExistException
	 *             Si le prêt existe déjà
	 */
	public boolean convertBookIntoBorrow(int itemID, int memberID, Date beginningDate, Date endingDate,
			Integer extensionID) throws MemberCantBorrowException, MemberNbBorrowsException, DatesOrderException,
					IntervalBetweenStartDateAndEndDateException, ExtensionNotAvailableException,
					BorrowAlreadyExistException {

		/*
		 * Recupération de la réservation localement puis suppression. Si le
		 * prêt n'a pas pû être créé (exception), réajout de la réservation.
		 * (BorrowServices vérifie dans les tables Borrow et Book pour vérifier
		 * si l'exemplaire n'est pas utilisé pour ces dates, si le Book est
		 * laissé la vérification ne pourra pas être valide)
		 */

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

	/**
	 * Vérifie que l'adhérent a le droit de réserver
	 * 
	 * @param member
	 *            Un adhérent non null
	 * @throws MemberCantBookException
	 *             Si l'adhérent n'a pas le droit de réserver
	 */
	private void checkMemberCanBook(Member member) throws MemberCantBookException {
		if (!member.getMemberContext().canBook()) {
			throw new MemberCantBookException(member.getFirstName() + " " + member.getLastName());
		}
	}

	/**
	 * Vérifie si l'adhérent peut encore réserver par rapport au nombre de
	 * réservations qu'il possède
	 * 
	 * @param member
	 *            Un adhérent non null
	 * @throws MemberNbBooksException
	 *             Si l'adhérent a atteint la limite maximale de réservations
	 *             définie dans les paramètres de l'application
	 */
	private void checkMemberNbBooks(Member member) throws MemberNbBooksException {
		int memberNbBooks = bookDAO.getAll(member.getMemberID()).size();
		int maxNbBooks = parametersServices.getNumberOfBookings();
		if (memberNbBooks >= maxNbBooks) {
			throw new MemberNbBooksException(member.getFirstName() + " " + member.getLastName(), memberNbBooks,
					maxNbBooks);
		}
	}

	/**
	 * Vérifie l'ordre des dates
	 * 
	 * @param beginningDate
	 *            Date de début de la réservation
	 * @param endingDate
	 *            Date de fin de la réservation
	 * @throws DatesOrderException
	 *             Si pour cette réservation, la date de début se situe
	 *             chronologiquement après celle de fin
	 */
	private void checkDatesOrder(Date beginningDate, Date endingDate) throws DatesOrderException {
		if (beginningDate.before(getTodayDateWithoutTime()) || beginningDate.after(endingDate)) {
			throw new DatesOrderException();
		}
	}

	/**
	 * Vérifie la validité de la date de début de la réservation
	 * 
	 * @param startDate
	 *            Date de début de la réservation
	 * @throws IntervalBetweenNowAndStartDateException
	 *             Si l'intervalle entre la date du système et la date de début
	 *             de la réservation ne respecte pas les paramètres de
	 *             l'application
	 */
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

	/**
	 * Vérifie la validité des dates utilisées pour la réservation
	 * 
	 * @param startDate
	 *            Date de début de la réservation
	 * @param endDate
	 *            Date de fin de la réservation
	 * @throws IntervalBetweenStartDateAndEndDateException
	 *             Si l'intervalle entre la date de début et la date de fin de
	 *             la réservation ne respecte pas les paramètres de
	 *             l'application
	 */
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

	/**
	 * Vérifie s'il existe un exemplaire disponible du jeu pour une période
	 * précise
	 * 
	 * @param game
	 *            Le jeu non null à réserver
	 * @param beginningDate
	 *            La date de début de la réservation
	 * @param endingDate
	 *            La date de fin de la réservation
	 * @return Un exemplaire s'il y en a un de disponible, sinon null
	 * @throws NoneItemAvailableException
	 *             Si pour un certain jeu il n'existe pas d'exemplaires
	 *             disponible (en comptant un qui restera toujours un en stock)
	 */
	private Item checkAndGetAvailableItem(Game game, Date beginningDate, Date endingDate)
			throws NoneItemAvailableException {
		Item potentialItem = gameServices.getOneAvailableItem(game.getGameID(), beginningDate, endingDate);
		if (potentialItem == null) {
			throw new NoneItemAvailableException(game.getName(), beginningDate, endingDate);
		}
		return potentialItem;
	}

	/**
	 * Vérifie si l'extension choisie (si choisie) est disponible pour une
	 * période précise
	 * 
	 * @param extension
	 *            L'extension qui a pû être choisie (peut être null)
	 * @param beginningDate
	 *            La date de début de la réservation
	 * @param endingDate
	 *            La date de fin de la réservation
	 * @throws ExtensionNotAvailableException
	 *             Si l'extension n'est pas disponible pour la réservation
	 */
	private void checkExtensionIsAvailable(Extension extension, Date beginningDate, Date endingDate)
			throws ExtensionNotAvailableException {
		if (extension != null) {
			if (bookDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate)
					|| borrowDAO.extensionUsedDuringPeriod(extension.getExtensionID(), beginningDate, endingDate)) {
				throw new ExtensionNotAvailableException(extension.getName());
			}
		}
	}

	/**
	 * Vérifie que la réservation identifiée par la couple {potentialItem,
	 * member, beginningDate} n'existe pas déjà
	 * 
	 * @param potentialItem
	 *            Exemplaire non null à réserver
	 * @param member
	 *            Adhérent non null qui réserve
	 * @param beginningDate
	 *            Date de début de la réservation
	 * @throws BookAlreadyExistException
	 *             Si la réservation existe déjà
	 */
	private void checkBookNotAlreadyExist(Item potentialItem, Member member, Date beginningDate)
			throws BookAlreadyExistException {
		Book canBeExistingBook = this.bookDAO.get(potentialItem.getItemID(), member.getMemberID(), beginningDate);
		if (canBeExistingBook != null) {
			throw new BookAlreadyExistException(canBeExistingBook);
		}

	}

	/**
	 * Créé et retourne la date du système avec uniquement les champs jour, mois
	 * et année initialisés
	 * 
	 * @return Une date
	 */
	private Date getTodayDateWithoutTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

}
