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
	
	public BookListView(BookListModel model)
	{
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

	}
}
