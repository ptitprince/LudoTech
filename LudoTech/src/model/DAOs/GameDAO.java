package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.pojos.Game;
import model.pojos.MemberContext;

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
	 * @param game
	 *            Le jeu à supprimer dans la base de données
	 *
	 * @return true La suppression a été faite correctement
	 * @return false Une exception est survenue, la suppression s'est peut-être
	 *         mal passée
	 */

	public boolean remove(Game game) {
		try {
			super.connect();
			
			PreparedStatement psDelete = connection.prepareStatement("REMOVE FROM " + "Game WHERE " + "id = ?");
			psDelete.setInt(1, game.getGameID());
			psDelete.execute();
			psDelete.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Game get(int id_game){
		Game game = new Game();
		try{
			super.connect();
			
			PreparedStatement psGet = connection.prepareStatement("SELECT * FROM Game WHERE id = ?");
			psGet.setInt(1, id_game);
			psGet.execute();
			psGet.closeOnCompletion();
			
			ResultSet resultSet = psGet.getResultSet();
			
			if (resultSet.next()) { // Positionnement sur le premier résultat
				game = new Game( resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), 
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(6), resultSet.getString(8), resultSet.getString(9));
			}
			super.disconnect();
			return game;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
