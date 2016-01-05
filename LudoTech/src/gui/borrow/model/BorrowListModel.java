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

public class BorrowListModel extends AbstractTableModel {
	private final String[] HEADERS = { TextView.get("gameID"), TextView.get("gameName"), TextView.get("gameCategory"),
			TextView.get("gameEditor"), TextView.get("gamePublishingYear"), TextView.get("gameMinAge"),
			TextView.get("gamePlayers"), TextView.get("gameAvailable") };

	private BorrowServices borrowServices;

	private List<Borrow> borrowList;

	public BorrowListModel(BorrowServices borrowServices) {
		this.borrowServices = borrowServices;
		this.borrowList = new ArrayList<Borrow>();
	}

	public void refresh() {
		//this.borrowList = this.borrowServices.getBorrows();
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
			return this.borrowList.get(rowIndex).getItem().getItemID() ;
		case 1:
			return this.borrowList.get(rowIndex).getMember().getMemberID();
		case 2:
			return this.borrowList.get(rowIndex).getExtension().getExtensionID();
		case 3:
			return "";
		case 4:
			return this.borrowList.get(rowIndex).getMember().getFirstName() + this.borrowList.get(rowIndex).getMember().getLastName();
		case 5:
			return this.borrowList.get(rowIndex).getBeginningDate();
		case 6:
			return this.borrowList.get(rowIndex).getEndingDate();
		case 7:
			return this.borrowList.get(rowIndex).getExtension().getName();
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
}
