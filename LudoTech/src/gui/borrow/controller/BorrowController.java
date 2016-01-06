package gui.borrow.controller;

import gui.LudoTechApplication;
import gui.borrow.model.BorrowListModel;
import gui.borrow.view.BorrowListView;
import gui.borrow.view.BorrowSearchView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import model.services.BorrowServices;
import model.services.ItemServices;

@SuppressWarnings("serial")
public class BorrowController extends JPanel {

	private BorrowServices borrowServices;
	private ItemServices itemServices;

	private BorrowSearchView borrowSearchView;
	private BorrowListView borrowListView;

	private BorrowListModel borrowListModel;

	public BorrowController() {
		this.borrowServices = new BorrowServices();
		this.itemServices = new ItemServices();
		this.borrowListModel = new BorrowListModel(this.borrowServices,
				this.itemServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	public void makeGUI() {
		this.borrowSearchView = new BorrowSearchView();
		this.borrowListView = new BorrowListView(this.borrowListModel);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, this.borrowSearchView, this.borrowListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH / 4);
		this.add(splitPane, BorderLayout.CENTER);
	}

	public void refreshBorrowList() {
		/*SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HashMap<String, String> filter = new HashMap<String, String>();
				filter.put("game_name", borrowSearchView.getGamesNameValue().trim());
				filter.put("member_name", borrowSearchView.getMembersNameValue().trim());
				filter.put("ending_date", borrowSearchView.getEndingDateValue().trim());
			}
		});*/
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				borrowListModel.refresh();
			}
		});
	}

	private void makeListeners() {
		// Clic sur le bouton "chercher" sur la liste des emprunts
		this.borrowSearchView.getSearchButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						refreshBorrowList();
					}
				});
	}
}
