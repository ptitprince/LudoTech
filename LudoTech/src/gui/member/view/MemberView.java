package gui.member.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Properties;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import gui.LudoTechApplication;
import gui.utils.SpringUtilities;
import gui.utils.TextView;


@SuppressWarnings("serial")
public class MemberView extends JPanel {

	private JTextField firstName;
	private JTextField lastName;
	private JTextField pseudo;
	private JTextField password;
	private JTextField isAdmin;
	private JTextField birthDate;
	private JTextField phoneNumber;
	private JTextField email;
	private JTextField streetAddress;
	private JTextField postalCode;
	private JTextField city;
	private JButton validateButton;
	private JButton cancelButton;

	
public MemberView() {
	this.setLayout(new BorderLayout());

	this.makeGUI();
}


private void makeGUI() {

	JPanel memberPanel = new JPanel(new SpringLayout());
	
	
	JLabel firstNameLabel = new JLabel(TextView.get("firstName"));
	memberPanel.add(firstNameLabel);
	this.firstName = new JTextField();
	this.firstName.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	firstNameLabel.setLabelFor(this.firstName);
	memberPanel.add(this.firstName);

	JLabel lastNameLabel = new JLabel(TextView.get("lastName"));
	memberPanel.add(lastNameLabel);
	this.lastName = new JTextField();
	this.lastName.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	lastNameLabel.setLabelFor(this.lastName);
	memberPanel.add(this.lastName);

	JLabel pseudoLabel = new JLabel(TextView.get("pseudo"));
	memberPanel.add(pseudoLabel);
	this.pseudo = new JTextField();
	this.pseudo.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	pseudoLabel.setLabelFor(this.pseudo);
	memberPanel.add(this.pseudo);
	
	JLabel passwordLabel = new JLabel(TextView.get("password"));
	memberPanel.add(passwordLabel);
	this.password = new JTextField();
	this.password.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	passwordLabel.setLabelFor(this.password);
	memberPanel.add(this.password);
		
	JLabel isAdminLabel = new JLabel(TextView.get("isAdmin"));
	memberPanel.add(isAdminLabel);
	this.isAdmin = new JTextField();
	this.isAdmin.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	isAdminLabel.setLabelFor(this.isAdmin);
	memberPanel.add(this.isAdmin);
	
	JLabel birthDateLabel = new JLabel(TextView.get("birthDate"));
	memberPanel.add(birthDate);
	this.birthDate = new JTextField();
	this.birthDate.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	birthDateLabel.setLabelFor(this.birthDate);
	memberPanel.add(this.birthDate);
	
	JLabel phoneNumberLabel = new JLabel(TextView.get("phoneNumber"));
	memberPanel.add(phoneNumberLabel);
	this.phoneNumber = new JTextField();
	this.phoneNumber.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	phoneNumberLabel.setLabelFor(this.phoneNumber);
	memberPanel.add(this.phoneNumber);
	
	JLabel emailLabel = new JLabel(TextView.get("email"));
	memberPanel.add(email);
	this.email = new JTextField();
	this.email.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	emailLabel.setLabelFor(this.email);
	memberPanel.add(this.email);
	
	JLabel streetAddressLabel = new JLabel(TextView.get("streetAddress"));
	memberPanel.add(streetAddress);
	this.streetAddress = new JTextField();
	this.streetAddress.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	streetAddressLabel.setLabelFor(this.streetAddress);
	memberPanel.add(this.streetAddress);
	
	JLabel postalCodeLabel = new JLabel(TextView.get("postalCode"));
	memberPanel.add(postalCode);
	this.postalCode = new JTextField();
	this.postalCode.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	postalCodeLabel.setLabelFor(this.postalCode);
	memberPanel.add(this.postalCode);
	
	JLabel cityLabel = new JLabel(TextView.get("city"));
	memberPanel.add(city);
	this.city = new JTextField();
	this.city.setPreferredSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
	cityLabel.setLabelFor(this.city);
	memberPanel.add(this.city);

	SpringUtilities.makeCompactGrid(memberPanel, 5, 3, 6, 6, 20, 5);
	
	this.add(memberPanel, BorderLayout.CENTER);

	JPanel actionsPanel = new JPanel();
	this.validateButton = new JButton(TextView.get("validate"));
	actionsPanel.add(this.validateButton);
	this.cancelButton = new JButton(TextView.get("cancel"));
	actionsPanel.add(this.cancelButton);
	this.add(actionsPanel, BorderLayout.SOUTH);

}

public void load(Properties member) {
	this.firstName.setText(member.getProperty("firstName"));
	this.lastName.setText(member.getProperty("lastName"));
	this.pseudo.setText(member.getProperty("pseudo"));
	this.password.setText(member.getProperty("password"));
	this.isAdmin.setText(member.getProperty("isAdmin"));
	this.birthDate.setText(member.getProperty("birthDate"));
	this.phoneNumber.setText(member.getProperty("phoneNumber"));
	this.email.setText(member.getProperty("email"));
	this.streetAddress.setText(member.getProperty("streetAddress"));
	this.postalCode.setText(member.getProperty("postalCode"));
	this.city.setText(member.getProperty("city"));

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


public String getPassword() {
	return this.password.getText();

}

public boolean getIsAdmin() {
	return this.isAdmin.getText() != null;

}

public int getBirthDate() {
	return Integer.parseInt(this.birthDate.getText());

}

public int getPhoneNumber() {
	return Integer.parseInt(this.phoneNumber.getText());

}

public int getEmail() {
	return Integer.parseInt(this.email.getText());

}

public int getStreetAddress() {
	return Integer.parseInt(this.streetAddress.getText());

}

public int getPostalCode() {
	return Integer.parseInt(this.postalCode.getText());

}

public int getCity() {
	return Integer.parseInt(this.city.getText());

}

public JButton getValidateButton() {
	return this.validateButton;
}

public JButton getCancelButton() {
	return this.cancelButton;
}




}	
	


