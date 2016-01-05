package gui.book.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import gui.utils.TextView;
import model.POJOs.Book;
import model.POJOs.Game;
import model.services.BookServices;
import model.services.GameServices;

public class BookListModel extends AbstractTableModel {
	private final String[] HEADERS = { TextView.get("gameID"), TextView.get("gameName"), TextView.get("gameCategory"),
			TextView.get("gameEditor"), TextView.get("gamePublishingYear"), TextView.get("gameMinAge"),
			TextView.get("gamePlayers"), TextView.get("gameAvailable") };

	private BookServices bookServices;

	private List<Book> bookList;

	public BookListModel(BookServices bookServices) {
		this.setBookServices(bookServices);
		this.bookList = new ArrayList<Book>();
	}

	public void refresh() {
		//this.bookList = this.bookServices.getBookList();
		this.fireTableDataChanged();
	}

	public int getColumnCount() {
		return HEADERS.length;
	}

	public String getColumnName(int columnIndex) {
		return HEADERS[columnIndex];
	}

	public int getRowCount() {
		return this.bookList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.bookList.get(rowIndex).getItem().getItemID();
		case 1:
			return this.bookList.get(rowIndex).getMember().getMemberID();
		case 2:
			return this.bookList.get(rowIndex).getExtension().getExtensionID();
		case 3:
			return "";
		case 4:
			return this.bookList.get(rowIndex).getMember().getLastName();
		case 5:
			
			return this.bookList.get(rowIndex).getStartDate();
		case 6:
			return this.bookList.get(rowIndex).getEndDate();
		case 7:
			return this.bookList.get(rowIndex).getExtension().getExtensionID();
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			return String.class;
		case 0:
			return Integer.class;
		case 7:
			return Boolean.class;
		default:
			return Object.class;
		}
	}

	public BookServices getBookServices() {
		return bookServices;
	}

	public void setBookServices(BookServices bookServices) {
		this.bookServices = bookServices;
	}


}
