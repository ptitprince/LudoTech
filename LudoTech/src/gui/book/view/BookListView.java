package gui.book.view;

import java.awt.BorderLayout;
import java.awt.Font;

import gui.book.model.BookListModel;
import gui.utils.TextView;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class BookListView extends JPanel{
	
	private JTable table;
	private JButton addBookButton;
	private JButton cancelBookButton;
	private JButton comeGetGameButton;
	
	private boolean currentMemberIsAdmin;
	
	public BookListView(BookListModel bookModel, boolean currentMemberIsAdmin) {
		this.setLayout(new BorderLayout());
		this.currentMemberIsAdmin = currentMemberIsAdmin;
		this.makeGUI(bookModel);
		
	}

	private void makeGUI(BookListModel bookModel) {
		JLabel title = new JLabel(TextView.get("bookListTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		this.table = new JTable(bookModel);
		// Autoriser le tri sur le tableau
		this.table.setAutoCreateRowSorter(true);
		// Cacher les 3 premières colonnes des identifiants (lors de la suppression de la 1ere, la 2nd prend sa place à l'indice 0)
		this.table.removeColumn(table.getColumnModel().getColumn(0)); 
		this.table.removeColumn(table.getColumnModel().getColumn(0));
		this.table.removeColumn(table.getColumnModel().getColumn(0)); 
		
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel actionsPanel = new JPanel();
		this.addBookButton = new JButton(TextView.get("bookAdd"));
		actionsPanel.add(this.addBookButton);
		this.cancelBookButton = new JButton(TextView.get("bookCancel"));
		actionsPanel.add(this.cancelBookButton);
		if (this.currentMemberIsAdmin) {
			this.comeGetGameButton = new JButton(TextView.get("bookComeGet"));
			actionsPanel.add(this.comeGetGameButton);
		}
		this.add(actionsPanel, BorderLayout.SOUTH);
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public JButton getAddBookButton() {
		return this.addBookButton;
	}
	
	public JButton getComeGetGameButton() {
		return this.comeGetGameButton;
	}
	
	public JButton getCancelBookButton() {
		return this.cancelBookButton;
	}
}
