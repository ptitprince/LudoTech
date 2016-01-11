package backend.POJOs;

import java.util.Date;

/**
 * Représente un adhérent
 */
public class Member {

	/**
	 * L'identifiant de l'adhérent
	 */
	private int memberID;

	/**
	 * Le nom de l'adhérent
	 */
	private String lastName;

	/**
	 * Le prénom de l'adhérent
	 **/

	private String firstName;

	/**
	 * Le pseudo de l'adhérent
	 */
	private String pseudo;

	/**
	 * Le mot de passe de l'adhérent
	 */
	private String password;

	/**
	 * Détermine si l'adhérent est un administrateur ou non
	 */
	private boolean isAdmin;

	/**
	 * La date d'anniversaire de l'adhérent
	 */
	private Date birthDate;

	/**
	 * Le téléphone de l'adhérent
	 */
	private String phoneNumber;

	/**
	 * Le mail de l'adhérent
	 */
	private String email;

	/**
	 * L'adresse de l'adhérent
	 */
	private String streetAddress;

	/**
	 * Le code postal de l'adhérent
	 */
	private String postalCode;

	/**
	 * La ville de l'adhérent
	 */
	private String city;

	/**
	 * Le contexte de l'adhérent
	 */
	private MemberContext memberContext;

	/**
	 * Contruit un adhérent sans identifiant avec des informations et un
	 * contexte
	 * 
	 * @param firstName
	 *            Le prénom de l'adhérent
	 * @param lastName
	 *            Le nom de l'adhérent
	 * @param pseudo
	 *            Le pseudo de l'adhérent
	 * @param password
	 *            Le mot de passe de l'adhérent
	 * @param isAdmin
	 *            Le fait que l'adhérent soit administrateur ou non
	 * @param birthDate
	 *            La date de naissance de l'adhérent
	 * @param phoneNumber
	 *            Le numéro de téléphone de l'adhérent
	 * @param email
	 *            L'addresse email de l'adhérent
	 * @param streetAddress
	 *            L'addresse physique de l'adhérent
	 * @param postalCode
	 *            Le code postal de l'adhérent
	 * @param city
	 *            La ville de l'adhérent
	 * @param memberContext
	 *            Le contexte non null de l'adhérent
	 */
	public Member(String firstName, String lastName, String pseudo, String password, boolean isAdmin, Date birthDate,
			String phoneNumber, String email, String streetAddress, String postalCode, String city,
			MemberContext memberContext) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.pseudo = pseudo;
		this.password = password;
		this.isAdmin = isAdmin;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.memberContext = memberContext;

	}

	/**
	 * Contruit un adhérent avec un identifiant connu, des informations et un
	 * contexte
	 * 
	 * @param memberID
	 *            L'identifiant unique de l'adhérent
	 * @param firstName
	 *            Le prénom de l'adhérent
	 * @param lastName
	 *            Le nom de l'adhérent
	 * @param pseudo
	 *            Le pseudo de l'adhérent
	 * @param password
	 *            Le mot de passe de l'adhérent
	 * @param isAdmin
	 *            Le fait que l'adhérent soit administrateur ou non
	 * @param birthDate
	 *            La date de naissance de l'adhérent
	 * @param phoneNumber
	 *            Le numéro de téléphone de l'adhérent
	 * @param email
	 *            L'addresse email de l'adhérent
	 * @param streetAddress
	 *            L'addresse physique de l'adhérent
	 * @param postalCode
	 *            Le code postal de l'adhérent
	 * @param city
	 *            La ville de l'adhérent
	 * @param memberContext
	 *            Le contexte non null de l'adhérent
	 */
	public Member(int memberID, String lastName, String firstName, String pseudo, String password, boolean isAdmin,
			Date birthDate, String phoneNumber, String email, String streetAddress, String postalCode, String city,
			MemberContext memberContext) {
		this.memberID = memberID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pseudo = pseudo;
		this.password = password;
		this.isAdmin = isAdmin;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.memberContext = memberContext;
	}

	/**
	 * Contruit un adhérent avec un identifiant connu, des informations et sans
	 * contexte
	 * 
	 * @param memberID
	 *            L'identifiant unique de l'adhérent
	 * @param firstName
	 *            Le prénom de l'adhérent
	 * @param lastName
	 *            Le nom de l'adhérent
	 * @param pseudo
	 *            Le pseudo de l'adhérent
	 * @param password
	 *            Le mot de passe de l'adhérent
	 * @param isAdmin
	 *            Le fait que l'adhérent soit administrateur ou non
	 * @param birthDate
	 *            La date de naissance de l'adhérent
	 * @param phoneNumber
	 *            Le numéro de téléphone de l'adhérent
	 * @param email
	 *            L'addresse email de l'adhérent
	 * @param streetAddress
	 *            L'addresse physique de l'adhérent
	 * @param postalCode
	 *            Le code postal de l'adhérent
	 * @param city
	 *            La ville de l'adhérent
	 */
	public Member(int memberID, String lastName, String firstName, String pseudo, String password, boolean isAdmin,
			Date birthDate, String phoneNumber, String email, String streetAddress, String postalCode, String city) {
		this.memberID = memberID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pseudo = pseudo;
		this.password = password;
		this.isAdmin = isAdmin;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public MemberContext getMemberContext() {
		return memberContext;
	}

	public void setMemberContext(MemberContext memberContext) {
		this.memberContext = memberContext;
	}

	public String toString() {
		return this.firstName + " " + this.lastName;
	}

}