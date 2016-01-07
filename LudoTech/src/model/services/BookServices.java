package model.services;

import java.util.Date;
import model.services.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.DAOs.BookDAO;
import model.DAOs.BorrowDAO;
import model.DAOs.ItemDAO;
import model.POJOs.Book;
import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Item;
import model.POJOs.Member;

public class BookServices {
	private final BorrowDAO borrowDAO;
	private final BookDAO bookDAO;
	private final ItemDAO itemDAO;
	private final ParametersServices parametersServices;
	private final ItemServices itemServices;
	private final ExtensionServices extensionServices;
	private final MemberContextServices memberContextServices;
	private BookDAO bookDAO2;

	public BookServices() {
		super();
		this.borrowDAO = new BorrowDAO();
		this.bookDAO = new BookDAO();
		this.itemDAO = new ItemDAO();
		this.parametersServices = new ParametersServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new  ExtensionServices();
		this.memberContextServices = new MemberContextServices();
	}

	

	public List<Book> getAll() {
		return this.bookDAO.getBooks(-1);
	}
	
	public List<Book> getAllAccordingToUserID(int userID) {
		return this.bookDAO.getBooks(userID);
	}


	/**
	 * TODO
	 * Ajouter une nouvelle réservation
	 */
//	public Book addBook(Game game ,Item item, Member member, Date beginningDate,
//			Date endingDate, Extension extension) {
//		
//		int a;
//		long diffValue = beginningDate.getTime() - endingDate.getTime();
//		long diffDays = TimeUnit.DAYS.convert(diffValue, TimeUnit.MILLISECONDS);
//		
//		
//		ParametersServices parametresServices = null;
//		if ((diffDays>=0)&&(diffDays>=parametresServices.getDurationOfBorrowingsInWeeks())&&
//				(diffDays>=parametresServices.getDurationBetweenBookingandBorrowingInWeeks()
//			)&&
//				(itemServices.countItemsOfGame(game.getGameID())>=0)
//				&& (borrowDAO.getExtension(game.getGameID(),member.getMemberID(),beginningDate)!=0)
//				&&
//				(memberContextServices.getCanBook(member.getMemberID()))
//				) 
//				{
//			if(endingDate.getDay()==0)
//			{
//				 endingDate.setDate(endingDate.getDate() + 1);;
//				
//			}
//			else if (endingDate.getDay()==6)
//			{ 
//				endingDate.setDate(endingDate.getDate()-5);// Si c'est samedi je le remet a lundi
//			}
//			
//			Book book = new Book(item,member,beginningDate,endingDate,extension);
//			bookDAO.add(book);
//			
//			
//			
//			return book;
//				}
//		else 
//			return null;
//	
//	
//			
		
		
		
		
		/*
		 * game et member ne peuvent pas être null, 
		 * - vérifier si la date de fin est > à la date de début
		 * - vérifier que l'écart entre aujourd"hui et la date de début soit >= à la valeur de la constante des paramètres (utiliser ParametersServices avec une fonction en + pour recup "durationBetweenBookingandBorrowingInWeeks", se calquer sur celle déjà faite et voir le fichier res/preferences.properties)
		 * - vérifier si l'écart entre les dates correspond au paramètre de durée de pret (utiliser ParametersServices avec une fonction en + pour recup "durationOfBorrowingsInWeeks", se calquer sur celle déjà faite et voir le fichier res/preferences.properties)
		 * - si la date de retour tombe un week-end (samedi ou dimanche), la reporter au lundi suivant
		 * - vérifier si le game à un item disponible (en comptant qu'il en faut au moins 1 en stock) (qui ne fais pas partie d'un Borrow ou Book qui se superpose sur les dates indiquées -> utiliser/créer une fonction dans BorrowDAO et BookDAO), si oui en récupérer un
		 * - vérifier si l'extension est dispo pour ces dates (encore BorrowDAO et BookDAO)
		 * - vérifier si l'adhérent à le droit de réserver (booléen de permission dans le contexte)
		 * - vérifier si l'adhénent n'a pas dépassé le quota de réservation possible (utiliser ParametersServices avec une fonction en + pour recup "nbBookings", se calquer sur celle déjà faite et voir le fichier res/preferences.properties
		 * - ajouter le book en base de données, le retourner
		 * - si une vérification n'est pas effectuée, retourner null
		 */
		
		
		
		
		
		
		
		
		
		
		
		
	//}
	
	/**
	 * TODO
	 * Annulation d'une réservation, mettre en malus à l'adhérent à cause de cette fausse réservation et supprimer la réservation
	 */
	public boolean cancelBook(int itemID, int memberID, Date beginningDate) {
		
		memberContextServices.oneMoreNbFakeBooking(memberID);
		
		
	bookDAO2 = null;
	
	bookDAO2.remove(itemID, memberID, beginningDate);
		
		/*
		 * - Utiliser (créer) une fonction dans MemberContextServices pour incrémenter le compteur de nbFakeBookings de l'adhérent
		 * - Supprimer le book en base de données avec le couple des 3 clés primaires (itemID, memberID, beginningDate) et retourner le résultat booléen
		 */
		return true;
	}

	/**
	 * TODO
	 * Un adhérent vient chercher le jeu, la réservation est supprimée et devient un emprunt (qui lui ajouté)
	 */
	public boolean convertBookIntoBorrow(int itemID, int memberID, Date beginningDate, Date endingDate, int extensionID) {
		/*
		 * - Supprimer le book de la base de données (avec BookDAO et le couple {itemID, memberID, beginningDate})
		 * - Construire un nouvel objet Borrow et l'enregistrer dans la base de données avec BorrowDAO et retourner le résultat booléen
		 */
		return true;
	}
	
	

}
