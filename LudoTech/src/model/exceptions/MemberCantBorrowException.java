package model.exceptions;

import javax.swing.JOptionPane;

import gui.utils.TextView;

public class MemberCantBorrowException extends Exception {

	private String memberName;
	
	public MemberCantBorrowException(String memberName) {
		this.memberName = memberName;
	}
	
	public String getMemberName() {
		return this.memberName;
	}
	
	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, String.format(text, this.memberName));
	}
	
}
