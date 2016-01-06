package gui.borrow.view;

import gui.LudoTechApplication;
import gui.utils.PostLoadableComboBoxModel;
import gui.utils.PostLoadableExtensionComboBoxModel;
import gui.utils.PostLoadableGameComboBoxModel;
import gui.utils.PostLoadableMemberComboBoxModel;
import gui.utils.SpringUtilities;
import gui.utils.TextView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Member;

public class BorrowView extends JDialog{
	
	private static final double WINDOW_RATIO = 1.10;

	private JComboBox<Game> borrowGameNameComboBox;
	private JComboBox<Member> borrowMemberNameComboBox;
	private JTextField borrowBeginningDateField;
	private JTextField borrowEndingDateField;
	private JComboBox<Extension> borrowExtensionNameComboBox;
	
	private JButton validButton;
	private JButton cancelButton;
	
	public BorrowView() {
		this.setSize((int) (LudoTechApplication.WINDOW_WIDTH / WINDOW_RATIO),
				(int) (LudoTechApplication.WINDOW_HEIGHT / WINDOW_RATIO));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}
	
	private void makeGUI() {
		JLabel title = new JLabel(TextView.get("borrowAddTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel searchPropertiesPanel = new JPanel(new SpringLayout());
		
		//Nom du jeu
		JLabel borrowGameNameLabel = new JLabel(TextView.get("borrowGameName"));
		searchPropertiesPanel.add(borrowGameNameLabel);
		this.borrowGameNameComboBox = new JComboBox<Game>();
		this.borrowGameNameComboBox.setModel(new PostLoadableGameComboBoxModel());
		this.borrowGameNameComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		borrowGameNameLabel.setLabelFor(this.borrowGameNameComboBox);
		searchPropertiesPanel.add(this.borrowGameNameComboBox);
		
		//Nom du membre
		JLabel borrowMemberNameLabel = new JLabel(TextView.get("borrowMemberName"));
		searchPropertiesPanel.add(borrowMemberNameLabel);
		this.borrowMemberNameComboBox = new JComboBox<Member>();
		this.borrowMemberNameComboBox.setModel(new PostLoadableMemberComboBoxModel());
		this.borrowMemberNameComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		borrowMemberNameLabel.setLabelFor(this.borrowMemberNameComboBox);
		searchPropertiesPanel.add(this.borrowMemberNameComboBox);
		
		//Date de début
		JLabel beginningDateLabel = new JLabel(TextView.get("borrowBeginningDate"));
		searchPropertiesPanel.add(beginningDateLabel);
		this.borrowBeginningDateField = new JTextField();
		this.borrowBeginningDateField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		beginningDateLabel.setLabelFor(this.borrowBeginningDateField);
		searchPropertiesPanel.add(this.borrowBeginningDateField);
		
		//Date de fin
		JLabel endingDateLabel = new JLabel(TextView.get("borrowEndingDate"));
		searchPropertiesPanel.add(endingDateLabel);
		this.borrowEndingDateField = new JTextField();
		this.borrowEndingDateField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		endingDateLabel.setLabelFor(this.borrowEndingDateField);
		searchPropertiesPanel.add(this.borrowEndingDateField);
		
		//Nom de l'extension
		JLabel borrowExtensionNameLabel = new JLabel(TextView.get("borrowGameName"));
		searchPropertiesPanel.add(borrowExtensionNameLabel);
		this.borrowExtensionNameComboBox = new JComboBox<Extension>();
		this.borrowExtensionNameComboBox .setModel(new PostLoadableExtensionComboBoxModel());
		this.borrowExtensionNameComboBox .setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		borrowExtensionNameLabel.setLabelFor(this.borrowExtensionNameComboBox);
		searchPropertiesPanel.add(this.borrowExtensionNameComboBox);
		
		SpringUtilities.makeCompactGrid(searchPropertiesPanel, 5, 2, 6, 6, 6, 6);
		
		this.add(searchPropertiesPanel, BorderLayout.CENTER);
		
		this.validButton = new JButton(TextView.get("validate"));
		this.cancelButton = new JButton(TextView.get("cancel"));
		this.add(this.validButton, BorderLayout.SOUTH);
		this.add(this.validButton, BorderLayout.SOUTH);
		
	}
	
	public void loadGames(List<Game> games) {
		((PostLoadableGameComboBoxModel) this.borrowGameNameComboBox.getModel()).loadData(games);
	}
	
	public JComboBox<Extension> getExtensionComboBox() {
		return this.borrowExtensionNameComboBox;
	}
}
