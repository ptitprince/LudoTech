package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.POJOs.Extension;
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
	public boolean add(Member member, int memberContextID, int memberCredentialsID) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "MEMBER(first_name, last_name, pseudo,password, is_admin, birth_date, phone_number, email, street_address, postal_code, city, member_context_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "ID" });
			psInsert.setString(1, member.getFirstName());
			psInsert.setString(2, member.getLastName());
			psInsert.setString(3, member.getPseudo());
			psInsert.setString(3, member.getPassword());
			psInsert.setBoolean(3, member.getIsAdmin());
			psInsert.setDate(4, new java.sql.Date(member.getBirthDate().getTime()));
			psInsert.setInt(5, member.getPhoneNumber());
			psInsert.setString(6, member.getEmail());
			psInsert.setString(7, member.getStreetAddress());
			psInsert.setString(8, member.getPostalCode());
			psInsert.setString(9, member.getCity());
			psInsert.setInt(10, memberContextID);

			psInsert.executeUpdate();

			// Récupération de l'identifiant du contexte d'un adhérent généré
			// automatiquement par Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				member.setId(idRS.getInt(1));

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

	public boolean edit(Member member, int memberContextID) {
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement("UPDATE MEMBER "
					+ "SET first_name = ?, last_name = ?, pseudo = ?, password = ?, is_admin = ?, birth_date = ?, phone_number = ?, email = ?, street_address = ?, postal_Code = ?, city = ?, member_contextID = ? "
					+ "WHERE id = ?");
			psEdit.setString(1, member.getFirstName());
			psEdit.setString(2, member.getLastName());
			psEdit.setString(3, member.getPseudo());
			psEdit.setString(4, member.getPassword());
			psEdit.setBoolean(5, member.getIsAdmin());
			psEdit.setDate(6, new java.sql.Date(member.getBirthDate().getTime()));
			psEdit.setInt(7, member.getPhoneNumber());
			psEdit.setString(8, member.getEmail());
			psEdit.setString(9, member.getStreetAddress());
			psEdit.setString(10, member.getPostalCode());
			psEdit.setString(11, member.getCity());
			psEdit.setInt(12, memberContextID);

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
				Date date = resultSet.getDate("birth_date");
				member = new Member(id, resultSet.getString("first_name"), resultSet.getString("last_name"),
						resultSet.getString("pseudo"), resultSet.getString("password"), resultSet.getBoolean("is_admin"),
						date, resultSet.getInt("phone_number"), resultSet.getString("email_address"),
						resultSet.getString("street_address"), resultSet.getString("postal_code"),
						resultSet.getString("city"));

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

			PreparedStatement psSelect = connection.prepareStatement("SELECT member_context_id FROM MEMBER WHERE id = ?");
			psSelect.setInt(1, memberID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				memberContextID = resultSet.getInt("member_context_id");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberContextID;
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

	/**
	 * Vérifie s'il existe un adhérent possédant ce pseudo et ce mot de passe
	 * dans la base de données
	 * 
	 * @param pseudo
	 *            Le pseudo de l'adhérent à trouver
	 * @param password
	 *            Le mot de passe de l'adhérent à trouver
	 * @return Un Member si l'adhérent a été trouvé, sinon null
	 */
	public Member exist(String pseudo, String password) {
		try {
			super.connect();

			PreparedStatement psSelect = connection
					.prepareStatement("SELECT * FROM MEMBER WHERE pseudo = ? AND password = ?");
			psSelect.setString(1, pseudo);
			psSelect.setString(2, password);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Member member = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				Date date = resultSet.getDate("birth_date");
				member = new Member(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("pseudo"),
						resultSet.getString("password"), resultSet.getBoolean("is_admin"), date,
						resultSet.getInt("phone_number"), resultSet.getString("email_address"),
						resultSet.getString("street_address"), resultSet.getString("postal_code"),
						resultSet.getString("city"));
			}

			super.disconnect();
			return member;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtient la colonne définissant si un adhérent est ou non un
	 * administrateur
	 * 
	 * @param memberID
	 *            L'identifiant d'un adhérent existant
	 * @return La valeur de la colonne définissant si l'adhérent est administrateur
	 */
	public boolean isAdmin(int memberID) {
		boolean isAdmin = false;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT is_admin FROM MEMBER WHERE id = ?");
			psSelect.setInt(1, memberID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				isAdmin = resultSet.getBoolean("is_admin");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdmin;
	}

}