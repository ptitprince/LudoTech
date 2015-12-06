package gui.catalog.controller;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import gui.LudoTechApplication;
import gui.catalog.model.GameListModel;
import gui.catalog.view.GameListView;
import gui.catalog.view.GameSearchView;

@SuppressWarnings("serial")
public class CatalogController extends JPanel {
	
	private GameSearchView searchPane;
	
	private GameListView gameListView;
	private GameListModel gameListModel;
	
	public CatalogController() {
		this.gameListModel = new GameListModel();
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	public void makeGUI() {
		this.searchPane = new GameSearchView();
		this.gameListView = new GameListView(this.gameListModel);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.searchPane, this.gameListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH/4);
		this.add(splitPane, BorderLayout.CENTER);
	}
	
	public void refreshGameList() {
		this.gameListModel.refresh();
	}
}
