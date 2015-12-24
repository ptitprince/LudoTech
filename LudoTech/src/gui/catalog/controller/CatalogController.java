package gui.catalog.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import gui.LudoTechApplication;
import gui.catalog.model.GameListModel;
import gui.catalog.view.GameListView;
import gui.catalog.view.GameSearchView;
import gui.catalog.view.GameView;
import gui.utils.exceptions.NotValidNumberFieldException;
import gui.utils.TextView;
import model.POJOs.Game;
import model.services.GameServices;
import model.services.ItemServices;

@SuppressWarnings("serial")
public class CatalogController extends JPanel {

	private GameServices gameServices;
	private ItemServices itemServices;

	private GameListModel gameListModel;

	private GameSearchView searchPane;
	private GameListView gameListView;
	private GameView gameView;

	public CatalogController() {
		this.gameServices = new GameServices();
		this.itemServices = new ItemServices();
		this.gameListModel = new GameListModel(this.gameServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
		this.loadLists();
	}

	public void makeGUI() {
		this.searchPane = new GameSearchView();
		this.gameListView = new GameListView(this.gameListModel);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.searchPane, this.gameListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH / 4);
		this.add(splitPane, BorderLayout.CENTER);

		this.gameView = new GameView();
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
					int nbItems = itemServices.countItemsOfGame(game.getGameID());
					gameView.load(game.getName(), gameID, game.getCategory(), game.getEditor(),
							game.getPublishingYear(), game.getMinimumPlayers(), game.getMaximumPlayers(),
							game.getMinimumAge(), game.getDescription(), nbItems);
					gameView.setVisible(true);
				}
			}
		});

		// Clic sur le bouton "ajouter un jeu" de la liste des jeux
		this.gameListView.getAddGameButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameView.load("", -1, "", "", Calendar.getInstance().get(Calendar.YEAR), 1, 2, 3, "", 0);
				gameView.setVisible(true);
			}
		});

		// Clic sur le bouton "suprimmer un jeu" de la liste des jeux
		this.gameListView.getDeleteGameButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTable table = gameListView.getTable();
				int selectedRowIndex = table.getSelectedRow();
				if (selectedRowIndex > -1) {
					int gameID = (Integer) table.getModel().getValueAt(selectedRowIndex, 0);
					int nbItems = itemServices.countItemsOfGame(gameID);
					if (nbItems > 0) {
						String text = TextView.get("catalogDeleteGameException") + "\n"
								+ TextView.get("catalogGameNbItems") + " : " + nbItems;
						JOptionPane.showMessageDialog(null, text);
					} else {
						gameServices.remove(gameID);
						refreshGameList();
					}
				}
			}
		});

		// Clic sur le bouton "valider" de la pop-up
		this.gameView.getValidateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (gameView.isCreatingGame()) {
						Game newGame = gameServices.addGame(gameView.getName(), gameView.getDescription(),
								gameView.getPublishingYearStartRange(), gameView.getMinAge(),
								gameView.getNbPlayersStartRange(), gameView.getNbPlayersEndRange(),
								gameView.getCategory(), gameView.getEditor());
						itemServices.setItemsNumberOfGame(newGame.getGameID(), gameView.getNbItems());
					} else {
						gameServices.editGame(gameView.getId(), gameView.getName(), gameView.getDescription(),
								gameView.getPublishingYearStartRange(), gameView.getMinAge(),
								gameView.getNbPlayersStartRange(), gameView.getNbPlayersEndRange(),
								gameView.getCategory(), gameView.getEditor());
						itemServices.setItemsNumberOfGame(gameView.getId(), gameView.getNbItems());
					}
					gameView.setVisible(false);
					refreshGameList();
				} catch (NotValidNumberFieldException exception) {
					String text = TextView.get("invalidField") + "\"" + exception.getFieldName() + "\"" + ".\n"
							+ TextView.get("valueInInvalidField")
							+ ((exception.getFieldValue().equals("")) ? TextView.get("emptyValue")
									: "\"" + exception.getFieldValue() + "\" ")
							+ TextView.get("typeOfValidValue") + ((exception.getFieldValue().equals(""))
									? TextView.get("notEmptyValue") : exception.getFieldType())
							+ ".";
					JOptionPane.showMessageDialog(null, text);
				}
			}
		});

		// Clic sur le bouton "annuler" de la pop-up
		this.gameView.getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameView.setVisible(false);
			}
		});

	}

	private void loadLists() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gameView.loadCategories(gameServices.getCategoryList());
				gameView.loadEditors(gameServices.getEditorList());
			}
		});

	}

	public void refreshGameList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gameListModel.refresh();
			}
		});
	}
}
