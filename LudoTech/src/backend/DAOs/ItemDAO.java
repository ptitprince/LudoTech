package backend.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.POJOs.Item;

/**
 * Classe manipulant des objets de type Item (Exemplaire de jeu) dans la base de
 * données
 */
public class ItemDAO extends DAO {

	/**
	 * Cherche un exemplaire possédant l'identifiant en paramètre dans la base
	 * de données
	 * 
	 * @param itemID
	 *            L'identifiant d'un exemplaire existant en base de données
	 * @return Un exemplaire si trouvé, sinon null
	 */
	public Item get(int itemID) {
		try {
			super.connect();

			String request = "SELECT * FROM ITEM WHERE id=?";
			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.setInt(1, itemID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Item item = null;
			if (resultSet.next()) {
				item = new Item(itemID);
			}

			super.disconnect();
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Détermine le nom du jeu auquel est associé l'exemplaire dont
	 * l'identifiant est passé en paramètre
	 * 
	 * @param itemID
	 *            L'identifiant d'un exemplaire existant en base de données
	 * @return Le nom du jeu si l'exemplaire est trouvé, sinon une chaîne de
	 *         caractères vide.
	 */
	public String getGameName(int itemID) {
		String name = "";
		try {
			super.connect();

			String request = "SELECT GAME.name AS game_name FROM ITEM " + "JOIN GAME ON ITEM.game_id = GAME.id "
					+ "WHERE ITEM.id = ?";

			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.setInt(1, itemID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) {
				name = resultSet.getString("game_name");
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
	 * @return La liste potentiellement vide des exemplaires possédés par le jeu
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
				items.add(new Item(resultSet.getInt("id")));
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

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

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO " + "ITEM(game_id) " + "VALUES (?)",
					new String[] { "ID" });
			// Auto-incrémentation sur la clé primaire ID
			psInsert.setInt(1, gameID);

			psInsert.executeUpdate();

			// Récupération de l'identifiant de l'exemplaire généré
			// automatiquement par
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

}
