package gui.borrow.view;

import gui.LudoTechApplication;
import gui.utils.PostLoadableComboBoxModel;
import gui.utils.TextView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class BorrowSearchView extends JPanel{
	private JTextField gameNameField;
	private JTextField memberNameField;
	private JComboBox<String> stateBorrowComboBox; // recherche parmi les états (en retard, rendu, etc...)
	//recherche par la date de rendu.
	private JTextField extensionNameField;
	
	private JButton searchButton;
	
	public BorrowSearchView() {
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}
	
	private void makeGUI() {
		JLabel title = new JLabel(TextView.get("borrowListTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);
		
		JPanel searchPropertiesPanel = new JPanel(new SpringLayout());
	
		//Nom du jeu : 
		JLabel gameNameLabel = new JLabel(TextView.get("borrowGameName"));
		searchPropertiesPanel.add(gameNameLabel);
		this.gameNameField = new JTextField();
		this.gameNameField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		gameNameLabel.setLabelFor(this.gameNameField);
		
		//Nom du membre : 
		JLabel memberNameLabel = new JLabel(TextView.get("borrowMemberName"));
		searchPropertiesPanel.add(memberNameLabel);
		this.memberNameField = new JTextField();
		this.memberNameField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		memberNameLabel.setLabelFor(this.memberNameField);
		
		// L'état de l'emprunt :
		JLabel stateLabel = new JLabel(TextView.get("borrowStateComboBox"));
		searchPropertiesPanel.add(stateLabel);
		this.stateBorrowComboBox = new JComboBox<String>();
		this.stateBorrowComboBox.setModel(new PostLoadableComboBoxModel());
		this.stateBorrowComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		stateLabel.setLabelFor(this.stateBorrowComboBox);
		searchPropertiesPanel.add(this.stateBorrowComboBox);
		
		// Date de rendue :
		// Nom de l'extension :
		
		
	}
}
