package gui.book.controller;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import gui.LudoTechApplication;
import gui.book.model.BookListModel;
import gui.book.view.BookListView;
import gui.book.view.BookSearchView;
import model.services.BookServices;
import model.services.ItemServices;

@SuppressWarnings("serial")
public class BookController extends JPanel {
	
	private BookServices bookServices;
	private ItemServices itemServices;
	
	private BookSearchView bookSearchView;
	private BookListView bookListView;
	
	private BookListModel bookListModel;

	public BookController() {
		this.bookServices = new BookServices();
		this.itemServices = new ItemServices();
		this.bookListModel= new BookListModel(this.bookServices,this.itemServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}
	
	public void makeGUI() {
		this.bookSearchView = new BookSearchView();
		this.bookListView = new BookListView(this.bookListModel);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.bookSearchView,
				this.bookListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH / 4);
		this.add(splitPane, BorderLayout.CENTER);
	}
	
	public void refreshBookList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bookListModel.refresh();
			}
		});
	}
}
