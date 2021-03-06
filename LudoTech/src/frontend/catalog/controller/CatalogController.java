package frontend.catalog.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import backend.POJOs.Game;
import backend.services.ExtensionServices;
import backend.services.GameServices;
import backend.services.ItemServices;
import backend.services.MemberServices;
import frontend.LudoTechApplication;
import frontend.catalog.model.ExtensionListModel;
import frontend.catalog.model.GameListModel;
import frontend.catalog.view.GameListView;
import frontend.catalog.view.GameSearchView;
import frontend.catalog.view.GameView;
import frontend.utils.exceptions.NotValidNumberFieldException;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class CatalogController extends JPanel {
	
	private int currentMemberID;

	private GameServices gameServices;
	private ItemServices itemServices;
	private ExtensionServices extensionServices;
	private MemberServices memberServices;

	private GameListModel gameListModel;
	private ExtensionListModel extensionListModel;

	private GameSearchView gameSearchView;
	private GameListView gameListView;
	private GameView gameView;

	public CatalogController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.gameServices = new GameServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new ExtensionServices();
		this.memberServices = new MemberServices();
		this.gameListModel = new GameListModel(this.gameServices);
		this.extensionListModel = new ExtensionListModel(this.extensionServices);
		
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
		this.loadLists();
	}

	private void makeGUI() {
		boolean currentMemberIsAdmin = this.memberServices.isAdmin(this.currentMemberID);
		this.gameSearchView = new GameSearchView();
		this.gameListView = new GameListView(this.gameListModel, currentMemberIsAdmin);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.gameSearchView,
				this.gameListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH / 3);
		this.add(splitPane, BorderLayout.CENTER);

		this.gameView = new GameView(this.extensionListModel, currentMemberIsAdmin);
		this.gameView.setLocationRelativeTo(this);
	}

	private void makeListeners() {

		// Double clic sur une ligne -> Affichage et chargement des informations
		// du jeu sélectionné dans la pop-up
		this.gameListView.getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = gameListView.getTable();
					int gameID = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
					Game game = gameServices.getGame(gameID);
					int nbItems = itemServices.countItemsOfGame(gameID);
					refreshExtensionList(gameID);
					gameView.load(game.getName(), gameID, gameServices.isAvailableNow(gameID), game.getCategory(),
							game.getEditor(), game.getPublishingYear(), game.getMinimumPlayers(),
							game.getMaximumPlayers(), game.getMinimumAge(), game.getDescription(), nbItems);
					gameView.setVisible(true);
				}
			}
		});

		// Clic sur le bouton "Chercher" sur la liste des jeux
		this.gameSearchView.getSearchButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshGameList();
			}
		});

		if (this.memberServices.isAdmin(this.currentMemberID)) {

			// Clic sur le bouton "Ajouter un jeu" de la liste des jeux
			this.gameListView.getAddGameButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gameView.load("", -1, false, "", "", Calendar.getInstance().get(Calendar.YEAR), 1, 2, 3, "", 0);
					gameView.setVisible(true);
				}
			});

			// Clic sur le bouton "Suprimmer un jeu" de la liste des jeux
			this.gameListView.getDeleteGameButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTable table = gameListView.getTable();
					int selectedRowIndex = table.getSelectedRow();
					if (selectedRowIndex > -1) {
						if (showDeleteGameConfirmation()) {
							int gameID = (Integer) table.getModel().getValueAt(selectedRowIndex, 0);
							int nbExtensions = extensionServices.countExtensions(gameID);
							int nbItems = itemServices.countItemsOfGame(gameID);
							if (nbExtensions > 0) {
								String text = TextView.get("catalogDeleteGameNbExtensionsException") + "\n"
										+ TextView.get("catalogGameNbExtensions") + " " + nbExtensions;
								JOptionPane.showMessageDialog(null, text);
							} else if (nbItems > 0) {
								String text = TextView.get("catalogDeleteGameNbItemsException") + "\n"
										+ TextView.get("catalogGameNbItems") + " " + nbItems;
								JOptionPane.showMessageDialog(null, text);
							} else {
								gameServices.removeGame(gameID);
								refreshGameList();
							}
						}
					}
				}
			});

			// Clic sur le bouton "Valider" de la pop-up de jeu
			this.gameView.getValidateButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (gameView.getName().equals("")) {
							JOptionPane.showMessageDialog(null, TextView.get("catalogGameEmptyFieldsException"));
						} else {
							if (gameView.isCreatingGame()) {
								Game newGame = gameServices.addGame(gameView.getName(), gameView.getDescription(),
										gameView.getPublishingYearStartRange(), gameView.getMinAge(),
										gameView.getNbPlayersStartRange(), gameView.getNbPlayersEndRange(),
										gameView.getCategory(), gameView.getEditor());
								itemServices.setItemsNumberOfGame(newGame.getGameID(), gameView.getNbItems());
							} else {
								if (gameServices.canDecreaseItemAmount(gameView.getId(), gameView.getNbItems())) {
									gameServices.editGame(gameView.getId(), gameView.getName(), gameView.getDescription(),
											gameView.getPublishingYearStartRange(), gameView.getMinAge(),
											gameView.getNbPlayersStartRange(), gameView.getNbPlayersEndRange(),
											gameView.getCategory(), gameView.getEditor());
									itemServices.setItemsNumberOfGame(gameView.getId(), gameView.getNbItems());
								} else {
									JOptionPane.showMessageDialog(null, TextView.get("catalogGameNeedAtLeastOneItemAvailable"));
								}
							}
							gameView.setVisible(false);
							refreshGameList();
						}
					} catch (NotValidNumberFieldException exception) {
						showInvalidFieldsException(exception);
					}
				}
			});

			// Clic sur le bouton "Annuler" de la pop-up de jeu
			this.gameView.getCancelButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gameView.setVisible(false);
				}
			});

			// Clic sur le bouton "Ajouter un exemplaire" de la pop-up de jeu
			this.gameView.getAddExtensionButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (gameView.isCreatingGame()) {
						String text = TextView.get("catalogAddGameExtensionException");
						String title = TextView.get("catalogGameAddExtensionTitle");
						JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
					} else {
						String name = JOptionPane.showInputDialog(null, TextView.get("catalogGameAddExtensionLabel"),
								TextView.get("catalogGameAddExtensionTitle"), JOptionPane.QUESTION_MESSAGE);
						if (name != null && !name.equals("")) {
							extensionServices.addExtension(name, gameView.getId());
							refreshExtensionList(gameView.getId());
						}
					}
				}
			});

			// Clic sur le bouton "Suprimmer un extention" de la pop-up de jeu
			this.gameView.getDeleteExtensionButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JList list = gameView.getExtensionList();
					int selectedIndex = list.getSelectedIndex();
					if (selectedIndex > -1) {
						int extensionID = ((ExtensionListModel) list.getModel()).getIDAt(selectedIndex);
						if (gameServices.canRemoveExtension(extensionID)) {
							extensionServices.deleteExtension(extensionID);
							refreshExtensionList(gameView.getId());
						} else {
							JOptionPane.showMessageDialog(null, TextView.get("catalogGameCantDeleteExtension"));
						}
					}
				}
			});
		}

	}

	private void loadLists() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				List<String> categories = gameServices.getGameCategories(true);
				List<String> editors = gameServices.getGameEditors(true);
				gameSearchView.loadCategories(categories);
				gameSearchView.loadEditors(editors);
				gameView.loadCategories(categories);
				gameView.loadEditors(editors);
			}
		});

	}

	public void refreshGameList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					HashMap<String, String> filter = new HashMap<String, String>();
					filter.put("name", gameSearchView.getNameValue().trim());
					filter.put("category", gameSearchView.getCategoryValue().trim());
					filter.put("editor", gameSearchView.getEditorValue().trim());
					filter.put("publishing_year", gameSearchView.getPublishingYearValue().trim());
					filter.put("nb_players", gameSearchView.getNbPlayersValue().trim());
					filter.put("minimum_age", gameSearchView.getMinAgeValue().trim());
					filter.put("is_available", gameSearchView.getAvailableCheckBoxValue() + "");
					gameListModel.refresh(filter);
				} catch (NotValidNumberFieldException exception) {
					showInvalidFieldsException(exception);
				}
			}
		});
	}

	private void refreshExtensionList(final int gameID) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				extensionListModel.refresh(gameID);
			}
		});
	}

	private void showInvalidFieldsException(NotValidNumberFieldException exception) {
		String text = TextView.get("invalidField") + "\"" + exception.getFieldName() + "\"" + ".\n"
				+ TextView.get("valueInInvalidField")
				+ ((exception.getFieldValue().equals("")) ? TextView.get("emptyValue")
						: "\"" + exception.getFieldValue() + "\" ")
				+ TextView.get("typeOfValidValue")
				+ ((exception.getFieldValue().equals("")) ? TextView.get("notEmptyValue") : exception.getFieldType())
				+ ".";
		JOptionPane.showMessageDialog(null, text);
	}

	private boolean showDeleteGameConfirmation() {
		String text = TextView.get("gameConfirmDeleting");
		int result = JOptionPane.showConfirmDialog(null, text, "", JOptionPane.YES_OPTION);
		return (result == 0);
	}
}
