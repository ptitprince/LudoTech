package gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.catalog.controller.CatalogController;
import gui.utils.EmptyPanel;

@SuppressWarnings("serial")
public class MainController extends JTabbedPane {
	
	private static final String HOME_TAB_NAME = "Accueil";
	private static final String CATALOG_TAB_NAME = "Catalogue";
	private static final String BORROW_TAB_NAME = "Prêts";
	private static final String BOOK_TAB_NAME = "Réservations";
	private static final String PROFILE_TAB_NAME = "Profil";
	private static final String MEMBERS_TAB_NAME = "Adhérents";
	private static final String PARAMETERS_TAB_NAME = "Paramètres";
	
	private CatalogController catalogController;

	public MainController() {
		this.catalogController = new CatalogController();
		this.makeGUI();
		this.makeListeners();
	}

	private void makeGUI() {
		this.addTab(HOME_TAB_NAME, new EmptyPanel());
		this.addTab(CATALOG_TAB_NAME, this.catalogController);
		this.addTab(BORROW_TAB_NAME, new EmptyPanel());
		this.addTab(BOOK_TAB_NAME, new EmptyPanel());
		this.addTab(PROFILE_TAB_NAME, new EmptyPanel());
		this.addTab(MEMBERS_TAB_NAME, new EmptyPanel());
		this.addTab(PARAMETERS_TAB_NAME, new EmptyPanel());
	}
	
	private void makeListeners() {
		this.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	switch (MainController.this.getSelectedIndex()) {
	        	case 1:
	        		MainController.this.catalogController.refreshGameList();
	        		break;
	        	default:
	        		break;
	        	}
	        }
	    });
	}
	
}
