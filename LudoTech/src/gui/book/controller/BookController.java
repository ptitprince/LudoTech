package gui.book.controller;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import gui.LudoTechApplication;
import gui.book.view.BookListView;
import gui.book.view.BookSearchView;
import gui.catalog.view.GameListView;
import gui.catalog.view.GameSearchView;
import gui.catalog.view.GameView;
import model.services.BookServices;

public class BookController extends JPanel {
	
	private BookServices bookservices;
	private BookSearchView bookSearchView;
	private BookListView bookListView;

	public BookController() {
		
		this.bookservices = new BookServices();
		this.setLayout(new BorderLayout());
		this.makeGUI();
		
	
	}
	
	public void makeGUI() {
		this.bookSearchView = new BookSearchView();
		this.bookListView = new BookListView();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.bookSearchView,
				this.bookListView);
		splitPane.setDividerLocation(LudoTechApplication.WINDOW_WIDTH / 4);
		this.add(splitPane, BorderLayout.CENTER);

		
	}
}
