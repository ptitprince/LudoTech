package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.POJOs.Game;
import model.POJOs.Item;

/**
 * Classe manipulant des objets de type Item dans la base de données
 */
public class ItemDAO extends DAO {

	/**
	 * Ajoute un exemplaire (en lui donnant un identifiant) pour un certain jeu
	 * dans la base de données
	 * 
	 * @param item
	 *            Un exemplaire non null sans identifiant
	 * @param gameID
	 *            L'identifiant du jeu existant en base de données
	 * @return True si l'exemplaire a bien été ajouté en base de données, sinon
	 *         False
	 */
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

	/**
	 * Supprime un exemplaire existant de la base de données
	 * 
	 * @param id
	 *            L'identifiant d'un exemplaire existant
	 * @return True si l'exemplaire a bien été supprimé, sinon False
	 */
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
	
	public String getGameName(int itemID) {
		String name = "";
		try {
			super.connect();

			String request = "SELECT GAME.name FROM ITEM "
					+ "JOIN GAME ON ITEM.game_id = GAME.id "
					+ "WHERE ITEM.id = ?";

			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.setInt(1, itemID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) {
				name = resultSet.getString("GAME.name");
			}
			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * Liste les exemplaires possédés par un certain jeu
	 * 
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @return La liste des exemplaires possédés par le jeu
	 */
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
