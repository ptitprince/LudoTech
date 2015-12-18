package gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.catalog.controller.CatalogController;
import gui.parameters.controller.ParametersController;
import gui.utils.EmptyPanel;
import gui.utils.TextView;

@SuppressWarnings("serial")
public class MainController extends JTabbedPane {
	
	private CatalogController catalogController;
	private ParametersController parametersController;

	public MainController() {
		this.catalogController = new CatalogController();
		this.parametersController = new ParametersController();
		this.makeGUI();
		this.makeListeners();
	}

	private void makeGUI() {
		this.addTab(TextView.get("tabHome"), new EmptyPanel());
		this.addTab(TextView.get("tabCatalog"), this.catalogController);
		this.addTab(TextView.get("tabBorrow"), new EmptyPanel());
		this.addTab(TextView.get("tabBook"), new EmptyPanel());
		this.addTab(TextView.get("tabProfile"), new EmptyPanel());
		this.addTab(TextView.get("tabMembers"), new EmptyPanel());
		this.addTab(TextView.get("tabParameters"), this.parametersController);
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
