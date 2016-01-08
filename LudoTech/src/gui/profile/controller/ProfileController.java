
package gui.profile.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.profile.view.ProfileView;
import gui.utils.TextView;
import gui.utils.exceptions.NotValidNumberFieldException;
import model.POJOs.Member;
import model.services.MemberContextServices;
import model.services.MemberServices;

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

	public void makeGUI() {
		this.profileView = new ProfileView(memberServices.isAdmin(currentMemberID));
		this.add(this.profileView);

	}

	private void makeListeners() {
		this.profileView.getValidateButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							memberServices.saveMember(currentMemberID, profileView.getFirstName(),
								profileView.getLastName(),profileView.getPseudo(), profileView.getPassword(), profileView.getIsAdmin(), profileView.getBirthDate(), profileView.getPhoneNumber(),
								profileView.getEmail(),profileView.getStreetAddress(),profileView.getPostalCode(),
								profileView.getCity());
							memberContextServices.editMemberContext(currentMemberID, profileView.getNbDelays(), profileView.getNbFakeBookings(), profileView.getLastSubscriptionDate(), profileView.getCanBorrow(), profileView.getCanBook());
							String text = TextView.get("profileEditMemberConfirmation");
							String title = TextView.get("profileEditMemberException");
							JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);	
						} catch (ParseException e1) {
							showInvalidDateException();	
						} catch (NotValidNumberFieldException exception) {
							showInvalidFieldsException(exception);
						}
						
					}
				});
		
		this.profileView.getCancelButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						loadMember();
					}
				});
	}

	private void loadMember() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Member member = memberServices.getMember(currentMemberID);
				profileView.load(member.getFirstName(),
						member.getLastName(),member.getPseudo(), member.getPassword(), member.getIsAdmin(), member.getBirthDate(), member.getPhoneNumber(),
						member.getEmail(),member.getStreetAddress(),member.getPostalCode(),
						member.getCity(), member.getMemberContext().getNbFakeBookings(), member.getMemberContext().getNbDelays(), member.getMemberContext().canBook(), member.getMemberContext().canBorrow(), member.getMemberContext().getLastSubscriptionDate());

			}
		});

	}

	public void showInvalidDateException() {
		String text = TextView.get("profileEditMemberDateFormatException");
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


