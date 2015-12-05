package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.pojos.Game;

/**
 * Classe manipulant des objets de type Game dans la base de données
 */
public class GameDAO extends DAO {

	/**
	 * Ajoute une nouvelle ligne dans la table Game de la base de données, avec
	 * les informations d'un jeu en utilisant la génération automatique de
	 * l'identifiant (clé primaire) par le pilote Derby
	 * 
	 * @param game
	 *            Le jeu à ajouter dans la base de données
	 * @param gameCategoryID
	 *            L'identifiant en base de données de la catégorie du jeu
	 * @param gameEditorID
	 *            L'identifiant en base de données de l'éditeur du jeu
	 * @return true L'ajout du jeu a été fait correctement
	 * @return false Une exception est survenue, l'ajout s'est peut-être mal
	 *         passé
	 */
	public boolean add(Game game, int gameCategoryID, int gameEditorID) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "GAME(name, description, publishing_year, minimum_age, minimum_players, maximum_players, category_id, editor_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "ID" }); 
			// Auto-incrémentation sur la clé primaire ID
			psInsert.setString(1, game.getName());
			psInsert.setString(2, game.getDescription());
			psInsert.setInt(3, game.getPublishingYear());
			psInsert.setInt(4, game.getMinimumAge());
			psInsert.setInt(5, game.getMinimumPlayers());
			psInsert.setInt(6, game.getMaximumPlayers());
			psInsert.setInt(7, gameCategoryID);
			psInsert.setInt(8, gameEditorID);

			psInsert.executeUpdate();

			// Récupération de l'identifiant du jeu généré automatiquement par
			// Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				game.setGameID(idRS.getInt(1));
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
	 * Modifie les valeurs d'une ligne de la table Game dans la base de données
	 * en se servant de l'identifiant d'un jeu
	 * 
	 * @param game
	 *            Le jeu à modifier dans la base de données
	 * @param gameCategoryID
	 *            L'identifiant en base de données de la cat�gorie du jeu
	 * @param gameEditorID
	 *            L'identifiant en base de données de l'éditeur du jeu
	 * @return true La modification du jeu a été faite correctement
	 * @return false Une exception est survenue, la modification s'est peut-être
	 *         mal passée
	 */
	public boolean edit(Game game, int gameCategoryID, int gameEditorID) {
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement("UPDATE GAME "
					+ "SET name = ?, description = ?, publishing_year = ?, minimum_age = ?, minimum_players = ?, maximum_players = ?, category_id = ?, editor_id = ? "
					+ "WHERE id = ?");
			psEdit.setString(1, game.getName());
			psEdit.setString(2, game.getDescription());
			psEdit.setInt(3, game.getPublishingYear());
			psEdit.setInt(4, game.getMinimumAge());
			psEdit.setInt(5, game.getMinimumPlayers());
			psEdit.setInt(6, game.getMaximumPlayers());
			psEdit.setInt(7, gameCategoryID);
			psEdit.setInt(8, gameEditorID);
			psEdit.setInt(9, game.getGameID());

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
	 * Supprime une ligne de la table Game dans la base de données en se servant
	 * de l'identifiant d'un jeu
	 * 
	 * @param id
	 *            L'identifiant du jeu à supprimer
	 * @return True si le jeu a bien été supprimé ou s'il n'existe pas en base
	 *         de données, sinon False
	 */
	public boolean remove(int id) {
		try {
			super.connect();

			PreparedStatement psDelete = connection.prepareStatement("DELETE FROM Game WHERE id = ?");
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
	 * Trouve un jeu dans la base de données
	 * 
	 * @param id
	 *            L'identifiant du jeu à trouver
	 * @return Le jeu identifié par "id" ou null si aucun ne correspond en base
	 *         de données
	 */
	public Game get(int id) {
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM GAME WHERE id = ?");
			psSelect.setInt(1, id);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Game game = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				game = new Game(id, resultSet.getString("name"), resultSet.getString("description"),
						resultSet.getInt("publishing_year"), resultSet.getInt("minimum_age"),
						resultSet.getInt("minimum_players"), resultSet.getInt("maximum_players"), "", "");
			}
			super.disconnect();
			return game;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Trouve l'identifiant de la catégorie d'un jeu dans la base de données
	 * 
	 * @param id
	 *            L'identifiant du jeu à utiliser
	 * @return L'identifiant de la catégorie du jeu dont l'identifiant est passé
	 *         en paramètres ou -1 si le jeu ne possède pas de categorie
	 */
	public int getCategoryID(int gameID) {
		int categoryID = -1;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT category_id FROM GAME WHERE id = ?");
			psSelect.setInt(1, gameID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				categoryID = resultSet.getInt("category_id");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryID;
	}

	/**
	 * Trouve l'identifiant de l'éditeur d'un jeu dans la base de données
	 * 
	 * @param id
	 *            L'identifiant du jeu à utiliser
	 * @return L'identifiant de l'éditeur du jeu dont l'identifiant est passé en
	 *         paramètres ou -1 si le jeu ne possède pas d'éditeur
	 */
	public int getEditorID(int gameID) {
		int editorID = -1;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT editor_id FROM GAME WHERE id = ?");
			psSelect.setInt(1, gameID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				editorID = resultSet.getInt("editor_id");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return editorID;
	}

	/**
	 * Liste les identifiants de tous les jeux de la base de données
	 * 
	 * @return La liste des identifiants de tous les jeux
	 */
	public List<Integer> getIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM GAME");
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) { // Positionnement sur le premier résultat
				ids.add(resultSet.getInt("id"));
			}
			
			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

}