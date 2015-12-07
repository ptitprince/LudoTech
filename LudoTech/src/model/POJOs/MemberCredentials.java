package model.POJOs;

public class MemberCredentials {
	
	/**
	 * L'identifiant des droits
	 */
	private int memberCredentialsID;
	
	/**
	 * Le droit d'enregistrer une reservation 
	 */
	private boolean saveBooking;
	
	/**
	 * Le droit d'enregistrer un emprunt
	 */
	
	private boolean saveBorrowing;
	
	/**
	 * Le droit de gerer les jeux
	 */
	private boolean manageGames;
	
	/**
	 * Le droit de gerer les membres
	 */
	private boolean manageMembers;
	
	/**
	 * Le droit de gerer les etats de membres
	 */
	private boolean editContext;
	
	/**
	 * Le droit de gerer les droits de membres
	 */
	private boolean editCredentials;
	
	/**
	 * Le droit de gerer les parametres du programme
	 */
	private boolean editParameters;
	
	
	public MemberCredentials(boolean saveBorrowing, boolean saveBooking, boolean manageGames, boolean manageMembers,
			boolean editContext, boolean editCredentials, boolean editParameters) {
		super();
		this.saveBorrowing = saveBorrowing;
		this.saveBooking = saveBooking;
		this.manageGames = manageGames;
		this.manageMembers = manageMembers;
		this.editContext = editContext;
		this.editCredentials = editCredentials;
		this.editParameters = editParameters;
	}

	public MemberCredentials(int memberCredentialsID, boolean saveBorrowing, boolean saveBooking, boolean manageGames,
			boolean manageMembers, boolean editContext, boolean editCredentials, boolean editParameters) {
		super();
		this.memberCredentialsID = memberCredentialsID;
		this.saveBorrowing = saveBorrowing;
		this.saveBooking = saveBooking;
		this.manageGames = manageGames;
		this.manageMembers = manageMembers;
		this.editContext = editContext;
		this.editCredentials = editCredentials;
		this.editParameters = editParameters;
	}

	
	
	public int getId() {
		return memberCredentialsID;
	}

	public void setMemberCredentialsID(int memberCredentialsID) {
		this.memberCredentialsID = memberCredentialsID;
	}

	public boolean isSaveBorrowing() {
		return saveBorrowing;
	}

	public void setSaveBorrowing(boolean saveBorrowing) {
		this.saveBorrowing = saveBorrowing;
	}

	public boolean isSaveBooking() {
		return saveBooking;
	}

	public void setSaveBooking(boolean saveBooking) {
		this.saveBooking = saveBooking;
	}

	public boolean isManageGames() {
		return manageGames;
	}

	public void setManageGames(boolean manageGames) {
		this.manageGames = manageGames;
	}

	public boolean isManageMembers() {
		return manageMembers;
	}

	public void setManageMembers(boolean manageMembers) {
		this.manageMembers = manageMembers;
	}

	public boolean isEditContext() {
		return editContext;
	}

	public void setEditContext(boolean editContext) {
		this.editContext = editContext;
	}

	public boolean isEditCredentials() {
		return editCredentials;
	}

	public void setEditCredentials(boolean editCredentials) {
		this.editCredentials = editCredentials;
	}

	public boolean isEditParameters() {
		return editParameters;
	}

	public void setEditParameters(boolean editParameters) {
		this.editParameters = editParameters;
	}

	}
