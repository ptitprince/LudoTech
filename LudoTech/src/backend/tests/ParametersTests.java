package backend.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.Test;

import backend.services.ParametersServices;

public class ParametersTests {

	private Path originalPropertiesFilePath;
	private Path testPropertiesFilePath;
	private ParametersServices parametersServices;

	@Test
	public void testFileProperties() throws IOException {
		// Before
		this.originalPropertiesFilePath = FileSystems.getDefault().getPath("res", "preferences.properties");
		this.testPropertiesFilePath = FileSystems.getDefault().getPath("res", "test-preferences.properties");
		Files.copy(this.originalPropertiesFilePath, this.testPropertiesFilePath);
		this.parametersServices = new ParametersServices("test-preferences.properties");
		
		// Tests
		this.parametersServices.saveAllParameters(1, 2, 3, 4, 5);
		Properties properties = this.parametersServices.getAllParameters();
		assertEquals(1, Integer.parseInt(properties.getProperty("nbBorrowings", -1 + "")));
		assertEquals(2, Integer.parseInt(properties.getProperty("nbBookings", -1 + "")));
		assertEquals(3, Integer.parseInt(properties.getProperty("durationOfBorrowingsInWeeks", -1 + "")));
		assertEquals(4, Integer.parseInt(properties.getProperty("durationBetweenBookingandBorrowingInWeeks", -1 + "")));
		assertEquals(5, Integer.parseInt(properties.getProperty("durationOfCotisationInYears", -1 + "")));
		assertEquals(1, this.parametersServices.getNumberOfBorrowings());
		assertEquals(2, this.parametersServices.getNumberOfBookings());
		assertEquals(3, this.parametersServices.getDurationOfBorrowingsInWeeks());
		assertEquals(4, this.parametersServices.getDurationBetweenBookingAndBorrowingInWeeks());
		
		// After
		Files.delete(this.testPropertiesFilePath);
	}
	
}
