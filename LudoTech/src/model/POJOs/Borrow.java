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
	//private Item item ; ref exemplaire , à faire.
	
	/**
	 * L'utilisateur qui a fait l'emprunt (Member pas encore fait au 27/11).
	 */
	private Member member;
	
	/**
	 * Date de début de l'emprunt. besoin classe calendar pour date ?
	 */
	private Date dateDebut;
	
	/**
	 * Date de fin de l'emprunt (théorique).
	 */
	private Date dateFin;
	
	/**
	 * État de l'emprunt (en cours, en retard, ou fini en retard). Enumération à faire. Devra être supprimé si terminé sans encombres.
	 */
	private String borrowEtat;
	
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

	/* public Game getItem() {
		return copy;
	} */

	/* public void setItem(Item item) {
		this.item = item;
	} */

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getBorrowEtat() {
		return borrowEtat;
	}

	public void setBorrowEtat(String borrowEtat) {
		this.borrowEtat = borrowEtat;
	}

	public boolean isBorrowAvailable() {
		return borrowAvailable;
	}

	public void setBorrowAvailable(boolean borrowAvailable) {
		this.borrowAvailable = borrowAvailable;
	}

}
