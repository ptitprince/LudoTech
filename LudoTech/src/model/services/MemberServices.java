package model.services;

import java.util.Date;
import java.util.List;

import model.DAOs.MemberDAO;
import model.DAOs.MemberContextDAO;
import model.POJOs.Member;
import model.POJOs.MemberContext;

/**
 * Propose des fonctionnalités pour l'authentification dans l'application
 */
public class MemberServices {

	/**
	 * Objet d'accés aux données de type Member (adhérents)
	 */
	private MemberDAO memberDAO;
	

	/**
	 * Objet d'accés aux données de type MemberContext (adhérents)
	 */
	private MemberContextDAO memberContextDAO;

	/**
	 * Construit un nouveau service pour l'authentification
	 */
	public MemberServices() {
		this.memberDAO = new MemberDAO();
		this.memberContextDAO = new MemberContextDAO();
	}

	/**
	 * Vérifie l'autorisation la présence d'un adhérent en fonction d'un couple
	 * pseudo/mot de passe
	 * 
	 * @param pseudo
	 *            Le pseudo d'un possible adhérent
	 * @param password
	 *            Le mot de passe d'un possible adhérent
	 * @return Si un adhérent est trouvé avec le couple pseudo/mot de passe,
	 *         alors retourne un tableau d'entiers de taille 2, avec en premier
	 *         élément l'identifiant de l'adhérent et en deuxième élément le
	 *         fait qu'il soit un adminitrateur (valeur 1) ou non (valeur 0),
	 *         sinon retourne null
	 */
	public int[] checkAccess(String pseudo, String password) {
		Member member = this.memberDAO.exist(pseudo, password);
		if (member != null) {
			int[] memberData = new int[2];
			memberData[0] = member.getMemberID();
			memberData[1] = (member.getIsAdmin()) ? 1 : 0;
			return memberData;
		} else {
			return null;
		}
	}

	/**
	 * Vérifie si un certain adhérent est administrateur
	 * 
	 * @param memberID
	 *            L'identifiant d'un adhérent existant
	 * @return True si l'adhérent est administrateur, False sinon
	 */
	public boolean isAdmin(int memberID) {
		return this.memberDAO.isAdmin(memberID);
	}



	
	public List<Member> getMemberList()
	{
		return this.memberDAO.getMemberList();
	}
	
	
	
	
	

	/**
	 * Créé un nouveau membre
	 * 
	 * @param firstName
	 *			Prénom d'un membre
	 * @param lastName
	 *          Nom de famille d'un membre
	 * @param pseudo
	 *          Pseudo d'un membre
	 * @param password
	 *          Mot d'un passe d'un membre pour se connecter à l'application
	 * @param isAdmin
	 *          True si le membre est administrateur, False s'il est simple utilisateur
	 * @param birthDate
	 *          Date de naissance d'un membre
	 * @param phoneNumber
	 *          Numero de télephone d'un membre
	 * @param email
	 *           L'adresse mail d'un membre
	 * @param streetAddress
	 *           L'adresse postale d'un membre
	 * @param postalCode
	 *           Le code postal d'un membre
	 * @param city  
	 *           La ville d'un membre
	 * @param nbDelays          
	 *           Nombre de retards lors de la remise d'un prêt d'un membre
	 * @param nbFakeBookings          
	 *           Nombre de fausse demande de réservation
	 * @param lastSubscriptionDate          
	 *           Date de dernier payement de cotisation d'un membre
	 * @param canBorrow          
	 *           True si le membre est autorisé à emprunter False sinon
	 * @param canBook          
	 *           True si le membre est autorisé à réserver un jeu, False sinon
	 * @return Un objet de type Member si le membre a bien été ajouté, sinon null
	 */
	public Member addMember(String firstName, String lastName, String pseudo, String password, boolean isAdmin, Date birthDate,
			String phoneNumber, String email, String streetAddress, String postalCode, String city, int nbDelays, 
			int nbFakeBookings, Date lastSubscriptionDate, boolean canBorrow, boolean canBook) {
		
		MemberContext memberContext = new MemberContext(nbDelays, nbFakeBookings, lastSubscriptionDate, canBorrow, canBook );
		Member member = new Member(firstName, lastName, pseudo, password, isAdmin, birthDate,
				phoneNumber, email, streetAddress, postalCode, city, memberContext);
		this.memberContextDAO.add(memberContext);
		
		return this.memberDAO.add(member, memberContext.getId()) ? member : null;
	}


	
	
// PARTIE PROFILE	
	
	/**
	 * Trouve un membre existant
	 * 
	 * @param memberID
	 *            L'identifiant du membre
	 * @return Un objet de type Member si le membre a bien été trouvé, sinon null
	 */
	public Member getMember(int memberID) {
		return this.memberDAO.get(memberID);
	}

	
	/**
	 * Enregistre la modification d'un membre
	* 
	 * @param firstName
	 *			Prénom d'un membre
	 * @param lastName
	 *          Nom de famille d'un membre
	 * @param pseudo
	 *          Pseudo d'un membre
	 * @param password
	 *          Mot d'un passe d'un membre pour se connecter à l'application
	 * @param isAdmin
	 *          True si le membre est administrateur, False s'il est simple utilisateur
	 * @param birthDate
	 *          Date de naissance d'un membre
	 * @param phoneNumber
	 *          Numero de télephone d'un membre
	 * @param email
	 *           L'adresse mail d'un membre
	 * @param streetAddress
	 *           L'adresse postale d'un membre
	 * @param postalCode
	 *           Le code postal d'un membre
	 * @param city  
	 *           La ville d'un membre
	 * @param nbDelays          
	 *           Nombre de retards lors de la remise d'un prêt d'un membre
	 * @param nbFakeBookings          
	 *           Nombre de fausse demande de réservation
	 * @param lastSubscriptionDate          
	 *           Date de dernier payement de cotisation d'un membre
	 * @param canBorrow          
	 *           True si le membre est autorisé à emprunter False sinon
	 * @param canBook          
	 *           True si le membre est autorisé à réserver un jeu, False sinon
	 * @return Un objet de type Game si le jeu a bien été ajouté, sinon null
	 * */
	
	public Member saveMember(int memberID, String firstName, String lastName, String pseudo, String password, boolean isAdmin, Date birthDate,
			String phoneNumber, String email, String streetAddress, String postalCode, String city) {
		Member member = new Member(memberID, firstName, lastName, pseudo, password, isAdmin, birthDate, phoneNumber, email, streetAddress, postalCode, city);
		int contextID = this.memberDAO.getMemberContextID(memberID);
		return this.memberDAO.edit(member, contextID) ? member : null;
		
	}
	
	
	
}
