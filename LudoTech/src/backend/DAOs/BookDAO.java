package backend.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import backend.POJOs.Book;
import backend.POJOs.Extension;
import backend.POJOs.Item;
import backend.POJOs.Member;

/**
 * Classe manipulant des objets de type Book (Réservation) dans la base de
 * données
 */
public class BookDAO extends DAO {

	/**
	 * Cherche dans la base de données la réservation identifiée par le couple
	 * {itemID, memberID, startDate}
	 * 
	 * @param itemID
	 *            L'identifiant unique d'un exemplaire existant en base de
	 *            données
	 * @param memberID
	 *            L'identifiant unique d'un adhérent existant en base de données
	 * @param startDate
	 *            La date de début de la réservation
	 * @return Une réservation ou null si elle n'a pas été trouvée
	 */
	public Book get(int itemID, int memberID, Date startDate) {
		try {
			super.connect();

			// Utilisation des "AS" à cause des jointures.
			String request = "SELECT BOOK.start_date AS B_start_date, BOOK.end_date AS B_end_date, "
					+ "MEMBER.id AS M_id, MEMBER.first_name AS M_first_name, MEMBER.last_name AS M_last_name, MEMBER.pseudo AS M_pseudo, MEMBER.password AS M_password, MEMBER.is_admin AS M_is_admin, MEMBER.birth_date AS M_birth_date, MEMBER.phone_number AS M_phone_number, MEMBER.email_address AS M_email_address, MEMBER.street_address AS M_street_address, MEMBER.postal_code AS M_postal_code, MEMBER.city AS M_city, "
					+ "ITEM.id AS I_id, " + "EXTENSION.id AS E_id, EXTENSION.name AS E_name " + "FROM BOOK "
					+ "JOIN MEMBER ON BOOK.member_id = MEMBER.id " + "JOIN ITEM ON BOOK.item_id = ITEM.id "
					+ "LEFT JOIN EXTENSION ON BOOK.extension_id = EXTENSION.id "
					+ "WHERE BOOK.item_id = ? AND BOOK.member_id = ? AND BOOK.start_date = ?";
			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.setInt(1, itemID);
			psSelect.setInt(2, memberID);
			psSelect.setDate(3, new java.sql.Date(startDate.getTime()));
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Book book = null;
			if (resultSet.next()) {
				Item item = new Item(resultSet.getInt("I_id"));
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
	 * Cherche en base de données toutes les réservations sans distinction si le
	 * paramètre userID = -1. Sinon, sélectionne uniquement celles utilisant cet
	 * identifiant d'adhérent.
	 * 
	 * @param userID
	 *            L'identifiant d'un adhérent existant en base de données ou
	 *            "-1" si on ne souhaite pas le préciser.
	 * @return Une liste de réservations potentiellement vide
	 */
	public List<Book> getAll(int userID) {
		List<Book> books = new ArrayList<Book>();
		try {
			super.connect();

			// Utilisation des "AS" à cause des jointures.
			String request = "SELECT BOOK.start_date AS B_start_date, BOOK.end_date AS B_end_date, "
					+ "MEMBER.id AS M_id, MEMBER.first_name AS M_first_name, MEMBER.last_name AS M_last_name, MEMBER.pseudo AS M_pseudo, MEMBER.password AS M_password, MEMBER.is_admin AS M_is_admin, MEMBER.birth_date AS M_birth_date, MEMBER.phone_number AS M_phone_number, MEMBER.email_address AS M_email_address, MEMBER.street_address AS M_street_address, MEMBER.postal_code AS M_postal_code, MEMBER.city AS M_city, "
					+ "ITEM.id AS I_id, " + "EXTENSION.id AS E_id, EXTENSION.name AS E_name " + "FROM BOOK "
					+ "JOIN MEMBER ON BOOK.member_id = MEMBER.id " + "JOIN ITEM ON BOOK.item_id = ITEM.id "
					+ "LEFT JOIN EXTENSION ON BOOK.extension_id = EXTENSION.id ";

			// Filtrer sur l'utilisateur si l'identifiant n'est pas -1
			if (userID != -1) {
				request += "WHERE BOOK.member_id = " + userID;
			}

			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			while (resultSet.next()) {
				Item item = new Item(resultSet.getInt("I_id"));
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

	/**
	 * Ajoute une nouvelle réservation en base de données.
	 * 
	 * @param book
	 *            Une réservation possédant un exemplaire, un adhérent, une date
	 *            de début, une date de fin et potentiellement une extension
	 *            (pas obligatoire)
	 * @return True si l'ajout s'est bien passé, False sinon.
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
	 * Suprimme une réservation éxistante de la base de données identifiée par
	 * le couple {itemID, memberID, startDate}
	 * 
	 * @param itemID
	 *            L'identifiant unique d'un exemplaire existant en base de
	 *            données
	 * @param memberID
	 *            L'identifiant unique d'un adhérent existant en base de données
	 * @param startDate
	 *            La date de début de la réservation
	 * @return True si la suppression s'est bien passée, False sinon
	 */
	public boolean remove(int itemID, int memberID, Date startDate) {
		try {
			super.connect();

			PreparedStatement psDelete = connection
					.prepareStatement("DELETE FROM BOOK WHERE item_id = ? AND member_id = ? AND start_date = ?");
			psDelete.setInt(1, itemID);
			psDelete.setInt(2, memberID);
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
	 * Détermine si l'exemplaire dont l'identifiant est passé en paramètre est
	 * utilisé dans au moins une réservation
	 * 
	 * @param itemID
	 *            L'identifiant unique d'un exemplaire existant en base de
	 *            données
	 * @return True si l'exemplaire est utilisé par une réservation, False sinon
	 */
	public boolean itemUsed(int itemID) {
		boolean result = false;
		try {

			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT count(*) FROM BOOK WHERE item_id = ?");

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

	/**
	 * Détermine si l'exemplaire dont l'identifiant est passé en paramètre est
	 * utilisé dans au moins une réservation sur une certaine période
	 * 
	 * @param itemID
	 *            L'identifiant unique d'un exemplaire existant en base de
	 *            données
	 * @param startDate
	 *            Date de début de la période
	 * @param endDate
	 *            Date de fin de la période
	 * @return True si l'exemplaire est utilisée dans une réservation durant la
	 *         période précisée, False sinon
	 */
	public boolean itemUsedDuringPeriod(int itemID, Date startDate, Date endDate) {
		boolean result = false;
		try {
			super.connect();

			PreparedStatement psSelect = connection
					.prepareStatement("SELECT count(*) " + "FROM BOOK " + "WHERE item_id = ? "
							+ "AND (start_date <= ? OR start_date <= ?) " + "AND (end_date >= ? OR end_date >= ?)");

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

	/**
	 * Détermine si l'adhérent dont l'identifiant est passé en paramètre est
	 * utilisé dans au moins une réservation
	 * 
	 * @param memberID
	 *            L'identifiant unique d'un adhérent existant en base de données
	 * @return True si l'adhérent est utilisé par une réservation, False sinon
	 */
	public boolean memberUsed(int memberID) {
		boolean result = false;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT count(*) FROM BOOK WHERE member_id = ?");

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

	/**
	 * Détermine si l'extension dont l'identifiant est passé en paramètre est
	 * utilisée dans au moins une réservation
	 * 
	 * @param extensionID
	 *            L'identifiant unique d'une extension existante en base de
	 *            données
	 * @return True si l'extension est utilisée par une réservation, False sinon
	 */
	public boolean extensionUsed(int extensionID) {
		boolean result = false;
		try {
			super.connect();

			PreparedStatement psSelect = connection
					.prepareStatement("SELECT count(*) FROM BOOK WHERE extension_id = ?");

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

	/**
	 * Détermine si l'extension dont l'identifiant est passé en paramètre est
	 * utilisée dans au moins une réservation durant une période précise
	 * 
	 * @param extensionID
	 *            L'identifiant unique d'une extension existante en base de
	 *            données
	 * @param startDate
	 *            Date de début de la période
	 * @param endDate
	 *            Date de fin de la période
	 * @return True si l'extension est utilisée par une réservation durant la
	 *         période précisée, False sinon
	 */
	public boolean extensionUsedDuringPeriod(int extensionID, Date startDate, Date endDate) {
		boolean result = false;
		try {
			super.connect();

			PreparedStatement psSelect = connection
					.prepareStatement("SELECT count(*) " + "FROM BOOK " + "WHERE extension_id = ? "
							+ "AND (start_date <= ? OR start_date <= ?) " + "AND (end_date >= ? OR end_date >= ?)");

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
}
