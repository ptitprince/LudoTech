package gui.book.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import gui.utils.TextView;
import model.POJOs.Book;
import model.services.BookServices;
import model.services.ItemServices;

@SuppressWarnings("serial")
public class BookListModel extends AbstractTableModel {
	private final String[] HEADERS = { "", "", "",
			TextView.get("bookGameName"),
			TextView.get("bookMemberName"), 
			TextView.get("bookStartDate"),
			TextView.get("bookEndDate"), 
			TextView.get("bookExtensionName") };

	private BookServices bookServices;
	private ItemServices itemServices;
	
	private List<Book> bookList;

	public BookListModel(BookServices bookServices,ItemServices itemServices) {
		this.bookServices=bookServices;
		this.itemServices=itemServices;
		this.bookList = new ArrayList<Book>();
	}

	public void refresh() {
		this.bookList = this.bookServices.getAll();
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
			int itemID = this.bookList.get(rowIndex).getItem().getItemID();
			return this.itemServices.getNameOfGame(itemID) ;
		case 4:
			return this.bookList.get(rowIndex).getMember().getFirstName()+ " " + this.bookList.get(rowIndex).getMember().getLastName();
		case 5:
			
			return this.bookList.get(rowIndex).getStartDate().toString();
		case 6:
			return this.bookList.get(rowIndex).getEndDate().toString();
		case 7:
			if( this.bookList.get(rowIndex).getExtension()!= null)
				{
				return this.bookList.get(rowIndex).getExtension().getName();
				}
			else return "";
			
		default:
			throw new IllegalArgumentException();
		}
	}

	

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		
		case 0:
		case 1: 
		case 2: return Integer.class;
		case 3:
		case 4:
		case 5:
		case 6:		
		case 7:
			return String.class;
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
