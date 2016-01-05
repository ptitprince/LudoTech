package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import model.POJOs.Borrow;
import model.POJOs.Extension;
import model.POJOs.Item;
import model.POJOs.Member;

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
					.prepareStatement("INSERT INTO "
							+ "Borrow(item_id, member_id, beginning_date, ending_date, extension_id) "
							+ "VALUES (?, ?, ?, ?, ?)");
			psInsert.setInt(1, borrow.getItem().getItemID());
			psInsert.setInt(2, borrow.getMember().getMemberID());
			psInsert.setDate(3, new java.sql.Date(borrow.getBeginningDate()
					.getTime()));
			psInsert.setDate(4, new java.sql.Date(borrow.getEndingDate()
					.getTime()));
			psInsert.setInt(5, borrow.getExtension().getExtensionID());

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
					.prepareStatement("DELETE FROM BORROW WHERE item_id = ?, member_id = ?, beginning_date = ?");
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
	 * Modifie un emprunt donné en paramètre.
	 * 
	 * @param borrow
	 *            un emprunt.
	 * @param itemID
	 *            l'identifiant de l'item.
	 * @param memberID
	 *            l'identifiant du membre.
	 * @return un emprunt modifié.
	 */
	public boolean edit(Borrow borrow) {
		try {
			super.connect();

			PreparedStatement psEdit = connection
					.prepareStatement("UPDATE BORROW "
							+ "SET ending_date = ?, extension_id = ? "
							+ "WHERE item_id = ?, member_id = ?, beginning_date = ?");
			psEdit.setDate(1, new java.sql.Date(borrow.getEndingDate()
					.getTime()));
			psEdit.setInt(2, borrow.getExtension().getExtensionID());
			psEdit.setInt(3, borrow.getItem().getItemID());
			psEdit.setInt(4, borrow.getMember().getMemberID());
			psEdit.setInt(5, borrow.getExtension().getExtensionID());
			psEdit.setDate(7, new java.sql.Date(borrow.getBeginningDate()
					.getTime()));

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
	 * retourne un emprunt.
	 * 
	 * @param id
	 * @return un emprunt dont l'identifiant correspond, ou null.
	 */
	public Borrow get(int itemID, int memberID, Date beginningDate) {
		try {
			super.connect();

			String request = "SELECT BORROW.*, "
					+ "MEMBER.*, ITEM.*, EXTENSION.* "
					+ "FROM BORROW "
					+ "JOIN MEMBER ON BORROW.member_id = MEMBER.id "
					+ "JOIN ITEM ON BORROW.item_id = ITEM.id "
					+ "JOIN EXTENSION ON BORROW.extension_id = EXTENSION.id "
					+ "WHERE BORROW.item_id = ?, BORROW.member_id = ?, BORROW.beginning_date = ?";

			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.setInt(1, itemID);
			psSelect.setInt(2, memberID);
			psSelect.setDate(3, new java.sql.Date(beginningDate.getTime()));
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Borrow borrow = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				Item item = new Item(resultSet.getInt("ITEM.id"),
						resultSet.getString("ITEM.comments"));
				Member member = new Member(resultSet.getInt("MEMBER.id"),
						resultSet.getString("MEMBER.first_name"),
						resultSet.getString("MEMBER.last_name"),
						resultSet.getString("MEMBER.pseudo"),
						resultSet.getString("MEMBER.password"),
						resultSet.getBoolean("is_admin"),
						resultSet.getDate("MEMBER.birth_date"),
						resultSet.getString("MEMBER.phone_number"),
						resultSet.getString("MEMBER.email_address"),
						resultSet.getString("MEMBER.street_address"),
						resultSet.getString("MEMBER.postal_code"),
						resultSet.getString("MEMBER.city"));
				Extension extension = new Extension(
						resultSet.getInt("EXTENSION.id"),
						resultSet.getString("EXTENSION.name"));
				borrow = new Borrow(item, member,
						resultSet.getDate("beginning_date"),
						resultSet.getDate("ending_date"), extension);
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
	public List<Borrow> getBorrows(/* HashMap<String, String> filter */) {
		List<Borrow> borrows = new ArrayList<Borrow>();
		try {
			super.connect();

			String request = "SELECT item_id, member_id, beginning_date FROM BORROW";
			/*
			 * String whereClause = ""; boolean atLeastOneCondition = false; for
			 * (Entry<String, String> property : filter.entrySet()) { if
			 * (property.getKey().equals("item_id") &&
			 * !property.getValue().equals("")) { whereClause +=
			 * ((atLeastOneCondition) ? " AND " : " ") + "LOWER(BORROW." +
			 * property.getKey() + ")" + " LIKE LOWER('%" + property.getValue()
			 * + "%')"; atLeastOneCondition = true; } else if
			 * (property.getKey().equals("member_id") &&
			 * !property.getValue().equals("")) { whereClause +=
			 * ((atLeastOneCondition) ? " AND " : " ") + property.getKey() +
			 * property.getValue(); atLeastOneCondition = true; } else if
			 * (property.getKey().equals("beginning_date") &&
			 * !property.getValue().equals("")) { whereClause +=
			 * ((atLeastOneCondition) ? " AND " : " ") + property.getValue();
			 * atLeastOneCondition = true; } else if
			 * (property.getKey().equals("ending_date") &&
			 * !property.getValue().equals("")) { whereClause +=
			 * ((atLeastOneCondition) ? " AND " : " ") + property.getKey() +
			 * property.getValue(); atLeastOneCondition = true; } else if
			 * (property.getKey().equals("borrow_state") &&
			 * !property.getValue().equals("")) { whereClause +=
			 * ((atLeastOneCondition) ? " AND " : " ") +
			 * "LOWER(.category) = LOWER('" + property.getValue() + "')";
			 * atLeastOneCondition = true; } else if
			 * (property.getKey().equals("editor") &&
			 * !property.getValue().equals("")) { whereClause +=
			 * ((atLeastOneCondition) ? " AND " : " ") +
			 * "LOWER(GAME_EDITOR.name) = LOWER('" + property.getValue() + "')";
			 * atLeastOneCondition = true; } } // Pas compris, à demander. if
			 * (atLeastOneCondition) { request += "WHERE" + whereClause; }
			 */
			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();

			while (resultSet.next()) {// boucle pour sélectionner tous les
										// emprunts.
				borrows.add(this.get(resultSet.getInt("item_id"), resultSet.getInt("member_id"), resultSet.getDate("beginning_date")));
			}// plus de borrow, la fin de la "liste".
			super.disconnect();
			return borrows;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
