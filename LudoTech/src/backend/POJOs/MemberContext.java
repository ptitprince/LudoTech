package backend.POJOs;

import java.util.Date;

/**
 * Classe représentant le contexte actuel d'un adhérent (son statut par rapport à la ludothèque)
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
	 * Construit un contexte de membre avec un identifiant connu
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
	 * Construit un contexte de member sans identifiant
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNbDelays() {
		return nbDelays;
	}

	public void setNbDelays(int nbDelays) {
		this.nbDelays = nbDelays;
	}

	public int getNbFakeBookings() {
		return nbFakeBookings;
	}

	public void setNbFakeBookings(int nbFakeBookings) {
		this.nbFakeBookings = nbFakeBookings;
	}

	public Date getLastSubscriptionDate() {
		return lastSubscriptionDate;
	}

	public void setLastSubscriptionDate(Date lastSubscriptionDate) {
		this.lastSubscriptionDate = lastSubscriptionDate;
	}

	public boolean canBorrow() {
		return canBorrow;
	}

	public void setCanBorrow(boolean canBorrow) {
		this.canBorrow = canBorrow;
	}

	public boolean canBook() {
		return canBook;
	}

	public void setCanBook(boolean canBook) {
		this.canBook = canBook;
	}

}
