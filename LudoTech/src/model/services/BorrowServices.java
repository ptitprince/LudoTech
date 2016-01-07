package model.services;

import java.util.Date;
import java.util.List;

import model.DAOs.BorrowDAO;
import model.POJOs.Borrow;
import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Member;

public class BorrowServices {

	private final BorrowDAO borrowDAO;

	public BorrowServices() {
		this.borrowDAO = new BorrowDAO();
	}

	public List<Borrow> getAll() {
		return this.borrowDAO.getBorrows(-1);
	}
	
	public List<Borrow> getAllAccordingToUserID(int userID) {
		return this.borrowDAO.getBorrows(userID);
	}

	/**
	 * TODO
	 * Ajout d'un nouveau prêt dans la base de données
	 * Il est nécessaire d'effecuter des vérifications avant de l'ajouter
	 */
	public Borrow addBorrow(Game game, Member member, Date beginningDate,
			Date endingDate, Extension extension) {
		/*
		 * - vérifier si la date de fin est > à la date de début
		 * - vérifier si l'écart entre les dates corresponds au paramètre de durée de prêt (utiliser encore ParametersServices, avec "durationOfBorrowingsInWeeks" cette fois ci
		 * - si la date de retour tombe un week-end (samedi ou dimanche), la reporter au lundi suivant
		 * - vérifier si le game à un item disponible (en comptant qu'il en faut au moins 1 en stock) (qui ne fais pas partie d'un Borrow qui se superpose sur les dates indiquées -> utiliser/créer une fonction dans BorrowDAO), si oui en récupérer un
		 * - vérifier si l'extension est dispo pour ces dates (encore BorrowDAO)
		 * - vérifier si l'adhérent à le droit d'emprunter (booléen de permission dans le contexte)
		 * - vérifier si l'adhénent n'a pas dépassé le quota d'emprunts possible (utiliser ParametersServices avec une fonction en + pour recup "nbBorrowings", se calquer sur celle déjà faite et voir le fichier res/preferences.properties
		 * - ajouter le borrow en base de données, le retourner
		 * - si une vérification n'est pas effectuée, retourner null
		 */
		return null;
	}

	/**
	 * TODO
	 * Retour d'un emprunt -> suppression dans la base de données
	 * Faire des vérifications de fin de prêt pour modifier le contexte de l'adhérent / emprunteur
	 */
	public boolean removeBorrow(int itemID, int memberID, Date beginningDate, Date endingDate) {
		/*
		 * - Vérification que la date de retour (aujourd'hui) est <= à la date de fin de prêt
		 * 		Si non, incrémenter le compteur "nbDelays" de l'adhérent à l'aide d'une fonction dans MemberServices (qui enregistera également la modification dans la BDD à l'aide de la fonction edit)
		 * - Supprimer le borrow en base de données avec le couple des 3 clés primaires (itemID, memberID, beginningDate) et retourner le résultat booléen
		 */
		return true;
	}

}
