package backend.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import backend.POJOs.Borrow;
import backend.POJOs.Extension;
import backend.POJOs.Item;
import backend.POJOs.Member;

public class BorrowDAO extends DAO {

	/**
	 * Ajoute une nouvelle ligne dans la table Borrow, avec un identifiant créé
	 * par Derby.
	 * 
	 * @param Borrow
	 *            , l'emprunt à ajouter dans la base de données.
	 * @return true L'ajout de l'emprunt a été fait correctement.
	 * @return false Exception, peut-être un problème qui est survenu.
	 */

	public boolean add(Borrow borrow) {

		try {
			super.connect();

			PreparedStatement psInsert = connection
					.prepareStatement("INSERT INTO " + "Borrow(item_id, member_id, start_date, end_date"
							+ ((borrow.getExtension() != null) ? ", extension_id) " : ") ") + "VALUES (?, ?, ?, ?"
							+ ((borrow.getExtension() != null) ? ", ?)" : ")")); 
			psInsert.setInt(1, borrow.getItem().getItemID());
			psInsert.setInt(2, borrow.getMember().getMemberID());
			psInsert.setDate(3, new java.sql.Date(borrow.getBeginningDate().getTime()));
			psInsert.setDate(4, new java.sql.Date(borrow.getEndingDate().getTime()));
			if (borrow.getExtension() != null) {
				psInsert.setInt(5, borrow.getExtension().getExtensionID());
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
	 * Enlève un borrow dans la base de données.
	 * 
	 * @param id
	 *            de l'emprunt concerné.
	 * @return un booléen accusant de l'état de la suppression.
	 */
	public boolean remove(int itemID, int memberID, Date beginningDate) {
		try {
			super.connect();

			PreparedStatement psDelete = connection
					.prepareStatement("DELETE FROM BORROW WHERE item_id = ? AND member_id = ? AND start_date = ?");
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
	 * retourne un emprunt.
	 * 
	 * @param id
	 * @return un emprunt dont l'identifiant correspond, ou null.
	 */
	public Borrow get(int itemID, int memberID, Date beginningDate) {
		try {
			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			String request = "SELECT BORROW.start_date AS B_start_date, BORROW.end_date AS B_end_date, "
					+ "MEMBER.id AS M_id, MEMBER.first_name AS M_first_name, MEMBER.last_name AS M_last_name, MEMBER.pseudo AS M_pseudo, MEMBER.password AS M_password, MEMBER.is_admin AS M_is_admin, MEMBER.birth_date AS M_birth_date, MEMBER.phone_number AS M_phone_number, MEMBER.email_address AS M_email_address, MEMBER.street_address AS M_street_address, MEMBER.postal_code AS M_postal_code, MEMBER.city AS M_city, "
					+ "ITEM.id AS I_id, ITEM.comments AS I_comments, "
					+ "EXTENSION.id AS E_id, EXTENSION.name AS E_name " + "FROM BORROW "
					+ "JOIN MEMBER ON BORROW.member_id = MEMBER.id " + "JOIN ITEM ON BORROW.item_id = ITEM.id "
					+ "LEFT JOIN EXTENSION ON BORROW.extension_id = EXTENSION.id";
			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Borrow borrow = null;
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
				borrow = new Borrow(item, member, resultSet.getDate("B_start_date"), resultSet.getDate("B_end_date"),
						extension);
			}
			super.disconnect();
			return borrow;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Méthode d'accès à tous les emprunts.
	 * 
	 * @return la liste des emprunts.
	 */
	public List<Borrow> getBorrows(int userID) {
		List<Borrow> borrows = new ArrayList<Borrow>();
		try {
			super.connect();

			// Utilisation des "AS" à cause des jointures.
			// Il n'est pas possible
			// d'utiliser les DAOs dédiés aux Items, Members et Extensions car
			// une seule connection peut-être active à la fois pour toute la
			// base de données, donc qu'une seule requête à la fois (contrainte
			// du SGBD Derby)

			String request = "SELECT BORROW.start_date AS B_start_date, BORROW.end_date AS B_end_date, "
					+ "MEMBER.id AS M_id, MEMBER.first_name AS M_first_name, MEMBER.last_name AS M_last_name, MEMBER.pseudo AS M_pseudo, MEMBER.password AS M_password, MEMBER.is_admin AS M_is_admin, MEMBER.birth_date AS M_birth_date, MEMBER.phone_number AS M_phone_number, MEMBER.email_address AS M_email_address, MEMBER.street_address AS M_street_address, MEMBER.postal_code AS M_postal_code, MEMBER.city AS M_city, "
					+ "ITEM.id AS I_id, ITEM.comments AS I_comments, "
					+ "EXTENSION.id AS E_id, EXTENSION.name AS E_name " + "FROM BORROW "
					+ "JOIN MEMBER ON BORROW.member_id = MEMBER.id " + "JOIN ITEM ON BORROW.item_id = ITEM.id "
					+ "LEFT JOIN EXTENSION ON BORROW.extension_id = EXTENSION.id ";

			if (userID != -1) { // Filtrer sur l'utilisateur si l'identifiant
								// n'est pas -1
				request += "WHERE BORROW.member_id = " + userID;
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
				Borrow borrow = new Borrow(item, member, resultSet.getDate("B_start_date"),
						resultSet.getDate("B_end_date"), extension);
				borrows.add(borrow);
			}

			super.disconnect();
			return borrows;
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
					"SELECT count(*) FROM BORROW WHERE item_id = ?");
			
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
						+ "FROM BORROW "
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
					"SELECT count(*) FROM BORROW WHERE extension_id = ?");
			
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
						+ "FROM APP.BORROW "
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
					"SELECT count(*) FROM BORROW WHERE member_id = ?");
			
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

}
