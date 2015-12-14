package model.POJOs;

import java.util.Date;

public class Member {

	/**
	 * L'identifiant du membre
	 */
	private int memberID;
	private MemberContext memberContext;
	private MemberCredentials memberCredentials;
	private PostalAddress postalAddress;

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

	public PostalAddress getPostalAddress() {
		return postalAddress;
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

	public int getPostalAddressID() {
		return postalAddress.getId();
	}

	public void setPostalAddress(PostalAddress postalAddress) {
		this.postalAddress = postalAddress;
	}

	public void setPostalAddressID(int id) {
		this.postalAddress.setId(id);
	}

	public Member(String lastName, String firstName, String pseudo, Date birthDate, int phoneNumber, String email,
			MemberContext memberContext, MemberCredentials memberCredentials, PostalAddress postalAddress) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.pseudo = pseudo;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.memberContext = memberContext;
		this.memberCredentials = memberCredentials;
		this.postalAddress = postalAddress;
	}

	public Member(int memberID, String lastName, String firstName, String pseudo, Date birthDate, int phoneNumber,
			String email, MemberContext memberContext, MemberCredentials memberCredentials,
			PostalAddress postalAddress) {
		super();
		this.memberID = memberID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pseudo = pseudo;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.memberContext = memberContext;
		this.memberCredentials = memberCredentials;
		this.postalAddress = postalAddress;
	}

	public Member(int memberID, String lastName, String firstName, String pseudo, Date birthDate, int phoneNumber,
			String email) {
		super();
		this.memberID = memberID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pseudo = pseudo;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
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
	 * L'identifiant de la table MemberContext
	 */
	private int memberContextID;

	/**
	 * L'identifiant de la table MemberCredentials
	 */
	private int memberCredentialsID;

	/**
	 * L'identifiant de la table PostalAddress
	 */
	private int postalAddressID;

	public void setId(int ID) {
		this.memberID = ID;
	}

}