package frontend.profile.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.MaskFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import frontend.LudoTechApplication;
import frontend.utils.exceptions.NotValidNumberFieldException;
import frontend.utils.gui.DateFormatter;
import frontend.utils.gui.SpringUtilities;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class ProfileView extends JPanel {

	private int memberID;
	private int memberContextID;

	private JTextField firstName;
	private JTextField lastName;
	private JTextField pseudo;
	private JPasswordField password;
	private JCheckBox isAdmin;
	private JDatePickerImpl datePicker;
	private JTextField phoneNumber;
	private JTextField email;
	private JTextField streetAddress;
	private JTextField postalCode;
	private JTextField city;

	private JTextField nbFakeBookings;
	private JTextField nbDelays;
	private JCheckBox canBook;
	private JCheckBox canBorrow;
	private JDatePickerImpl lastSubscriptionDatePicker;

	private JButton validateButton;
	private JButton cancelButton;

	public ProfileView(boolean currentMemberIsAdmin) {
		this.memberID = -1;
		this.memberContextID = -1;
		this.setLayout(new BorderLayout());
		this.makeGUI(currentMemberIsAdmin);
	}

	private void makeGUI(boolean currentMemberIsAdmin) {

		JPanel memberPanel = new JPanel(new SpringLayout());

		JLabel firstNameLabel = new JLabel(TextView.get("firstName"));
		memberPanel.add(firstNameLabel);
		this.firstName = new JTextField();
		this.firstName.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		firstNameLabel.setLabelFor(this.firstName);
		memberPanel.add(this.firstName);

		JLabel lastNameLabel = new JLabel(TextView.get("lastName"));
		memberPanel.add(lastNameLabel);
		this.lastName = new JTextField();
		this.lastName.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		lastNameLabel.setLabelFor(this.lastName);
		memberPanel.add(this.lastName);

		JLabel pseudoLabel = new JLabel(TextView.get("pseudo"));
		memberPanel.add(pseudoLabel);
		this.pseudo = new JTextField();
		this.pseudo.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		pseudoLabel.setLabelFor(this.pseudo);
		memberPanel.add(this.pseudo);

		JLabel passwordLabel = new JLabel(TextView.get("password"));
		memberPanel.add(passwordLabel);
		this.password = new JPasswordField();
		this.password.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		passwordLabel.setLabelFor(this.password);
		memberPanel.add(this.password);

		JLabel isAdminLabel = new JLabel(TextView.get("isAdmin"));
		memberPanel.add(isAdminLabel);
		this.isAdmin = new JCheckBox();
		this.isAdmin.setEnabled(currentMemberIsAdmin);
		isAdminLabel.setLabelFor(this.isAdmin);
		memberPanel.add(this.isAdmin);

		JLabel birthDateLabel = new JLabel(TextView.get("birthDate"));
		memberPanel.add(birthDateLabel);
		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.datePicker = new JDatePickerImpl(datePanel, new DateFormatter());
		birthDateLabel.setLabelFor(this.datePicker);
		memberPanel.add(datePicker);

		JLabel phoneNumberLabel = new JLabel(TextView.get("phoneNumber"));
		memberPanel.add(phoneNumberLabel);
		MaskFormatter phoneNumberFormatter = null;
		try { phoneNumberFormatter = new MaskFormatter("##########"); } catch (ParseException e) {};
		this.phoneNumber = new JFormattedTextField(phoneNumberFormatter);
		Action phoneNumberBeep = phoneNumber.getActionMap().get(DefaultEditorKit.deletePrevCharAction);
		phoneNumberBeep.setEnabled(false);
		this.phoneNumber.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		phoneNumberLabel.setLabelFor(this.phoneNumber);
		memberPanel.add(this.phoneNumber);

		JLabel emailLabel = new JLabel(TextView.get("email"));
		memberPanel.add(emailLabel);
		this.email = new JTextField();
		this.email.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		emailLabel.setLabelFor(this.email);
		memberPanel.add(this.email);

		JLabel streetAddressLabel = new JLabel(TextView.get("streetAddress"));
		memberPanel.add(streetAddressLabel);
		this.streetAddress = new JTextField();
		this.streetAddress.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		streetAddressLabel.setLabelFor(this.streetAddress);
		memberPanel.add(this.streetAddress);

		JLabel postalCodeLabel = new JLabel(TextView.get("postalCode"));
		memberPanel.add(postalCodeLabel);
		MaskFormatter postalCodeFormatter = null;
		try { postalCodeFormatter = new MaskFormatter("#####"); } catch (ParseException e) {};
		this.postalCode = new JFormattedTextField(postalCodeFormatter);
		Action postalCodeBeep = postalCode.getActionMap().get(DefaultEditorKit.deletePrevCharAction);
		postalCodeBeep.setEnabled(false);
		this.postalCode.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		postalCodeLabel.setLabelFor(this.postalCode);
		memberPanel.add(this.postalCode);

		JLabel cityLabel = new JLabel(TextView.get("city"));
		memberPanel.add(cityLabel);
		this.city = new JTextField();
		this.city.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		cityLabel.setLabelFor(this.city);
		memberPanel.add(this.city);

		JLabel nbFakeBookingsLabel = new JLabel(TextView.get("nbFakeBookings"));
		memberPanel.add(nbFakeBookingsLabel);
		this.nbFakeBookings = new JTextField();
		this.nbFakeBookings.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		nbFakeBookingsLabel.setLabelFor(this.nbFakeBookings);
		this.nbFakeBookings.setEnabled(currentMemberIsAdmin);
		memberPanel.add(this.nbFakeBookings);

		JLabel nbDelaysLabel = new JLabel(TextView.get("nbDelays"));
		memberPanel.add(nbDelaysLabel);
		this.nbDelays = new JTextField();
		this.nbDelays.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
		nbDelaysLabel.setLabelFor(this.nbDelays);
		this.nbDelays.setEnabled(currentMemberIsAdmin);
		memberPanel.add(this.nbDelays);

		JLabel canBookLabel = new JLabel(TextView.get("canBook"));
		memberPanel.add(canBookLabel);
		this.canBook = new JCheckBox();
		canBookLabel.setLabelFor(this.canBook);
		this.canBook.setEnabled(currentMemberIsAdmin);
		memberPanel.add(this.canBook);

		JLabel canBorrowLabel = new JLabel(TextView.get("canBorrow"));
		memberPanel.add(canBorrowLabel);
		this.canBorrow = new JCheckBox();
		canBorrowLabel.setLabelFor(this.canBorrow);
		this.canBorrow.setEnabled(currentMemberIsAdmin);
		memberPanel.add(this.canBorrow);

		JLabel lastSubscriptionDateLabel = new JLabel(TextView.get("lastSubscriptionDate"));
		memberPanel.add(lastSubscriptionDateLabel);
		UtilDateModel subscriptionModel = new UtilDateModel();
		subscriptionModel.setSelected(true);
		Properties pSubs = new Properties();
		pSubs.put("text.today", "Today");
		pSubs.put("text.month", "Month");
		pSubs.put("text.year", "Year");
		JDatePanelImpl dateSubsPanel = new JDatePanelImpl(subscriptionModel, pSubs);
		this.lastSubscriptionDatePicker = new JDatePickerImpl(dateSubsPanel, new DateFormatter());
		lastSubscriptionDateLabel.setLabelFor(this.lastSubscriptionDatePicker);
		// Déterminer l'activation du champ date et de son sélecteur
		this.lastSubscriptionDatePicker.setEnabled(currentMemberIsAdmin);
		this.lastSubscriptionDatePicker.getComponent(1).setEnabled(currentMemberIsAdmin);
		memberPanel.add(lastSubscriptionDatePicker);

		SpringUtilities.makeCompactGrid(memberPanel, 16, 2, 6, 6, 20, 5);

		this.add(memberPanel, BorderLayout.CENTER);

		JPanel actionsPanel = new JPanel();
		this.validateButton = new JButton(TextView.get("validate"));
		actionsPanel.add(this.validateButton);
		this.cancelButton = new JButton(TextView.get("cancel"));
		actionsPanel.add(this.cancelButton);
		this.add(actionsPanel, BorderLayout.SOUTH);

	}

	public void load(int memberID, int memberContextID, String firstName, String lastName, String pseudo, String password, boolean isAdmin,
			Date birthDate, String phoneNumber, String email, String streetAddress, String postalCode, String city,
			int nbFakeBookings, int nbDelays, boolean canBorrow, boolean canBook, Date lastSubscriptionDate) {
		this.memberID = memberID;
		this.memberContextID = memberContextID;

		this.firstName.setText(firstName);
		this.lastName.setText(lastName);
		this.pseudo.setText(pseudo);
		this.password.setText(password);
		this.isAdmin.setSelected(isAdmin);
		Calendar birthDateCalendar = Calendar.getInstance();
		birthDateCalendar.setTime(birthDate);
		this.datePicker.getModel().setDate(birthDateCalendar.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH);
		this.phoneNumber.setText(phoneNumber);
		this.email.setText(email);
		this.streetAddress.setText(streetAddress);
		this.postalCode.setText(postalCode);
		this.city.setText(city);

		this.nbFakeBookings.setText(Integer.toString(nbFakeBookings));
		this.nbDelays.setText(Integer.toString(nbDelays));
		this.canBook.setSelected(canBook);
		this.canBorrow.setSelected(canBorrow);
		Calendar lastSubscriptionDateCalendar = Calendar.getInstance();
		lastSubscriptionDateCalendar.setTime(lastSubscriptionDate);
		this.lastSubscriptionDatePicker.getModel().setDate(lastSubscriptionDateCalendar.get(Calendar.YEAR),
				Calendar.MONTH, Calendar.DAY_OF_MONTH);
	}
	
	public int getMemberID() {
		return this.memberID;
	}
	
	public int getMemberContextID() {
		return this.memberContextID;
	}
	
	public boolean isCreatingMember() {
		return (this.memberID == -1);
	}

	public String getFirstName() {
		return this.firstName.getText();
	}

	public String getLastName() {
		return this.lastName.getText();

	}

	public int getNbDelays() throws NotValidNumberFieldException {
		int nbDelays = -1;
		try {
			nbDelays = Integer.parseInt(this.nbDelays.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("nbDelays"), this.nbDelays.getText(),
					TextView.get("integerType"));
		}
		return nbDelays;
	}

	public int getNbFakeBookings() throws NotValidNumberFieldException {
		int nbFakeBookings = -1;
		try {
			nbFakeBookings = Integer.parseInt(this.nbFakeBookings.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("nbFakeBookings"), this.nbFakeBookings.getText(),
					TextView.get("integerType"));
		}
		return nbFakeBookings;
	}

	public String getPseudo() {
		return this.pseudo.getText();

	}

	public boolean getIsAdmin() {
		return this.isAdmin.isSelected();

	}

	public boolean getCanBook() {
		return this.canBook.isSelected();

	}

	public boolean getCanBorrow() {
		return this.canBorrow.isSelected();

	}

	public Date getBirthDate() {
		return (Date) this.datePicker.getModel().getValue();

	}

	public Date getLastSubscriptionDate() {
		return (Date) this.lastSubscriptionDatePicker.getModel().getValue();
	}

	public String getPhoneNumber() {
		return this.phoneNumber.getText();

	}

	public String getEmail() {
		return this.email.getText();

	}

	public String getStreetAddress() {
		return this.streetAddress.getText();

	}

	public String getPostalCode() {
		return this.postalCode.getText();

	}

	public String getCity() {
		return this.city.getText();

	}

	public JButton getValidateButton() {
		return this.validateButton;
	}

	public JButton getCancelButton() {
		return this.cancelButton;
	}

	public String getPassword() {
		return this.password.getText();
	}

	

}
