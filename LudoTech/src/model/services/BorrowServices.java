package model.services;

import java.util.Date;

import model.DAOs.BorrowDAO;
import model.DAOs.MemberDAO;
import model.POJOs.Borrow;
import model.POJOs.Item;
import model.POJOs.Member;

public class BorrowServices {

	/**
	 * Pour tous :
	 * Pouvoir faire une recherche parmi les emprunts.
	 * 
	 * Pour adhérent :
	 * - Voir SES emprunts en cours ;
	 * Pour chaque emprunt :
	 * 	- Accéder aux infos ;
	 * 	- Statut de l'emprunt ;
	 * 	- Nombre jours restant OU en retard ;
	 * 
	 * Pour un admin : 
	 * - Voir TOUS les emprunts des adhérents (doit pouvoir voir les adhérents);
	 * Pour chaque emprunt : 
	 * 	- Pour les emprunts finis, indiquer comment ça s'est terminé. 
	 * 	- Enregistrer le retour d'un emprunt.
	 * 
	 */
	
	/**
	 * createBorrow : -> Emprunt
	 * Permet de créer un Emprunt.
	 * 
	 * rechercherEmprunts : criteres  -> Emprunt
	 * Permet de chercher un emprunt suivant d'éventuels critères.
	 * 
	 * isAdherent : Member -> Boolean 
	 * Permet de vérifier si la personne est un membre ou pas.
	 * 
	 * isAdmin : Member -> Boolean 
	 * Permet de vérifier si la personne est admin ou pas.
	 * 
	 * getBorrowInformation : Emprunt -> texte 
	 * Renvoie les infos d'un emprunt.
	 * 
	 * getStatusBorrow : Emprunt -> texte
	 * Renvoie le statut d'un emprunt.
	 * 
	 * getNumberOfDays : Emprunt -> texte/entier
	 * Renvoie le nombre de jours en positif ou négatif d'un emprunt.
	 * On pose que si le nombre est négatif, on a un emprunt en retard.
	 * 
	 * getAllBorrows : criteres -> listeEmprunts
	 * Renvoie une liste d'emprunts, suivant des critères. 
	 * 
	 * getEndStatus : Emprunt -> texte 
	 * Renvoie comment s'est terminé un emprunt, s'il a été bien terminé ou non. 
	 * 
	 * setEndStatus : Emprunt x texte -> Emprunt 
	 * Modifie le statut de la fin d'un emprunt. 
	 * 
	 */
	
	/**
	 * objet d'accès aux données de type borrow.
	 */
	private final BorrowDAO borrowDAO;
	
	/**
	 * objet d'accès aux données de type member.
	 */
	
	private final MemberDAO memberDAO;	
	
	public BorrowServices()	{
		this.borrowDAO = new BorrowDAO();
		this.memberDAO = new MemberDAO();
	}
	
	/**
	 * Création et ajout en base de données d'un nouvel emprunt
	 * @param borrowID : l'identifiant de l'emprunt;
	 * @param itemID : l'identifiant de l'exemplaire;
	 * @param memberID : l'identifiant du membre;
	 * @param dateFin : date de fin de l'emprunt. Doit être supérieur à dateDebut (genious);
	 * @param dateDebut : date de debut de l'emprunt. Doit être inférieur à dateFin;
	 * @param State : Etat de l'emprunt.
	 * @param Available : booléen sur l'état de l'emprunt.
	 * 
	 */
	
	public Borrow addBorrow(Item item, Member member, Date beginningDate, Date endingDate, String borrowState, boolean borrowAvailable)	{
		Borrow borrow = new Borrow(item, member, beginningDate, endingDate, borrowState, borrowAvailable);
		return this.borrowDAO.add(borrow, borrow.getItem().getItemID(), borrow.getMember().getMemberID()) ? borrow : null;
		//Redondance avec le member et l'item dans borrow ?
	}
	
	/**
	 * Suppression en base de données de l'emprunt.
	 * @param l'id de l'emprunt que l'on veut supprimer.
	 * @return un booléen accusant de l'état de la suppression.
	 * 
	 */
	
	public boolean removeBorrow(int id)	{
		return this.borrowDAO.remove(id);
	}
	
	/**
	 * Edition d'un nouvel emprunt dans la base de données.
	 * @param borrowID : l'identifiant de l'emprunt;
	 * @param itemID : l'identifiant de l'exemplaire;
	 * @param memberID : l'identifiant du membre;
	 * @param dateFin : date de fin de l'emprunt. Doit être supérieur à dateDebut (genious);
	 * @param dateDebut : date de debut de l'emprunt. Doit être inférieur à dateFin;
	 * @param State : Etat de l'emprunt.
	 * @param Available : booléen sur l'état de l'emprunt.
	 * @return Un emprunt modifié.
	 * 
	 */
	
	public Borrow editBorrow(Item item, Member member, Date beginningDate, Date endingDate, String borrowState, boolean borrowAvailable)	{
		Borrow borrow = new Borrow(item, member, beginningDate, endingDate, borrowState, borrowAvailable);
		return this.borrowDAO.edit(borrow, borrow.getItem().getItemID(), borrow.getMember().getMemberID()) ? borrow : null;
		//Redondance avec le member et l'item dans borrow ?
	}
	
	/**
	 * Création et ajout en base de données d'un nouvel emprunt
	 * @param borrowID : l'identifiant de l'emprunt;
	 * @param itemID : l'identifiant de l'exemplaire;
	 * @param memberID : l'identifiant du membre;
	 * @param dateFin : date de fin de l'emprunt. Doit être supérieur à dateDebut (genious);
	 * @param dateDebut : date de debut de l'emprunt. Doit être inférieur à dateFin;
	 * @param State : Etat de l'emprunt.
	 * @param Available : booléen sur l'état de l'emprunt.
	 * TODO : Refaire le get dans le DAO.
	 
	
	public Borrow getBorrow(Item item, Member member, Date beginningDate, Date endingDate, String borrowState, boolean borrowAvailable)	{
		Borrow borrow = new Borrow(item, member, beginningDate, endingDate, borrowState, borrowAvailable);
		return this.borrowDAO.get(borrow, borrow.getItem().getItemID(), borrow.getMember().getMemberID()) ? borrow : null;
	}*/
	
}
