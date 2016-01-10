package frontend.parameters.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import frontend.LudoTechApplication;
import frontend.utils.exceptions.NotValidNumberFieldException;
import frontend.utils.gui.SpringUtilities;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class ParametersView extends JPanel {

	private JTextField nbBorrowings;
	private JTextField nbBookings;
	private JTextField durationOfBorrowings;
	private JTextField durationBetweenBookingandBorrowing;
	private JTextField durationOfCotisation;

	private JButton validateButton;
	private JButton cancelButton;

	public ParametersView() {
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	private void makeGUI() {
		JPanel parametersPanel = new JPanel(new SpringLayout());

		// Nombre d'emprunts par adhérent
		JLabel borrowingsLabel = new JLabel(TextView.get("nbBorrowings"));
		parametersPanel.add(borrowingsLabel);
		this.nbBorrowings = new JTextField();
		this.nbBorrowings.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
		borrowingsLabel.setLabelFor(this.nbBorrowings);
		parametersPanel.add(this.nbBorrowings);
		JLabel borrowingsUnitLabel = new JLabel(TextView.get("numberUnit"));
		parametersPanel.add(borrowingsUnitLabel);

		// Nombre de réservations par adhérent
		JLabel bookingsLabel = new JLabel(TextView.get("nbBookings"));
		parametersPanel.add(bookingsLabel);
		this.nbBookings = new JTextField();
		bookingsLabel.setLabelFor(this.nbBookings);
		parametersPanel.add(this.nbBookings);
		JLabel bookingsUnitLabel = new JLabel(TextView.get("numberUnit"));
		parametersPanel.add(bookingsUnitLabel);

		// Durée d'un emprunt
		JLabel durationOfBorrowingsLabel = new JLabel(TextView.get("durationOfBorrowings"));
		parametersPanel.add(durationOfBorrowingsLabel);
		this.durationOfBorrowings = new JTextField();
		durationOfBorrowingsLabel.setLabelFor(this.durationOfBorrowings);
		parametersPanel.add(this.durationOfBorrowings);
		JLabel durationOfBorrowingsUnitLabel = new JLabel(TextView.get("weekUnit"));
		parametersPanel.add(durationOfBorrowingsUnitLabel);

		// Durée entre la date de réservation et le début de l'emprunt
		JLabel durationBetweenBookingandBorrowingLabel = new JLabel(TextView.get("durationBetweenBookingandBorrowing"));
		parametersPanel.add(durationBetweenBookingandBorrowingLabel);
		this.durationBetweenBookingandBorrowing = new JTextField();
		durationBetweenBookingandBorrowingLabel.setLabelFor(this.durationBetweenBookingandBorrowing);
		parametersPanel.add(this.durationBetweenBookingandBorrowing);
		JLabel durationBetweenBookingandBorrowingUnitLabel = new JLabel(TextView.get("weekUnit"));
		parametersPanel.add(durationBetweenBookingandBorrowingUnitLabel);

		// Durée d'une cotisation pour un adhérent
		JLabel durationOfCotisationLabel = new JLabel(TextView.get("durationOfCotisation"));
		parametersPanel.add(durationOfCotisationLabel);
		this.durationOfCotisation = new JTextField();
		durationOfCotisationLabel.setLabelFor(this.durationOfCotisation);
		parametersPanel.add(this.durationOfCotisation);
		JLabel durationOfCotisationUnitLabel = new JLabel(TextView.get("yearUnit"));
		parametersPanel.add(durationOfCotisationUnitLabel);

		SpringUtilities.makeCompactGrid(parametersPanel, 5, 3, 6, 6, 20, 5);
		this.add(parametersPanel, BorderLayout.CENTER);

		JPanel actionsPanel = new JPanel();
		this.validateButton = new JButton(TextView.get("validate"));
		actionsPanel.add(this.validateButton);
		this.cancelButton = new JButton(TextView.get("cancel"));
		actionsPanel.add(this.cancelButton);
		this.add(actionsPanel, BorderLayout.SOUTH);
	}

	public void load(Properties parameters) {
		this.nbBorrowings.setText(parameters.getProperty("nbBorrowings"));
		this.nbBookings.setText(parameters.getProperty("nbBookings"));
		this.durationOfBorrowings.setText(parameters.getProperty("durationOfBorrowingsInWeeks"));
		this.durationBetweenBookingandBorrowing.setText(parameters.getProperty("durationBetweenBookingandBorrowingInWeeks"));
		this.durationOfCotisation.setText(parameters.getProperty("durationOfCotisationInYears"));
	}

	public int getNbBorrowings() throws NotValidNumberFieldException {
		int testBorrowings = -1;
		try {
			testBorrowings = Integer.parseInt(this.nbBorrowings.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("nbBorrowings"), this.nbBorrowings.getText(),
					TextView.get("integerType"));
		}
		return testBorrowings;
	}

	public int getNbBookings() throws NotValidNumberFieldException {
		int testBookings = -1;
		try {
			testBookings = Integer.parseInt(this.nbBookings.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("nbBookings"), this.nbBookings.getText(),
					TextView.get("integerType"));
		}
		return testBookings;
	}

	public int getDurationOfBorrowings() throws NotValidNumberFieldException {
		int testdurationOfBorrowings = -1;
		try {
			testdurationOfBorrowings = Integer.parseInt(this.durationOfBorrowings.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("durationOfBorrowings"),
					this.durationOfBorrowings.getText(), TextView.get("integerType"));
		}
		return testdurationOfBorrowings;
	}

	public int getDurationBetweenBookingandBorrowing() throws NotValidNumberFieldException {
		int durationBetweenBookingandBorrowing = -1;
		try {
			durationBetweenBookingandBorrowing = Integer.parseInt(this.durationBetweenBookingandBorrowing.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("durationBetweenBookingandBorrowing"),
					this.durationBetweenBookingandBorrowing.getText(), TextView.get("integerType"));
		}
		return durationBetweenBookingandBorrowing;
	}

	public int getDurationOfCotisation() throws NotValidNumberFieldException {
		int durationOfCotisation = -1;
		try {
			durationOfCotisation = Integer.parseInt(this.durationOfCotisation.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("durationOfCotisation"),
					this.durationOfCotisation.getText(), TextView.get("integerType"));
		}
		return durationOfCotisation;
	}

	public JButton getValidateButton() {
		return this.validateButton;
	}

	public JButton getCancelButton() {
		return this.cancelButton;
	}

}
