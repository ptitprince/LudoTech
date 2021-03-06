package frontend.catalog.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import frontend.LudoTechApplication;
import frontend.utils.exceptions.NotValidNumberFieldException;
import frontend.utils.gui.PostLoadableComboBoxModel;
import frontend.utils.gui.SpringUtilities;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class GameSearchView extends JPanel {

	private JTextField nameField;
	private JComboBox<String> categoryComboBox;
	private JComboBox<String> editorComboBox;
	private JTextField publishingYearField;
	private JTextField nbPlayersField;
	private JTextField minAgeField;
	private JCheckBox availableCheckBox;

	private JButton searchButton;

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
		this.publishingYearField = new JFormattedTextField(NumberFormat.getNumberInstance());
		this.publishingYearField.setMaximumSize(new Dimension(35, 20));
		publishingYearRangeLabel.setLabelFor(this.publishingYearField);
		searchPropertiesPanel.add(this.publishingYearField);

		// Nombre de joueurs
		JLabel nbPlayersRangeLabel = new JLabel(TextView.get("gamePlayers"));
		searchPropertiesPanel.add(nbPlayersRangeLabel);
		this.nbPlayersField = new JFormattedTextField(NumberFormat.getNumberInstance());
		this.nbPlayersField.setMaximumSize(new Dimension(35, 20));
		nbPlayersRangeLabel.setLabelFor(this.nbPlayersField);
		searchPropertiesPanel.add(this.nbPlayersField);

		// Age minimum recommandé
		JLabel minAgeLabel = new JLabel(TextView.get("gameMinAge"));
		searchPropertiesPanel.add(minAgeLabel);
		this.minAgeField = new JFormattedTextField(NumberFormat.getNumberInstance());
		this.minAgeField.setMaximumSize(new Dimension(35, 20));
		minAgeLabel.setLabelFor(this.minAgeField);
		searchPropertiesPanel.add(this.minAgeField);

		// Disponibilité
		JLabel availableLabel = new JLabel(TextView.get("gameAvailable"));
		searchPropertiesPanel.add(availableLabel);
		this.availableCheckBox = new JCheckBox();
		availableLabel.setLabelFor(this.availableCheckBox);
		searchPropertiesPanel.add(this.availableCheckBox);

		SpringUtilities.makeCompactGrid(searchPropertiesPanel, 7, 2, 6, 6, 6, 6);

		this.add(searchPropertiesPanel, BorderLayout.CENTER);

		this.searchButton = new JButton(TextView.get("validate"));
		this.add(this.searchButton, BorderLayout.SOUTH);
	}

	public void loadCategories(List<String> items) {
		((PostLoadableComboBoxModel) this.categoryComboBox.getModel()).loadData(items);
	}

	public void loadEditors(List<String> items) {
		((PostLoadableComboBoxModel) this.editorComboBox.getModel()).loadData(items);
	}

	public String getNameValue() {
		return this.nameField.getText();
	}

	public String getCategoryValue() {
		String category = (String) this.categoryComboBox.getSelectedItem();
		return (category != null) ? category : "";
	}

	public String getEditorValue() {
		String editor = (String) this.editorComboBox.getSelectedItem();
		return (editor != null) ? editor : "";
	}

	public String getPublishingYearValue() throws NotValidNumberFieldException {
		try {
			if (!this.publishingYearField.getText().equals("")) {
				Integer.parseInt(this.publishingYearField.getText());
			}
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("gamePublishingYear"),
					this.publishingYearField.getText(), TextView.get("integerType"));
		}
		return this.publishingYearField.getText();
	}

	public String getNbPlayersValue() throws NotValidNumberFieldException {
		try {
			if (!this.nbPlayersField.getText().equals("")) {
				Integer.parseInt(this.nbPlayersField.getText());
			}
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("gamePlayers"), this.nbPlayersField.getText(),
					TextView.get("integerType"));
		}
		return this.nbPlayersField.getText();
	}

	public String getMinAgeValue() throws NotValidNumberFieldException {
		try {
			if (!this.minAgeField.getText().equals("")) {
				Integer.parseInt(this.minAgeField.getText());
			}
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("gameMinAge"), this.minAgeField.getText(),
					TextView.get("integerType"));
		}
		return this.minAgeField.getText();
	}

	public boolean getAvailableCheckBoxValue() {
		return this.availableCheckBox.isSelected();
	}

	public JButton getSearchButton() {
		return this.searchButton;
	}
	
}
