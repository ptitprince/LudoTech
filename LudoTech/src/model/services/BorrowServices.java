package model.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.DAOs.BorrowDAO;
import model.DAOs.ItemDAO;
import model.POJOs.Borrow;
import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Item;
import model.POJOs.Member;

public class BorrowServices {

	private final BorrowDAO borrowDAO;
	private final ItemDAO itemDAO;
	private final ParametersServices parametersServices;
	private final ItemServices itemServices;
	private final ExtensionServices extensionServices;

	public BorrowServices() {
		this.borrowDAO = new BorrowDAO();
		this.itemDAO = new ItemDAO();
		this.parametersServices = new ParametersServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new ExtensionServices();
	}

	public List<Borrow> getAll() {
		return this.borrowDAO.getBorrows(-1);
	}

	public List<Borrow> getAllAccordingToUserID(int userID) {
		return this.borrowDAO.getBorrows(userID);
	}

	/**
	 * TODO Ajout d'un nouveau prêt dans la base de données Il est nécessaire
	 * d'effecuter des vérifications avant de l'ajouter
	 */
	public Borrow addBorrow(Game game, Member member, Date beginningDate,
			Date endingDate, Extension extension) {
		/*
		 * - vérifier si la date de fin est > à la date de début - vérifier si
		 * l'écart entre les dates corresponds au paramètre de durée de prêt
		 * (utiliser encore ParametersServices, avec
		 * "durationOfBorrowingsInWeeks" cette fois ci - si la date de retour
		 * tombe un week-end (samedi ou dimanche), la reporter au lundi suivant
		 * - vérifier si le game a un item disponible (en comptant qu'il en faut
		 * au moins 1 en stock) (qui ne fais pas partie d'un Borrow qui se
		 * superpose sur les dates indiquées -> utiliser/créer une fonction dans
		 * BorrowDAO), si oui en récupérer un - vérifier si l'extension est
		 * dispo pour ces dates (encore BorrowDAO) - vérifier si l'adhérent à le
		 * droit d'emprunter (booléen de permission dans le contexte) - vérifier
		 * si l'adhénent n'a pas dépassé le quota d'emprunts possible (utiliser
		 * ParametersServices avec une fonction en + pour recup "nbBorrowings"
		 * se calquer sur celle déjà faite et voir le
		 * fichierres/preferences.properties - ajouter le borrow en base de
		 * données, le retourner - si une vérification n'est pas effectuée,
		 * retourner null
		 */
		if (endingDate.before(beginningDate)) {
			return null; // renvoi de l'erreur.
		} else if (endingDate.getMonth() == beginningDate.getMonth()) {
			// endingDate >= beginningDate
			if ((endingDate.getDate() - beginningDate.getDate() == parametersServices
					.getDurationOfBorrowingsInWeeks() * 7)) {
				// Durée de prêt bien égale ici.
				if (endingDate.getDay() == 0) {
					// dimanche, on reporte au lundi
					endingDate.setDate(endingDate.getDate() + 1);

				} else if (endingDate.getDay() == 6) {
					// Samedi, on reporte au lundi
					endingDate.setDate(endingDate.getDate() + 2);
				}

			} else {
				return null;
			}
		} else {
			int ecartDate = 0;
			if (beginningDate.getMonth() == 0 || beginningDate.getMonth() == 0
					|| beginningDate.getMonth() == 0
					|| beginningDate.getMonth() == 0
					|| beginningDate.getMonth() == 0
					|| beginningDate.getMonth() == 0
					|| beginningDate.getMonth() == 0) {
				int newDate = beginningDate.getDate() % 31;
				ecartDate = newDate + endingDate.getDate();
			} else {
				int newDate = beginningDate.getDate() % 30;
				ecartDate = newDate + endingDate.getDate();
			}
			if (ecartDate == parametersServices
					.getDurationOfBorrowingsInWeeks() * 7) {
				if (endingDate.getDay() == 0) {
					// dimanche, on reporte au lundi
					endingDate.setDate(endingDate.getDate() + 1);

				} else if (endingDate.getDay() == 6) {
					// Samedi, on reporte au lundi
					endingDate.setDate(endingDate.getDate() + 2);
				}
			} else {
				return null;
			}
		}

		if (this.itemServices.countItemsOfGame(game.getGameID()) > 0) {
			// S'il reste des items du jeu
			List<Item> items = this.itemDAO
					.getAllHavingGameID(game.getGameID());
			boolean atLeastOneGood = false;
			int i = 0;
			// On vérifie qu'au moins un des items ne fasse pas partie
			// d'un prêt.
			while (i < items.size() && !(atLeastOneGood)) {
				/*
				 * Invariant : parcours de la liste jusqu'à la fin ou avoir un
				 * item utilisable.
				 */
				if (!(this.borrowDAO.getByItemID(items.get(i).getItemID()))) {
					atLeastOneGood = true;
				}
				i++;
			}// i => item.size() || atLeastOneGood
			if (atLeastOneGood) {
				this.itemDAO.remove(items.get(i - 1).getItemID());
				// Borrow borrow = new Borrow(items.get(i-1), member,
				// beginningDate, endingDate, extension);
			}
		} else {
			return null;
		}
		/* Extension possède des items ? */
		return null;
	}

	/**
	 * TODO Retour d'un emprunt -> suppression dans la base de données Faire des
	 * vérifications de fin de prêt pour modifier le contexte de l'adhérent /
	 * emprunteur
	 */
	public boolean removeBorrow(int itemID, int memberID, Date beginningDate,
			Date endingDate) {
		/*
		 * - Vérification que la date de retour (aujourd'hui) est <= à la date
		 * de fin de prêt Si non, incrémenter le compteur "nbDelays" de
		 * l'adhérent à l'aide d'une fonction dans MemberServices (qui
		 * enregistera également la modification dans la BDD à l'aide de la
		 * fonction edit) - Supprimer le borrow en base de données avec le
		 * couple des 3 clés primaires (itemID, memberID, beginningDate) et
		 * retourner le résultat booléen
		 */
		return true;
	}

}
