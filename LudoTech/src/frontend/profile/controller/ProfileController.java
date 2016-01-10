
package frontend.profile.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import backend.POJOs.Member;
import backend.services.MemberContextServices;
import backend.services.MemberServices;
import frontend.profile.view.ProfileView;
import frontend.utils.exceptions.NotValidNumberFieldException;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class ProfileController extends JPanel {

	private int currentMemberID;

	private MemberServices memberServices;
	private MemberContextServices memberContextServices;

	private ProfileView profileView;

	public ProfileController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.memberServices = new MemberServices();
		this.memberContextServices = new MemberContextServices();
		this.profileView = new ProfileView(memberServices.isAdmin(currentMemberID));
		this.makeGUI();
		this.makeListeners();
		this.loadMember();

	}

	private void makeGUI() {
		this.profileView = new ProfileView(memberServices.isAdmin(currentMemberID));
		this.add(this.profileView);

	}

	private void makeListeners() {
		this.profileView.getValidateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (profileView.getFirstName().equals("") || profileView.getLastName().equals("")
							|| profileView.getPseudo().equals("") || profileView.getPassword().equals("")) {
						JOptionPane.showMessageDialog(null, TextView.get("profileMemberEmptyFieldsException"));
					} else {
						memberServices.saveMember(profileView.getMemberID(), profileView.getFirstName(),
								profileView.getLastName(), profileView.getPseudo(), profileView.getPassword(),
								profileView.getIsAdmin(), profileView.getBirthDate(), profileView.getPhoneNumber(),
								profileView.getEmail(), profileView.getStreetAddress(), profileView.getPostalCode(),
								profileView.getCity());
						memberContextServices.editMemberContext(profileView.getMemberContextID(),
								profileView.getNbDelays(), profileView.getNbFakeBookings(),
								profileView.getLastSubscriptionDate(), profileView.getCanBorrow(),
								profileView.getCanBook());
						JOptionPane.showMessageDialog(null, TextView.get("profileEditMemberConfirmation"));
					}
				} catch (NotValidNumberFieldException exception) {
					showInvalidFieldsException(exception);
				}

			}
		});

		this.profileView.getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadMember();
			}
		});
	}

	private void loadMember() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Member member = memberServices.getMember(currentMemberID);
				profileView.load(member.getMemberID(), member.getMemberContext().getId(), member.getFirstName(),
						member.getLastName(), member.getPseudo(), member.getPassword(), member.getIsAdmin(),
						member.getBirthDate(), member.getPhoneNumber(), member.getEmail(), member.getStreetAddress(),
						member.getPostalCode(), member.getCity(), member.getMemberContext().getNbFakeBookings(),
						member.getMemberContext().getNbDelays(), member.getMemberContext().canBorrow(),
						member.getMemberContext().canBook(), member.getMemberContext().getLastSubscriptionDate());

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

	public void refreshData() {
		this.loadMember();
	}

}
