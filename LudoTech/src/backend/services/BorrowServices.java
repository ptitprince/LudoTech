package backend.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import backend.DAOs.BookDAO;
import backend.DAOs.BorrowDAO;
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
 * Propose des fonctionnalités de traitement sur des prêts
 */
public class BorrowServices {

	/**
	 * Services associés aux jeux
	 */
	private final GameServices gameServices;

	/**
	 * Services associés aux adhérents
	 */
	private final MemberServices memberServices;

	/**
	 * Services associés aux contextes d'adhérents
	 */
	private final MemberContextServices memberContextServices;

	/**
	 * Services associés aux paramètres de l'application
	 */
	private final ParametersServices parametersServices;

	/**
	 * Objet de manipulation de la base de données pour des réservations
	 */
	private final BookDAO bookDAO;

	/**
	 * Objet de manipulation de la base de données pour des prêts
	 */
	private final BorrowDAO borrowDAO;

	/**
	 * Construit des services pour les prêts
	 */
	public BorrowServices() {
		this.bookDAO = new BookDAO();
		this.borrowDAO = new BorrowDAO();
		this.parametersServices = new ParametersServices();
		this.memberContextServices = new MemberContextServices();
		this.memberServices = new MemberServices();
		this.gameServices = new GameServices();
	}

	/**
	 * Liste tous les prêts
	 * 
	 * @return Une liste de prêts potentiellement vide
	 */
	public List<Borrow> getAll() {
		return this.borrowDAO.getAll(-1);
	}

	/**
	 * Liste tous les prêts d'un adhérent dont l'identifiant est passé en
	 * paramètre
	 * 
	 * @param memberID
	 *            L'identifiant d'un adhérent
	 * @return Une liste de prêts de l'adhérent potentiellement vide
	 */
	public List<Borrow> getAllAccordingToUserID(int memberID) {
		return this.borrowDAO.getAll(memberID);
	}

	/**
	 * Ajoute un nouveau prêt pour un jeu en vérifiant si c'est possible
	 * (utilisé par l'interface graphique)
	 * 
	 * @param game
	 *            Le jeu non null à emprunter
	 * @param member
	 *            L'adhérent non null qui emprunte
	 * @param beginningDate
	 *            La date de début de prêt
	 * @param endingDate
	 *            La date de fin de prêt
	 * @param extension
	 *            L'extension qui peut être empruntée en supplément (peut être
	 *            null)
	 * @return Le prêt s'il est accepté, sinon null
	 * @throws NoneItemAvailableException
	 *             Si pour un certain jeu il n'existe pas d'exemplaires
	 *             disponible (en comptant un qui restera toujours en un stock)
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
	public Borrow addBorrow(Game game, Member member, Date beginningDate, Date endingDate, Extension extension)
			throws NoneItemAvailableException, MemberCantBorrowException, MemberNbBorrowsException, DatesOrderException,
			IntervalBetweenStartDateAndEndDateException, ExtensionNotAvailableException, BorrowAlreadyExistException {
		Item potentialItem = gameServices.getOneAvailableItem(game.getGameID(), beginningDate, endingDate);
		if (potentialItem != null) {
			return addBorrowKnowingItem(potentialItem, member, beginningDate, endingDate, extension);
		} else {
			throw new NoneItemAvailableException(game.getName(), beginningDate, endingDate);
		}
	}

	/**
	 * Ajoute un nouveau prêt pour un exemplaire si c'est possible (Utilisé pour
	 * convertir une réservation en prêt depuis BookServices)
	 * 
	 * @param potentialItem
	 *            L'exemplaire non null choisit pour le prêt
	 * @param member
	 *            L'adhérent non null qui veut emprunter
	 * @param beginningDate
	 *            La date de début du prêt
	 * @param endingDate
	 *            La date de fin du prêt
	 * @param extension
	 *            L'extension qui peut être empruntée en supplément (peut être
	 *            null)
	 * @return Le prêt s'il est accepté, sinon null
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
	public Borrow addBorrowKnowingItem(Item potentialItem, Member member, Date beginningDate, Date endingDate,
			Extension extension) throws MemberCantBorrowException, MemberNbBorrowsException, DatesOrderException,
					IntervalBetweenStartDateAndEndDateException, ExtensionNotAvailableException,
					BorrowAlreadyExistException {
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

	/**
	 * Supprime un prêt (quand il est terminé ou si trop vieux) en incrémentant
	 * le nombre de retards de l'adhérent si besoin
	 * 
	 * @param itemID
	 *            L'identifiant unique d'un exemplaire
	 * @param memberID
	 *            L'identifiant unique d'un adhérent
	 * @param startDate
	 *            La date de début du prêt
	 * @param endingDate
	 *            La date de fin du prêt
	 * @return True si le prêt a été supprimé, sinon False
	 */
	public boolean removeBorrow(int itemID, int memberID, Date beginningDate, Date endingDate) {
		if (endingDate.before(new Date())) {
			this.memberContextServices.incrementNbDelays(this.memberServices.getMemberContext(memberID));
		}
		return this.borrowDAO.remove(itemID, memberID, beginningDate);
	}

	/**
	 * Vérifie que l'adhérent a le droit d'emprunter
	 * 
	 * @param member
	 *            Un adhérent non null
	 * @throws MemberCantBorrowException
	 *             Si l'adhérent n'a pas le droit d'emprunter
	 */
	private void checkMemberCanBorrow(Member member) throws MemberCantBorrowException {
		if (!member.getMemberContext().canBorrow()) {
			throw new MemberCantBorrowException(member.getFirstName() + " " + member.getLastName());
		}
	}

	/**
	 * Vérifie si l'adhérent peut encore emprunter par rapport au nombre de
	 * prêts qu'il possède
	 * 
	 * @param member
	 *            Un adhérent non null
	 * @throws MemberNbBorrowsException
	 *             Si l'adhérent a atteint la limite maximale de prêts définie
	 *             dans les paramètres de l'application
	 */
	private void checkMemberNbBorrows(Member member) throws MemberNbBorrowsException {
		int memberNbBorrows = borrowDAO.getAll(member.getMemberID()).size();
		int maxNbBorrows = parametersServices.getNumberOfBorrowings();
		if (memberNbBorrows >= maxNbBorrows) {
			throw new MemberNbBorrowsException(member.getFirstName() + " " + member.getLastName(), memberNbBorrows,
					maxNbBorrows);
		}
	}

	/**
	 * Vérifie l'ordre des dates
	 * 
	 * @param beginningDate
	 *            Date de début du prêt
	 * @param endingDate
	 *            Date de fin du prêt
	 * @throws DatesOrderException
	 *             Si pour ce prêt, la date de début se situe chronologiquement
	 *             après celle de fin
	 */
	private void checkDatesOrder(Date beginningDate, Date endingDate) throws DatesOrderException {
		if (beginningDate.before(getTodayDateWithoutTime()) || beginningDate.after(endingDate)) {
			throw new DatesOrderException();
		}
	}

	/**
	 * Vérifie la validité des dates utilisées pour le prêt
	 * 
	 * @param startDate
	 *            Date de début du prêt
	 * @param endDate
	 *            Date de fin du prêt
	 * @throws IntervalBetweenStartDateAndEndDateException
	 *             Si l'intervalle entre la date de début et la date de fin du
	 *             prêt ne respecte pas les paramètres de l'application
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
	 * Vérifie si l'extension choisie (si choisie) est disponible pour une
	 * période précise
	 * 
	 * @param extension
	 *            L'extension qui a pû être choisie (peut être null)
	 * @param beginningDate
	 *            La date de début du prêt
	 * @param endingDate
	 *            La date de fin du prêt
	 * @throws ExtensionNotAvailableException
	 *             Si l'extension n'est pas disponible pour le prêt
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
	 * Vérifie que le prêt identifié par la couple {potentialItem, member,
	 * beginningDate} n'existe pas déjà
	 * 
	 * @param potentialItem
	 *            Exemplaire non null à emprunter
	 * @param member
	 *            Adhérent non null qui emprunte
	 * @param beginningDate
	 *            Date de début du prêt
	 * @throws BorrowAlreadyExistException
	 *             Si le prêt existe déjà
	 */
	private void checkBorrowNotAlreadyExist(Item potentialItem, Member member, Date beginningDate)
			throws BorrowAlreadyExistException {
		Borrow canBeExistingBorrow = this.borrowDAO.get(potentialItem.getItemID(), member.getMemberID(), beginningDate);
		if (canBeExistingBorrow != null) {
			throw new BorrowAlreadyExistException(canBeExistingBorrow);
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
