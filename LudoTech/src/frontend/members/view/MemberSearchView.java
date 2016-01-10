package frontend.members.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import frontend.LudoTechApplication;
import frontend.utils.gui.SpringUtilities;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class MemberSearchView extends JPanel {

	private JTextField firstName;
	private JTextField lastName;
	private JTextField pseudo;

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

		SpringUtilities.makeCompactGrid(searchPropertiesPanel, 3, 2, 6, 6, 6, 6);

		this.add(searchPropertiesPanel, BorderLayout.CENTER);

		this.searchButton = new JButton(TextView.get("validate"));
		this.add(this.searchButton, BorderLayout.SOUTH);
	}

	public JButton getSearchButton() {
		return this.searchButton;
	}

	public String getFirstNameValue() {
		return this.firstName.getText();
	}

	public String getLastNameValue() {
			return this.lastName.getText();
		}

	public String getPseudoValue() {
		return this.pseudo.getText();
	}

}
