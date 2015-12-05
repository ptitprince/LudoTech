package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.pojos.MemberContext;

/**
 * Classe manipulant des objets de type MemberContext dans la base de données
 */
public class MemberContextDAO extends DAO {

	/**
	 * Ajoute un nouveau contexte d'adhérent en base de données en lui assignant
	 * automatiquement un nouvel identifiant
	 * 
	 * @param memberContext
	 *            Le contexte de membre à ajouter
	 * @return True si l'ajout s'est bien passé, sinon False
	 */
	public boolean add(MemberContext memberContext) {
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "MEMBER_CONTEXT(nb_delays, nb_fake_bookings, last_subscription_date, can_borrow, can_book) "
					+ "VALUES (?, ?, ?, ?, ?)", new String[] { "ID" });
			psInsert.setInt(1, memberContext.getNbDelays());
			psInsert.setInt(2, memberContext.getNbFakeBookings());
			// Conversion d'une date java.util vers une date java.sql
			psInsert.setDate(3, new java.sql.Date(memberContext.getLastSubscriptionDate().getTime())); 
			psInsert.setBoolean(4, memberContext.canBorrow());
			psInsert.setBoolean(5, memberContext.canBook());

			psInsert.executeUpdate();

			// Récupération de l'identifiant du contexte d'un adhérent généré
			// automatiquement par Derby
			ResultSet idRS = psInsert.getGeneratedKeys();
			if (idRS != null && idRS.next()) {
				memberContext.setId(idRS.getInt(1));
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
	 * Modifie un contexte de membre dans la base de données
	 * 
	 * @param memberContext
	 *            Le contexte de membre à modifier, possèdant un identifiant
	 * @return True si la modification s'est bien passé, sinon False
	 */
	public boolean edit(MemberContext memberContext) {
		try {
			super.connect();

			PreparedStatement psEdit = connection.prepareStatement("UPDATE MEMBER_CONTEXT "
					+ "SET nb_delays = ?, nb_fake_bookings = ?, last_subscription_date = ?, can_borrow = ?, can_book = ?"
					+ "WHERE id = ?");
			psEdit.setInt(1, memberContext.getNbDelays());
			psEdit.setInt(2, memberContext.getNbFakeBookings());
			// Conversion d'une date java.util vers une date java.sql
			psEdit.setDate(3, new java.sql.Date(memberContext.getLastSubscriptionDate().getTime()));
			psEdit.setBoolean(4, memberContext.canBorrow());
			psEdit.setBoolean(5, memberContext.canBook());
			psEdit.setInt(6, memberContext.getId());

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
	 * Supprime le contexte d'un adhérent en base de données
	 * @param id L'identifiant du contexte à supprimer
	 * @return True si le contexte a bien été supprimé ou s'il n'existe pas en base de données, sinon False
	 */
	public boolean remove(int id) {
		try {
			super.connect();

			PreparedStatement psRemove = connection
					.prepareStatement("DELETE FROM " + "MEMBER_CONTEXT WHERE " + "id = ?");
			psRemove.setInt(1, id);
			psRemove.execute();
			psRemove.closeOnCompletion();

			super.disconnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Trouve le contexte d'un adhérent en base de données
	 * @param id L'identifiant du contexte à trouver
	 * @return Le contexte du membre identifié par "id" ou null si aucun ne correspond en base de données
	 */
	public MemberContext get(int id) {
		try {
			super.connect();

			PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM MEMBER_CONTEXT WHERE id = ?");
			psSelect.setInt(1, id);
			psSelect.execute();
			psSelect.closeOnCompletion();

			ResultSet resultSet = psSelect.getResultSet();
			MemberContext memberContext = null;
			if (resultSet.next()) { // Positionnement sur le premier résultat
				memberContext = new MemberContext(id, resultSet.getInt("nb_delays"), resultSet.getInt("nb_fake_bookings"), resultSet.getDate("last_subscription_date"),
						resultSet.getBoolean("can_borrow"), resultSet.getBoolean("can_book"));
			}
			super.disconnect();
			return memberContext;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
