package backend.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import backend.POJOs.Member;
import backend.POJOs.MemberContext;

/**
 * Classe manipulant des objets de type Member (Adhérent) dans la base de
 * données
 */
public class MemberDAO extends DAO {

	/**
	 * Trouve un adhérent dans la base de données
	 * 
	 * @param id
	 *            L'identifiant d'un adhérent existant en base de données
	 * @return Un adhérent si trouvé, sinon null
	 */

	public Member get(int id) {
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT MEMBER.*, MEMBER_CONTEXT.*, "
					+ "MEMBER.id AS M_id, " + "MEMBER_CONTEXT.id AS MC_id "
					+ " FROM MEMBER JOIN MEMBER_CONTEXT ON MEMBER.context_id = MEMBER_CONTEXT.id WHERE MEMBER.id = ? ");
			psSelect.setInt(1, id);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			Member member = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				MemberContext memberContext = new MemberContext(resultSet.getInt("MC_id"),
						resultSet.getInt("nb_delays"), resultSet.getInt("nb_fake_bookings"),
						resultSet.getDate("last_subscription_date"), resultSet.getBoolean("can_borrow"),
						resultSet.getBoolean("can_book"));
				member = new Member(id, resultSet.getString("first_name"), resultSet.getString("last_name"),
						resultSet.getString("pseudo"), resultSet.getString("password"),
						resultSet.getBoolean("is_admin"), resultSet.getDate("birth_date"),
						resultSet.getString("phone_number"), resultSet.getString("email_address"),
						resultSet.getString("street_address"), resultSet.getString("postal_code"),
						resultSet.getString("city"), memberContext);

			}
			super.disconnect();
			return member;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Liste tous les adhérents de la base de données correspondants au filtre
	 * 
	 * @param filter
	 *            Ensemble de couples <clef(String), valeur(String)> où la clef
	 *            est incluse dans la liste : first_name, last_name, pseudo.
	 * @return Une liste d'adhérents potentiellement vide respectant le filtre
	 */
	public List<Member> getAll(HashMap<String, String> filter) {
		List<Member> member = new ArrayList<Member>();
		try {
			super.connect();

			String request = "SELECT MEMBER.*, MEMBER_CONTEXT.*, " + "MEMBER.id AS M_id, "
					+ "MEMBER_CONTEXT.id AS MC_id " + "FROM MEMBER "
					+ "JOIN MEMBER_CONTEXT ON MEMBER.context_id = MEMBER_CONTEXT.id ";

			String whereClause = "";
			boolean atLeastOneCondition = false;
			for (Entry<String, String> property : filter.entrySet()) {
				if (property.getKey().equals("first_name") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + "LOWER(MEMBER." + property.getKey() + ")"
							+ " LIKE LOWER('%" + property.getValue() + "%')";
					atLeastOneCondition = true;
				} else if (property.getKey().equals("last_name") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + "LOWER(MEMBER." + property.getKey() + ")"
							+ " LIKE LOWER('%" + property.getValue() + "%')";
					atLeastOneCondition = true;
				} else if (property.getKey().equals("pseudo") && !property.getValue().equals("")) {
					whereClause += ((atLeastOneCondition) ? " AND " : " ") + "LOWER(MEMBER." + property.getKey() + ")"
							+ " LIKE LOWER('%" + property.getValue() + "%')";
					atLeastOneCondition = true;
				}
			}

			if (atLeastOneCondition) {
				request += " WHERE" + whereClause;
			}

			PreparedStatement psSelect = connection.prepareStatement(request);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			while (resultSet.next()) {
				MemberContext memberContext = new MemberContext(resultSet.getInt("MC_id"),
						resultSet.getInt("nb_delays"), resultSet.getInt("nb_fake_bookings"),
						resultSet.getDate("last_subscription_date"), resultSet.getBoolean("can_borrow"),
						resultSet.getBoolean("can_book"));
				member.add(new Member(resultSet.getInt("M_id"), resultSet.getString("last_name"),
						resultSet.getString("first_name"), resultSet.getString("pseudo"),
						resultSet.getString("password"), resultSet.getBoolean("is_admin"),
						resultSet.getDate("birth_date"), resultSet.getString("phone_number"),
						resultSet.getString("email_address"), resultSet.getString("street_address"),
						resultSet.getString("postal_code"), resultSet.getString("city"), memberContext));
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}

	/**
	 * Trouve l'identifiant du contexte d'un adhérent dans la base de données
	 * 
	 * @param memberID
	 *            L'identifiant d'un adhérent existant en base de données
	 * @return L'identifiant du contexte de l'adhérent dont l'identifiant est
	 *         passé en paramètres ou -1 si l'adhérent ne possède pas de
	 *         contexte
	 */
	public int getMemberContextID(int memberID) {
		int memberContextID = -1;
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT context_id FROM MEMBER WHERE id = ?");
			psSelect.setInt(1, memberID);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			if (resultSet.next()) { // Positionnement sur le premier résultat
				memberContextID = resultSet.getInt("context_id");
			}

			super.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberContextID;
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
						resultSet.getString("phone_number"), resultSet.getString("email_address"),
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
	 * @return La valeur de la colonne définissant si l'adhérent est
	 *         administrateur
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

	/**
	 * Ajoute un nouvel adhérent à la base de données, dont l'identifiant est
	 * initialisé automatiquement
	 * 
	 * @param member
	 *            L'adhérent à ajouter dans la base de données
	 * @param memberContextID
	 *            L'identifiant du contexte de l'adhérent
	 * @return True si l'ajout de l'adhérent a été fait correctement, sinon
	 *         False
	 */
	public boolean add(Member member, int memberContextID) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "MEMBER(first_name, last_name, pseudo, password, is_admin, birth_date, phone_number, email_address, street_address, postal_code, city, context_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "ID" });
			psInsert.setString(1, member.getFirstName());
			psInsert.setString(2, member.getLastName());
			psInsert.setString(3, member.getPseudo());
			psInsert.setString(4, member.getPassword());
			psInsert.setBoolean(5, member.isAdmin());
			psInsert.setDate(6, new java.sql.Date(member.getBirthDate().getTime()));
			psInsert.setString(7, member.getPhoneNumber());
			psInsert.setString(8, member.getEmail());
			psInsert.setString(9, member.getStreetAddress());
			psInsert.setString(10, member.getPostalCode());
			psInsert.setString(11, member.getCity());
			psInsert.setInt(12, memberContextID);

			psInsert.executeUpdate();

			// Récupération de l'identifiant du contexte d'un adhérent généré
			// automatiquement par Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				member.setMemberID(idRS.getInt(1));

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
	 * Modifie un adhérent existant en base de données
	 * 
	 * @param member
	 *            L'adhérent à modifier dans la base de données
	 * @param memberContextID
	 *            L'identifiant du contexte de adhérent existant en base de
	 *            données
	 * @return True si la modification s'est bien passée, False sinon
	 */
	public boolean edit(Member member, int memberContextID) {
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement(
					"UPDATE MEMBER " + "SET first_name = ?, last_name = ?, pseudo = ?, password = ?, is_admin = ?, "
							+ "birth_date = ?, phone_number = ?, email_address = ?, street_address = ?, "
							+ "postal_code = ?, city = ?, context_id = ? " + "WHERE id = ?");
			psEdit.setString(1, member.getFirstName());
			psEdit.setString(2, member.getLastName());
			psEdit.setString(3, member.getPseudo());
			psEdit.setString(4, member.getPassword());
			psEdit.setBoolean(5, member.isAdmin());
			psEdit.setDate(6, new java.sql.Date(member.getBirthDate().getTime()));
			psEdit.setString(7, member.getPhoneNumber());
			psEdit.setString(8, member.getEmail());
			psEdit.setString(9, member.getStreetAddress());
			psEdit.setString(10, member.getPostalCode());
			psEdit.setString(11, member.getCity());
			psEdit.setInt(12, memberContextID);
			psEdit.setInt(13, member.getMemberID());

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
	 * Supprime un adhérent de la base de données
	 * 
	 * @param id
	 *            L'identifiant de l'adhérent existant en base de données à
	 *            supprimer
	 * @return True si l'adhérent a bien été supprimé ou s'il n'existe pas en
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

}