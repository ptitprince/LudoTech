package model.DAOs;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.POJOs.Book;

public class BookDAO extends DAO {

	/**
	 * Ajoute une nouvelle ligne dans la table Game de la base de données, avec
	 * les informations d'un jeu en utilisant la génération automatique de
	 * l'identifiant (clé primaire) par le pilote Derby
	 * 
	 * @param Book
	 *            La reservation a ajouter
	 * @param Game
	 *            le jeu a emprunté
	 * @return true L'ajout du jeu a été fait correctement
	 * @return false Une exception est survenue, l'ajout s'est peut-être mal
	 *         passé
	 */

	public boolean add(Book book) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "Book(item_id, member_id, beginning_date, end_date, extension_id) " + "VALUES (?, ?, ?, ?, ?)",
					new String[] { "ID" });

			psInsert.setInt(1, book.getItem().getItemID());
			psInsert.setInt(2, book.getMember().getMemberID());
			psInsert.setDate(3, new java.sql.Date(book.getStartDate().getTime()));
			psInsert.setDate(4, new java.sql.Date(book.getEndDate().getTime()));
			psInsert.setInt(5, book.getExtension().getExtensionID());

			psInsert.executeUpdate();

			// Récupération de l'identifiant du jeu généré automatiquement par
			// Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				book.setBookID(idRS.getInt(1));
			} else {
				throw new SQLException();
			}

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifier une reservation
	 * 
	 * 
	 * @param book
	 *            C'est la reservation
	 * @param itemID
	 *            C'est l'identifiant de l'exemplaire
	 * @param memberID
	 *            C'est l'identifiant du membre
	 * @param extensionID
	 *            C'est l'identifiant de l'extension
	 * @return
	 */

	public boolean edit(Book book) {
		try {
			super.connect();
			PreparedStatement psEdit = connection.prepareStatement("UPDATE BOOK "
					+ "SET  ending_date= ?, extension_id= ?) "
					+ "WHERE item_id=?,member_id = ? , beginning_date=?");
			

			
			
			psEdit.setDate(1, new java.sql.Date(book.getEndDate().getTime()));
			psEdit.setInt(2, book.getExtension().getExtensionID());
			psEdit.setInt(3, book.getItem().getItemID());
			psEdit.setInt(4, book.getMember().getMemberID());
			psEdit.setDate(5, new java.sql.Date(book.getStartDate().getTime()));
			
			

			psEdit.executeUpdate();
			psEdit.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Supprime une ligne de la table Book dans la base de données en se servant
	 * de l'identifiant d'une reservation
	 * 
	 * @param id
	 *            L'identifiant de la reservation à supprimer
	 * @return True si la reservation a bien été supprimé ou s'il n'existe pas
	 *         en base de données, sinon False
	 */
	public boolean remove(int itemId,int memberId,Date startDate) {
		try {
			super.connect();

			PreparedStatement psDelete = connection
					.prepareStatement("DELETE FROM BOOK WHERE item_id, member_id = ?, begining_date=?");
			psDelete.setInt(1, itemId);
			psDelete.setInt(2, memberId);	
			psDelete.setDate(3,startDate);
			psDelete.execute();
			psDelete.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	
	/***
	 * La prendre de chez Yves en changeant la table 
	 */

	/**
	 * Lister toutes les reservations avec les extensions et les exemplaires
	 * 
	 * 
	 * @return
	 */
	public List<Book> getAllHavingBook() {
		List<Book> books = new ArrayList<Book>();
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM BOOK ");
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) { // Positionnement sur le premier résultat
				/*
				 */
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
}
