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
import model.services.MemberServices;

@SuppressWarnings("serial")
public class BookController extends JPanel {

	private BookServices bookServices;

	private ItemServices itemServices;
	private ExtensionServices extensionServices;
	private BookListView bookListView;
	private MemberServices memberServices;
	private BookListModel bookListModel;
	private int currentMemberID;
	
	public BookController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.memberServices = new MemberServices();
		this.bookServices = new BookServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new ExtensionServices();
		this.bookListModel = new BookListModel(this.bookServices, this.itemServices);

		this.setLayout(new BorderLayout());
		this.makeGUI();
		
	}

	public void makeGUI() {

		this.bookListView = new BookListView(this.bookListModel,this.memberServices.isAdmin(currentMemberID));

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
