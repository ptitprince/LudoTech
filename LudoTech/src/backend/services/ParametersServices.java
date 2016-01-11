package backend.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Expose des services de traitements sur les paramètres de l'application
 */
public class ParametersServices {

	/**
	 * Chemin relatif vers le fichier stockant les paramètres
	 */
	private String filePath;

	/**
	 * Construit un nouveau service pour les paramètres utilisant le fichier de
	 * preferences par défaut
	 */
	public ParametersServices() {
		this.filePath = "preferences.properties";
	}

	/**
	 * Construit un nouveau service pour les paramètres utilisant le fichier
	 * déterminer par le chemin passé en paramètre
	 * 
	 * @param otherFilePath
	 *            Le chemin du fichier a utilisé de la forme "name.properties"
	 */
	public ParametersServices(String otherFilePath) {
		this.filePath = otherFilePath;
	}

	/**
	 * Récupère tous les paramètres de l'application
	 * 
	 * @return Un objet Properties encapsulant tous les paramètres
	 */
	public Properties getAllParameters() {
		Properties properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream("res/" + filePath);
			properties.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * Sauvegarde les paramètres de l'application
	 * 
	 * @param nbBorrowings
	 *            Nombre de prêts maximum
	 * @param nbBookings
	 *            Nombre de réservations maxium
	 * @param durationOfBorrowings
	 *            Durée des prêts en semaines
	 * @param durationBetweenBookingandBorrowing
	 *            Durée minimum entre la réservation et la date de début de prêt
	 *            en semaines
	 * @param durationOfCotisation
	 *            Durée d'une cotisation en années
	 */
	public void saveAllParameters(int nbBorrowings, int nbBookings, int durationOfBorrowings,
			int durationBetweenBookingandBorrowing, int durationOfCotisation) {
		Properties properties = new Properties();
		properties.setProperty("nbBorrowings", nbBorrowings + "");
		properties.setProperty("nbBookings", nbBookings + "");
		properties.setProperty("durationOfBorrowingsInWeeks", durationOfBorrowings + "");
		properties.setProperty("durationBetweenBookingandBorrowingInWeeks", durationBetweenBookingandBorrowing + "");
		properties.setProperty("durationOfCotisationInYears", durationOfCotisation + "");

		try {
			FileOutputStream fos = new FileOutputStream("res/" + filePath);
			properties.store(fos, "");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return Le nombre de prêts maximum
	 */
	public int getNumberOfBorrowings() {
		return Integer.parseInt(this.getAllParameters().getProperty("nbBorrowings", 0 + ""));
	}

	/**
	 * @return Le nombre de réservations maximum
	 */
	public int getNumberOfBookings() {
		return Integer.parseInt(this.getAllParameters().getProperty("nbBookings", 0 + ""));
	}

	/**
	 * @return La durée des prêts en semaines
	 */
	public int getDurationOfBorrowingsInWeeks() {
		return Integer.parseInt(this.getAllParameters().getProperty("durationOfBorrowingsInWeeks", 0 + ""));
	}

	/**
	 * @return La durée minimum entre la réservation et la date de début de prêt
	 *         en semaines
	 */
	public int getDurationBetweenBookingAndBorrowingInWeeks() {
		return Integer
				.parseInt(this.getAllParameters().getProperty("durationBetweenBookingandBorrowingInWeeks", 0 + ""));
	}

}
