package gui.book.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gui.book.model.BookListModel;
import gui.utils.TextView;

public class BookListView extends JPanel{

	private JTable table;

	private JButton addBookButton;
	private JButton deleteBookButton;
	private JButton fromBookToBorrowButton;
	private boolean currentMemberIsAdmin;
	public BookListView(BookListModel model, boolean currentMemberIsAdmin)
	{
		this.currentMemberIsAdmin = currentMemberIsAdmin;
		this.setLayout(new BorderLayout());
		this.makeGUI(model);
	}

	private void makeGUI(BookListModel model) {
		JLabel title = new JLabel(TextView.get("bookListTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		this.table = new JTable(model);
		// Autoriser le tri sur le tableau
		this.table.setAutoCreateRowSorter(true);
		// Cacher la colonne des identifiants
		this.table.removeColumn(table.getColumnModel().getColumn(0));
		this.table.removeColumn(table.getColumnModel().getColumn(0));
		this.table.removeColumn(table.getColumnModel().getColumn(0));
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel actionsPanel = new JPanel();
		this.addBookButton = new JButton(TextView.get("add"));
		actionsPanel.add(this.addBookButton);
		this.deleteBookButton = new JButton(TextView.get("delete"));
		actionsPanel.add(this.deleteBookButton);
		this.add(actionsPanel, BorderLayout.SOUTH);
		
		if (this.currentMemberIsAdmin) {
			this.fromBookToBorrowButton = new JButton(TextView.get("Change"));
			actionsPanel.add(this.fromBookToBorrowButton);
		}

	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JButton getAddBookButton() {
		return addBookButton;
	}

	public void setAddBookButton(JButton addBookButton) {
		this.addBookButton = addBookButton;
	}

	public JButton getDeleteBookButton() {
		return deleteBookButton;
	}

	public void setDeleteBookButton(JButton deleteBookButton) {
		this.deleteBookButton = deleteBookButton;
	}
}
