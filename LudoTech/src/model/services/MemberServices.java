package model.services;

import java.util.Date;
import java.util.List;

import model.DAOs.MemberDAO;
import model.POJOs.Member;

/**
 * Propose des fonctionnalités pour l'authentification dans l'application
 */
public class MemberServices {

	/**
	 * Objet d'accés aux données de type Member (adhérents)
	 */
	private MemberDAO memberDAO;

	/**
	 * Construit un nouveau service pour l'authentification
	 */
	public MemberServices() {
		this.memberDAO = new MemberDAO();
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

	public Member saveMember(int memberID, String firstName, String lastName, String pseudo, String password, boolean isAdmin, Date birthDate,
			String phoneNumber, String email, String streetAddress, String postalCode, String city) {
		Member member = new Member(memberID, firstName, lastName, pseudo, password, isAdmin, birthDate, phoneNumber, email, streetAddress, postalCode, city);
		int contextID = this.memberDAO.getMemberContextID(memberID);
		return this.memberDAO.edit(member, contextID) ? member : null;
		
	}
	
	public List<Member> getMemberList()
	{
		return this.memberDAO.getMemberList();
	}
	
}
