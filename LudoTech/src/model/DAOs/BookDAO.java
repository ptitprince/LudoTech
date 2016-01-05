package model.DAOs;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.POJOs.Book;
import model.POJOs.Extension;
import model.POJOs.Item;
import model.POJOs.Member;

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
					+ "Book(item_id, member_id, start_date, end_date, extension_id) " + "VALUES (?, ?, ?, ?, ?)",
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
					+ "SET end_date = ?, extension_id = ?) "
					+ "WHERE item_id = ? AND member_id = ? AND start_date = ?");
			
			psEdit.setDate(1, new java.sql.Date(book.getEndDate().getTime()));
			psEdit.setInt(2, book.getExtension().getExtensionID());
			psEdit.setInt(3, book.getItem().getItemID());
			psEdit.setInt(4, book.getMember().getMemberID());
			psEdit.setInt(5, book.getExtension().getExtensionID());
			psEdit.setDate(7, new java.sql.Date(book.getStartDate().getTime()));

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
					.prepareStatement("DELETE FROM BOOK WHERE item_id = ? AND member_id = ? AND start_date = ?");
			psDelete.setInt(1, itemId);
			psDelete.setInt(2, memberId);	
			psDelete.setDate(3, new java.sql.Date(startDate.getTime()));
			psDelete.execute();
			psDelete.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 
	 * @param itemID
	 * @param memberID
	 * @param beginningDate
	 * @return Retourne une reservation
	 */
	
	public Book get(int itemID, int memberID, Date beginningDate) {
		try {
			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)
			
			String request = "SELECT BOOK.start_date AS B_start_date, BOOK.end_date AS B_end_date, "
					+ "MEMBER.id AS M_id, MEMBER.first_name AS M_first_name, MEMBER.last_name AS M_last_name, MEMBER.pseudo AS M_pseudo, MEMBER.password AS M_password, MEMBER.is_admin AS M_is_admin, MEMBER.birth_date AS M_birth_date, MEMBER.phone_number AS M_phone_number, MEMBER.email_address AS M_email_address, MEMBER.street_address AS M_street_address, MEMBER.postal_code AS M_postal_code, MEMBER.city AS M_city, "
					+ "ITEM.id AS I_id, ITEM.comments AS I_comments, "
					+ "EXTENSION.id AS E_id, EXTENSION.name AS E_name " 
					+ "FROM BOOK "
					+ "JOIN MEMBER ON BOOK.member_id = MEMBER.id " 
					+ "JOIN ITEM ON BOOK.item_id = ITEM.id "
					+ "LEFT JOIN EXTENSION ON BOOK.extension_id = EXTENSION.id";
			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Book book = null;
			if (resultSet.next()) {
				Item item = new Item(resultSet.getInt("I_id"), resultSet.getString("I_comments"));
				Member member = new Member(resultSet.getInt("M_id"), resultSet.getString("M_first_name"),
						resultSet.getString("M_last_name"), resultSet.getString("M_pseudo"),
						resultSet.getString("M_password"), resultSet.getBoolean("M_is_admin"),
						resultSet.getDate("M_birth_date"), resultSet.getString("M_phone_number"),
						resultSet.getString("M_email_address"), resultSet.getString("M_street_address"),
						resultSet.getString("M_postal_code"), resultSet.getString("M_city"));
				Extension extension = new Extension(resultSet.getInt("E_id"), resultSet.getString("E_name"));
				book = new Book(item, member, resultSet.getDate("B_start_date"),
						resultSet.getDate("B_end_date"), extension);
			}
			super.disconnect();
			return book;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
	public List<Book> getBooks(/* HashMap<String, String> filter */) {
		List<Book> books = new ArrayList<Book>();
		try {
			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			String request = "SELECT BOOK.start_date AS B_start_date, BOOK.end_date AS B_end_date, "
					+ "MEMBER.id AS M_id, MEMBER.first_name AS M_first_name, MEMBER.last_name AS M_last_name, MEMBER.pseudo AS M_pseudo, MEMBER.password AS M_password, MEMBER.is_admin AS M_is_admin, MEMBER.birth_date AS M_birth_date, MEMBER.phone_number AS M_phone_number, MEMBER.email_address AS M_email_address, MEMBER.street_address AS M_street_address, MEMBER.postal_code AS M_postal_code, MEMBER.city AS M_city, "
					+ "ITEM.id AS I_id, ITEM.comments AS I_comments, "
					+ "EXTENSION.id AS E_id, EXTENSION.name AS E_name " 
					+ "FROM BOOK "
					+ "JOIN MEMBER ON BOOK.member_id = MEMBER.id " 
					+ "JOIN ITEM ON BOOK.item_id = ITEM.id "
					+ "LEFT JOIN EXTENSION ON BOOK.extension_id = EXTENSION.id";
			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			while (resultSet.next()) {
				Item item = new Item(resultSet.getInt("I_id"), resultSet.getString("I_comments"));
				Member member = new Member(resultSet.getInt("M_id"), resultSet.getString("M_first_name"),
						resultSet.getString("M_last_name"), resultSet.getString("M_pseudo"),
						resultSet.getString("M_password"), resultSet.getBoolean("M_is_admin"),
						resultSet.getDate("M_birth_date"), resultSet.getString("M_phone_number"),
						resultSet.getString("M_email_address"), resultSet.getString("M_street_address"),
						resultSet.getString("M_postal_code"), resultSet.getString("M_city"));
				Extension extension = new Extension(resultSet.getInt("E_id"), resultSet.getString("E_name"));
				Book book = new Book(item, member, resultSet.getDate("B_start_date"),
						resultSet.getDate("B_end_date"), extension);
				books.add(book);
			}
						
			super.disconnect();
			return books;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
}
