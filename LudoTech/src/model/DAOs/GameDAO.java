package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.pojos.Game;

/**
 * Classe manipulant des objets de type Game dans la base de donn�es
 * 
 * @author Th�o Gauchoux
 *
 */
public class GameDAO extends DAO {

	/**
	 * Ajoute une nouvelle ligne dans la table Game de la base de donn�es, avec
	 * les informations d'un jeu en utilisant la G�n�ration automatique de
	 * l'identifiant (cl� primaire) par le pilote Derby
	 * 
	 * @param game
	 *            Le jeu � ajouter dans la base de donn�es
	 * @param gameCategoryID
	 *            L'identifiant en base de donn�es de la cat�gorie du jeu
	 * @param gameEditorID
	 *            L'identifiant en base de donn�es de l'�diteur du jeu
	 * @return true L'ajout du a �t� fait correctement
	 * @return false Une exception est survenue, l'ajout s'est peut-�tre mal
	 *         pass�
	 */
	public boolean add(Game game, int gameCategoryID, int gameEditorID) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement(
					"INSERT INTO " 
						+ "Game(name, description, publishing_year, minimum_age, minimum_players, maximum_players, category_id, editor_id) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
						new String[] { "ID" });
			psInsert.setString(1, game.getName());
			psInsert.setString(2, game.getDescription());
			psInsert.setInt(3, game.getPublishingYear());
			psInsert.setInt(4, game.getMinimumAge());
			psInsert.setInt(5, game.getMinimumPlayers());
			psInsert.setInt(6, game.getMaximumPlayers());
			psInsert.setInt(7, gameCategoryID);
			psInsert.setInt(8, gameEditorID);

			psInsert.executeUpdate();
			
			// R�cup�ration de l'identifiant du jeu g�n�r� automatiquement par Derby
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
	 * Modifie les valeurs d'une ligne de la table Game dans la base de donn�es en se servant de l'identifiant d'un jeu
	 * 
	 * @param game
	 *            Le jeu � modifier dans la base de donn�es
	 * @param gameCategoryID
	 *            L'identifiant en base de donn�es de la cat�gorie du jeu
	 * @param gameEditorID
	 *            L'identifiant en base de donn�es de l'�diteur du jeu
	 * @return true La modification a �t� faite correctement
	 * @return false Une exception est survenue, la modification s'est peut-�tre mal pass�e
	 */
	public boolean edit(Game game, int gameCategoryID, int gameEditorID) {
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement(
					"UPDATE Game "
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
	 * Supprime une ligne de la table Game dans la base de donn�es en se servant de l'identifiant d'un jeu
	 * 
	 * @param game
	 *            Le jeu � supprimer dans la base de donn�es
	 *
	 * @return true La suppression a �t� faite correctement
	 * @return false Une exception est survenue, la suppression s'est peut-�tre mal pass�e
	 */
	
	public boolean remove(Game game) {
		try {
			PreparedStatement psInsert = connection.prepareStatement(
					"REMOVE FROM "
					+ "Game WHERE "
					+ "id_game = ?");
					psInsert.setString(1, game.getName());
					psInsert.execute();
					psInsert.closeOnCompletion();

					super.disconnect();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
	}

}
