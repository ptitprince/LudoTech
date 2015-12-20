package model.POJOs;

import java.util.Date;

public class Member {

	/**
	 * L'identifiant du membre
	 */
	private int memberID;
	private MemberContext memberContext;
	private MemberCredentials memberCredentials;

	public MemberContext getMemberContext() {
		return memberContext;
	}

	public void setMemberContext(MemberContext memberContext) {
		this.memberContext = memberContext;
	}

	public MemberCredentials getMemberCredentials() {
		return memberCredentials;
	}

	public void setMemberCredentials(MemberCredentials memberCredentials) {
		this.memberCredentials = memberCredentials;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
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
	
	public int getMemberContextID() {
		return memberContextID;
	}

	public void setMemberContextID(int id) {
		this.memberContext.setId(id);
	}

	public int getMemberCredentialsID() {
		return memberCredentialsID;
	}

	public void setMemberCredentialsID(int id) {
		this.memberCredentials.setMemberCredentialsID(id);
	}

	public Member(String lastName, String firstName, String pseudo, Date birthDate, int phoneNumber, String email, String streetAddress, String postalCode, String city,
			MemberContext memberContext, MemberCredentials memberCredentials) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.pseudo = pseudo;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.memberContext = memberContext;
		this.memberCredentials = memberCredentials;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
	}

	public Member(int memberID, String lastName, String firstName, String pseudo, Date birthDate, int phoneNumber,
			String email, String streetAddress, String postalCode, String city, MemberContext memberContext, MemberCredentials memberCredentials) {
		this.memberID = memberID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pseudo = pseudo;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.memberContext = memberContext;
		this.memberCredentials = memberCredentials;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
	}

	public Member(int memberID, String lastName, String firstName, String pseudo, Date birthDate, int phoneNumber,
			String email, String streetAddress, String postalCode, String city) {
		this.memberID = memberID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pseudo = pseudo;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
	}

	/**
	 * Le nom du membre
	 */
	private String lastName;

	/**
	 * Le prénom du membre
	 **/

	private String firstName;

	/**
	 * Le pseudo du membre
	 */
	private String pseudo;

	/**
	 * Le prénom du membre
	 **/

	private Date birthDate;

	/**
	 * Le téléphone du membre
	 **/

	private int phoneNumber;

	/**
	 * Le mail du membre
	 **/

	private String email;

	
	/**
	 * L'adresse en elle même, le nom de la rue, le bâtiment, le numéro de la rue, etc...
	 */
	private String streetAddress;
	
	/**
	 * Code postal de l'adresse.
	 */
	private String postalCode;
	
	/**
	 * La ville de l'adresse.
	 */
	private String city;
	
	
	/**
	 * L'identifiant de la table MemberContext
	 */
	private int memberContextID;

	/**
	 * L'identifiant de la table MemberCredentials
	 */
	private int memberCredentialsID;



	public void setId(int ID) {
		this.memberID = ID;
	}

}