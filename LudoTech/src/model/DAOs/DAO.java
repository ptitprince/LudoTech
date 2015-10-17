package model.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe abstraite g�rant la connection avec la base de donn�es
 * 
 * @author Th�o Gauchoux
 *
 */
public abstract class DAO {

	/**
	 * Constante d�finissant le pilote JDBC � utiliser
	 */
	private final static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	/**
	 * Constante d�finissant l'URL � utiliser par JDBC pour acc�der � la base de
	 * donn�es
	 */
	private final static String CONNECTION_URL = "jdbc:derby:db;create=true";

	/**
	 * Nom du sch�ma � utiliser pour toute connection � la base de donn�es
	 */
	private static String usedSchema = "APP";

	/**
	 * Repr�sentation de la connection � la base de donn�es. Elle peut-�tre
	 * nulle (jamais utilis�e) ou ouverte/ferm�e.
	 */
	protected static Connection connection;

	/**
	 * V�rifie si le pilote JDBC sp�cifi� existe bien dans l'application
	 * 
	 * @return true le pilote JDBC existe bien
	 * @return false le pilote JDBC n'a pas �t� trouv�
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
	 * Modifie le nom du schema � utiliser dans la base de donn�es
	 * 
	 * @param schemaToUse
	 *            Le nouveau nom � utiliser
	 */
	public static void setUsedSchema(String schemaToUse) {
		usedSchema = schemaToUse;
	}

	/**
	 * Connecte l'application � la base de donn�es embarqu�e et d�finie quel
	 * sch�ma � utiliser pour cette connection
	 * 
	 * @throws SQLException
	 *             S'il est impossible d'obtenir une connection � la base de
	 *             donn�es (souvent d� � une utilisation parall�le)
	 */
	protected void connect() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(CONNECTION_URL);
			connection.setSchema(usedSchema);
		}
	}

	/**
	 * Deconnecte l'application de la base de donn�es embarqu�e
	 * 
	 * @throws SQLException
	 */
	protected void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

}
