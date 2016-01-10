package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.POJOs.Book;
import model.POJOs.Extension;
import model.POJOs.Item;
import model.POJOs.Member;

public class BookDAO extends DAO {

	/**
	 * Ajoute une nouvelle ligne dans la table Book, avec un identifiant créé
	 * par Derby.
	 * 
	 * @param Book
	 *            , l'réservation à ajouter dans la base de données.
	 * @return true L'ajout de l'réservation a été fait correctement.
	 * @return false Exception, peut-être un problème qui est survenu.
	 */

	public boolean add(Book book) {

		try {
			super.connect();

			PreparedStatement psInsert = connection
					.prepareStatement("INSERT INTO " + "Book(item_id, member_id, start_date, end_date"
							+ ((book.getExtension() != null) ? ", extension_id) " : ") ") + "VALUES (?, ?, ?, ?"
							+ ((book.getExtension() != null) ? ", ?)" : ")"));
			psInsert.setInt(1, book.getItem().getItemID());
			psInsert.setInt(2, book.getMember().getMemberID());
			psInsert.setDate(3, new java.sql.Date(book.getStartDate().getTime()));
			psInsert.setDate(4, new java.sql.Date(book.getEndDate().getTime()));
			if (book.getExtension() != null) {
				psInsert.setInt(5, book.getExtension().getExtensionID());
			}

			psInsert.executeUpdate();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Enlève un book dans la base de données.
	 * 
	 * @param id
	 *            de l'réservation concerné.
	 * @return un booléen accusant de l'état de la suppression.
	 */
	public boolean remove(int itemID, int memberID, Date beginningDate) {
		try {
			super.connect();

			PreparedStatement psDelete = connection
					.prepareStatement("DELETE FROM BOOK WHERE item_id = ? AND member_id = ? AND start_date = ?");
			psDelete.setInt(1, itemID);
			psDelete.setInt(2, memberID);
			psDelete.setDate(3, new java.sql.Date(beginningDate.getTime()));
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
	 * retourne un réservation.
	 * 
	 * @param id
	 * @return une réservation dont l'identifiant correspond, ou null.
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
					+ "LEFT JOIN EXTENSION ON BOOK.extension_id = EXTENSION.id "
					+ "WHERE BOOK.item_id = ? AND BOOK.member_id = ? AND BOOK.start_date = ?";
			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.setInt(1, itemID);
			psSelect.setInt(2, memberID);
			psSelect.setDate(3, new java.sql.Date(beginningDate.getTime()));
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
				Extension extension = null;
				if (resultSet.getInt("E_id") > 0) {
					extension = new Extension(resultSet.getInt("E_id"), resultSet.getString("E_name"));
				}
				book = new Book(item, member, resultSet.getDate("B_start_date"), resultSet.getDate("B_end_date"),
						extension);
			}
			super.disconnect();
			return book;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Méthode d'accès à tous les réservations.
	 * 
	 * @return la liste des réservations.
	 */
	public List<Book> getBooks(int userID) {
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
					+ "EXTENSION.id AS E_id, EXTENSION.name AS E_name " + "FROM BOOK "
					+ "JOIN MEMBER ON BOOK.member_id = MEMBER.id " + "JOIN ITEM ON BOOK.item_id = ITEM.id "
					+ "LEFT JOIN EXTENSION ON BOOK.extension_id = EXTENSION.id ";

			if (userID != -1) { // Filtrer sur l'utilisateur si l'identifiant
								// n'est pas -1
				request += "WHERE BOOK.member_id = " + userID;
			}

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
				Book book = new Book(item, member, resultSet.getDate("B_start_date"), resultSet.getDate("B_end_date"),
						extension);
				books.add(book);
			}

			super.disconnect();
			return books;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public boolean itemUsed(int itemID) {
		boolean result = false;
		try {

			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			PreparedStatement psSelect = connection.prepareStatement(
					"SELECT count(*) FROM BOOK WHERE item_id = ?");
			
			psSelect.setInt(1, itemID);

			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			if (resultSet.next()) {
				result = (resultSet.getInt(1) > 0);
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return result;
	}
	
	public boolean itemUsedDuringPeriod(int itemID, Date startDate, Date endDate) {
		boolean result = false;
		try {

			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			PreparedStatement psSelect = connection.prepareStatement(
					"SELECT count(*) "
						+ "FROM BOOK "
						+ "WHERE item_id = ? "
						+ "AND (start_date <= ? OR start_date <= ?) "
						+ "AND (end_date >= ? OR end_date >= ?)");
			
			psSelect.setInt(1, itemID);
			psSelect.setDate(2, new java.sql.Date(startDate.getTime()));
			psSelect.setDate(3, new java.sql.Date(endDate.getTime()));
			psSelect.setDate(4, new java.sql.Date(startDate.getTime()));
			psSelect.setDate(5, new java.sql.Date(endDate.getTime()));

			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			if (resultSet.next()) {
				result = (resultSet.getInt(1) > 0);
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return result;
	}
	
	public boolean extensionUsed(int extensionID) {
		boolean result = false;
		try {

			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			PreparedStatement psSelect = connection.prepareStatement(
					"SELECT count(*) FROM BOOK WHERE extension_id = ?");
			
			psSelect.setInt(1, extensionID);

			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			if (resultSet.next()) {
				result = (resultSet.getInt(1) > 0);
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return result;
	}

	public boolean extensionUsedDuringPeriod(int extensionID, Date startDate, Date endDate) {
		boolean result = false;
		try {

			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			PreparedStatement psSelect = connection.prepareStatement(
					"SELECT count(*) "
						+ "FROM BOOK "
						+ "WHERE extension_id = ? "
						+ "AND (start_date <= ? OR start_date <= ?) "
						+ "AND (end_date >= ? OR end_date >= ?)");
			
			psSelect.setInt(1, extensionID);
			psSelect.setDate(2, new java.sql.Date(startDate.getTime()));
			psSelect.setDate(3, new java.sql.Date(endDate.getTime()));
			psSelect.setDate(4, new java.sql.Date(startDate.getTime()));
			psSelect.setDate(5, new java.sql.Date(endDate.getTime()));

			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			if (resultSet.next()) {
				result = (resultSet.getInt(1) > 0);
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return result;
	}
	
	public boolean memberUsed(int memberID) {
		boolean result = false;
		try {

			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			PreparedStatement psSelect = connection.prepareStatement(
					"SELECT count(*) FROM BOOK WHERE member_id = ?");
			
			psSelect.setInt(1, memberID);

			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			if (resultSet.next()) {
				result = (resultSet.getInt(1) > 0);
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return result;
	}

	public int getBooksNb(int memberID) {
		int result = 0;
		try {

			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			PreparedStatement psSelect = connection
					.prepareStatement("SELECT count(member_id) FROM BOOK WHERE member_id = ?");

			psSelect.setInt(1, memberID);

			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
