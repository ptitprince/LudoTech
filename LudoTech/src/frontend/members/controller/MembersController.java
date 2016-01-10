package frontend.members.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import backend.POJOs.Member;
import backend.POJOs.MemberContext;
import backend.services.MemberContextServices;
import backend.services.MemberServices;
import frontend.LudoTechApplication;
import frontend.members.model.MemberListModel;
import frontend.members.view.MemberListView;
import frontend.members.view.MemberSearchView;
import frontend.members.view.MemberView;
import frontend.profile.view.ProfileView;
import frontend.utils.exceptions.NotValidNumberFieldException;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class MembersController extends JPanel {

	private MemberListView memberListView;
	private MemberSearchView memberSearchView;

	private MemberServices memberServices;
	private MemberContextServices memberContextServices;

	private MemberListModel memberListModel;
	private MemberView memberView;

	public MembersController(int currentMemberID) {
		this.memberServices = new MemberServices();
		this.memberContextServices = new MemberContextServices();
		this.memberListModel = new MemberListModel(this.memberServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
	}

	private void makeGUI() {
		this.memberSearchView = new MemberSearchView();
		this.memberListView = new MemberListView(this.memberListModel);
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
					memberView.getProfileView().load(memberID, context.getId(), member.getFirstName(),
							member.getLastName(), member.getPseudo(), member.getPassword(), member.getIsAdmin(),
							member.getBirthDate(), member.getPhoneNumber(), member.getEmail(),
							member.getStreetAddress(), member.getPostalCode(), member.getCity(),
							context.getNbFakeBookings(), context.getNbDelays(), context.canBorrow(), context.canBook(),
							context.getLastSubscriptionDate());
					memberView.setVisible(true);
				}
			}
		});

	

		this.memberView.getProfileView().getValidateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ProfileView profileView = memberView.getProfileView();
					if (profileView.getFirstName().equals("") || profileView.getLastName().equals("")
							|| profileView.getPseudo().equals("") || profileView.getPassword().equals("")) {
						JOptionPane.showMessageDialog(null, TextView.get("profileMemberEmptyFieldsException"));
					} else {
						if (profileView.isCreatingMember()) {
							memberServices.addMember(profileView.getFirstName(), profileView.getLastName(),
									profileView.getPseudo(), profileView.getPassword(), profileView.getIsAdmin(),
									profileView.getBirthDate(), profileView.getPhoneNumber(), profileView.getEmail(),
									profileView.getStreetAddress(), profileView.getPostalCode(), profileView.getCity(),
									profileView.getNbDelays(), profileView.getNbFakeBookings(),
									profileView.getLastSubscriptionDate(), profileView.getCanBorrow(),
									profileView.getCanBook());
							JOptionPane.showMessageDialog(null, TextView.get("profileAddMemberConfirmation"));
						} else {
							memberContextServices.editMemberContext(profileView.getMemberContextID(),
									profileView.getNbDelays(), profileView.getNbFakeBookings(),
									profileView.getLastSubscriptionDate(), profileView.getCanBorrow(),
									profileView.getCanBook());
							memberServices.saveMember(profileView.getMemberID(), profileView.getFirstName(),
									profileView.getLastName(), profileView.getPseudo(), profileView.getPassword(),
									profileView.getIsAdmin(), profileView.getBirthDate(), profileView.getPhoneNumber(),
									profileView.getEmail(), profileView.getStreetAddress(), profileView.getPostalCode(),
									profileView.getCity());
							JOptionPane.showMessageDialog(null, TextView.get("profileEditMemberConfirmation"));
						}
						memberView.setVisible(false);
						refreshMemberList();
					}
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

		this.memberListView.getAddMemberButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberView.getProfileView().load(-1, -1, "", "", "", "", false, Calendar.getInstance().getTime(), "",
						"", "", "", "", 0, 0, true, true, Calendar.getInstance().getTime());
				memberView.setVisible(true);
			}
		});

		this.memberListView.getDeleteMemberButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showDeleteMemberConfirmation()) {
					JTable table = memberListView.getTable();
					int selectedRowIndex = table.getSelectedRow();
					if (selectedRowIndex > -1) {
						int memberID = (Integer) table.getModel().getValueAt(selectedRowIndex, 0);
						if (memberServices.canDeleteMember(memberID)) {
							int contextID = memberServices.getMember(memberID).getMemberContextID();
							memberServices.removeMember(memberID);
							memberContextServices.removeMemberContext(contextID);
							refreshMemberList();
						} else {
							JOptionPane.showMessageDialog(null, TextView.get("memberCantBeDeletedException"));
						}
					}
				}
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

	private void showInvalidFieldsException(NotValidNumberFieldException exception) {
		String text = TextView.get("invalidField") + "\"" + exception.getFieldName() + "\"" + ".\n"
				+ TextView.get("valueInInvalidField")
				+ ((exception.getFieldValue().equals("")) ? TextView.get("emptyValue")
						: "\"" + exception.getFieldValue() + "\" ")
				+ TextView.get("typeOfValidValue")
				+ ((exception.getFieldValue().equals("")) ? TextView.get("notEmptyValue") : exception.getFieldType())
				+ ".";
		JOptionPane.showMessageDialog(null, text);
	}

	private boolean showDeleteMemberConfirmation() {
		String text = TextView.get("memberConfirmDeleting");
		int result = JOptionPane.showConfirmDialog(null, text, "", JOptionPane.YES_OPTION);
		return (result == 0);
	}

}
