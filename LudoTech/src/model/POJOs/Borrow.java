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
	 * Le jeu qui a été emprunté.
	 */
	private Game game;
	
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
	private boolean borrowDisponible;
	
	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

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

	public boolean isBorrowDisponible() {
		return borrowDisponible;
	}

	public void setBorrowDisponible(boolean borrowDisponible) {
		this.borrowDisponible = borrowDisponible;
	}

}
