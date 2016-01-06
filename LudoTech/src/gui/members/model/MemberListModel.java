package gui.members.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import gui.utils.TextView;
import model.POJOs.Member;
import model.services.MemberServices;

@SuppressWarnings("serial")
public class MemberListModel extends AbstractTableModel{

	private final String[] HEADERS = { "", TextView.get("lastName"), TextView.get("firstName"), TextView.get("pseudo"), TextView.get("birthDate"), TextView.get("phoneNumber"), TextView.get("canBorrow"),
			TextView.get("lastSubscriptionDate") };
	
	private MemberServices memberServices;
	
	private List<Member> memberList;
	
	public MemberListModel(MemberServices memberServices) {
		this.memberServices = memberServices;
		this.memberList = new ArrayList<Member>();
	}
	
	public void refresh() {
		this.memberList = this.memberServices.getMemberList();
		this.fireTableDataChanged();
	}
	
	public int getColumnCount() {
		return HEADERS.length;
	}
	
	public String getColumnName(int columnIndex) {
		return HEADERS[columnIndex];
	}
	
	public int getRowCount() {
		return this.memberList.size();
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.memberList.get(rowIndex).getMemberID();
		case 1:
			return this.memberList.get(rowIndex).getLastName();
		case 2:
			return this.memberList.get(rowIndex).getFirstName();
		case 3:
			return this.memberList.get(rowIndex).getPseudo();
		case 4:
			return this.memberList.get(rowIndex).getBirthDate();
		case 5:
			return this.memberList.get(rowIndex).getPhoneNumber();
		case 6:
			return this.memberList.get(rowIndex).getMemberContext().canBorrow();
		case 7:
			return this.memberList.get(rowIndex).getMemberContext().getLastSubscriptionDate();
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
		case 5:
			return String.class;
		case 0:
			return Integer.class;
		case 4:
		case 7:
			return Date.class;
		case 6:
			return Boolean.class;
		default:
			return Object.class;
		}
	}

	
	
}
