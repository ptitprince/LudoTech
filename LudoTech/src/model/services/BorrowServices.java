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
	private final MemberContextServices memberContextServices;

	public BorrowServices() {
		this.borrowDAO = new BorrowDAO();
		this.itemDAO = new ItemDAO();
		this.parametersServices = new ParametersServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new ExtensionServices();
		this.memberContextServices = new MemberContextServices();
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
	@SuppressWarnings("deprecation")
	public Borrow addBorrow(Game game, Member member, Date beginningDate,
			Date endingDate, Extension extension) {
		/*
		 * - vérifier si l'adhénent n'a pas dépassé le quota d'emprunts possible
		 * (utiliser ParametersServices avec une fonction en + pour recup
		 * "nbBorrowings" se calquer sur celle déjà faite et voir le
		 * fichierres/preferences.properties - ajouter le borrow en base de
		 * données, le retourner - si une vérification n'est pas effectuée,
		 * retourner null
		 */
		if (this.memberContextServices.getCanBook(member.getMemberContextID())) {
			
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
				if (beginningDate.getMonth() == 0
						|| beginningDate.getMonth() == 2
						|| beginningDate.getMonth() == 4
						|| beginningDate.getMonth() == 6
						|| beginningDate.getMonth() == 7
						|| beginningDate.getMonth() == 9
						|| beginningDate.getMonth() == 11) {
					// le mois avec 31 est repéré
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
				List<Item> items = this.itemDAO.getAllHavingGameID(game
						.getGameID());
				boolean atLeastOneGood = false;
				int i = 0;
				// On vérifie qu'au moins un des items ne fasse pas partie
				// d'un prêt.
				while (i < items.size() && !(atLeastOneGood)) {
					/*
					 * Invariant : parcours de la liste jusqu'à la fin ou avoir
					 * un item utilisable.
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
			if (this.extensionServices.countExtensions(game.getGameID()) > 0) {
				// On vérifie si le jeu peut avoir des extensions.
				List<Item> extensionItems = this.itemDAO
						.getAllHavingGameID(extension.getExtensionID());
				boolean atLeastOneGood = false;
				int i = 0;

				if (this.itemServices.countItemsOfGame(extension
						.getExtensionID()) > 0) {
					// TODO Vérifier qu'on estime que les items d'extension sont
					// classés comme les items de game.

					while (i < extensionItems.size() && !(atLeastOneGood)) {
						if (!(this.borrowDAO.getByItemID(extensionItems.get(i)
								.getItemID()))) {
							atLeastOneGood = true;
						}
						i++;
					}
				}
				if (atLeastOneGood) {
					this.itemDAO.remove(extensionItems.get(i - 1).getItemID());
				}
			}

		}
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
