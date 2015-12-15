package gui.catalog.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import gui.LudoTechApplication;
import gui.utils.PostLoadableComboBoxModel;
import gui.utils.SpringUtilities;
import gui.utils.TextView;

@SuppressWarnings("serial")
public class GameView extends JDialog {

	private static final double WINDOW_RATIO = 1.25;

	private JTextField nameField;
	private JTextField idField;
	private JComboBox<String> categoryComboBox;
	private JComboBox<String> editorComboBox;
	private JTextField publishingYearStartRangeField;
	private JTextField nbPlayersStartRangeField;
	private JTextField nbPlayersEndRangeField;
	private JTextField minAgeField;
	private JTextArea descriptionBox;
	
	private JButton validateButton;
	private JButton cancelButton;

	public GameView() {
		this.setTitle(TextView.get("catalogGamePopupTitle"));
		this.setSize((int) (LudoTechApplication.WINDOW_WIDTH / WINDOW_RATIO),
				(int) (LudoTechApplication.WINDOW_HEIGHT / WINDOW_RATIO));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.makeGUI();
	}

	private void makeGUI() {
		JPanel boxesPanel = new JPanel();
		GridBagLayout boxesLayout = new GridBagLayout();
		boxesPanel.setLayout(boxesLayout);

		GridBagConstraints boxesConstraints = new GridBagConstraints();
		boxesConstraints.fill = GridBagConstraints.BOTH;
		boxesConstraints.insets = new Insets(5, 5, 5, 5);
		boxesConstraints.weightx = 2;
		boxesConstraints.weighty = 3;

		makeInfosPanel(boxesPanel, boxesConstraints);
		makeDescriptionPanel(boxesPanel, boxesConstraints);
		makeExtensionsPanel(boxesPanel, boxesConstraints);
		makeItemsPanel(boxesPanel, boxesConstraints);

		this.add(boxesPanel, BorderLayout.CENTER);
		
		JPanel actionsPanel = new JPanel();
		this.validateButton = new JButton(TextView.get("validate"));
		actionsPanel.add(this.validateButton);
		this.cancelButton = new JButton(TextView.get("cancel"));
		actionsPanel.add(this.cancelButton);
		this.add(actionsPanel, BorderLayout.SOUTH);
	}

	public void makeInfosPanel(JPanel boxesPanel, GridBagConstraints boxesConstraints) {

		JPanel infosPanel = new JPanel();
		TitledBorder infosBorder = BorderFactory.createTitledBorder(TextView.get("catalogGameInfosTitle"));
		infosBorder.setTitleJustification(TitledBorder.LEFT);
		infosPanel.setBorder(infosBorder);
		boxesConstraints.gridx = 0;
		boxesConstraints.gridy = 0;
		boxesConstraints.gridwidth = 2;

		// Informations principales
		JPanel mainInfosPanel = new JPanel(new SpringLayout());

		// 1ère ligne

		// Nom (1ère et 2ème colonnes)
		JLabel nameLabel = new JLabel(TextView.get("gameName"));
		mainInfosPanel.add(nameLabel);
		this.nameField = new JTextField();
		this.nameField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		nameLabel.setLabelFor(this.nameField);
		mainInfosPanel.add(this.nameField);

		// ID (3ème et 4ème colonnes)
		JLabel idLabel = new JLabel(TextView.get("gameID"));
		mainInfosPanel.add(idLabel);
		this.idField = new JTextField();
		this.idField.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH / 10, 20));
		this.idField.setEnabled(false);
		idLabel.setLabelFor(this.idField);
		mainInfosPanel.add(this.idField);

		// Disponibilité (5ème et 6ème colonnes)
		JLabel availableLabel = new JLabel(TextView.get("gameAvailable"));
		mainInfosPanel.add(availableLabel);
		JCheckBox availableCheckBox = new JCheckBox();
		availableCheckBox.setEnabled(false);
		availableLabel.setLabelFor(availableCheckBox);
		mainInfosPanel.add(availableCheckBox);

		// 2ème ligne

		// Catégorie (1ère et 2ème colonnes)
		JLabel categoryLabel = new JLabel(TextView.get("gameCategory"));
		mainInfosPanel.add(categoryLabel);
		this.categoryComboBox = new JComboBox<String>();
		this.categoryComboBox.setModel(new PostLoadableComboBoxModel());
		this.categoryComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		categoryLabel.setLabelFor(this.categoryComboBox);
		mainInfosPanel.add(this.categoryComboBox);

		// Editeur (3ème et 4ème colonnes)
		JLabel editorLabel = new JLabel(TextView.get("gameEditor"));
		mainInfosPanel.add(editorLabel);
		this.editorComboBox = new JComboBox<String>();
		this.editorComboBox.setModel(new PostLoadableComboBoxModel());
		this.editorComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		editorLabel.setLabelFor(this.editorComboBox);
		mainInfosPanel.add(this.editorComboBox);

		// Année d'édition (5ème et 6ème colonnes)
		JLabel publishingYearRangeLabel = new JLabel(TextView.get("gamePublishingYear"));
		mainInfosPanel.add(publishingYearRangeLabel);
		this.publishingYearStartRangeField = new JTextField();
		this.publishingYearStartRangeField.setMaximumSize(new Dimension(150, 20));
		mainInfosPanel.add(this.publishingYearStartRangeField);

		// 3ème ligne

		// Nombre de joueurs (1ère et 2ème colonnes)
		// 1ère colonne
		JLabel nbPlayersRangeLabel = new JLabel(TextView.get("gamePlayers"));
		mainInfosPanel.add(nbPlayersRangeLabel);
		// 2ème colonne
		JPanel nbPlayersRangePanel = new JPanel(new FlowLayout());
		nbPlayersRangeLabel.setLabelFor(nbPlayersRangePanel);
		nbPlayersRangePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		// "de"
		JLabel nbPlayersFromRangeLabel = new JLabel(TextView.get("rangeFrom"));
		nbPlayersRangePanel.add(nbPlayersFromRangeLabel);
		// Nombre de joueur minimum
		this.nbPlayersStartRangeField = new JTextField();
		this.nbPlayersStartRangeField.setPreferredSize(new Dimension(35, 20));
		nbPlayersRangePanel.add(this.nbPlayersStartRangeField);
		// "à"
		JLabel nbPlayersToRangeLabel = new JLabel(TextView.get("rangeTo"));
		nbPlayersRangePanel.add(nbPlayersToRangeLabel);
		// Nombre de joueur maximum
		this.nbPlayersEndRangeField = new JTextField();
		this.nbPlayersEndRangeField.setPreferredSize(new Dimension(35, 20));
		nbPlayersRangePanel.add(this.nbPlayersEndRangeField);
		mainInfosPanel.add(nbPlayersRangePanel);

		// Age recommandé (3ème et 4ème colonnes)
		// 3ème colonne
		JLabel minAgeLabel = new JLabel(TextView.get("gameMinAge"));
		mainInfosPanel.add(minAgeLabel);
		// 4ème colonne
		JPanel minAgePanel = new JPanel(new FlowLayout());
		minAgePanel.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		this.minAgeField = new JTextField();
		this.minAgeField.setPreferredSize(new Dimension(35, 20));
		minAgePanel.add(this.minAgeField);
		JLabel minAgePostLabel = new JLabel(TextView.get("years"));
		minAgePanel.add(minAgePostLabel);
		minAgeLabel.setLabelFor(minAgePanel);
		mainInfosPanel.add(minAgePanel);

		// 5ème et 6ème colonnes (vides)
		mainInfosPanel.add(new JLabel());
		mainInfosPanel.add(new JLabel());

		// 3 lignes et 6 colonnes.
		// Un champ (libellé + zone de saisie) s'étend sur 2 colonnes.
		SpringUtilities.makeCompactGrid(mainInfosPanel, 3, 6, 6, 6, 20, 5);
		infosPanel.add(mainInfosPanel);

		boxesPanel.add(infosPanel, boxesConstraints);
	}
	
	public void makeDescriptionPanel(JPanel boxesPanel, GridBagConstraints boxesConstraints) {
		JPanel descriptionPanel = new JPanel(new BorderLayout());
		TitledBorder extensionsBorder = BorderFactory.createTitledBorder(TextView.get("catalogGameDescriptionTitle"));
		extensionsBorder.setTitleJustification(TitledBorder.LEFT);
		descriptionPanel.setBorder(extensionsBorder);
		boxesConstraints.gridx = 0;
		boxesConstraints.gridy = 1;
		boxesConstraints.gridwidth = 2;
		
		this.descriptionBox = new JTextArea();
		this.descriptionBox.setMaximumSize(new Dimension((int) (LudoTechApplication.WINDOW_WIDTH), 50));
		JScrollPane scrollPane = new JScrollPane(this.descriptionBox);
		descriptionPanel.add(scrollPane, BorderLayout.CENTER);
		
		boxesPanel.add(descriptionPanel, boxesConstraints);
	}

	public void makeExtensionsPanel(JPanel boxesPanel, GridBagConstraints boxesConstraints) {
		JPanel extensionsPanel = new JPanel();
		TitledBorder extensionsBorder = BorderFactory.createTitledBorder(TextView.get("catalogGameExtensionsTitle"));
		extensionsBorder.setTitleJustification(TitledBorder.LEFT);
		extensionsPanel.setBorder(extensionsBorder);
		boxesConstraints.gridx = 0;
		boxesConstraints.gridy = 2;
		boxesConstraints.gridwidth = 1;
		boxesPanel.add(extensionsPanel, boxesConstraints);
	}

	public void makeItemsPanel(JPanel boxesPanel, GridBagConstraints boxesConstraints) {
		JPanel itemsPanel = new JPanel();
		TitledBorder itemsBorder = BorderFactory.createTitledBorder(TextView.get("catalogGameItemsTitle"));
		itemsBorder.setTitleJustification(TitledBorder.LEFT);
		itemsPanel.setBorder(itemsBorder);
		boxesConstraints.gridx = 1;
		boxesConstraints.gridy = 2;
		boxesConstraints.gridwidth = 1;
		boxesPanel.add(itemsPanel, boxesConstraints);
	}

	public void load(String name, int gameID, String category, String editor, int publishingYear, int minPlayers, int maxPlayers, int minAge, String description) {
		this.nameField.setText(name);
		this.idField.setText("" + gameID);
		this.categoryComboBox.setSelectedItem(category);
		this.editorComboBox.setSelectedItem(editor);
		this.publishingYearStartRangeField.setText("" + publishingYear);
		this.nbPlayersStartRangeField.setText("" + minPlayers);
		this.nbPlayersEndRangeField.setText("" + maxPlayers);
		this.minAgeField.setText("" + minAge);
		this.descriptionBox.setText(description);
	}
	
	public void loadCategories(List<String> items) {
		((PostLoadableComboBoxModel) this.categoryComboBox.getModel()).loadData(items);
	}
	
	public void loadEditors(List<String> items) {
		((PostLoadableComboBoxModel) this.editorComboBox.getModel()).loadData(items);
	}

	public String getName() {
		return this.nameField.getText();
	}

	public int getId() {
		return Integer.parseInt(this.idField.getText());
	}

	public String getCategory() {
		return this.categoryComboBox.getItemAt(this.categoryComboBox.getSelectedIndex());
	}

	public String getEditor() {
		return this.editorComboBox.getItemAt(this.editorComboBox.getSelectedIndex());
	}

	public int getPublishingYearStartRange() {
		return Integer.parseInt(this.publishingYearStartRangeField.getText());
	}

	public int getNbPlayersStartRange() {
		return Integer.parseInt(this.nbPlayersStartRangeField.getText());
	}

	public int getNbPlayersEndRange() {
		return Integer.parseInt(this.nbPlayersEndRangeField.getText());
	}

	public int getMinAge() {
		return Integer.parseInt(this.minAgeField.getText());
	}

	public String getDescription() {
		return this.descriptionBox.getText();
	}

	public JButton getValidateButton() {
		return this.validateButton;
	}

	public JButton getCancelButton() {
		return this.cancelButton;
	}

}
