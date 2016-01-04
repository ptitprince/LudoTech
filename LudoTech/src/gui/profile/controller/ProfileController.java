
package gui.profile.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.profile.view.ProfileView;
import model.POJOs.Member;
import model.services.MemberServices;

@SuppressWarnings("serial")
public class ProfileController extends JPanel {

	private int currentMemberID;
	
	private MemberServices memberServices;

	private ProfileView profileView;

	public ProfileController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.memberServices = new MemberServices();
		this.profileView = new ProfileView();
		this.makeGUI();
		this.makeListeners();
		this.loadMember();

	}

	public void makeGUI() {
		this.profileView = new ProfileView();
		this.add(this.profileView);

	}

	private void makeListeners() {
		this.profileView.getValidateButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*memberServices.saveMember(profileView.getFisrtName(),
						profileView.getLastName(),profileView.getPseudo(), profileView.getPassword(), profileView.getIsAdmin(), profileView.getBirthDate(),
						profileView.getEmail(),profileView.getStreetAddress(),profileView.getPostalCode(),
						profileView.getCity());*/
							
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
						member.getLastName(),member.getPseudo(), member.getIsAdmin(), member.getBirthDate(), member.getPhoneNumber(),
						member.getEmail(),member.getStreetAddress(),member.getPostalCode(),
						member.getCity());

			}
		});

	}

	
}


