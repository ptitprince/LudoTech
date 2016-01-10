package frontend.login.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import backend.services.MemberServices;
import frontend.login.view.LoginObserver;
import frontend.login.view.LoginView;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class LoginController extends JPanel {

	private MemberServices memberServices;

	private LoginView loginView;

	private List<LoginObserver> observers;

	public LoginController() {
		this.memberServices = new MemberServices();
		this.observers = new ArrayList<LoginObserver>();
		this.makeGUI();
		this.makeListeners();
	}

	private void makeGUI() {
		this.loginView = new LoginView();
		this.add(this.loginView);
	}

	private void makeListeners() {

		// Clic sur le bouton "Se connecter" de l'onglet de connexion
		this.loginView.getValidateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendLoginRequest();
			}
		});
		
		// Appuie sur la touche "entrer" du clavier sur le champ de mot de passe
		this.loginView.getPasswordField().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendLoginRequest();
			}
		});

	}
	
	private void sendLoginRequest() {
		String pseudo = loginView.getPseudoField().getText();
		String password = loginView.getPasswordField().getText();
		int[] memberData = memberServices.checkAccess(pseudo, password);
		if (memberData != null) {
			for (LoginObserver observer : observers) {
				observer.notifySuccessfulLogin(memberData[0], (memberData[1] == 1) ? true : false);
			}
		} else {
			displayLoginError();
		}
	}

	public void addObserver(LoginObserver observer) {
		this.observers.add(observer);
	}

	private void displayLoginError() {
		JOptionPane.showMessageDialog(null, TextView.get("loginException"));
	}

}
