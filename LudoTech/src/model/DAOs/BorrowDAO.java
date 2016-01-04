package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
					+ "Borrow(borrow_id, item_id, member_id, beginning_date, ending_date, borrow_state, borrow_available) "
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
	/**
	 * Modifie un emprunt donné en paramètre.
	 * @param borrow un emprunt.
	 * @param itemID l'identifiant de l'item.
	 * @param memberID l'identifiant du membre.
	 * @return un emprunt modifié.
	 */
	public boolean edit(Borrow borrow, int itemID, int memberID)	{
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement("UPDATE BORROW "
					+ "SET item_id = ?, member_id = ?, beginning_date = ?, ending_date = ?, borrow_state = ?, borrow_available = ?) "
					+ "WHERE borrow_id = ?");
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
	/**
	 * retourne un emprunt.
	 * @param id
	 * @return un emprunt dont l'identifiant correspond, ou null.
	 */
	public Borrow get(int id) {
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM BORROW WHERE borrow_id = ?");
			psSelect.setInt(1, id);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Borrow borrow = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				borrow = new Borrow(id, resultSet.getInt("item_id"), resultSet.getInt("member_id"),
						resultSet.getDate("beginning_date"), resultSet.getDate("ending_date"),
						resultSet.getString("borrow_state"), resultSet.getBoolean("borrow_available"));
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
	 * @return la liste des emprunts.
	 */
	public List<Borrow> getBorrows()	{
		List<Borrow> borrows = new ArrayList<Borrow>();
		try {
			super.connect();
			
			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM BORROW");
			psSelect.execute();
			psSelect.closeOnCompletion();
			
			ResultSet resultSet = psSelect.getResultSet();
			
			while(resultSet.next())	{// boucle pour sélectionner tous les emprunts.
				borrows.add(new Borrow(resultSet.getInt("borrow_id"), resultSet.getInt("item_id"), resultSet.getInt("member_id"),
						resultSet.getDate("beginning_date"), resultSet.getDate("ending_date"),resultSet.getString("borrow_state"), 
						resultSet.getBoolean("borrow_available")));
			}//plus de borrow, la fin de la "liste".
			super.disconnect();
			return borrows;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		
	}
	
}
