package frontend.members.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import frontend.members.model.MemberListModel;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class MemberListView extends JPanel {

	private JTable table;

	private JButton addMemberButton;
	private JButton deleteMemberButton;

	public MemberListView(MemberListModel model) {
		this.setLayout(new BorderLayout());
		this.makeGUI(model);
	}

	private void makeGUI(MemberListModel model) {
		JLabel title = new JLabel(TextView.get("membersListTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		this.table = new JTable(model);
		// Autoriser le tri sur le tableau
		this.table.setAutoCreateRowSorter(true);
		// Cacher la colonne des identifiants
		this.table.removeColumn(table.getColumnModel().getColumn(0));
		this.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel actionsPanel = new JPanel();
		JLabel editGameTip = new JLabel(TextView.get("editLineTip"));
		actionsPanel.add(editGameTip);
		this.addMemberButton = new JButton(TextView.get("add"));
		actionsPanel.add(this.addMemberButton);
		this.deleteMemberButton = new JButton(TextView.get("delete"));
		actionsPanel.add(this.deleteMemberButton);
		this.add(actionsPanel, BorderLayout.SOUTH);

	}

	public JTable getTable() {
		return this.table;
	}

	public JButton getAddMemberButton() {
		return this.addMemberButton;
	}

	public JButton getDeleteMemberButton() {
		return this.deleteMemberButton;
	}
}
