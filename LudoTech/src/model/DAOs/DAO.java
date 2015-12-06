package model.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe abstraite gérant la connection avec la base de données
 */
public abstract class DAO {

	/**
	 * Constante définissant le pilote JDBC � utiliser
	 */
	private final static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	/**
	 * Constante définissant l'URL à utiliser par JDBC pour accéder à la base de
	 * données
	 */
	private final static String CONNECTION_URL = "jdbc:derby:db;create=true";

	/**
	 * Nom du schéma à utiliser pour toute connection à la base de données
	 */
	private static String usedSchema = "APP";

	/**
	 * Représentation de la connection à la base de données. Elle peut-être
	 * nulle (jamais utilisée) ou ouverte/fermée.
	 */
	protected static Connection connection;

	/**
	 * Vérifie si le pilote JDBC spécifié existe bien dans l'application
	 * 
	 * @return true le pilote JDBC existe bien
	 * @return false le pilote JDBC n'a pas été trouvé
	 */
	public static boolean checkDatabaseDriver() {
		try {
			Class.forName(DRIVER);
			return true;
		} catch (ClassNotFoundException exception) {
			return false;
		}
	}

	/**
	 * Modifie le nom du schema à utiliser dans la base de données
	 * 
	 * @param schemaToUse
	 *            Le nouveau nom à utiliser
	 */
	public static void setUsedSchema(String schemaToUse) {
		usedSchema = schemaToUse;
	}

	/**
	 * Connecte l'application � la base de données embarquée et définie quel
	 * schéma utiliser pour cette connection
	 * 
	 * @throws SQLException
	 *             S'il est impossible d'obtenir une connection à la base de
	 *             données (souvent dû à une utilisation parallèle)
	 */
	protected void connect() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(CONNECTION_URL);
			connection.setSchema(usedSchema);
		}
	}

	/**
	 * Deconnecte l'application de la base de données embarquée
	 * 
	 * @throws SQLException
	 */
	protected void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

}
