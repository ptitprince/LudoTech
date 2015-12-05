package model.pojos;
/**
 * Représentation d'une adresse postale (conformément à la table PostalAddress)
 * 
 * @author Yves Longchamp
 *
 */
public class PostalAddress {
	
	/**
	 * Identifiant de l'adresse postale.
	 */
	private int id ;
	
	/**
	 * L'adresse en elle même, le nom de la rue, le bâtiment, le numéro de la rue, etc...
	 */
	private String streetAddress;
	
	/**
	 * Code postal de l'adresse.
	 */
	private String postalCode;
	
	/**
	 * La ville de l'adresse.
	 */
	private String city;
	
	/**
	 * Le pays de l'adresse.
	 */
	private String country;

	/**
	 * @param id : identifiant de l'adresse.
	 * @param streetAddress : adresse postale sans le code postal, la ville et le pays.
	 * @param postalCode : code postale de l'adresse.
	 * @param city : Ville de l'adresse.
	 * @param country : Pays de l'adresse. 
	 */
	public PostalAddress(int id, String streetAddress, String postalCode,
			
			String city, String country) {
		
		super();
		this.id = id;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id est identifiant de PostalAddress
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress est l'adresse sans le code postal, ville, et pays
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode est le code postal de l'adresse
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city est la ville de l'adresse
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country le pays de l'adresse
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	

}
