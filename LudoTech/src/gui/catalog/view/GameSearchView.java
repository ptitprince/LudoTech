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
import gui.utils.exceptions.NotValidNumberFieldException;

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
		this.publishingYearField = new JTextField();
		this.publishingYearField.setMaximumSize(new Dimension(35, 20));
		publishingYearRangeLabel.setLabelFor(this.publishingYearField);
		searchPropertiesPanel.add(this.publishingYearField);

		// Nombre de joueurs
		JLabel nbPlayersRangeLabel = new JLabel(TextView.get("gamePlayers"));
		searchPropertiesPanel.add(nbPlayersRangeLabel);
		this.nbPlayersField = new JTextField();
		this.nbPlayersField.setMaximumSize(new Dimension(35, 20));
		nbPlayersRangeLabel.setLabelFor(this.nbPlayersField);
		searchPropertiesPanel.add(this.nbPlayersField);

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

	public JCheckBox getAvailableCheckBox() {
		return availableCheckBox;
	}

	public JButton getSearchButton() {
		return this.searchButton;
	}
	
	public void resetFields() {
		this.nameField.setText("");
		this.categoryComboBox.setSelectedIndex(-1);
		this.editorComboBox.setSelectedIndex(-1);
		this.publishingYearField.setText("");
		this.nbPlayersField.setText("");
		this.minAgeField.setText("");
		this.availableCheckBox.setSelected(false);
	}
}
