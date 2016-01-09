package model.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ParametersServices {
	
	private final String PATH = "preferences.properties";

	public Properties getAllParameters() {
		Properties properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream("res/" + PATH);
			properties.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public void saveAllParameters(int nbBorrowings, int nbBookings,
			int durationOfBorrowings, int durationBetweenBookingandBorrowing,
			int durationOfCotisation) {
		Properties properties=  new Properties();
		properties.setProperty("nbBorrowings", nbBorrowings+"");
		properties.setProperty("nbBookings", nbBookings+"");
		properties.setProperty("durationOfBorrowingsInWeeks", durationOfBorrowings+"");
		properties.setProperty("durationBetweenBookingandBorrowingInWeeks", durationBetweenBookingandBorrowing+"");
		properties.setProperty("durationOfCotisationInYears", durationOfCotisation+"");

		try {
			FileOutputStream fos = new FileOutputStream("res/" + PATH);
			properties.store(fos, "");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int getDurationOfBorrowingsInWeeks() {
		return Integer.parseInt(this.getAllParameters().getProperty("durationOfBorrowingsInWeeks", 0+""));
	}
	public int getNumberOfBookings() {
		return Integer.parseInt(this.getAllParameters().getProperty("nbBookings", 0+""));
	}
	
	public int getNumberOfBorrowings() {
		return Integer.parseInt(this.getAllParameters().getProperty("nbBorrowings", 0+""));
	}

	public int getDurationBetweenBookingandBorrowingInWeeks() {
		return Integer.parseInt(this.getAllParameters().getProperty("durationBetweenBookingandBorrowingInWeeks", 0+""));
	}

}
