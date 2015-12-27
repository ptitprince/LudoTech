package model.POJOs;
import java.util.Date;
/**
 * Représentation d'un emprunt (conformément à la table Borrow)
 * 
 * @author Yves Longchamp
 *
 */

public class Borrow {
	
	/**
	 * Identifiant de l'emprunt.
	 */
	private int borrowId;
	
	/**
	 * L'exemplaire qui a été emprunté.
	 */
	private Item item;
	
	/**
	 * L'utilisateur qui a fait l'emprunt.
	 */
	private Member member;
	
	/**
	 * Date de début de l'emprunt. besoin classe calendar pour date ?
	 */
	private Date beginningDate;
	
	/**
	 * Date de fin de l'emprunt (théorique).
	 */
	private Date endingDate;
	
	/**
	 * État de l'emprunt (en cours, en retard, ou fini en retard). Enumération à faire. Devra être supprimé si terminé sans encombres.
	 */
	private String borrowState;
	
	/**
	 * Savoir si le jeu est disponible, avec un booléen.
	 */
	private boolean borrowAvailable;
	
	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
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

	public Date getBeginningDate() {
		return beginningDate;
	}

	public void setDateDebut(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public String getBorrowState() {
		return borrowState;
	}

	public void setBorrowState(String borrowState) {
		this.borrowState = borrowState;
	}

	public boolean isBorrowAvailable() {
		return borrowAvailable;
	}

	public void setBorrowAvailable(boolean borrowAvailable) {
		this.borrowAvailable = borrowAvailable;
	}
	
	/**
	 * Construit et retourne un nouvel emprunt dont l'identifiant est connu.
	 * @param borrowID L'identifiant de l'emprunt.
	 * @param item l'exemplaire concerné.
	 * @param member le membre concerné.
	 * @param beginningDate La date de début du prêt.
	 * @param endingDate La date de fin du prêt.
	 * @param borrowState L'état de l'emprunt.
	 * @param borrowAvailable La disponibilité du jeu. 
	 */
	public Borrow(int borrowID, Item item, Member member, Date beginningDate, Date endingDate, String borrowState, boolean borrowAvailable) {
		this.borrowId = borrowID;
		this.item = item;
		this.member = member;
		this.beginningDate = beginningDate;
		this.endingDate = endingDate;
		this.borrowState = borrowState;
		this.borrowAvailable = borrowAvailable;
		
	}

	/**
	 * Construit et retourne un nouvel emprunt dont l'identifiant inconnu
	 * @param item l'exemplaire concerné.
	 * @param member le membre concerné.
	 * @param beginningDate La date de début du prêt.
	 * @param endingDate La date de fin du prêt.
	 * @param borrowState L'état de l'emprunt.
	 * @param borrowAvailable La disponibilité du jeu. 
	 */
	public Borrow(Item item, Member member, Date beginningDate, Date endingDate, String borrowState, boolean borrowAvailable) {
		this.item = item; //item ou bien juste l'ID ? 
		this.member = member; 
		this.beginningDate = beginningDate;
		this.endingDate = endingDate;
		this.borrowState = borrowState;
		this.borrowAvailable = borrowAvailable;
		
	}

}
