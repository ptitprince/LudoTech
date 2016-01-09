package model.services;

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

	/**
	 * Méthode pour ajouter un emprunt, en vérifiant toutes les conditions.
	 * 
	 * @param game
	 *            le jeu que l'on souhaite emprunter.
	 * @param member
	 *            le membre qui est concerné.
	 * @param beginningDate
	 *            la date prévue de début d'emprunt.
	 * @param endingDate
	 *            la date de fin prévue pour l'emprunt.
	 * @param extension
	 *            une éventuelle extension.
	 * @return un emprunt de type Borrow, ou null si une condition n'est pas
	 *         réalisée.
	 */
	@SuppressWarnings("deprecation")
	public Borrow addBorrow(Game game, Member member, Date beginningDate, Date endingDate, Extension extension) {
		if (this.memberContextServices.getCanBook(member.getMemberContextID())) {
			// le membre a le droit d'emprunter.
			List<Borrow> borrows = getAllAccordingToUserID(member.getMemberID());

			if (borrows.size() < this.parametersServices.getNumberOfBorrowings()) {
				// le membre n'a pas dépassé sa taille d'emprunt limite.
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
					// on recommence ici pour ceux qui ne sont pas dans le même
					// mois.
					int ecartDate = 0;
					if (beginningDate.getMonth() == 0 || beginningDate.getMonth() == 2 || beginningDate.getMonth() == 4
							|| beginningDate.getMonth() == 6 || beginningDate.getMonth() == 7
							|| beginningDate.getMonth() == 9 || beginningDate.getMonth() == 11) {
						// le mois avec 31 jours est repéré
						int newDate = beginningDate.getDate() % 31;
						ecartDate = newDate + endingDate.getDate();
					} else {
						int newDate = beginningDate.getDate() % 30;
						ecartDate = newDate + endingDate.getDate();
					}

					if (ecartDate == parametersServices.getDurationOfBorrowingsInWeeks() * 7) {
						// L'écart de dates correspond bien à la durée
						// autorisée.
						if (endingDate.getDay() == 0) {
							// L'emprunt se termine un dimanche, on reporte au
							// lundi.
							endingDate.setDate(endingDate.getDate() + 1);

						} else if (endingDate.getDay() == 6) {
							// pareill pour samedi, on reporte au lundi.
							endingDate.setDate(endingDate.getDate() + 2);
						}
					} else {
						return null;
					}
					if (this.itemServices.countItemsOfGame(game.getGameID()) > 1) {
						// S'il reste des items du jeu
						List<Item> items = this.itemDAO.getAllHavingGameID(game.getGameID());
						boolean atLeastOneGood = false;
						int i = 0;
						// On vérifie qu'au moins un des items ne fasse pas
						// partie
						// d'un prêt.
						while (!(atLeastOneGood) && i < items.size()) {
							atLeastOneGood = !this.borrowDAO.getIfExists(items.get(i).getItemID());
							i++;
						}

						if (atLeastOneGood) {
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
									// Tout est bon, nous pouvons créer un
									// emprunt.
									Borrow borrow = new Borrow(items.get(i - 1), member, beginningDate, endingDate,
											extension);
									this.borrowDAO.add(borrow);
									return borrow;
								}

							} else {
								Borrow borrow = new Borrow(items.get(i - 1), member, beginningDate, endingDate,
										extension);
								this.borrowDAO.add(borrow);
								return borrow;
								// TODO cas à vérifier, où il n'y a pas
								// d'extensions.
							}
						} else {
							return null;
						}
					} else {
						return null;
					}
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return null;
		// TODO a vérifier ici.
	}

	/**
	 * Méthode pour supprimer un emprunt.
	 * 
	 * @param itemID
	 *            l'identifiant de l'item.
	 * @param memberID
	 *            l'identifiant du membre.
	 * @param beginningDate
	 *            la date de début du prêt.
	 * @param endingDate
	 *            la date de fin du prêt.
	 * @return un booléen, confirmant si la suppression a été effectuée ou non.
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
