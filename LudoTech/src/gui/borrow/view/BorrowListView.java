package gui.borrow.view;

import java.awt.BorderLayout;
import java.awt.Font;

import gui.borrow.model.BorrowListModel;
import gui.utils.TextView;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BorrowListView extends JPanel{
	
	private JTable table;
	private JButton addBorrowButton;
	private JButton deleteBorrowButton;
	
	public BorrowListView(BorrowListModel borrowModel) {
		this.setLayout(new BorderLayout());
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
	}

}