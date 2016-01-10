package backend.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe manipulant des objets de type GameCategory dans la base de données
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

				// Récupération de l'identifiant généré automatiquement par
				// Derby
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
	 * Liste toutes les catégories de jeu existantes
	 * 
	 * @param sorted
	 *            Vrai si la liste doit être triée par ordre alphabétique
	 * @return La liste des catégories de jeu existantes
	 */
	public List<String> list(boolean sorted) {
		List<String> categories = new ArrayList<String>();
		try {
			super.connect();

			PreparedStatement psSelect = connection
					.prepareStatement("SELECT * FROM GAME_CATEGORY " + ((sorted) ? "ORDER BY category ASC" : ""));
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) { // Positionnement sur le premier résultat
				categories.add(resultSet.getString("category"));
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
}
