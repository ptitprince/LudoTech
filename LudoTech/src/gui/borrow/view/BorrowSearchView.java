package gui.borrow.view;

import gui.LudoTechApplication;
import gui.utils.PostLoadableComboBoxModel;
import gui.utils.SpringUtilities;
import gui.utils.TextView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class BorrowSearchView extends JPanel {
	private JTextField gameNameField;
	private JTextField memberNameField;
	/*
	 * private JComboBox<String> borrowStateComboBox; // recherche parmi les
	 * états // (en retard, rendu, // etc...)
	 */
	private JTextField endingDateField; // recherche par la date de rendu.
	private JTextField extensionNameField;

	private JButton searchButton;

	public BorrowSearchView() {
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	private void makeGUI() {
		JLabel title = new JLabel(TextView.get("borrowSearchTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel searchPropertiesPanel = new JPanel(new SpringLayout());

		// Nom du jeu :
		JLabel gameNameLabel = new JLabel(TextView.get("borrowGameName"));
		searchPropertiesPanel.add(gameNameLabel);
		this.gameNameField = new JTextField();
		this.gameNameField.setMaximumSize(new Dimension(
				LudoTechApplication.WINDOW_WIDTH, 20));
		gameNameLabel.setLabelFor(this.gameNameField);
		searchPropertiesPanel.add(this.gameNameField);

		// Nom du membre :
		JLabel memberNameLabel = new JLabel(TextView.get("borrowMemberName"));
		searchPropertiesPanel.add(memberNameLabel);
		this.memberNameField = new JTextField();
		this.memberNameField.setMaximumSize(new Dimension(
				LudoTechApplication.WINDOW_WIDTH, 20));
		memberNameLabel.setLabelFor(this.memberNameField);
		searchPropertiesPanel.add(this.memberNameField);

		/*
		 * // L'état de l'emprunt : 
		 * JLabel stateLabel = newJLabel(TextView.get("borrowStateComboBox"));
		 * searchPropertiesPanel.add(stateLabel); 
		 * this.borrowStateComboBox = newJComboBox<String>(); 
		 * this.borrowStateComboBox.setModel(newPostLoadableComboBoxModel());
		 * this.borrowStateComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		 * stateLabel.setLabelFor(this.borrowStateComboBox);
		 * searchPropertiesPanel.add(this.borrowStateComboBox);
		 */

		// Date de rendue :
		JLabel dateLabel = new JLabel(TextView.get("borrowEndingDate"));
		searchPropertiesPanel.add(dateLabel);
		this.endingDateField = new JTextField();
		this.endingDateField.setMaximumSize(new Dimension(
				LudoTechApplication.WINDOW_WIDTH, 20));
		dateLabel.setLabelFor(this.endingDateField);
		searchPropertiesPanel.add(this.endingDateField);

		// Nom de l'extension :
		JLabel extensionNameLabel = new JLabel(
				TextView.get("borrowExtensionName"));
		searchPropertiesPanel.add(extensionNameLabel);
		this.extensionNameField = new JTextField();
		this.extensionNameField.setMaximumSize(new Dimension(
				LudoTechApplication.WINDOW_WIDTH, 20));
		extensionNameLabel.setLabelFor(this.extensionNameField);
		searchPropertiesPanel.add(this.extensionNameField);

		SpringUtilities
				.makeCompactGrid(searchPropertiesPanel, 4, 2, 6, 6, 6, 6);

		this.add(searchPropertiesPanel, BorderLayout.CENTER);

		this.searchButton = new JButton(TextView.get("validate"));
		this.add(this.searchButton, BorderLayout.SOUTH);
	}

	public String getGamesNameValue() {
		return this.gameNameField.getText();
	}

	public String getMembersNameValue() {
		return this.memberNameField.getText();
	}

	public void loadStates() {
		// Renvoie la liste des états : En cours, rendu, en retard... etc
		// services borrow à utiliser pour renvoyer la liste des états.
	}

	public String getEndingDateValue() {
		return this.endingDateField.getText();
	}

	public JButton getSearchButton() {
		return this.searchButton;
	}

	public void resetFields() {
		this.gameNameField.setText("");
		this.memberNameField.setText("");
		//this.borrowStateComboBox.setSelectedIndex(-1);
		this.endingDateField.setText("");
		this.extensionNameField.setText("");

	}
}
