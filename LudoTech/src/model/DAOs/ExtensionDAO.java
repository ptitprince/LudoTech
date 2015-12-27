package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.POJOs.Extension;

/**
 * Classe manipulant des objets de type Extension dans la base de données
 */
public class ExtensionDAO extends DAO {

	/**
	 * Ajoute une extension (en lui donnant un identifiant) pour un certain jeu
	 * dans la base de données
	 * 
	 * @param extension
	 *            Une extension non null sans identifiant
	 * @param gameID
	 *            L'identifiant du jeu existant en base de données
	 * @return True si l'extension a bien été ajoutée en base de données, sinon
	 *         False
	 */
	public boolean add(Extension extension, int gameID) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement(
					"INSERT INTO " + "EXTENSION(name, game_id) " + "VALUES (?, ?)", new String[] { "ID" });
			// Auto-incrémentation sur la clé primaire ID
			psInsert.setString(1, extension.getName());
			psInsert.setInt(2, gameID);

			psInsert.executeUpdate();

			// Récupération de l'identifiant de l'extension générée
			// automatiquement par Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				extension.setExtensionID(idRS.getInt(1));
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
	 * Supprime une extension existante de la base de données
	 * 
	 * @param id
	 *            L'identifiant d'une extension existante
	 * @return True si l'extension a bien été supprimée, sinon False
	 */
	public boolean remove(int id) {
		try {
			super.connect();

			PreparedStatement psDelete = connection.prepareStatement("DELETE FROM Extension WHERE id = ?");
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
	 * Liste les extensions possédées par un certain jeu
	 * 
	 * @param gameID
	 *            L'identifiant d'un jeu existant
	 * @return La liste des extensions possédées par le jeu
	 */
	public List<Extension> getAll(int gameID) {
		List<Extension> extensions = new ArrayList<Extension>();
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM Extension WHERE game_id = ?");
			psSelect.setInt(1, gameID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) { // Positionnement sur le premier résultat
				extensions.add(new Extension(resultSet.getInt("id"), resultSet.getString("name")));
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return extensions;
	}

}
