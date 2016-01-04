package gui.profile.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import gui.LudoTechApplication;
import gui.utils.DateFormatter;
import gui.utils.SpringUtilities;
import gui.utils.TextView;


@SuppressWarnings("serial")
public class ProfileView extends JPanel {

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
	private JButton validateButton;
	private JButton cancelButton;

	
public ProfileView() {
	this.setLayout(new BorderLayout());

	this.makeGUI();
}


private void makeGUI() {

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
	this.phoneNumber = new JTextField();
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
	this.postalCode = new JTextField();
	this.postalCode.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
	postalCodeLabel.setLabelFor(this.postalCode);
	memberPanel.add(this.postalCode);
	
	JLabel cityLabel = new JLabel(TextView.get("city"));
	memberPanel.add(cityLabel);
	this.city = new JTextField();
	this.city.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 5, 20));
	cityLabel.setLabelFor(this.city);
	memberPanel.add(this.city);

	SpringUtilities.makeCompactGrid(memberPanel, 11, 2, 6, 6, 20, 5);
	
	this.add(memberPanel, BorderLayout.CENTER);

	JPanel actionsPanel = new JPanel();
	this.validateButton = new JButton(TextView.get("validate"));
	actionsPanel.add(this.validateButton);
	this.cancelButton = new JButton(TextView.get("cancel"));
	actionsPanel.add(this.cancelButton);
	this.add(actionsPanel, BorderLayout.SOUTH);

}


public void load( String firstName, String lastName, String pseudo, String password, boolean isAdmin, Date birthDate, String phoneNumber,
		String email, String streetAddress, String postalCode, String city) {
	this.firstName.setText(firstName);
	this.lastName.setText(lastName);
	this.pseudo.setText(pseudo);
	this.password.setText(password);
	this.isAdmin.setSelected(isAdmin);
	
	SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
	SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
	SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	int year = Integer.parseInt(sdfYear.format(birthDate));
	int month = Integer.parseInt(sdfMonth.format(birthDate));
	int day = Integer.parseInt(sdfDay.format(birthDate));
	this.datePicker.getModel().setDate(year, month, day);
	
	this.phoneNumber.setText(phoneNumber);
	this.email.setText(email);
	this.streetAddress.setText(streetAddress);
	this.postalCode.setText(postalCode);
	this.city.setText(city);

}

public String getFisrtName() {
	return this.firstName.getText();
}

public String getLastName() {
	return this.lastName.getText();

}

public String getPseudo() {
	return this.pseudo.getText();

}

public boolean getIsAdmin() {
	return this.isAdmin.isSelected();

}

public Date getBirthDate() {
	DateModel<?> model = this.datePicker.getModel();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String dateInString = ""+model.getDay()+"/"+model.getMonth()+"/"+model.getYear();
	try {
		return sdf.parse(dateInString);
	} catch (ParseException e) {
		e.printStackTrace();
		return null;
	}
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
	


