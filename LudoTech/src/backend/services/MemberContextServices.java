package backend.services;

import java.util.Date;

import backend.DAOs.MemberContextDAO;
import backend.POJOs.MemberContext;

/**
 * Expose des services de traitement sur des contextes d'adhérents
 */
public class MemberContextServices {

	/**
	 * Objet de manipulation de la base de données pour les contextes
	 * d'adhérents
	 */
	private final MemberContextDAO memberContextDAO;

	/**
	 * Construit un nouveau service pour les contextes d'adhérents
	 */
	public MemberContextServices() {
		this.memberContextDAO = new MemberContextDAO();
	}

	/**
	 * Augmente de 1 le nombre de réservations annulées d'un adhérent
	 * 
	 * @param idMemberContext
	 *            L'identifiant unique du contexte de l'adhérent désiré
	 */
	public void incrementNbFakeBookings(int idMemberContext) {
		memberContextDAO.editNbFakeBook(idMemberContext, (memberContextDAO.getNbFakeBooks(idMemberContext) + 1));
	}

	/**
	 * Augmente de 1 le nombre de retards d'un adhérent
	 * 
	 * @param memberContext
	 *            Le contexte non null de l'adhérent désiré
	 */
	public void incrementNbDelays(MemberContext memberContext) {
		memberContext.setNbDelays(memberContext.getNbDelays() + 1);
		this.memberContextDAO.edit(memberContext);
	}

	/**
	 * Modifier un contexte d'adhérent existant dans la base de données
	 * 
	 * @param id
	 *            L'identifiant unique du contexte d'un membre
	 * @param nbDelays
	 *            Le nombre de retards pour le retour de prêts
	 * @param nbFakeBookings
	 *            Le nombre de réservations annulées
	 * @param lastSubscriptionDate
	 *            La date de la dernière cotisation
	 * @param canBorrow
	 *            A le droit d'emprunter des jeux
	 * @param canBook
	 *            A le droit de réserver des jeux
	 * @return Le contexte d'adhérent modifié
	 */
	public MemberContext editMemberContext(int id, int nbDelays, int nbFakeBookings, Date lastSubscriptionDate,
			boolean canBorrow, boolean canBook) {
		MemberContext editableMemberContext = new MemberContext(id, nbDelays, nbFakeBookings, lastSubscriptionDate,
				canBorrow, canBook);
		return this.memberContextDAO.edit(editableMemberContext) ? editableMemberContext : null;
	}

	/**
	 * Supprimer un contexte d'adhérent existant dans la base de données
	 * 
	 * @param id
	 *            L'identifiant unique du contexte d'un membre
	 * @return True si la suppression c'est bien passée, sinon False
	 */
	public boolean removeMemberContext(int id) {
		return memberContextDAO.remove(id);
	}

}
