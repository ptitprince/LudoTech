package gui.book.model;

import gui.utils.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.table.AbstractTableModel;

import model.POJOs.Book;
import model.services.BookServices;
import model.services.ItemServices;

@SuppressWarnings("serial")
public class BookListModel extends AbstractTableModel {
	private final String[] HEADERS = { "", "", "",
			TextView.get("bookGame"), 
			TextView.get("bookMember"),
			TextView.get("bookBeginningDate"),
			TextView.get("bookEndingDate"),
			TextView.get("bookExtension"),
			TextView.get("bookDurationBetweenTodayAndEstimatedStartDate")};

	private BookServices bookServices;
	private ItemServices itemServices;

	private List<Book> bookList;

	public BookListModel(BookServices bookServices, ItemServices itemServices) {
		this.bookServices = bookServices;
		this.itemServices = itemServices;
		this.bookList = new ArrayList<Book>();
	}

	public void refresh() {
		this.bookList = this.bookServices.getAll();
		this.fireTableDataChanged();
	}
	
	public void refreshForSimpleUser(int currentMemberID) {
		this.bookList = this.bookServices.getAllAccordingToUserID(currentMemberID);
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		switch (columnIndex) {
		case 0:
			return this.bookList.get(rowIndex).getItem().getItemID();
		case 1:
			return this.bookList.get(rowIndex).getMember().getMemberID();
		case 2:
			return this.bookList.get(rowIndex).getExtension()
					.getExtensionID();
		case 3:
			int itemID = this.bookList.get(rowIndex).getItem().getItemID();
			return this.itemServices.getNameOfGame(itemID);
		case 4:
			return this.bookList.get(rowIndex).getMember().getFirstName()
					+ " "
					+ this.bookList.get(rowIndex).getMember().getLastName();
		case 5:
			return sdf.format(this.bookList.get(rowIndex).getStartDate());
		case 6:
			return sdf.format(this.bookList.get(rowIndex).getEndDate());
		case 7:
			if (this.bookList.get(rowIndex).getExtension() != null) {
				return this.bookList.get(rowIndex).getExtension().getName();
			} else {
				return "";
			}
		case 8 :
			long diffValue = this.bookList.get(rowIndex).getStartDate().getTime() - new Date().getTime();
			long diffDays = TimeUnit.DAYS.convert(diffValue, TimeUnit.MILLISECONDS);
			if (diffDays == 0) {
				return TextView.get("bookToComeGetToday");
			} else if (diffDays > 0) {
				return TextView.get("bookToComeGetInXDays") + (diffDays + 1) + " " + TextView.get("bookToComeGetDays");
			} else {
				return TextView.get("bookToComeGetXDaysAgo") + Math.abs(diffDays) + " " + TextView.get("bookToComeGetDays");
			}
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
			return Integer.class;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return String.class;
		default:
			return Object.class;
		}
	}
	
}
