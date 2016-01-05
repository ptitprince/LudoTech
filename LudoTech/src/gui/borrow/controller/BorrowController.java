package gui.borrow.controller;

import gui.LudoTechApplication;
import gui.borrow.model.BorrowListModel;
import gui.borrow.view.BorrowListView;
import gui.borrow.view.BorrowSearchView;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.services.BorrowServices;

@SuppressWarnings("serial")
public class BorrowController extends JPanel {

	private BorrowServices borrowServices;
	private BorrowSearchView borrowSearchView;
	private BorrowListView borrowListView;
	private BorrowListModel borrowlistModel;

	public BorrowController() {
		this.borrowServices = new BorrowServices();
		this.borrowlistModel = new BorrowListModel(this.borrowServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	public void makeGUI() {
		this.borrowSearchView = new BorrowSearchView();
		this.borrowListView = new BorrowListView(this.borrowlistModel);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, this.borrowSearchView, this.borrowListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH / 4);
		this.add(splitPane, BorderLayout.CENTER);

	}
}
