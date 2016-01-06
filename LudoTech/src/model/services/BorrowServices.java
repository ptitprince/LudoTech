package model.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.DAOs.BorrowDAO;
import model.POJOs.Borrow;
import model.POJOs.Extension;
import model.POJOs.Item;
import model.POJOs.Member;

public class BorrowServices {

	/**
	 * Objet d'accès aux données de type Borrow.
	 */
	private final BorrowDAO borrowDAO;

	public BorrowServices() {
		this.borrowDAO = new BorrowDAO();
	}

	public List<Borrow> getAll() {
		return this.borrowDAO.getBorrows();
	}

	/**
	 * Pour tous : Pouvoir faire une recherche parmi les emprunts.
	 * 
	 * Pour adhérent : - Voir SES emprunts en cours ; Pour chaque emprunt : -
	 * Accéder aux infos ; - Statut de l'emprunt ; - Nombre jours restant OU en
	 * retard ;
	 * 
	 * Pour un admin : - Voir TOUS les emprunts des adhérents (doit pouvoir voir
	 * les adhérents); Pour chaque emprunt : - Pour les emprunts finis, indiquer
	 * comment ça s'est terminé. - Enregistrer le retour d'un emprunt.
	 * 
	 */

	/**
	 * Création et ajout en base de données d'un nouvel emprunt
	 * 
	 * @param borrowID
	 *            : l'identifiant de l'emprunt;
	 * @param itemID
	 *            : l'identifiant de l'exemplaire;
	 * @param memberID
	 *            : l'identifiant du membre;
	 * @param dateFin
	 *            : date de fin de l'emprunt. Doit être supérieur à dateDebut
	 *            (genious);
	 * @param dateDebut
	 *            : date de debut de l'emprunt. Doit être inférieur à dateFin;
	 * @param State
	 *            : Etat de l'emprunt.
	 * @param Available
	 *            : booléen sur l'état de l'emprunt.
	 * 
	 */

	public Borrow addBorrow(Item item, Member member, Date beginningDate,
			Date endingDate, Extension extension) {
		Borrow borrow = new Borrow(item, member, beginningDate, endingDate,
				extension);
		return this.borrowDAO.add(borrow) ? borrow : null;
		// Redondance avec le member et l'item dans borrow ?
	}

	/**
	 * Suppression en base de données de l'emprunt.
	 * 
	 * @param l
	 *            'id de l'emprunt que l'on veut supprimer.
	 * @return un booléen accusant de l'état de la suppression.
	 * 
	 */

	public boolean removeBorrow(int itemID, int memberID, Date beginningDate) {
		return this.borrowDAO.remove(itemID, memberID, beginningDate);
	}

	/**
	 * Edition d'un nouvel emprunt dans la base de données.
	 * 
	 * @param itemID
	 *            : l'identifiant de l'exemplaire;
	 * @param memberID
	 *            : l'identifiant du membre;
	 * @param dateFin
	 *            : date de fin de l'emprunt. Doit être supérieur à dateDebut
	 *            (genious);
	 * @param dateDebut
	 *            : date de debut de l'emprunt. Doit être inférieur à dateFin;
	 * @return Un emprunt modifié.
	 * 
	 */

	public Borrow editBorrow(Item item, Member member, Date beginningDate,
			Date endingDate, Extension extension) {
		Borrow borrow = new Borrow(item, member, beginningDate, endingDate,
				extension);
		return this.borrowDAO.edit(borrow) ? borrow : null;
		// Redondance avec le member et l'item dans borrow ?
	}

	/**
	 * Récupération d'un borrow.
	 * 
	 * @param id
	 *            l'identifiant du borrow.
	 * @return un borrow qui correspond à un id.
	 */

	public Borrow getBorrow(int itemID, int memberID, Date beginningDate) {
		return this.borrowDAO.get(itemID, memberID, beginningDate);
	}

	/**
	 * Récupération de tous les emprunts.
	 * 
	 * @param vide
	 *            .
	 * @return une liste des emprunts.
	 */
	public List<Borrow> getBorrows() {
		return this.borrowDAO.getBorrows();
	}

}
