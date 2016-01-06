package gui.borrow.controller;

import gui.borrow.model.BorrowListModel;
import gui.borrow.view.BorrowListView;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.services.BorrowServices;
import model.services.ItemServices;
import model.services.MemberServices;

@SuppressWarnings("serial")
public class BorrowController extends JPanel {

	private BorrowServices borrowServices;
	private ItemServices itemServices;
	private MemberServices memberServices;

	private BorrowListModel borrowListModel;

	private BorrowListView borrowListView;

	private int currentMemberID;

	public BorrowController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.borrowServices = new BorrowServices();
		this.itemServices = new ItemServices();
		this.memberServices = new MemberServices();
		this.borrowListModel = new BorrowListModel(this.borrowServices,
				this.itemServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	public void makeGUI() {
		this.borrowListView = new BorrowListView(this.borrowListModel, this.memberServices.isAdmin(currentMemberID));
		this.add(borrowListView, BorderLayout.CENTER);
	}

	public void refreshBorrowList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				borrowListModel.refresh();
			}
		});
	}
}
