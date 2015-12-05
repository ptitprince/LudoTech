package model.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.pojos.PostalAddress;

public class PostalAddressDAO extends DAO {
	
	public boolean add(PostalAddress postalAddress)	{
		
		try	{
			
				super.connect();
				PreparedStatement psInsert = connection.prepareStatement(
					"INSERT INTO " 
					+ "Postal_Address(street_address, postal_code, city, country) "
						+ "VALUES (?, ?, ?, ?)",
						new String[] { "ID" });
				psInsert.setString(1, postalAddress.getStreetAddress());
				psInsert.setString(2, postalAddress.getPostalCode());
				psInsert.setString(3, postalAddress.getCity());
				psInsert.setString(4, postalAddress.getCountry());

				psInsert.executeUpdate();
			
				/**
				 * Ici, un identifiant est généré automatiquement par Derby
				 */
				ResultSet idRS = psInsert.getGeneratedKeys();
				if (idRS != null && idRS.next()) {
					postalAddress.setId(idRS.getInt(1));
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
		


	public boolean edit(PostalAddress postalAddress)	{
		
		try	{
			
			super.connect();
			PreparedStatement psInsert = connection.prepareStatement(
				"update Postal_Address " 
				+ "SET street_address = ?, postal_code = ?, city = ?, country = ?) "
				+"WHERE id = ?",
					new String[] { "ID" });
			psInsert.setString(1, postalAddress.getStreetAddress());
			psInsert.setString(2, postalAddress.getPostalCode());
			psInsert.setString(3, postalAddress.getCity());
			psInsert.setString(4, postalAddress.getCountry());
			psInsert.setInt(5, postalAddress.getId());

			psInsert.executeUpdate();
		
		
		super.disconnect();
		return true;
	} catch (SQLException e) {
		e.printStackTrace();
		return false;
	}
	
	}

}