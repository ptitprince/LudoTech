package model.DAOs;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.POJOs.Book;
import model.POJOs.Borrow;
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

			String request = "SELECT BOOK.*, "
					+ "MEMBER.*, ITEM.*, EXTENSION.* "
					+ "FROM BOOK "
					+ "JOIN MEMBER ON BOOK.member_id = MEMBER.id "
					+ "JOIN ITEM ON BOOK.item_id = ITEM.id "
					+ "JOIN EXTENSION ON BORROW.extension_id = EXTENSION.id "
					+ "WHERE BOOK.item_id = ?, BOOK.member_id = ?, BOOK.beginning_date = ?";

			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.setInt(1, itemID);
			psSelect.setInt(2, memberID);
			psSelect.setDate(3, new java.sql.Date(beginningDate.getTime()));
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Book book = null;
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
				book = new Book(item, member,
						resultSet.getDate("beginning_date"),
						resultSet.getDate("ending_date"), extension);
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

			String request = "SELECT item_id, member_id, beginning_date FROM BOOK";
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
				books.add(this.get(resultSet.getInt("item_id"), resultSet.getInt("member_id"), resultSet.getDate("beginning_date")));
			}// plus de borrow, la fin de la "liste".
			super.disconnect();
			return books;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
}
