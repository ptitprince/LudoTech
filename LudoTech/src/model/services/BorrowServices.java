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
	private final MemberServices memberServices;

	public BorrowServices() {
		this.borrowDAO = new BorrowDAO();
		this.itemDAO = new ItemDAO();
		this.parametersServices = new ParametersServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new ExtensionServices();
		this.memberContextServices = new MemberContextServices();
		this.memberServices = new MemberServices();
	}

	public List<Borrow> getAll() {
		return this.borrowDAO.getBorrows(-1);
	}

	public List<Borrow> getAllAccordingToUserID(int userID) {
		return this.borrowDAO.getBorrows(userID);
	}

	/**
	 * TODO Ajout d'un nouveau prêt dans la base de données Il est nécessaire
	 * d'effecuter des vérifications avant de l'ajouter Non fonctionnel, à
	 * revoir.
	 */
	@SuppressWarnings("deprecation")
	public Borrow addBorrow(Game game, Member member, Date beginningDate, Date endingDate, Extension extension) {
		/*
		 * - vérifier si l'adhénent n'a pas dépassé le quota d'emprunts possible
		 * (utiliser ParametersServices avec une fonction en + pour recup
		 * "nbBorrowings" se calquer sur celle déjà faite et voir le
		 * fichierres/preferences.properties - ajouter le borrow en base de
		 * données, le retourner - si une vérification n'est pas effectuée,
		 * retourner null
		 */
		if (this.memberContextServices.getCanBook(member.getMemberContextID())) {
			// if(this.parametersServices) TODO
			if (endingDate.before(beginningDate)) {
				return null; // renvoi de l'erreur.
			} else if (endingDate.getMonth() == beginningDate.getMonth()) {
				// endingDate >= beginningDate
				if ((endingDate.getDate()
						- beginningDate.getDate() == parametersServices.getDurationOfBorrowingsInWeeks() * 7)) {
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
				if (beginningDate.getMonth() == 0 || beginningDate.getMonth() == 2 || beginningDate.getMonth() == 4
						|| beginningDate.getMonth() == 6 || beginningDate.getMonth() == 7
						|| beginningDate.getMonth() == 9 || beginningDate.getMonth() == 11) {
					// le mois avec 31 est repéré
					int newDate = beginningDate.getDate() % 31;
					ecartDate = newDate + endingDate.getDate();
				} else {
					int newDate = beginningDate.getDate() % 30;
					ecartDate = newDate + endingDate.getDate();
				}
				if (ecartDate == parametersServices.getDurationOfBorrowingsInWeeks() * 7) {
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
				List<Item> items = this.itemDAO.getAllHavingGameID(game.getGameID());
				boolean atLeastOneGood = false;
				int i = 0;
				// On vérifie qu'au moins un des items ne fasse pas partie
				// d'un prêt.
				while (!(atLeastOneGood) && i < items.size()) {
					atLeastOneGood = this.borrowDAO.getIfExists(items.get(i).getItemID());
					i++;
				}

				if (atLeastOneGood) {
					this.itemDAO.remove(items.get(i - 1).getItemID());
					if (extension != null) {
						if (this.extensionServices.countExtensions(game.getGameID()) > 0) {
							// On vérifie si le jeu a des extensions.
							atLeastOneGood = this.borrowDAO.getIfExtensionExists(extension.getExtensionID());
							if (atLeastOneGood) {
								this.extensionServices.deleteExtension(extension.getExtensionID());
								Borrow borrow = new Borrow(items.get(i - 1), member, beginningDate, endingDate,
										extension);
								this.borrowDAO.add(borrow);
								return borrow;
							} else {
								return null;
							}
						} else {
							return null;
						}
					} else {
						Borrow borrow = new Borrow(items.get(i - 1), member, beginningDate, endingDate, extension);
						this.borrowDAO.add(borrow);
						return borrow;
					}

				} else {
					return null;
				}
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	/**
	 * FONCTIONNE \o/
	 */
	public boolean removeBorrow(int itemID, int memberID, Date beginningDate, Date endingDate) {

		Date today = new Date();
		int delay = endingDate.compareTo(today);
		// compareTo donne 0 si égaux, 1 si endingDate est plus grand, -1 si
		// plus petit
		if (delay == 1) {
			this.memberContextServices.addNbDelays(this.memberServices.getMemberContext(memberID));
		}
		if (this.borrowDAO.remove(itemID, memberID, beginningDate)) {
			return true;
		} else {
			return false;
		}
	}

}
