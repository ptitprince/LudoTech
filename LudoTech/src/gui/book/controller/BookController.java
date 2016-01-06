package gui.book.controller;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import gui.LudoTechApplication;
import gui.book.model.BookListModel;
import gui.book.view.BookListView;

import gui.utils.TextView;
import model.POJOs.Game;
import model.services.BookServices;
import model.services.ExtensionServices;
import model.services.GameServices;
import model.services.ItemServices;

@SuppressWarnings("serial")
public class BookController extends JPanel {

	private BookServices bookServices;

	private ItemServices itemServices;
	private ExtensionServices extensionServices;
	private BookListView bookListView;

	private BookListModel bookListModel;

	public BookController() {
		this.bookServices = new BookServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new ExtensionServices();
		this.bookListModel = new BookListModel(this.bookServices, this.itemServices);

		this.setLayout(new BorderLayout());
		this.makeGUI();
		
	}

	public void makeGUI() {

		this.bookListView = new BookListView(this.bookListModel);

		this.add(bookListView, BorderLayout.CENTER);

	}

	private void makeListeners() {

	}

	public void refreshBookList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bookListModel.refresh();
			}
		});

	}

}
