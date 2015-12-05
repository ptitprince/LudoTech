package model.services;

import java.util.Date;

import model.DAOs.MemberContextDAO;
import model.pojos.MemberContext;

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
	
	/**
	 * Obtenir un contexte d'adhérent
	 * @param id
	 *            L'identifiant unique du contexte d'un membre
	 * @return Le contexte d'adhérent identifier par "id"
	 */
	public MemberContext getMemberContext(int id) {
		return memberContextDAO.get(id);
	}
	
	/**
	 * Ajouter un nouveau contexte d'adhérent à la base de données
	 * @param nbDelays
	 *            Le nombre de retards pour le retour de prêts
	 * @param nbFakeBookings
	 *            Le nombre de réservations annulées
	 * @param date
	 *            La date de la dernière cotisation
	 * @param canBorrow
	 *            A le droit d'emprunter des jeux
	 * @param canBook
	 *            A le droit de réserver des jeux
	 * @return Le contexte d'adhérent qui vient d'être créé, avec un identifiant créé automatiquement à l'enregistrement
	 */
	public MemberContext addMemberContext(int nbDelays, int nbFakeBookings, Date date, boolean canBorrow, boolean canBook) {
		MemberContext newMemberContext = new MemberContext(nbDelays, nbFakeBookings, date, canBorrow, canBook);
		return this.memberContextDAO.add(newMemberContext) ? newMemberContext : null;
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
