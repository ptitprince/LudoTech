package model.pojos;

import java.util.Date;

/**
 * Classe représentant le contexte actuel au sujet d'un membre, c'est a dire le
 * statut qu'il a par rapport à la ludothèque (s'il est actuellement en retard,
 * s'il a payé sa cotisation...)
 */
public class MemberContext {

	/**
	 * L'identifiant unique du contexte d'un membre
	 */
	private int id;

	/**
	 * Le nombre de retards pour le retour de prêts
	 */
	private int nbDelays;

	/**
	 * Le nombre de réservations annulées
	 */
	private int nbFakeBookings;

	/**
	 * La date de la dernière cotisation
	 */
	private Date lastSubscriptionDate;

	/**
	 * A le droit d'emprunter des jeux
	 */
	private boolean canBorrow;

	/**
	 * A le droit de réserver des jeux
	 */
	private boolean canBook;

	/**
	 * Constructeur du contexte d'un membre en fournissant un identifiant
	 * 
	 * @param id
	 *            Identifiant unique du contexte d'un membre
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
	 */
	public MemberContext(int id, int nbDelays, int nbFakeBookings, Date lastSubscriptionDate, boolean canBorrow,
			boolean canBook) {
		this.id = id;
		this.nbDelays = nbDelays;
		this.nbFakeBookings = nbFakeBookings;
		this.lastSubscriptionDate = lastSubscriptionDate;
		this.canBorrow = canBorrow;
		this.canBook = canBook;
	}

	/**
	 * Constructeur du contexte d'un membre sans fournir d'identifiant
	 * 
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
	 */
	public MemberContext(int nbDelays, int nbFakeBookings, Date lastSubscriptionDate, boolean canBorrow,
			boolean canBook) {
		this.nbDelays = nbDelays;
		this.nbFakeBookings = nbFakeBookings;
		this.lastSubscriptionDate = lastSubscriptionDate;
		this.canBorrow = canBorrow;
		this.canBook = canBook;
	}

	/**
	 * @return Identifiant unique du contexte d'un membre
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            Identifiant unique du contexte d'un membre
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Le nombre de retards pour le retour de prêts
	 */
	public int getNbDelays() {
		return nbDelays;
	}

	/**
	 * @param nbDelays
	 *            Le nombre de retards pour le retour de prêts
	 */
	public void setNbDelays(int nbDelays) {
		this.nbDelays = nbDelays;
	}

	/**
	 * @return Le nombre de réservations annulées
	 */
	public int getNbFakeBookings() {
		return nbFakeBookings;
	}

	/**
	 * @param nbFakeBookings
	 *            Le nombre de réservations annulées
	 */
	public void setNbFakeBookings(int nbFakeBookings) {
		this.nbFakeBookings = nbFakeBookings;
	}

	/**
	 * @return La date de la dernière cotisation
	 */
	public Date getLastSubscriptionDate() {
		return lastSubscriptionDate;
	}

	/**
	 * @param lastSubscriptionDate
	 *            La date de la dernière cotisation
	 */
	public void setLastSubscriptionDate(Date lastSubscriptionDate) {
		this.lastSubscriptionDate = lastSubscriptionDate;
	}

	/**
	 * @return A le droit d'emprunter des jeux
	 */
	public boolean canBorrow() {
		return canBorrow;
	}

	/**
	 * @param canBorrow
	 *            A le droit d'emprunter des jeux
	 */
	public void setCanBorrow(boolean canBorrow) {
		this.canBorrow = canBorrow;
	}

	/**
	 * @return A le droit de réserver des jeux
	 */
	public boolean canBook() {
		return canBook;
	}

	/**
	 * @param canBook
	 *            A le droit de réserver des jeux
	 */
	public void setCanBook(boolean canBook) {
		this.canBook = canBook;
	}

}
