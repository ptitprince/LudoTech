package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.POJOs.Borrow;

public class BorrowDAO extends DAO {
	
	/**
	 * Ajoute une nouvelle ligne dans la table Borrow, avec 
	 * un identifiant créé par Derby.
	 * 
	 * @param 	  Borrow, l'emprunt à ajouter dans la base de
	 * données.
	 * @return true L'ajout de l'emprunt a été fait correctement. 
	 * @return false Exception, peut-être un problème qui est survenu.
	 */
	
	public boolean add(Borrow borrow, int itemID, int memberID)	{
		
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "Borrow(borrowId, itemID, memberID, beginningDate, endingDate, borrowState, borrowAvailable) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)", new String[] { "ID" });
			psInsert.setInt(1, borrow.getBorrowId());
			psInsert.setInt(2, itemID);
			//Extension un jeu ? Besoin d'un ID ?
			psInsert.setInt(3, memberID);
			psInsert.setDate(4, new java.sql.Date(borrow.getBeginningDate().getTime()));
			psInsert.setDate(5, new java.sql.Date(borrow.getEndingDate().getTime()));
			psInsert.setString(6, borrow.getBorrowState());;
			psInsert.setBoolean(7, borrow.isBorrowAvailable());

			psInsert.executeUpdate();

			// Récupération de l'identifiant du contexte d'un adhérent généré
			// automatiquement par Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				borrow.setBorrowId(idRS.getInt(1));
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
	 * Enlève un borrow dans la base de données.
	 * @param id de l'emprunt concerné.
	 * @return un booléen accusant de l'état de la suppression.
	 */
	
	public boolean remove(int id) {
		try {
			super.connect();

			PreparedStatement psDelete = connection.prepareStatement("DELETE FROM BORROW WHERE id = ?");
			psDelete.setInt(1, id);
			psDelete.execute();
			psDelete.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean edit(Borrow borrow, int itemID, int memberID)	{
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement("UPDATE BORROW "
					+ "SET itemID = ?, memberID = ?, beginningDate = ?, endingDate = ?, borrowState = ?, borrowAvailable = ?) "
					+ "WHERE id = ?");
			psEdit.setInt(1, itemID);
			//Extension un jeu ? Besoin d'un ID ?
			psEdit.setInt(2, memberID);
			psEdit.setDate(3, new java.sql.Date(borrow.getBeginningDate().getTime()));
			psEdit.setDate(4, new java.sql.Date(borrow.getEndingDate().getTime()));
			psEdit.setString(5, borrow.getBorrowState());;
			psEdit.setBoolean(6, borrow.isBorrowAvailable()); //Si canbook ou canborrow sur le membre, pas utile ?
			psEdit.setInt(7, borrow.getBorrowId());

			psEdit.executeUpdate();
			psEdit.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*public Borrow get(int id) {
		try { //TODO : problème avec le resultSet
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM BORROW WHERE id = ?");
			psSelect.setInt(1, id);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Borrow borrow = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				borrow = new Borrow(id, resultSet.get("item"), resultSet.get("member"),
						resultSet.getDate("beginningDate"), resultSet.getDate("endingDate"),
						resultSet.getString("borrowState"), resultSet.getBoolean("borrowAvailable"));
			}
			super.disconnect();
			return game;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
	
}
