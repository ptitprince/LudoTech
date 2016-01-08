package gui.members.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import gui.LudoTechApplication;
import gui.members.model.MemberListModel;
import gui.membersList.view.MemberListView;
import gui.membersList.view.MemberSearchView;
import gui.profile.view.ProfileView;
import gui.utils.TextView;
import gui.utils.exceptions.NotValidNumberFieldException;
import model.POJOs.Member;
import model.POJOs.MemberContext;
import model.services.MemberServices;
import model.services.MemberContextServices;
import gui.membersList.view.MemberView;

@SuppressWarnings("serial")
public class MembersController extends JPanel {

	private MemberListView memberListView;
	private MemberSearchView memberSearchView;

	private int currentMemberID;

	private MemberServices memberServices;
	private MemberContextServices memberContextServices;

	private MemberListModel memberListModel;
	private MemberView memberView;

	public MembersController(int currentMemberID) {

		this.currentMemberID = currentMemberID;
		this.memberServices = new MemberServices();
		this.memberContextServices = new MemberContextServices();
		this.memberListModel = new MemberListModel(this.memberServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
	}

	public void makeGUI() {
		boolean currentMemberIsAdmin = this.memberServices.isAdmin(this.currentMemberID);
		this.memberSearchView = new MemberSearchView();
		this.memberListView = new MemberListView(this.memberListModel, currentMemberIsAdmin);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.memberSearchView,
				this.memberListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH / 4);
		this.add(splitPane, BorderLayout.CENTER);

		this.memberView = new MemberView();
		this.memberView.setLocationRelativeTo(this);
	}

	private void makeListeners() {

		// Clic sur le bouton "chercher" sur la liste des membres
		this.memberSearchView.getSearchButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshMemberList();
			}
		});

		// Double clic sur une ligne -> Affichage et chargement des informations
		// du membre sélectionné dans la pop-up
		this.memberListView.getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = memberListView.getTable();
					int memberID = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
					Member member = memberServices.getMember(memberID);
					MemberContext context = member.getMemberContext();
					memberView.getProfileView().load(member.getFirstName(), member.getLastName(), member.getPseudo(),
							member.getPassword(), member.getIsAdmin(), member.getBirthDate(), member.getPhoneNumber(),
							member.getEmail(), member.getStreetAddress(), member.getPostalCode(), member.getCity(),
							context.getNbFakeBookings(), context.getNbDelays(), context.canBook(), context.canBorrow(),
							context.getLastSubscriptionDate());
					memberView.setVisible(true);
				}
			}
		});

		this.memberListView.getAddMemberButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberView.getProfileView().load("", "", "", "", false, Calendar.getInstance().getTime(), "", "", "",
						"", "", 0, 0, false, false, Calendar.getInstance().getTime());
				memberView.getProfileView().setVisible(true);
			}
		});

		this.memberListView.getDeleteMemberButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTable table = memberListView.getTable();
				int selectedRowIndex = table.getSelectedRow();
				if (selectedRowIndex > -1) {
					int memberID = (Integer) table.getModel().getValueAt(selectedRowIndex, 0);
					memberServices.removeMember(memberID);
					refreshMemberList();
				}
			}
		});

		this.memberView.getProfileView().getValidateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Dans le cas de l'enregistrement depuis la liste des
					// adhérents il ne faut pas utiliser l'id de l'utilisateur
					// actuel
					ProfileView profileView = memberView.getProfileView();
					memberServices.saveMember(currentMemberID, profileView.getFirstName(), profileView.getLastName(),
							profileView.getPseudo(), profileView.getPassword(), profileView.getIsAdmin(),
							profileView.getBirthDate(), profileView.getPhoneNumber(), profileView.getEmail(),
							profileView.getStreetAddress(), profileView.getPostalCode(), profileView.getCity());
					memberContextServices.editMemberContext(currentMemberID, profileView.getNbDelays(),
							profileView.getNbFakeBookings(), profileView.getLastSubscriptionDate(),
							profileView.getCanBorrow(), profileView.getCanBook());
					String text = TextView.get("profileEditMemberConfirmation");
					String title = TextView.get("profileEditMemberException");
					JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
					memberView.setVisible(false);
				} catch (ParseException e1) {
					showInvalidDateException();
				} catch (NotValidNumberFieldException exception) {
					showInvalidFieldsException(exception);
				}

			}
		});

		this.memberView.getProfileView().getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberView.setVisible(false);
			}
		});

	}

	public void refreshMemberList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HashMap<String, String> filter = new HashMap<String, String>();
				filter.put("first_name", memberSearchView.getFirstNameValue().trim());
				filter.put("last_name", memberSearchView.getLastNameValue().trim());
				filter.put("pseudo", memberSearchView.getPseudoValue().trim());
				memberListModel.refresh(filter);

			}
		});

	}
	
	public void showInvalidDateException() {
		String text = TextView.get("membersEditMemberDateFormatException");
		JOptionPane.showMessageDialog(null, text);
	}
	
	public void showInvalidFieldsException(NotValidNumberFieldException exception) {
		String text = TextView.get("invalidField") + "\"" + exception.getFieldName() + "\"" + ".\n"
				+ TextView.get("valueInInvalidField")
				+ ((exception.getFieldValue().equals("")) ? TextView.get("emptyValue")
						: "\"" + exception.getFieldValue() + "\" ")
				+ TextView.get("typeOfValidValue")
				+ ((exception.getFieldValue().equals("")) ? TextView.get("notEmptyValue") : exception.getFieldType())
				+ ".";
		JOptionPane.showMessageDialog(null, text);
	}
	
}
