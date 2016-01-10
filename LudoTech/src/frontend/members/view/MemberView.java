package frontend.members.view;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import frontend.LudoTechApplication;
import frontend.profile.view.ProfileView;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class MemberView extends JDialog {
	
	private static final double WINDOW_RATIO = 1.10;
	
	private ProfileView profileView;
	
	public MemberView(){
		
		this.setTitle(TextView.get("membersMemberPopupTitle"));
		this.setSize((int) (LudoTechApplication.WINDOW_WIDTH / WINDOW_RATIO),
				(int) (LudoTechApplication.WINDOW_HEIGHT / WINDOW_RATIO));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		makeGUI();
	}

	private void makeGUI(){
		
		this.profileView = new ProfileView(true); // est vrai car ce panneau n'est accessible qu'aux admins
		this.add(profileView, BorderLayout.CENTER);
		
	}
	
	public ProfileView getProfileView(){
		return this.profileView;
	}
}
