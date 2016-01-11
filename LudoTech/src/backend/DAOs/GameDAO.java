package backend.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import backend.POJOs.Game;

/**
 * Classe manipulant des objets de type Game (Jeu) dans la base de données
 */
public class GameDAO extends DAO {

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

			String request = "SELECT GAME.*, " + "GAME_EDITOR.name AS editor_name, "
					+ "GAME_CATEGORY.category AS category_name " + "FROM GAME "
					+ "JOIN GAME_EDITOR ON GAME.editor_id = GAME_EDITOR.id "
					+ "JOIN GAME_CATEGORY ON GAME.category_id = GAME_CATEGORY.id " + "WHERE GAME.id = ?";

			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.setInt(1, id);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Game game = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				game = new Game(id, resultSet.getString("name"), resultSet.getString("description"),
						resultSet.getInt("publishing_year"), resultSet.getInt("minimum_age"),
						resultSet.getInt("minimum_players"), resultSet.getInt("maximum_players"),
						resultSet.getString("category_name"), resultSet.getString("editor_name"));
			}
			super.disconnect();
			return game;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Liste tous les jeux de la base de données d'après le filtre passé en
	 * paramètre
	 * 
	 * @param filter
	 *            Ensemble de couples <clef(String), valeur(String)> où la clef
	 *            est incluse dans la liste : name, publishing_year, nb_players,
	 *            minimum_age, category, editor.
	 * @return Une liste de jeux potentiellement vide respectant le filtre
	 */
	public List<Game> getAll(HashMap<String, String> filter) {
		List<Game> games = new ArrayList<Game>();
		try {
			super.connect();

			String request = "SELECT GAME.*, " + "GAME_EDITOR.name AS editor_name, "
					+ "GAME_CATEGORY.category AS category_name " + "FROM GAME "
					+ "JOIN GAME_EDITOR ON GAME.editor_id = GAME_EDITOR.id "
					+ "JOIN GAME_CATEGORY ON GAME.category_id = GAME_CATEGORY.id";
			String whereClause = "";
			boolean atLeastOneCondition = false;
			for (Entry<String, String> property : filter.entrySet()) {
				if (property.getKey().equals("name") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + "LOWER(GAME." + property.getKey() + ")"
							+ " LIKE LOWER('%" + property.getValue() + "%')";
					atLeastOneCondition = true;
				} else if (property.getKey().equals("publishing_year") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + property.getKey() + " = "
							+ property.getValue();
					atLeastOneCondition = true;
				} else if (property.getKey().equals("nb_players") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + "minimum_players <= " + property.getValue()
							+ " AND maximum_players >= " + property.getValue();
					atLeastOneCondition = true;
				} else if (property.getKey().equals("minimum_age") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + property.getKey() + " <= "
							+ property.getValue();
					atLeastOneCondition = true;
				} else if (property.getKey().equals("category") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + "LOWER(GAME_CATEGORY.category) = LOWER('"
							+ property.getValue() + "')";
					atLeastOneCondition = true;
				} else if (property.getKey().equals("editor") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + "LOWER(GAME_EDITOR.name) = LOWER('"
							+ property.getValue() + "')";
					atLeastOneCondition = true;
				}
			}
			if (atLeastOneCondition) {
				request += " WHERE" + whereClause;
			}
			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) {
				games.add(new Game(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getInt("publishing_year"),
						resultSet.getInt("minimum_age"), resultSet.getInt("minimum_players"),
						resultSet.getInt("maximum_players"), resultSet.getString("category_name"),
						resultSet.getString("editor_name")));
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return games;
	}

	/**
	 * Ajoute un nouveau jeu dans la base de données (qui obtient
	 * automatiquement un identifiant)
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
	 * Modifie un jeu éxistant de la base de données
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
	 * Supprime un jeu existant de la base de données
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

}