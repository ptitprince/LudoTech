package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.POJOs.Item;

public class ItemDAO extends DAO {

	public boolean add(Item item, int gameID) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement(
					"INSERT INTO " + "ITEM(comments, game_id) " + "VALUES (?, ?)", new String[] { "ID" });
			// Auto-incrémentation sur la clé primaire ID
			psInsert.setString(1, item.getComments());
			psInsert.setInt(2, gameID);

			psInsert.executeUpdate();

			// Récupération de l'identifiant de l'exemplaire généré automatiquement par
			// Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				item.setItemID(idRS.getInt(1));
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

	public boolean remove(int id) {
		try {
			super.connect();

			PreparedStatement psDelete = connection.prepareStatement("DELETE FROM Item WHERE id = ?");
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

	public List<Item> getAllHavingGameID(int gameID) {
		List<Item> items = new ArrayList<Item>();
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM Item WHERE game_id = ?");
			psSelect.setInt(1, gameID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) { // Positionnement sur le premier résultat
				items.add(new Item(resultSet.getInt("id"), resultSet.getString("comments")));
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

}