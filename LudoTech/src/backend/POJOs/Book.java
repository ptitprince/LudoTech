package backend.POJOs;

import java.util.Date;

/**
 * Représentation d'une réservation
 */
public class Book {

	/**
	 * Exemplaire a reserver
	 */
	private Item item;

	/**
	 * Membre qui reserve
	 */
	private Member member;

	/**
	 * Date de début de la réservation
	 */
	private Date startDate;
	
	/**
	 * Date de fin de la réservation
	 */
	private Date endDate;

	/**
	 * L'extension qui peut être reservée avec le jeu (peut être null)
	 */
	private Extension extension;

	/**
	 * Créé une nouvelle réservation en associant un exemplaire (et potentiellement une extension) à un adhérent pour une période données
	 * @param item L'exemplaire réservé non null
	 * @param member L'adhérent qui réserve non null
	 * @param startDate La date de début de réservation
	 * @param endDate La date de fin de réservation
	 * @param extension L'extension qui peut être ajoutée à la réservation (peut-être null)
	 */
	public Book(Item item, Member member, Date startDate, Date endDate, Extension extension) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.member = member;
		this.item = item;
		this.extension = extension;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

}
