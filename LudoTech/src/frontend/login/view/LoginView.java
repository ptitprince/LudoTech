package frontend.login.view;

import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class LoginView extends JPanel {

	private JTextField pseudoField;
	private JPasswordField passwordField;
	
	private JButton validateButton;
	
	public LoginView() {
		this.setLayout(new BoxLayout(this, 1));
		this.makeGUI();
	}

	private void makeGUI() {
		JLabel connectionLabel = new JLabel(TextView.get("loginConnectYou"));
		connectionLabel.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(connectionLabel);
		
		this.add(Box.createVerticalStrut(30));
		
		JLabel pseudoLabel = new JLabel(TextView.get("loginPseudoLabel"));
		this.add(pseudoLabel);
		this.pseudoField = new JTextField();
		pseudoLabel.setLabelFor(this.pseudoField);
		this.add(this.pseudoField);
		
		this.add(Box.createVerticalStrut(10));
		
		JLabel passwordLabel = new JLabel(TextView.get("loginPasswordLabel"));
		this.add(passwordLabel);
		this.passwordField = new JPasswordField();
		passwordLabel.setLabelFor(this.passwordField);
		this.add(this.passwordField);
		
		this.add(Box.createVerticalStrut(30));
		
		this.validateButton = new JButton(TextView.get("loginValidate")); 
		this.add(this.validateButton);
	}
	
	public JTextField getPseudoField() {
		return this.pseudoField;
	}
	
	public JTextField getPasswordField() {
		return this.passwordField;
	}
	
	public JButton getValidateButton() {
		return this.validateButton;
	}
	
}
