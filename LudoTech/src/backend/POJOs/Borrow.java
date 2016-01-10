package backend.POJOs;
import java.util.Date;
/**
 * Représentation d'un emprunt (conformément à la table Borrow)
 * 
 * @author Yves Longchamp
 *
 */

public class Borrow {
	
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
	
	private Extension extension;
	
	


	public Borrow(Item item, Member member, Date beginningDate, Date endingDate, Extension extension) {
		this.item = item;
		this.member = member;
		this.beginningDate = beginningDate;
		this.endingDate = endingDate;
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
	
	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

}
