package gui.catalog.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import gui.LudoTechApplication;
import gui.utils.SpringUtilities;
import gui.utils.TextView;

@SuppressWarnings("serial")
public class GameSearchView extends JPanel {

	public GameSearchView() {
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	private void makeGUI() {
		JLabel title = new JLabel(TextView.get("catalogSearchTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel searchPropertiesPanel = new JPanel(new SpringLayout());

		// Nom
		JLabel nameLabel = new JLabel(TextView.get("gameName"));
		searchPropertiesPanel.add(nameLabel);
		JTextField nameField = new JTextField();
		nameField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		nameLabel.setLabelFor(nameField);
		searchPropertiesPanel.add(nameField);
		
		// Catégorie
		JLabel categoryLabel = new JLabel(TextView.get("gameCategory"));
		searchPropertiesPanel.add(categoryLabel);
		JComboBox categoryComboBox = new JComboBox();
		categoryComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		categoryLabel.setLabelFor(categoryComboBox);
		searchPropertiesPanel.add(categoryComboBox);
		
		// Editeur
		JLabel editorLabel = new JLabel(TextView.get("gameEditor"));
		searchPropertiesPanel.add(editorLabel);
		JComboBox editorComboBox = new JComboBox();
		editorComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		editorLabel.setLabelFor(editorComboBox);
		searchPropertiesPanel.add(editorComboBox);
		
		// Année d'édition
		JLabel publishingYearRangeLabel = new JLabel(TextView.get("gamePublishingYear"));
		searchPropertiesPanel.add(publishingYearRangeLabel);
		JPanel publishingYearRangePanel = new JPanel(new FlowLayout());
		publishingYearRangePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		JTextField publishingYearStartRangeField = new JTextField();
		publishingYearStartRangeField.setPreferredSize(new Dimension(35, 20));
		publishingYearRangePanel.add(publishingYearStartRangeField);
		JLabel publishingYearToRangeLabel = new JLabel(TextView.get("rangeTo"));
		publishingYearRangePanel.add(publishingYearToRangeLabel);
		JTextField publishingYearEndRangeField = new JTextField();
		publishingYearEndRangeField.setPreferredSize(new Dimension(35, 20));
		publishingYearRangePanel.add(publishingYearEndRangeField);
		publishingYearRangeLabel.setLabelFor(publishingYearRangePanel);
		searchPropertiesPanel.add(publishingYearRangePanel);
		
		// Nombre de joueurs
		JLabel nbPlayersRangeLabel = new JLabel(TextView.get("gamePlayers"));
		searchPropertiesPanel.add(nbPlayersRangeLabel);
		JPanel nbPlayersRangePanel = new JPanel(new FlowLayout());
		nbPlayersRangePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		JTextField nbPlayersStartRangeField = new JTextField();
		nbPlayersStartRangeField.setPreferredSize(new Dimension(35, 20));
		nbPlayersRangePanel.add(nbPlayersStartRangeField);
		JLabel nbPlayersToRangeLabel = new JLabel(TextView.get("rangeTo"));
		nbPlayersRangePanel.add(nbPlayersToRangeLabel);
		JTextField nbPlayersEndRangeField = new JTextField();
		nbPlayersEndRangeField.setPreferredSize(new Dimension(35, 20));
		nbPlayersRangePanel.add(nbPlayersEndRangeField);
		nbPlayersRangeLabel.setLabelFor(nbPlayersRangePanel);
		searchPropertiesPanel.add(nbPlayersRangePanel);
		
		// Age minimum recommandé
		JLabel minAgeLabel = new JLabel(TextView.get("gameMinAge"));
		searchPropertiesPanel.add(minAgeLabel);
		JPanel minAgePanel = new JPanel(new FlowLayout());
		minAgePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		JTextField minAgeField = new JTextField();
		minAgeField.setPreferredSize(new Dimension(35, 20));
		minAgePanel.add(minAgeField);
		JLabel minAgePostLabel = new JLabel(TextView.get("years"));
		minAgePanel.add(minAgePostLabel);
		minAgeLabel.setLabelFor(minAgePanel);
		searchPropertiesPanel.add(minAgePanel);
		
		// Disponibilité
		JLabel availableLabel = new JLabel(TextView.get("gameAvailable"));
		searchPropertiesPanel.add(availableLabel);
		JCheckBox availableCheckBox = new JCheckBox();
		availableLabel.setLabelFor(availableCheckBox);
		searchPropertiesPanel.add(availableCheckBox);

		SpringUtilities.makeCompactGrid(searchPropertiesPanel, 7, 2, 6, 6, 6, 6);

		this.add(searchPropertiesPanel, BorderLayout.CENTER);

		JButton searchButton = new JButton(TextView.get("validate"));
		this.add(searchButton, BorderLayout.SOUTH);
	}
}
