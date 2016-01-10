package frontend.catalog.view;

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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import frontend.LudoTechApplication;
import frontend.catalog.model.ExtensionListModel;
import frontend.utils.exceptions.NotValidNumberFieldException;
import frontend.utils.gui.PostLoadableComboBoxModel;
import frontend.utils.gui.SpringUtilities;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class GameView extends JDialog {

	private static final double WINDOW_RATIO = 1.10;

	private boolean creatingGame;
	private boolean currentMemberIsAdmin;
	
	private JTextField nameField;
	private JTextField idField;
	private JCheckBox availabilityCheckBox;
	private JComboBox<String> categoryComboBox;
	private JComboBox<String> editorComboBox;
	private JTextField publishingYearField;
	private JTextField nbPlayersStartRangeField;
	private JTextField nbPlayersEndRangeField;
	private JTextField minAgeField;
	private JTextArea descriptionBox;

	private JList extensionsList;
	private JButton addExtensionButton;
	private JButton deleteExtensionButton;

	private JSpinner nbItemsSpinner;
	private JLabel nbItemsRawValue;

	private JButton validateButton;
	private JButton cancelButton;

	public GameView(ExtensionListModel extensionListModel, boolean currentMemberIsAdmin) {
		this.creatingGame = false;
		this.currentMemberIsAdmin = currentMemberIsAdmin;

		this.setTitle(TextView.get("catalogGamePopupTitle"));
		this.setSize((int) (LudoTechApplication.WINDOW_WIDTH / WINDOW_RATIO),
				(int) (LudoTechApplication.WINDOW_HEIGHT / WINDOW_RATIO));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.makeGUI(extensionListModel);
		if (!currentMemberIsAdmin) {
			this.disableFields();
		}
	}

	private void makeGUI(ExtensionListModel extensionListModel) {
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
		makeExtensionsPanel(boxesPanel, boxesConstraints, extensionListModel);
		makeItemsPanel(boxesPanel, boxesConstraints);

		this.add(boxesPanel, BorderLayout.CENTER);

		if (this.currentMemberIsAdmin) {
			JPanel actionsPanel = new JPanel();
			this.validateButton = new JButton(TextView.get("validate"));
			actionsPanel.add(this.validateButton);
			this.cancelButton = new JButton(TextView.get("cancel"));
			actionsPanel.add(this.cancelButton);
			this.add(actionsPanel, BorderLayout.SOUTH);
		}
	}

	private void makeInfosPanel(JPanel boxesPanel, GridBagConstraints boxesConstraints) {

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
		this.idField.setEditable(false);
		idLabel.setLabelFor(this.idField);
		mainInfosPanel.add(this.idField);

		// Disponibilité (5ème et 6ème colonnes)
		JLabel availableLabel = new JLabel(TextView.get("gameAvailable"));
		mainInfosPanel.add(availableLabel);
		this.availabilityCheckBox = new JCheckBox();
		availabilityCheckBox.setEnabled(false);
		availableLabel.setLabelFor(availabilityCheckBox);
		mainInfosPanel.add(availabilityCheckBox);

		// 2ème ligne

		// Catégorie (1ère et 2ème colonnes)
		JLabel categoryLabel = new JLabel(TextView.get("gameCategory"));
		mainInfosPanel.add(categoryLabel);
		this.categoryComboBox = new JComboBox<String>();
		this.categoryComboBox.setEditable(true);
		this.categoryComboBox.setModel(new PostLoadableComboBoxModel());
		this.categoryComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		categoryLabel.setLabelFor(this.categoryComboBox);
		mainInfosPanel.add(this.categoryComboBox);

		// Editeur (3ème et 4ème colonnes)
		JLabel editorLabel = new JLabel(TextView.get("gameEditor"));
		mainInfosPanel.add(editorLabel);
		this.editorComboBox = new JComboBox<String>();
		this.editorComboBox.setEditable(true);
		this.editorComboBox.setModel(new PostLoadableComboBoxModel());
		this.editorComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		editorLabel.setLabelFor(this.editorComboBox);
		mainInfosPanel.add(this.editorComboBox);

		// Année d'édition (5ème et 6ème colonnes)
		JLabel publishingYearRangeLabel = new JLabel(TextView.get("gamePublishingYear"));
		mainInfosPanel.add(publishingYearRangeLabel);
		this.publishingYearField = new JTextField();
		this.publishingYearField.setMaximumSize(new Dimension(150, 20));
		mainInfosPanel.add(this.publishingYearField);

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

	private void makeDescriptionPanel(JPanel boxesPanel, GridBagConstraints boxesConstraints) {
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

	private void makeExtensionsPanel(JPanel boxesPanel, GridBagConstraints boxesConstraints,
			ExtensionListModel extensionListModel) {
		JPanel extensionsPanel = new JPanel(new BorderLayout());
		TitledBorder extensionsBorder = BorderFactory.createTitledBorder(TextView.get("catalogGameExtensionsTitle"));
		extensionsBorder.setTitleJustification(TitledBorder.LEFT);
		extensionsPanel.setBorder(extensionsBorder);
		boxesConstraints.gridx = 0;
		boxesConstraints.gridy = 2;
		boxesConstraints.gridwidth = 1;

		this.extensionsList = new JList();
		this.extensionsList.setModel(extensionListModel);
		this.extensionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.extensionsList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane listScroller = new JScrollPane(this.extensionsList);
		listScroller.setMaximumSize(new Dimension(listScroller.getWidth(), 50));
		extensionsPanel.add(listScroller, BorderLayout.CENTER);

		if (this.currentMemberIsAdmin) {
			JPanel extensionsActionsPanel = new JPanel(new FlowLayout());
			this.addExtensionButton = new JButton(TextView.get("add"));
			extensionsActionsPanel.add(this.addExtensionButton);
			this.deleteExtensionButton = new JButton(TextView.get("delete"));
			extensionsActionsPanel.add(this.deleteExtensionButton);
			extensionsPanel.add(extensionsActionsPanel, BorderLayout.SOUTH);
		}

		boxesPanel.add(extensionsPanel, boxesConstraints);
	}

	private void makeItemsPanel(JPanel boxesPanel, GridBagConstraints boxesConstraints) {
		JPanel itemsPanel = new JPanel(new FlowLayout());
		TitledBorder itemsBorder = BorderFactory.createTitledBorder(TextView.get("catalogGameItemsTitle"));
		itemsBorder.setTitleJustification(TitledBorder.LEFT);
		itemsPanel.setBorder(itemsBorder);
		boxesConstraints.gridx = 1;
		boxesConstraints.gridy = 2;
		boxesConstraints.gridwidth = 1;

		JLabel nbItemsLabel = new JLabel(TextView.get("catalogGameNbItems"));
		itemsPanel.add(nbItemsLabel);

		this.nbItemsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		this.nbItemsRawValue = new JLabel();
		if (this.currentMemberIsAdmin) {
			itemsPanel.add(this.nbItemsSpinner);
		} else {
			itemsPanel.add(this.nbItemsRawValue);
		}

		boxesPanel.add(itemsPanel, boxesConstraints);
	}

	private void disableFields() {
		this.nameField.setEditable(false);
		this.categoryComboBox.setEnabled(false);
		this.editorComboBox.setEnabled(false);
		this.publishingYearField.setEditable(false);
		this.nbPlayersStartRangeField.setEditable(false);
		this.nbPlayersEndRangeField.setEditable(false);
		this.minAgeField.setEditable(false);
		this.descriptionBox.setEditable(false);
	}

	public void load(String name, int gameID, boolean isAvailable, String category, String editor, int publishingYear, int minPlayers,
			int maxPlayers, int minAge, String description, int nbItems) {
		this.nameField.setText(name);
		this.creatingGame = (gameID == -1);
		this.idField.setText((gameID == -1) ? "" : "" + gameID);
		this.availabilityCheckBox.setSelected(isAvailable);
		this.categoryComboBox.setSelectedItem(TextView.makeFirstLetterUpper(category));
		this.editorComboBox.setSelectedItem(TextView.makeFirstLetterUpper(editor));
		this.publishingYearField.setText("" + publishingYear);
		this.nbPlayersStartRangeField.setText("" + minPlayers);
		this.nbPlayersEndRangeField.setText("" + maxPlayers);
		this.minAgeField.setText("" + minAge);
		this.descriptionBox.setText(description);
		this.nbItemsSpinner.setValue(nbItems);
		this.nbItemsRawValue.setText(nbItems + "");
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
		String category = (String) this.categoryComboBox.getSelectedItem();
		return (category != null) ? category : "";
	}

	public String getEditor() {
		String editor = (String) this.editorComboBox.getSelectedItem();
		return (editor != null) ? editor : "";
	}

	public int getPublishingYearStartRange() throws NotValidNumberFieldException {
		int publishingYear = -1;
		try {
			publishingYear = Integer.parseInt(this.publishingYearField.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("gamePublishingYear"),
					this.publishingYearField.getText(), TextView.get("integerType"));
		}
		return publishingYear;
	}

	public int getNbPlayersStartRange() throws NotValidNumberFieldException {
		int minPlayers = -1;
		try {
			minPlayers = Integer.parseInt(this.nbPlayersStartRangeField.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("gamePlayers"), this.nbPlayersStartRangeField.getText(),
					TextView.get("integerType"));
		}
		return minPlayers;
	}

	public int getNbPlayersEndRange() throws NotValidNumberFieldException {
		int maxPlayers = -1;
		try {
			maxPlayers = Integer.parseInt(this.nbPlayersEndRangeField.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("gamePlayers"), this.nbPlayersEndRangeField.getText(),
					TextView.get("integerType"));
		}
		return maxPlayers;
	}

	public int getMinAge() throws NotValidNumberFieldException {
		int minAge = -1;
		try {
			minAge = Integer.parseInt(this.minAgeField.getText());
		} catch (NumberFormatException exception) {
			throw new NotValidNumberFieldException(TextView.get("gameMinAge"), this.minAgeField.getText(),
					TextView.get("integerType"));
		}
		return minAge;
	}

	public String getDescription() {
		return this.descriptionBox.getText();
	}

	public JList getExtensionList() {
		return this.extensionsList;
	}

	public JButton getAddExtensionButton() {
		return this.addExtensionButton;
	}

	public JButton getDeleteExtensionButton() {
		return this.deleteExtensionButton;
	}

	public int getNbItems() {
		return (Integer) this.nbItemsSpinner.getValue();
	}

	public JButton getValidateButton() {
		return this.validateButton;
	}

	public JButton getCancelButton() {
		return this.cancelButton;
	}
	
	public boolean isCreatingGame() {
		return this.creatingGame;
	}

}
