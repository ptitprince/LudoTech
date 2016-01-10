package backend.services;

import java.util.Date;

import backend.DAOs.MemberContextDAO;
import backend.POJOs.MemberContext;

/**
 * Expose des services de traitement sur des contextes d'adhérents 
 */
public class MemberContextServices {

	/**
	 * Objet d'accès aux données de type MemberContext
	 */
	private final MemberContextDAO memberContextDAO;
	
	public MemberContextServices() {
		this.memberContextDAO = new MemberContextDAO();
	}	
	
	public void oneMoreNbFakeBooking(int idMemberContext)
	{
		memberContextDAO.editNbFakeBook(idMemberContext, (memberContextDAO.getNbFakeBooks(idMemberContext)+1));
	}
	
	
	
	
	/**
	 * Modifier un contexte d'adhérent existant dans la base de données
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
	
	
	
	public void setNbDelays(int nbDelays)
	{
		nbDelays = nbDelays + 1;
	}
	/**
	 * On incrémente la valeur du nombre de retard
	 * @param memberContext le contexte d'un membre
	 */
	public void addNbDelays(MemberContext memberContext) {
		memberContext.setNbDelays(memberContext.getNbDelays() +1);
		this.memberContextDAO.edit(memberContext);
	}
	
	public MemberContext editMemberContext(int id, int nbDelays, int nbFakeBookings, Date lastSubscriptionDate, boolean canBorrow, boolean canBook) {
		MemberContext editableMemberContext = new MemberContext(id, nbDelays, nbFakeBookings, lastSubscriptionDate, canBorrow, canBook);
		return this.memberContextDAO.edit(editableMemberContext) ? editableMemberContext : null;
	}
	
	/**
	 * Supprimer un contexte d'adhérent existant dans la base de données
	 * @param id
	 *            L'identifiant unique du contexte d'un membre
	 * @return True si la suppression c'est bien passée, sinon False
	 */
	public boolean removeMemberContext(int id) {
		return memberContextDAO.remove(id);
	}
	
}