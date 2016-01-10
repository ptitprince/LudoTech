package frontend;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import frontend.book.controller.BookController;
import frontend.borrow.controller.BorrowController;
import frontend.catalog.controller.CatalogController;
import frontend.login.controller.LoginController;
import frontend.login.view.LoginObserver;
import frontend.members.controller.MembersController;
import frontend.parameters.controller.ParametersController;
import frontend.profile.controller.ProfileController;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class MainController extends JTabbedPane implements LoginObserver {

	private LoginController loginController;
	private CatalogController catalogController;
	private ParametersController parametersController;
	private BookController bookController;
	private ProfileController profileController;
	private MembersController membersController;
	private BorrowController borrowController;

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
		this.borrowController = new BorrowController(currentMemberID);
		this.bookController = new BookController(currentMemberID);
		this.profileController = new ProfileController(currentMemberID);
		this.membersController = new MembersController(currentMemberID);
		this.parametersController = new ParametersController();
		
		this.catalogController.refreshGameList(); // Affichage de la liste lors du chargement (car 1er onglet affiché)
		
		this.addTab(TextView.get("tabCatalog"), this.catalogController);
		this.addTab(TextView.get("tabBorrow"), this.borrowController);
		this.addTab(TextView.get("tabBook"), this.bookController);
		this.addTab(TextView.get("tabProfile"), this.profileController);
		if (showAdminTabs) {
			this.addTab(TextView.get("tabMembers"), this.membersController);
			this.addTab(TextView.get("tabParameters"), this.parametersController);
		}
	}

	private void makeMainUseListeners() {
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				switch (getSelectedIndex()) {
				case 0:
					catalogController.refreshGameList();
					break;
				case 1 :
					borrowController.refreshBorrowList();
					break;
				case 2 :
					bookController.refreshBookList();
					break;
				case 3 :
					profileController.refreshData();
					break;
				case 4 :
					membersController.refreshMemberList();
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