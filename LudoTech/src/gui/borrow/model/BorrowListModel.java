package gui.borrow.model;

import gui.utils.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.POJOs.Borrow;
import model.POJOs.Game;
import model.services.BorrowServices;
import model.services.GameServices;
import model.services.ItemServices;

public class BorrowListModel extends AbstractTableModel {
	private final String[] HEADERS = { "", "", "",
			TextView.get("borrowGameName"), TextView.get("borrowMemberName"),
			TextView.get("borrowBeginningDate"),
			TextView.get("borrowEndingDate"),
			TextView.get("borrowExtensionName") };

	private BorrowServices borrowServices;
	private ItemServices itemServices;

	private List<Borrow> borrowList;

	public BorrowListModel(BorrowServices borrowServices, ItemServices itemServices) {
		this.borrowServices = borrowServices;
		this.itemServices = itemServices;
		this.borrowList = new ArrayList<Borrow>();
	}

	public void refresh() {
		// this.borrowList = this.borrowServices.getBorrows();
		this.fireTableDataChanged();
	}

	public int getColumnCount() {
		return HEADERS.length;
	}

	public String getColumnName(int columnIndex) {
		return HEADERS[columnIndex];
	}

	public int getRowCount() {
		return this.borrowList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.borrowList.get(rowIndex).getItem().getItemID();
		case 1:
			return this.borrowList.get(rowIndex).getMember().getMemberID();
		case 2:
			return this.borrowList.get(rowIndex).getExtension()
					.getExtensionID();
		case 3:
			int itemID = this.borrowList.get(rowIndex).getItem().getItemID();
			return this.itemServices.getNameOfGame(itemID);
		case 4:
			return this.borrowList.get(rowIndex).getMember().getFirstName()
					+ " "
					+ this.borrowList.get(rowIndex).getMember().getLastName();
		case 5:
			return this.borrowList.get(rowIndex).getBeginningDate().toString();
		case 6:
			return this.borrowList.get(rowIndex).getEndingDate().toString();
		case 7:
			if (this.borrowList.get(rowIndex).getExtension() != null) {
				return this.borrowList.get(rowIndex).getExtension().getName();
			} else {
				return "";
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
			return String.class;
		default:
			return Object.class;
		}
	}
}
