package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe manipulant des objets de type GameCategory dans la base de donn�es
 *
 */
public class GameCategoryDAO extends DAO {

	/**
	 * Cherche l'identifiant d'une cat�gorie de jeu (la cr�� si elle n'existe pas encore)
	 * @param gameCategory Le nom de la cat�gorie de jeu
	 * @return L'identifiant de la cat�gorie de jeu 
	 */
	public int getID(String gameCategory) {
		
		int idFound = -1;
		
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement(
					"SELECT ID FROM GAME_CATEGORY WHERE category = ?");
			psSelect.setString(1, gameCategory.toLowerCase());
			psSelect.execute();
			
			ResultSet idRS = psSelect.getResultSet();
			if (idRS != null && idRS.next()) { // L'identifiant a �t� trouv�
				idFound = idRS.getInt(1);
			} else {
				PreparedStatement psInsert = connection.prepareStatement(
						"INSERT INTO GAME_CATEGORY(category) VALUES (?)", new String[] { "ID" });
				psInsert.setString(1, gameCategory.toLowerCase());
				psInsert.executeUpdate();
				
				// R�cup�ration de l'identifiant g�n�r� automatiquement par Derby
				idRS = psInsert.getGeneratedKeys();
				if (idRS != null && idRS.next()) {
					idFound = idRS.getInt(1);
				} else {
					throw new SQLException();
				}
			}
			
			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idFound;
	}
}
