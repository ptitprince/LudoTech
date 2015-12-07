package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.POJOs.MemberCredentials;

public class MemberCredentialsDAO extends DAO {
	
	
	/**
	 * Ajoute une nouvelle ligne dans la table memberCredentials de la base de données, avec
	 * les informations d'un droit en utilisant la génération automatique de
	 * l'identifiant (clé primaire) par le pilote Derby
	 * 
	 * @param memberCredentials
	 *            Le memberCredentials à ajouter dans la base de données
	 * @param memberCredentialsID
	 *            L'identifiant en base de données de la memberCredentials
	 * @return true L'ajout des droits a été fait correctement
	 * @return false Une exception est survenue, l'ajout s'est peut-être mal
	 *         passé
	 */
	
	public boolean add(MemberCredentials memberCredentials, int memberCredentialsID) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "MEMBERCREDENTIALS(saveBorrowing, saveBooking, manageGames, manageMembers, editContext, editParameters, editCredentials) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)", new String[] { "ID" }); 
			// Auto-incrémentation sur la clé primaire ID
			psInsert.setBoolean(1, memberCredentials.isSaveBorrowing());
			psInsert.setBoolean(2, memberCredentials.isSaveBooking());
			psInsert.setBoolean(3, memberCredentials.isManageGames());
			psInsert.setBoolean(4, memberCredentials.isManageMembers());
			psInsert.setBoolean(5, memberCredentials.isEditContext());
			psInsert.setBoolean(6, memberCredentials.isEditParameters());
			psInsert.setBoolean(7, memberCredentials.isEditCredentials());
			
			psInsert.executeUpdate();

			// Récupération de l'identifiant du jeu généré automatiquement par
			// Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				memberCredentials.setMemberCredentialsID(idRS.getInt(1));
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
	 * Modifie les valeurs d'une ligne de la table memberCredentials dans la base de données
	 * en se servant de l'identifiant d'un droit
	 * 
	 * @param memberCredentials
	 *            Le memberCredentials à modifier dans la base de données
	 * @param memberCredentialsID
	 *            L'identifiant en base de données de memberCredentials
	 * @return true La modification des droits a été faite correctement
	 * @return false Une exception est survenue, la modification s'est peut-être
	 *         mal passée
	 */
	public boolean edit(MemberCredentials memberCredentials, int memberCredentialsID) {
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement("UPDATE MEMBERCREDENTIALS "
					+ "SET SaveBorrowing = ?, SaveBooking = ?, ManageGames = ?, ManageMembers = ?, EditContext = ?, EditParameters = ?, EditCredentials = ?"
					+ "WHERE id = ?");
			
			psEdit.setBoolean(1, memberCredentials.isSaveBorrowing());
			psEdit.setBoolean(2, memberCredentials.isSaveBooking());
			psEdit.setBoolean(3, memberCredentials.isManageGames());
			psEdit.setBoolean(4, memberCredentials.isManageMembers());
			psEdit.setBoolean(5, memberCredentials.isEditContext());
			psEdit.setBoolean(6, memberCredentials.isEditParameters());
			psEdit.setBoolean(7, memberCredentials.isEditCredentials());
			psEdit.setInt(8, memberCredentials.getId());

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
	 * Supprime une ligne de la table memberCredentials dans la base de données en se servant
	 * de l'identifiant d'un droit
	 * 
	 * @param id
	 *            L'identifiant du jeu à supprimer
	 * @return True si les droits ont bien été supprimés ou s'il n'existe pas en base
	 *         de données, sinon False
	 */
	public boolean remove(int id) {
		try {
			super.connect();

			PreparedStatement psDelete = connection.prepareStatement("DELETE FROM MemberCredentials WHERE id = ?");
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
	
	
}
