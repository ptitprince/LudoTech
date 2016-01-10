package backend.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe manipulant des objets de type GameEditor dans la base de données
 */
public class GameEditorDAO extends DAO {

	/**
	 * Cherche l'identifiant d'un éditeur de jeu (le créer s'il n'existe pas
	 * encore)
	 * 
	 * @param gameEditor
	 *            Le nom de l'éditeur de jeu
	 * @return L'identifiant de l'éditeur de jeu
	 */
	public int getID(String gameEditor) {
		int idFound = -1;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT id FROM GAME_EDITOR WHERE name = ?");
			psSelect.setString(1, gameEditor.toLowerCase());
			psSelect.execute();

			ResultSet idRS = psSelect.getResultSet();
			if (idRS != null && idRS.next()) { // L'identifiant a été trouvé
				idFound = idRS.getInt("id");
			} else {
				PreparedStatement psInsert = connection.prepareStatement("INSERT INTO GAME_EDITOR(name) VALUES (?)",
						new String[] { "ID" });
				psInsert.setString(1, gameEditor.toLowerCase());
				psInsert.executeUpdate();

				// Récupération de l'identifiant généré automatiquement par
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
	 * Liste tous les éditeurs de jeu existants
	 * @param sorted Vrai si la liste doit être triée par ordre alphabétique
	 * @return La liste des éditeurs de jeu existantes
	 */
	public List<String> list(boolean sorted) {
		List<String> editors = new ArrayList<String>();
		try {
			super.connect();

			PreparedStatement psSelect = connection
					.prepareStatement("SELECT * FROM GAME_EDITOR " + ((sorted) ? "ORDER BY name ASC" : ""));
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) { // Positionnement sur le premier résultat
				editors.add(resultSet.getString("name"));
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return editors;
	}
}
