package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.POJOs.Borrow;

public class BorrowDAO extends DAO {
	/**
	 * Ajoute une nouvelle ligne dans la table Borrow, avec 
	 * un identifiant créé par Derby.
	 * 
	 * @param 	  Borrow, l'emprunt à ajouter dans la base de
	 * données.
	 * @return true L'ajout de l'emprunt a été fait correctement. 
	 * @return false Exception, peut-être un problème qui est survenu.
	 */

	/* TODO : Error must be fixed !
	public boolean add (Borrow borrow)	{
		try {
			super.connect();

			PreparedStatement psInsert = connection.prepareStatement("INSERT INTO "
					+ "Borrow(borrowId, item(voir si besoin ajout ID), member, dateDebut, dateFin, borrowEtat, borrowDisponible) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)", new String[] { "ID" });
			psInsert.setInt(1, borrow.getBorrowId());
			psInsert.setString(2, borrow.getItem());
			//TODO : get/set Extension
			psInsert.setString(3, borrow.getMember()); //erreur dûe a l'absence de Member
			psInsert.setString(4, borrow.getDateDebut());
			psInsert.setString(5, borrow.getDateFin()));
			psInsert.setString(6, borrow.getBorrowEtat());;
			//psInsert.setString(7,borrow.get) ici, doit plus être une condition, à voir plus tard. 

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
	*/
}
