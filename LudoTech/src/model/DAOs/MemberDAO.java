package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.POJOs.Member;

/**
 * Classe manipulant des objets de type Member dans la base de données
 */
public class MemberDAO extends DAO {

	/**
	 * Ajoute une nouvelle ligne dans la table Member de la base de données,
	 * avec les informations d'un adhérent en utilisant la génération
	 * automatique de l'identifiant (clé primaire) par le pilote Derby
	 * 
	 * @param member
	 *            L'adhérent à ajouter dans la base de données
	 * @param memberContextID
	 *            L'identifiant du statut de l'adhérent, retards, fausses
	 *            réservations
	 * @param member
	 *            CredentialsID L'identifiant des droits de l'adhérent
	 * @param postalAddressID
	 *            L'identifiant de l'adresse de l'adhérent
	 * @return true L'ajout de l'adhérent a été fait correctement
	 * @return false Une exception est survenue, l'ajout s'est peut-être mal
	 *         passé
	 */
	public boolean add(Member member, int memberContextID, int memberCredentialsID, int postalAdressID) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "MEMBER(firstName, lastName, pseudo, birthDate, phoneNumber, email, memberContextID, memberCredentialsID, postalAddressID) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "ID" });
			psInsert.setString(1, member.getFirstName());
			psInsert.setString(2, member.getLastName());
			psInsert.setString(3, member.getPseudo());
			psInsert.setDate(4, new java.sql.Date(member.getBirthDate().getTime()));
			psInsert.setInt(5, member.getPhoneNumber());
			psInsert.setString(6, member.getEmail());
			psInsert.setInt(7, memberContextID);
			psInsert.setInt(8, memberCredentialsID);
			psInsert.setInt(9, postalAdressID);

			psInsert.executeUpdate();

			// Récupération de l'identifiant du contexte d'un adhérent généré
			// automatiquement par Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				/*	TODO : Error must be fixed !
					member.setId(idRS.getInt(1));
				*/
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
	 * Modifie les valeurs d'une ligne de la table Member dans la base de
	 * données en se servant de l'identifiant d'un membre
	 * 
	 * @param member
	 *            Le membre à modifier dans la base de données
	 * @param memberContextID
	 *            L'identifiant du statut du membre, retards, fausses
	 *            réservations et droits
	 * @param member
	 *            CredentialsID L'identifiant des droits d'un membre
	 * 
	 * @param postalAddressID
	 *            L'identifiant en base de données de l'adresse du membre
	 * @return true La modification du membre a été faite correctement
	 * @return false Une exception est survenue, l'ajout s'est peut-être mal
	 *         passé
	 */

	public boolean edit(Member member, int memberContextID, int memberCredentialsID, int postalAddressID) {
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement("UPDATE MEMBER "
					+ "SET firtName = ?, lastName = ?, pseudo = ?, birthDate = ?, phoneNumber = ?, email = ?, memberContextID = ?, memberCredentialsID = ?, postalAddress = ? "
					+ "WHERE id = ?");
			psEdit.setString(1, member.getFirstName());
			psEdit.setString(2, member.getLastName());
			psEdit.setString(3, member.getPseudo());
			psEdit.setDate(4, new java.sql.Date(member.getBirthDate().getTime()));
			psEdit.setInt(5, member.getPhoneNumber());
			psEdit.setString(6, member.getEmail());
			psEdit.setInt(7, memberContextID);
			psEdit.setInt(8, memberCredentialsID);
			psEdit.setInt(9, postalAddressID);

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
	 * Supprime une ligne de la table Member dans la base de données en se
	 * servant de l'identifiant d'un membre
	 * 
	 * @param id
	 *            L'identifiant du membre à supprimer
	 * @return True si le membre a bien été supprimé ou s'il n'existe pas en
	 *         base de données, sinon False
	 */
	public boolean remove(int id) {
		try {
			super.connect();

			PreparedStatement psDelete = connection.prepareStatement("DELETE FROM Member WHERE id = ?");
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
	 * Trouve un membre dans la base de données
	 * 
	 * @param id
	 *            L'identifiant du membre à trouver
	 * @return Le membre identifié par "id" ou null si aucun ne correspond en
	 *         base de données
	 */

	public Member get(int id) {
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM MEMBER WHERE id = ?");
			psSelect.setInt(1, id);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Member member = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				/* TODO : Error must be fixed !
				   member = new Member(id, resultSet.getString("firstName"), resultSet.getString("lastName"),
						resultSet.getString("pseudo"), resultSet.getDate("birthDate"), resultSet.getInt("phoneNumber"),
						resultSet.getString("email"), "", "", "");
				*/
			}
			super.disconnect();
			return member;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Trouve l'identifiant du statut d'un membre (memberContext) dans la base
	 * de données
	 * 
	 * @param id
	 *            L'identifiant du membre à utiliser
	 * @return L'identifiant du statut dont l'identifiant est passé en
	 *         paramètres ou -1 si le membre ne possède pas de statut
	 */
	public int getMemberContextID(int memberID) {
		int memberContextID = -1;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT memberContextID FROM MEMBER WHERE id = ?");
			psSelect.setInt(1, memberID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				memberContextID = resultSet.getInt("memberContextID");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberContextID;
	}

	/**
	 * Trouve l'identifiant des droits d'un membre (memberCredentials) dans la
	 * base de données
	 * 
	 * @param id
	 *            L'identifiant du membre à utiliser
	 * @return L'identifiant du statut dont l'identifiant est passé en
	 *         paramètres ou -1 si le membre ne possède pas de statut
	 */
	public int getMemberCredentialsID(int memberID) {
		int memberCredentialsID = -1;
		try {
			super.connect();

			PreparedStatement psSelect = connection
					.prepareStatement("SELECT memberCredentialsID FROM MEMBER WHERE id = ?");
			psSelect.setInt(1, memberID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				memberCredentialsID = resultSet.getInt("memberCredentialsID");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberCredentialsID;
	}

	/**
	 * Trouve l'identifiant de l'adresse postale d'un membre postalAddress) dans
	 * la base de données
	 * 
	 * @param id
	 *            L'identifiant du membre à utiliser
	 * @return L'identifiant de l'adresse postale dont l'identifiant est passé
	 *         en paramètres ou -1 si le membre ne possède pas de statut
	 */
	public int getPostalAddressID(int memberID) {
		int postalAddressID = -1;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT postalAddressID FROM MEMBER WHERE id = ?");
			psSelect.setInt(1, memberID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				postalAddressID = resultSet.getInt("postalAddressID");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return postalAddressID;
	}

	/**
	 * Liste les identifiants de tous les membres de la base de données
	 * 
	 * @return La liste des identifiants de tous les membres
	 */
	public List<Integer> getIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM MEMBER");
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) { // Positionnement sur le premier résultat
				ids.add(resultSet.getInt("id"));
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

}
