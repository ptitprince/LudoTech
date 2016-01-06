package gui.membersList.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdatepicker.impl.JDatePickerImpl;

import gui.LudoTechApplication;
import gui.utils.PostLoadableComboBoxModel;
import gui.utils.SpringUtilities;
import gui.utils.TextView;

@SuppressWarnings("serial")
public class MemberSearchView extends JPanel {

	private JTextField firstName;
	private JTextField lastName;
	private JTextField pseudo;
	private JCheckBox isAdmin;
	private JButton validateButton;
	private JButton cancelButton;
	
	private JButton searchButton;
	
	public MemberSearchView() {
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}
		
		private void makeGUI() {
			JLabel title = new JLabel(TextView.get("membersSearchTitle"));
			Font police = new Font("Arial", Font.BOLD, 16);
			title.setFont(police);
			title.setHorizontalAlignment(JLabel.CENTER);
			this.add(title, BorderLayout.NORTH);

			JPanel searchPropertiesPanel = new JPanel(new SpringLayout());

			// prénom
			JLabel firstNameLabel = new JLabel(TextView.get("firstName"));
			searchPropertiesPanel.add(firstNameLabel);
			this.firstName = new JTextField();
			this.firstName.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
			firstNameLabel.setLabelFor(this.firstName);
			searchPropertiesPanel.add(this.firstName);

			// nom
			JLabel lastNameLabel = new JLabel(TextView.get("lastName"));
			searchPropertiesPanel.add(lastNameLabel);
			this.lastName = new JTextField();
			this.lastName.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
			lastNameLabel.setLabelFor(this.lastName);
			searchPropertiesPanel.add(this.lastName);
			
			// pseudo
			JLabel pseudoLabel = new JLabel(TextView.get("pseudo"));
			searchPropertiesPanel.add(pseudoLabel);
			this.pseudo = new JTextField();
			this.pseudo.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
			pseudoLabel.setLabelFor(this.pseudo);
			searchPropertiesPanel.add(this.pseudo);
			
			// isAdmin
			JLabel isAdminLabel = new JLabel(TextView.get("isAdmin"));
			searchPropertiesPanel.add(isAdminLabel);
			this.isAdmin = new JCheckBox();
			this.isAdmin.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
			isAdminLabel.setLabelFor(this.isAdmin);
			searchPropertiesPanel.add(this.isAdmin);


			SpringUtilities.makeCompactGrid(searchPropertiesPanel, 4, 2, 6, 6, 6, 6);

			this.add(searchPropertiesPanel, BorderLayout.CENTER);

			this.searchButton = new JButton(TextView.get("validate"));
			this.add(this.searchButton, BorderLayout.SOUTH);
		}

		
		public JButton getSearchButton() {
			return this.searchButton;
		}
		
		public void resetFields() {
			this.firstName.setText("");
			this.lastName.setText("");
			this.pseudo.setText("");
			this.isAdmin.setSelected(false);
		}
}
