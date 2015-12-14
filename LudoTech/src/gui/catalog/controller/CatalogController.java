package gui.catalog.controller;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import gui.LudoTechApplication;
import gui.catalog.model.GameListModel;
import gui.catalog.view.GameListView;
import gui.catalog.view.GameSearchView;
import gui.catalog.view.GameView;
import model.POJOs.Game;
import model.services.GameServices;

@SuppressWarnings("serial")
public class CatalogController extends JPanel {

	private GameServices gameServices;

	private GameSearchView searchPane;
	private GameListView gameListView;
	private GameListModel gameListModel;
	private GameView gameView;

	public CatalogController() {
		this.gameServices = new GameServices();
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

		// Row double-clicked -> show game infos pop-up
		this.gameListView.getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = gameListView.getTable();
					int gameID = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
					Game game = gameServices.getGame(gameID);
					gameView.load(game.getName(), gameID, game.getCategory(), game.getEditor(),
							game.getPublishingYear(), game.getMinimumPlayers(), game.getMaximumPlayers(),
							game.getMinimumAge(), game.getDescription());
					gameView.showPopup();
				}
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
