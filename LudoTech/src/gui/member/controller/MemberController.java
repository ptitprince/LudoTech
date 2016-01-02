
package gui.member.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.member.view.MemberView;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.services.MemberServices;

@SuppressWarnings("serial")
public class MemberController extends JPanel {


	
	private MemberServices memberServices;

	private MemberView memberView;

	public MemberController() {
		this.memberServices = new MemberServices();
		this.memberView = new MemberView();
		this.makeGUI();
		this.makeListeners();
		this.loadMember();

	}

	public void makeGUI() {
		this.memberView = new MemberView();
		this.add(this.memberView);

	}

	private void makeListeners() {
		this.memberView.getValidateButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						memberServices.saveAllMember(memberView.getFisrtName(),
						memberView.getLastName(),memberView.getPseudo(), memberView.getPassword(), memberView.getIsAdmin(), memberView.getBirthDate(),
						memberView.getEmail(),memberView.getStreetAddress(),memberView.getPostalCode(),
						memberView.getCity());
							
					}
				});
		
		this.memberView.getCancelButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						loadMember();
					}
				});
	}

	private void loadMember() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				memberView.load(memberServices.getAllMember());

			}
		});

	}

	
}


