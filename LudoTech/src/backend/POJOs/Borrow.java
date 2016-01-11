package backend.POJOs;

import java.util.Date;

/**
 * Représentation d'un emprunt
 */
public class Borrow {
	
	/**
	 * L'exemplaire a emprunter
	 */
	private Item item;
	
	/**
	 * L'adhérent qui emprunte
	 */
	private Member member;
	
	/**
	 * Date de début de l'emprunt
	 */
	private Date startDate;
	
	/**
	 * Date de fin de l'emprunt
	 */
	private Date endDate;
	
	/**
	 * L'extension qui peut être empruntée avec le jeu (peut être null)
	 */
	private Extension extension;
	
	/**
	 * Construit un nouvel emprunt en associant un exemplaire (et potentiellement une extension) à un adhérent pour une période données
	 * @param item L'exemplaire emprunté non null
	 * @param member L'adhérent qui emprunte non null
	 * @param startDate La date de début d'emprunt
	 * @param endDate La date de fin d'emprunt
	 * @param extension L'extension qui peut être ajoutée à l'emprunt (peut-être null)
	 */
	public Borrow(Item item, Member member, Date startDate, Date endDate, Extension extension) {
		this.item = item;
		this.member = member;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public void setMemberId(Member member) {
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

	public void setEndDATE(Date endDate) {
		this.endDate = endDate;
	}
	
	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

}
