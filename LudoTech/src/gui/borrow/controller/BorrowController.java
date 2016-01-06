package gui.borrow.controller;

import gui.borrow.model.BorrowListModel;
import gui.borrow.view.BorrowListView;
import gui.borrow.view.BorrowView;
import gui.utils.TextView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.POJOs.Extension;
import model.POJOs.Game;
import model.services.BorrowServices;
import model.services.ExtensionServices;
import model.services.GameServices;
import model.services.ItemServices;
import model.services.MemberServices;

@SuppressWarnings("serial")
public class BorrowController extends JPanel {

	private BorrowServices borrowServices;
	private ItemServices itemServices;
	private MemberServices memberServices;
	private GameServices gameServices;
	private ExtensionServices extensionServices;

	private BorrowListModel borrowListModel;

	private BorrowListView borrowListView;

	private BorrowView borrowView;

	private int currentMemberID;

	public BorrowController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.borrowServices = new BorrowServices();
		this.itemServices = new ItemServices();
		this.memberServices = new MemberServices();
		this.gameServices = new GameServices();
		this.extensionServices = new ExtensionServices();
		this.borrowListModel = new BorrowListModel(this.borrowServices,
				this.itemServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
		this.loadLists();
	}

	public void makeGUI() {
		this.borrowListView = new BorrowListView(this.borrowListModel,
				this.memberServices.isAdmin(currentMemberID));
		this.add(borrowListView, BorderLayout.CENTER);
		
		this.borrowView = new BorrowView();
		this.borrowView.setLocationRelativeTo(this);
	}

	public void makeListeners() {
		this.borrowListView.getAddBorrowButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						borrowView.setVisible(true);
					}
				});
		this.borrowView.getExtensionComboBox().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						borrowView.setVisible(true);
					}
				});
	}
	
	private void loadLists() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				List<Game> games = gameServices.getGames(new HashMap<String, String>());
			
				borrowView.loadGames(games);
			}
		});

	}

	public void refreshBorrowList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				borrowListModel.refresh();
			}
		});
	}
}
