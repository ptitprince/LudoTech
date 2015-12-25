package gui.catalog.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import gui.LudoTechApplication;
import gui.utils.PostLoadableComboBoxModel;
import gui.utils.SpringUtilities;
import gui.utils.TextView;

@SuppressWarnings("serial")
public class GameSearchView extends JPanel {

	private JTextField nameField;
	private JComboBox<String> categoryComboBox;
	private JComboBox<String> editorComboBox;
	private JTextField publishingYearStartRangeField;
	private JTextField publishingYearEndRangeField;
	private JTextField nbPlayersStartRangeField;
	private JTextField nbPlayersEndRangeField;
	private JTextField minAgeField;
	private JCheckBox availableCheckBox;
	
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
		this.nameField = new JTextField();
		this.nameField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		nameLabel.setLabelFor(this.nameField);
		searchPropertiesPanel.add(this.nameField);
		
		// Catégorie
		JLabel categoryLabel = new JLabel(TextView.get("gameCategory"));
		searchPropertiesPanel.add(categoryLabel);
		this.categoryComboBox = new JComboBox<String>();
		this.categoryComboBox.setModel(new PostLoadableComboBoxModel());
		this.categoryComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		categoryLabel.setLabelFor(this.categoryComboBox);
		searchPropertiesPanel.add(this.categoryComboBox);
		
		// Editeur
		JLabel editorLabel = new JLabel(TextView.get("gameEditor"));
		searchPropertiesPanel.add(editorLabel);
		this.editorComboBox = new JComboBox<String>();
		this.editorComboBox.setModel(new PostLoadableComboBoxModel());
		this.editorComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		editorLabel.setLabelFor(this.editorComboBox);
		searchPropertiesPanel.add(this.editorComboBox);
		
		// Année d'édition
		JLabel publishingYearRangeLabel = new JLabel(TextView.get("gamePublishingYear"));
		searchPropertiesPanel.add(publishingYearRangeLabel);
		JPanel publishingYearRangePanel = new JPanel(new FlowLayout());
		publishingYearRangePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		this.publishingYearStartRangeField = new JTextField();
		this.publishingYearStartRangeField.setPreferredSize(new Dimension(35, 20));
		publishingYearRangePanel.add(this.publishingYearStartRangeField);
		JLabel publishingYearToRangeLabel = new JLabel(TextView.get("rangeTo"));
		publishingYearRangePanel.add(publishingYearToRangeLabel);
		this.publishingYearEndRangeField = new JTextField();
		this.publishingYearEndRangeField.setPreferredSize(new Dimension(35, 20));
		publishingYearRangePanel.add(this.publishingYearEndRangeField);
		publishingYearRangeLabel.setLabelFor(publishingYearRangePanel);
		searchPropertiesPanel.add(publishingYearRangePanel);
		
		// Nombre de joueurs
		JLabel nbPlayersRangeLabel = new JLabel(TextView.get("gamePlayers"));
		searchPropertiesPanel.add(nbPlayersRangeLabel);
		JPanel nbPlayersRangePanel = new JPanel(new FlowLayout());
		nbPlayersRangePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		this.nbPlayersStartRangeField = new JTextField();
		this.nbPlayersStartRangeField.setPreferredSize(new Dimension(35, 20));
		nbPlayersRangePanel.add(this.nbPlayersStartRangeField);
		JLabel nbPlayersToRangeLabel = new JLabel(TextView.get("rangeTo"));
		nbPlayersRangePanel.add(nbPlayersToRangeLabel);
		this.nbPlayersEndRangeField = new JTextField();
		this.nbPlayersEndRangeField.setPreferredSize(new Dimension(35, 20));
		nbPlayersRangePanel.add(this.nbPlayersEndRangeField);
		nbPlayersRangeLabel.setLabelFor(nbPlayersRangePanel);
		searchPropertiesPanel.add(nbPlayersRangePanel);
		
		// Age minimum recommandé
		JLabel minAgeLabel = new JLabel(TextView.get("gameMinAge"));
		searchPropertiesPanel.add(minAgeLabel);
		JPanel minAgePanel = new JPanel(new FlowLayout());
		minAgePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		this.minAgeField = new JTextField();
		this.minAgeField.setPreferredSize(new Dimension(35, 20));
		minAgePanel.add(this.minAgeField);
		JLabel minAgePostLabel = new JLabel(TextView.get("years"));
		minAgePanel.add(minAgePostLabel);
		minAgeLabel.setLabelFor(minAgePanel);
		searchPropertiesPanel.add(minAgePanel);
		
		// Disponibilité
		JLabel availableLabel = new JLabel(TextView.get("gameAvailable"));
		searchPropertiesPanel.add(availableLabel);
		this.availableCheckBox = new JCheckBox();
		availableLabel.setLabelFor(this.availableCheckBox);
		searchPropertiesPanel.add(this.availableCheckBox);

		SpringUtilities.makeCompactGrid(searchPropertiesPanel, 7, 2, 6, 6, 6, 6);

		this.add(searchPropertiesPanel, BorderLayout.CENTER);

		JButton searchButton = new JButton(TextView.get("validate"));
		this.add(searchButton, BorderLayout.SOUTH);
	}
	
	public void loadCategories(List<String> items) {
		((PostLoadableComboBoxModel) this.categoryComboBox.getModel()).loadData(items);
	}

	public void loadEditors(List<String> items) {
		((PostLoadableComboBoxModel) this.editorComboBox.getModel()).loadData(items);
	}
}
