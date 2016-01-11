package frontend.borrow.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.table.AbstractTableModel;

import backend.POJOs.Borrow;
import backend.services.BorrowServices;
import backend.services.ItemServices;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class BorrowListModel extends AbstractTableModel {
	private final String[] HEADERS = { "", "", "",
			TextView.get("borrowGame"), 
			TextView.get("borrowMember"),
			TextView.get("borrowBeginningDate"),
			TextView.get("borrowEndingDate"),
			TextView.get("borrowExtension"),
			TextView.get("borrowState"),
			TextView.get("borrowDurationBetweenTodayAndEstimatedEndDate")};

	private BorrowServices borrowServices;
	private ItemServices itemServices;

	private List<Borrow> borrowList;

	public BorrowListModel(BorrowServices borrowServices, ItemServices itemServices) {
		this.borrowServices = borrowServices;
		this.itemServices = itemServices;
		this.borrowList = new ArrayList<Borrow>();
	}

	public void refresh() {
		this.borrowList = this.borrowServices.getAll();
		this.fireTableDataChanged();
	}
	
	public void refreshForSimpleUser(int currentMemberID) {
		this.borrowList = this.borrowServices.getAllAccordingToUserID(currentMemberID);
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
			return sdf.format(this.borrowList.get(rowIndex).getStartDate());
		case 6:
			return sdf.format(this.borrowList.get(rowIndex).getEndDate());
		case 7:
			if (this.borrowList.get(rowIndex).getExtension() != null) {
				return this.borrowList.get(rowIndex).getExtension().getName();
			} else {
				return "";
			}
		case 8 :
			try {
				Date currentDate = sdf.parse(sdf.format(new Date())); // Astuce pour récupérer que la partie jour, mois, année d'une date
				if (this.borrowList.get(rowIndex).getEndDate().after(currentDate)) {
					return TextView.get("borrowOngoing");
				} else if (this.borrowList.get(rowIndex).getEndDate().before(currentDate)) {
					return TextView.get("borrowInLate");
				} else {
					return TextView.get("borrowStanding");
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return "";
			}
		case 9 :
			long diffValue = this.borrowList.get(rowIndex).getEndDate().getTime() - new Date().getTime();
			long diffDays = TimeUnit.DAYS.convert(diffValue, TimeUnit.MILLISECONDS);
			if (diffDays == 0) {
				return TextView.get("borrowToReturnToday");
			} else if (diffDays > 0) {
				return TextView.get("borrowToReturnInXDays") + (diffDays + 1) + " " + TextView.get("borrowToReturnDays");
			} else {
				return TextView.get("borrowToReturnXDaysAgo") + Math.abs(diffDays) + " " + TextView.get("borrowToReturnDays");
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
		case 9:
			return String.class;
		default:
			return Object.class;
		}
	}
	
}
