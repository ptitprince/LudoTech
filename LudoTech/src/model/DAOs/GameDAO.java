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
	 * @return true L'ajout du a �t� fait correctement
	 * @return false Une exception est survenue, l'ajout s'est peut-�tre mal
	 *         pass�
	 */
	public boolean add(Game game) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement(
					"INSERT INTO " 
						+ "Game(name, description, category, editor, author, publishing_year, nb_players) "
						+ "values (?, ?, ?, ?, ?, ?, ?)",
						new String[] { "GAME_ID" });
			psInsert.setString(1, game.getName());
			psInsert.setString(2, game.getDescription());
			psInsert.setString(3, game.getCategory().toString());
			psInsert.setString(4, game.getEditor());
			psInsert.setString(5, game.getAuthor());
			psInsert.setInt(6, game.getPublishingYear());
			psInsert.setInt(7, game.getNbPlayers());

			psInsert.executeUpdate();
			
			// R�cup�ration de l'identifiant du jeu g�n�r� automatiquement par Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null) {
				idRS.next();
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
	 * @return true La modification a �t� faite correctement
	 * @return false Une exception est survenue, la modification s'est peut-�tre mal pass�e
	 */
	public boolean edit(Game game) {
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement(
					"UPDATE Game "
						+ "SET name = ?, description = ?, category = ?, editor = ?, author = ?, publishing_year = ?, nb_players = ? "
						+ "WHERE game_id = ?");
			psEdit.setString(1, game.getName());
			psEdit.setString(2, game.getDescription());
			psEdit.setString(3, game.getCategory().toString());
			psEdit.setString(4, game.getEditor());
			psEdit.setString(5, game.getAuthor());
			psEdit.setInt(6, game.getPublishingYear());
			psEdit.setInt(7, game.getNbPlayers());
			psEdit.setInt(8, game.getGameID());
			
			psEdit.executeUpdate();
			psEdit.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
