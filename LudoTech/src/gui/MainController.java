package gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.catalog.controller.CatalogController;
import gui.login.controller.LoginController;
import gui.login.view.LoginObserver;
import gui.parameters.controller.ParametersController;
import gui.profile.controller.ProfileController;
import gui.utils.EmptyPanel;
import gui.utils.TextView;

@SuppressWarnings("serial")
public class MainController extends JTabbedPane implements LoginObserver {

	private LoginController loginController;
	private CatalogController catalogController;
	private ParametersController parametersController;
	private ProfileController profileController;

	private int currentMemberID;

	public MainController() {
		this.loginController = new LoginController();
		this.makeLoginGUI();
	}

	private void makeLoginGUI() {
		this.loginController.addObserver(this);
		this.addTab(TextView.get("tabLogin"), loginController);
	}

	private void makeMainUseGUI(boolean showAdminTabs) {
		this.catalogController = new CatalogController(currentMemberID);
		this.catalogController.refreshGameList();
		this.parametersController = new ParametersController();
		this.profileController = new ProfileController(currentMemberID);
		this.addTab(TextView.get("tabCatalog"), this.catalogController);
		this.addTab(TextView.get("tabBorrow"), new EmptyPanel());
		this.addTab(TextView.get("tabBook"), new EmptyPanel());
		this.addTab(TextView.get("tabProfile"), this.profileController);
		if (showAdminTabs) {
			this.addTab(TextView.get("tabMembers"), new EmptyPanel());
			this.addTab(TextView.get("tabParameters"), this.parametersController);
		}
	}

	private void makeMainUseListeners() {
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				switch (MainController.this.getSelectedIndex()) {
				case 0:
					MainController.this.catalogController.refreshGameList();
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void notifySuccessfulLogin(int memberID, boolean isAdmin) {
		this.currentMemberID = memberID;
		this.removeTabAt(0);
		this.makeMainUseGUI(isAdmin);
		this.makeMainUseListeners();
	}

}
