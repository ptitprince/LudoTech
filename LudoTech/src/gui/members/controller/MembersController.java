package gui.members.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import gui.LudoTechApplication;
import gui.catalog.model.ExtensionListModel;
import gui.catalog.model.GameListModel;
import gui.catalog.view.GameListView;
import gui.catalog.view.GameSearchView;
import gui.catalog.view.GameView;
import gui.members.model.MemberListModel;
import gui.membersList.view.MemberListView;
import gui.membersList.view.MemberSearchView;
import gui.profile.view.ProfileView;
import gui.utils.exceptions.NotValidNumberFieldException;
import gui.utils.TextView;
import model.POJOs.Game;
import model.POJOs.Member;
import model.services.ExtensionServices;
import model.services.MemberServices;
import model.services.MemberContextServices;

@SuppressWarnings("serial")
public class MembersController extends JPanel {


	private MemberListView memberListView;
	private MemberSearchView memberSearchView;
	
	private int currentMemberID;
	
	private MemberServices memberServices;
	private MemberContextServices memberContextServices;
	
	private MemberListModel memberListModel;
	private ProfileView profileView;
	
	
	
	public MembersController( int currentMemberID) {
		
		this.currentMemberID = currentMemberID;
		this.memberServices = new MemberServices();
		this.memberContextServices = new MemberContextServices();
		this.memberListModel = new MemberListModel( this.memberServices);
		
		this.makeGUI();
	}
	
	public void makeGUI() {
		boolean currentMemberIsAdmin = this.memberServices.isAdmin(this.currentMemberID);
		this.memberSearchView = new MemberSearchView();
		this.memberListView = new MemberListView(this.memberListModel, currentMemberIsAdmin);
		this.profileView = new ProfileView();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.memberSearchView,
				this.memberListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH / 4);
		this.add(splitPane, BorderLayout.CENTER);
	}
	
	private void makeListeners() {

//		// Double clic sur une ligne -> Affichage et chargement des informations
//		// du membre sélectionné dans la pop-up
//		this.memberListView.getTable().addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() == 2) {
//					JTable table = memberListView.getTable();
//					int memberID = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
//					Member member = memberServices.getMember(memberID);
//					profileView.load(member.getFirstName(), member.getLastName(), member.getPseudo(), member.getPassword(),
//							member.getIsAdmin(), member.getBirthDate(), member.getPhoneNumber(), member.getEmail(), member.getStreetAddress(),
//							member.getPostalCode(), member.getCity());
//					profileView.setVisible(true);
//				}
//			}
//		});
//		// Clic sur le bouton "chercher" sur la liste des membres
//			this.memberSearchView.getSearchButton().addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						refreshMemberList();
//					}
//				});
//		
//				this.memberListView.getAddGameButton().addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						profileView.load("", "", "", "", false,  Calendar.getInstance().getTime(), "", "", "", "", "");
//						profileView.setVisible(true);
//					}
//				});
//				
//				this.memberListView.getDeleteGameButton().addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						JTable table = memberListView.getTable();
//						int selectedRowIndex = table.getSelectedRow();
//						if (selectedRowIndex > -1) {
//							int memberID = (Integer) table.getModel().getValueAt(selectedRowIndex, 0);
//							memberServices.removeMember(memberID);
//							refreshGameList();
//							}
//						}
//					});
	
	}
}
