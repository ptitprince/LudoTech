package backend.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import backend.DAOs.BookDAO;
import backend.DAOs.BorrowDAO;
import backend.DAOs.MemberContextDAO;
import backend.DAOs.MemberDAO;
import backend.POJOs.Member;
import backend.POJOs.MemberContext;

/**
 * Expose des services de traitements sur des adhérents
 */
public class MemberServices {

	/**
	 * Objet de manipulation de la base de données pour les adhérents
	 */
	private MemberDAO memberDAO;

	/**
	 * Objet de manipulation de la base de données pour les contextes
	 * d'adhérents
	 */
	private MemberContextDAO memberContextDAO;

	/**
	 * Objet de manipulation de la base de données pour les réservations
	 */
	private final BookDAO bookDAO;

	/**
	 * Objet de manipulation de la base de données pour les prêts
	 */
	private final BorrowDAO borrowDAO;

	/**
	 * Construit un nouveau service pour les adhérents
	 */
	public MemberServices() {
		this.memberDAO = new MemberDAO();
		this.memberContextDAO = new MemberContextDAO();
		this.bookDAO = new BookDAO();
		this.borrowDAO = new BorrowDAO();
	}

	/**
	 * Trouve un adhérent existant
	 * 
	 * @param memberID
	 *            L'identifiant du adhérent
	 * @return Un adhérent si trouvé, sinon null
	 */
	public Member getMember(int memberID) {
		return this.memberDAO.get(memberID);
	}

	/**
	 * Trouve le contexte d'un adhérent dont l'identifiant est passé en
	 * paramètre
	 * 
	 * @param memberID
	 *            L'identifiant du adhérent
	 * @return Un contexte d'adhérent si trouvé, sinon null
	 */
	public MemberContext getMemberContext(int memberID) {
		return this.memberDAO.get(memberID).getMemberContext();
	}

	/**
	 * Liste les adhérents éxistant selon un filtre
	 * 
	 * @param filter
	 *            Ensemble de couples <clef(String), valeur(String)> où la clef
	 *            est incluse dans la liste : first_name, last_name, pseudo.
	 * @return Une liste d'adhérents potentiellement vide respectant le filtre
	 */
	public List<Member> getMemberList(HashMap<String, String> filter) {
		return this.memberDAO.getAll(filter);
	}

	/**
	 * Vérifie l'autorisation la présence d'un adhérent en fonction d'un couple
	 * pseudo/mot de passe
	 * 
	 * @param pseudo
	 *            Le pseudo d'un possibl'adhérent
	 * @param password
	 *            Le mot de passe d'un possibl'adhérent
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
			memberData[1] = (member.isAdmin()) ? 1 : 0;
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

	public boolean canDeleteMember(int memberID) {
		return (!bookDAO.memberUsed(memberID) && !borrowDAO.memberUsed(memberID));
	}

	/**
	 * Créé un nouveau adhérent et son contexte
	 * 
	 * @param firstName
	 *            Prénom d'un adhérent
	 * @param lastName
	 *            Nom de famille d'un adhérent
	 * @param pseudo
	 *            Pseudo d'un adhérent
	 * @param password
	 *            Mot d'un passe d'un adhérent pour se connecter à l'application
	 * @param isAdmin
	 *            True si l'adhérent est administrateur, False s'il est simple
	 *            utilisateur
	 * @param birthDate
	 *            Date de naissance d'un adhérent
	 * @param phoneNumber
	 *            Numero de télephone d'un adhérent
	 * @param email
	 *            L'adresse mail d'un adhérent
	 * @param streetAddress
	 *            L'adresse postale d'un adhérent
	 * @param postalCode
	 *            Le code postal d'un adhérent
	 * @param city
	 *            La ville d'un adhérent
	 * @param nbDelays
	 *            Nombre de retards lors de la remise d'un prêt d'un adhérent
	 * @param nbFakeBookings
	 *            Nombre de fausse demande de réservation
	 * @param lastSubscriptionDate
	 *            Date de dernier payement de cotisation d'un adhérent
	 * @param canBorrow
	 *            True si l'adhérent est autorisé à emprunter False sinon
	 * @param canBook
	 *            True si l'adhérent est autorisé à réserver un jeu, False sinon
	 * @return Un adhérent si l'ajout a été fait, sinon null
	 */
	public Member addMember(String firstName, String lastName, String pseudo, String password, boolean isAdmin,
			Date birthDate, String phoneNumber, String email, String streetAddress, String postalCode, String city,
			int nbDelays, int nbFakeBookings, Date lastSubscriptionDate, boolean canBorrow, boolean canBook) {

		MemberContext memberContext = new MemberContext(nbDelays, nbFakeBookings, lastSubscriptionDate, canBorrow,
				canBook);
		Member member = new Member(firstName, lastName, pseudo, password, isAdmin, birthDate, phoneNumber, email,
				streetAddress, postalCode, city, memberContext);
		this.memberContextDAO.add(memberContext);

		return this.memberDAO.add(member, memberContext.getId()) ? member : null;
	}

	/**
	 * Enregistre la modification d'un adhérent
	 * 
	 * @param firstName
	 *            Prénom d'un adhérent
	 * @param lastName
	 *            Nom de famille d'un adhérent
	 * @param pseudo
	 *            Pseudo d'un adhérent
	 * @param password
	 *            Mot d'un passe d'un adhérent pour se connecter à l'application
	 * @param isAdmin
	 *            True si l'adhérent est administrateur, False s'il est simple
	 *            utilisateur
	 * @param birthDate
	 *            Date de naissance d'un adhérent
	 * @param phoneNumber
	 *            Numero de télephone d'un adhérent
	 * @param email
	 *            L'adresse mail d'un adhérent
	 * @param streetAddress
	 *            L'adresse postale d'un adhérent
	 * @param postalCode
	 *            Le code postal d'un adhérent
	 * @param city
	 *            La ville d'un adhérent
	 * @param nbDelays
	 *            Nombre de retards lors de la remise d'un prêt d'un adhérent
	 * @param nbFakeBookings
	 *            Nombre de fausse demande de réservation
	 * @param lastSubscriptionDate
	 *            Date de dernier payement de cotisation d'un adhérent
	 * @param canBorrow
	 *            True si l'adhérent est autorisé à emprunter False sinon
	 * @param canBook
	 *            True si l'adhérent est autorisé à réserver un jeu, False sinon
	 * @return Un adhérent si la modification a été modifiée, sinon null
	 */
	public Member editMember(int memberID, String firstName, String lastName, String pseudo, String password,
			boolean isAdmin, Date birthDate, String phoneNumber, String email, String streetAddress, String postalCode,
			String city) {
		Member member = new Member(memberID, firstName, lastName, pseudo, password, isAdmin, birthDate, phoneNumber,
				email, streetAddress, postalCode, city);
		int contextID = this.memberDAO.getMemberContextID(memberID);
		return this.memberDAO.edit(member, contextID) ? member : null;
	}

	/**
	 * Supprime un adhérent existant
	 * 
	 * @param memberID
	 *            L'identifiant de l'adhérent à supprimer
	 * @return True si l'adhérent a été supprimé, sinon False
	 */
	public boolean removeMember(int memberID) {
		return this.memberDAO.remove(memberID);
	}

}
