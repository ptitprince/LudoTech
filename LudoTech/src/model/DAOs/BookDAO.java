package model.DAOs;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.POJOs.Book;
import model.POJOs.Borrow;
import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Member;

public class BookDAO extends DAO {
	
	
	
	/**
	 * Ajoute une nouvelle ligne dans la table Game de la base de données, avec
	 * les informations d'un jeu en utilisant la génération automatique de
	 * l'identifiant (clé primaire) par le pilote Derby
	 * 
	 * @param Book
	 *            La reservation a ajouter
	 *  @param Game
	 *  le jeu a emprunté
	 * @return true L'ajout du jeu a été fait correctement
	 * @return false Une exception est survenue, l'ajout s'est peut-être mal
	 *         passé
	 */
	
	
	
	public boolean add(Book Book, int IdItem, int extensionID,int memberId) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "Book(IdBook, StartDate, EndDate, memberId, itemId, extensionID) "
					+ "VALUES (?, ?, ?, ?, ?, ?)", new String[] { "ID" }); 
			// Auto-incrémentation sur la clé primaire ID
			psInsert.setInt(1,Book.getIdBook());
			psInsert.setDate(2, new  java.sql.Date (Book.getStartDate().getTime()));
			psInsert.setDate(3, new java.sql.Date (Book.getEndDate().getTime()));
			psInsert.setInt(4, memberId );
			psInsert.setInt(5, IdItem);
			psInsert.setInt(6, extensionID);
			

			psInsert.executeUpdate();

			// Récupération de l'identifiant de la reservation généré automatiquement par
			// Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				Book.setIdBook(idRS.getInt(1));
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
	 * Supprime une ligne de la table Book dans la base de données en se servant
	 * de l'identifiant d'une reservation
	 * 
	 * @param id
	 *            L'identifiant de la reservation à supprimer
	 * @return True si la reservation a bien été supprimé ou s'il n'existe pas en base
	 *         de données, sinon False
	 */
	public boolean remove(int BookId) {
		try {
			super.connect();

			PreparedStatement psDelete = connection.prepareStatement("DELETE FROM Book WHERE id = ?");
			psDelete.setInt(1, BookId);
			psDelete.execute();
			psDelete.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**Modifier une reservation 
	 * 
	 * 
	 * @param book C'est la reservation
	 * @param itemID C'est l'identifiant de l'exemplaire
	 * @param memberID C'est l'identifiant du membre
	 * @param extensionID C'est l'identifiant de l'extension
	 * @return
	 */
	
	
	public boolean edit(Book book, int itemID, int memberID,int extensionID)	{
		try {
			super.connect();
			PreparedStatement psEdit = connection.prepareStatement("UPDATE BOOK "
					+ "SET itemID = ?, memberID = ?, StartDate = ?, EndDate= ?, extensionID= ?) "
					+ "WHERE id = ?");
			psEdit.setInt(1, itemID);
			
			psEdit.setInt(2, memberID);
			psEdit.setDate(3, new java.sql.Date(Book.getStartDate().getTime()));
			psEdit.setDate(4, new java.sql.Date(Book.getEndDate().getTime()));
			psEdit.setInt(5, memberID);
			
			psEdit.executeUpdate();
			psEdit.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/** la fonction qui retourne le membre qui a effectuer la reservation 
	 *  
	 * 
	 * 
	 * 
	 * @param bookID
	 * @return
	 */
	
	public int getMemberID(int bookID) {
		int memberID = 0;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT memeber_id FROM BOOK WHERE id = ?");
			psSelect.setInt(1, bookID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				memberID = resultSet.getInt("member_id");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberID;
	}
	/** Retourne les extensions reservés
	 * 
	 * 
	 * @param bookID l'identifiant de la reservation
	 * @return
	 */
	
	
	public int getExtensionID(int bookID) {
		int extensionID = 0;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT extension_id FROM BOOK WHERE id = ?");
			psSelect.setInt(1, bookID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				extensionID = resultSet.getInt("extension_id");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return extensionID;
	}
	
	
	/** retourne l'exemplaire reservé 
	 * 
	 * @param bookID
	 * @return
	 */
	
	
	public int getItemID(int bookID) {
		int itemID = 0;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT item_id FROM BOOK WHERE id = ?");
			psSelect.setInt(1, bookID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				itemID = resultSet.getInt("item_id");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemID;
	}
	/** Lister toutes les reservations avec les extensions et les exemplaires 
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
				/** probleme books.add(new Book(resultSet.getInt("id"),resultSet.getDate("startdate") , resultSet.getDate("enddate"),resultSet.getInt("idmember"), resultSet.getInt("iditem"), resultSet.getInt("idextension")));
				 * 
				 * 
				 */
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
}




