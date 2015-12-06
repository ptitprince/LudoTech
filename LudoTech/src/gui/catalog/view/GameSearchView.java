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

@SuppressWarnings("serial")
public class GameSearchView extends JPanel {

	private static final String TITLE = "Rechercher un jeu";
	private static final String NAME_LABEL = "Nom";
	private static final String CATEGORY_LABEL = "Type de jeu";
	private static final String EDITOR_LABEL = "Editeur";
	private static final String PUBLISHING_YEAR_RANGE_LABEL = "Année d'édition";
	private static final String PLAYERS_RANGE_LABEL = "Joueurs";
	private static final String TO_RANGE = "à";
	private static final String MINIMUM_AGE_LABEL = "Age minimum";
	private static final String MINIMUM_AGE_POST_LABEL = "ans";
	private static final String AVAILABLE_LABEL = "Disponible";
	private static final String SEARCH_BUTTON_LABEL = "Valider";

	public GameSearchView() {
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	private void makeGUI() {
		JLabel title = new JLabel(TITLE);
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel searchPropertiesPanel = new JPanel(new SpringLayout());

		JLabel nameLabel = new JLabel(NAME_LABEL);
		searchPropertiesPanel.add(nameLabel);
		JTextField nameField = new JTextField();
		nameField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		nameLabel.setLabelFor(nameField);
		searchPropertiesPanel.add(nameField);
		
		JLabel categoryLabel = new JLabel(CATEGORY_LABEL);
		searchPropertiesPanel.add(categoryLabel);
		JComboBox categoryComboBox = new JComboBox();
		categoryComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		categoryLabel.setLabelFor(categoryComboBox);
		searchPropertiesPanel.add(categoryComboBox);
		
		JLabel editorLabel = new JLabel(EDITOR_LABEL);
		searchPropertiesPanel.add(editorLabel);
		JComboBox editorComboBox = new JComboBox();
		editorComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		editorLabel.setLabelFor(editorComboBox);
		searchPropertiesPanel.add(editorComboBox);
		
		JLabel publishingYearRangeLabel = new JLabel(PUBLISHING_YEAR_RANGE_LABEL);
		searchPropertiesPanel.add(publishingYearRangeLabel);
		JPanel publishingYearRangePanel = new JPanel(new FlowLayout());
		publishingYearRangePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		JTextField publishingYearStartRangeField = new JTextField();
		publishingYearStartRangeField.setPreferredSize(new Dimension(35, 20));
		publishingYearRangePanel.add(publishingYearStartRangeField);
		JLabel publishingYearToRangeLabel = new JLabel(TO_RANGE);
		publishingYearRangePanel.add(publishingYearToRangeLabel);
		JTextField publishingYearEndRangeField = new JTextField();
		publishingYearEndRangeField.setPreferredSize(new Dimension(35, 20));
		publishingYearRangePanel.add(publishingYearEndRangeField);
		publishingYearRangeLabel.setLabelFor(publishingYearRangePanel);
		searchPropertiesPanel.add(publishingYearRangePanel);
		
		JLabel nbPlayersRangeLabel = new JLabel(PLAYERS_RANGE_LABEL);
		searchPropertiesPanel.add(nbPlayersRangeLabel);
		JPanel nbPlayersRangePanel = new JPanel(new FlowLayout());
		nbPlayersRangePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		JTextField nbPlayersStartRangeField = new JTextField();
		nbPlayersStartRangeField.setPreferredSize(new Dimension(35, 20));
		nbPlayersRangePanel.add(nbPlayersStartRangeField);
		JLabel nbPlayersToRangeLabel = new JLabel(TO_RANGE);
		nbPlayersRangePanel.add(nbPlayersToRangeLabel);
		JTextField nbPlayersEndRangeField = new JTextField();
		nbPlayersEndRangeField.setPreferredSize(new Dimension(35, 20));
		nbPlayersRangePanel.add(nbPlayersEndRangeField);
		nbPlayersRangeLabel.setLabelFor(nbPlayersRangePanel);
		searchPropertiesPanel.add(nbPlayersRangePanel);
		
		JLabel minAgeLabel = new JLabel(MINIMUM_AGE_LABEL);
		searchPropertiesPanel.add(minAgeLabel);
		JPanel minAgePanel = new JPanel(new FlowLayout());
		minAgePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		JTextField minAgeField = new JTextField();
		minAgeField.setPreferredSize(new Dimension(35, 20));
		minAgePanel.add(minAgeField);
		JLabel minAgePostLabel = new JLabel(MINIMUM_AGE_POST_LABEL);
		minAgePanel.add(minAgePostLabel);
		minAgeLabel.setLabelFor(minAgePanel);
		searchPropertiesPanel.add(minAgePanel);
		
		JLabel availableLabel = new JLabel(AVAILABLE_LABEL);
		searchPropertiesPanel.add(availableLabel);
		JCheckBox availableCheckBox = new JCheckBox();
		availableLabel.setLabelFor(availableCheckBox);
		searchPropertiesPanel.add(availableCheckBox);

		SpringUtilities.makeCompactGrid(searchPropertiesPanel, 7, 2, 6, 6, 6, 6);

		this.add(searchPropertiesPanel, BorderLayout.CENTER);

		JButton searchButton = new JButton(SEARCH_BUTTON_LABEL);
		this.add(searchButton, BorderLayout.SOUTH);
	}
}
