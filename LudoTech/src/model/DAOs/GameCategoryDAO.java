package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe manipulant des objets de type GameCategory dans la base de données
 *
 */
public class GameCategoryDAO extends DAO {

	/**
	 * Cherche l'identifiant d'une catégorie de jeu (la créer si elle n'existe
	 * pas encore)
	 * 
	 * @param gameCategory
	 *            Le nom de la catégorie de jeu
	 * @return L'identifiant de la catégorie de jeu
	 */
	public int getID(String gameCategory) {
		int idFound = -1;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT ID FROM GAME_CATEGORY WHERE category = ?");
			psSelect.setString(1, gameCategory.toLowerCase());
			psSelect.execute();

			ResultSet idRS = psSelect.getResultSet();
			if (idRS != null && idRS.next()) { // L'identifiant a été trouvé
				idFound = idRS.getInt("id");
			} else {
				PreparedStatement psInsert = connection
						.prepareStatement("INSERT INTO GAME_CATEGORY(category) VALUES (?)", new String[] { "ID" });
				psInsert.setString(1, gameCategory.toLowerCase());
				psInsert.executeUpdate();

				// Récupération de l'identifiant généré automatiquement par Derby
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
	
	/**
	 * Trouve le nom d'une categorie de jeu dans la base de données
	 * @param categoryID L'identifiant de la catégorie à trouver
	 * @return Le nom de la catégorie identifié par "id" ou null si aucune ne correspond en base de données
	 */
	public String getName(int categoryID){
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM GAME_CATEGORY WHERE id = ?");
			psSelect.setInt(1, categoryID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			String category = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				category = resultSet.getString("category");
			}
			super.disconnect();
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
