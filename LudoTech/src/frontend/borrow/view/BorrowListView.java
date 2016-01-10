package frontend.borrow.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import frontend.borrow.model.BorrowListModel;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class BorrowListView extends JPanel{
	
	private JTable table;
	private JButton addBorrowButton;
	private JButton receiveBorrowButton;
	
	private boolean currentMemberIsAdmin;
	
	public BorrowListView(BorrowListModel borrowModel, boolean currentMemberIsAdmin) {
		this.setLayout(new BorderLayout());
		this.currentMemberIsAdmin = currentMemberIsAdmin;
		this.makeGUI(borrowModel);
		
	}

	private void makeGUI(BorrowListModel borrowModel) {
		JLabel title = new JLabel(TextView.get("borrowListTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		this.table = new JTable(borrowModel);
		// Autoriser le tri sur le tableau
		this.table.setAutoCreateRowSorter(true);
		// Cacher les 3 premières colonnes des identifiants (lors de la suppression de la 1ere, la 2nd prend sa place à l'indice 0)
		this.table.removeColumn(table.getColumnModel().getColumn(0)); 
		this.table.removeColumn(table.getColumnModel().getColumn(0));
		this.table.removeColumn(table.getColumnModel().getColumn(0)); 
		
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		if (this.currentMemberIsAdmin) {
			JPanel actionsPanel = new JPanel();
			this.addBorrowButton = new JButton(TextView.get("add"));
			actionsPanel.add(this.addBorrowButton);
			this.receiveBorrowButton = new JButton(TextView.get("borrowReceive"));
			actionsPanel.add(this.receiveBorrowButton);
			this.add(actionsPanel, BorderLayout.SOUTH);
		}
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public JButton getAddBorrowButton() {
		return this.addBorrowButton;
	}
	
	public JButton getReceiveBorrowButton() {
		return this.receiveBorrowButton;
	}
}
