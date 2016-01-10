package backend.exceptions;

import javax.swing.JOptionPane;

import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class MemberNbBooksException extends Exception {

	private String memberName;
	private int memberNbBooks;
	private int maxNbBooksPerMember;
	
	public MemberNbBooksException(String memberName, int memberNbBooks, int maxNbBooksPerMember) {
		this.memberName = memberName;
		this.memberNbBooks = memberNbBooks;
		this.maxNbBooksPerMember = maxNbBooksPerMember;
	}

	public String getMemberName() {
		return memberName;
	}

	public int getMemberNbBooks() {
		return memberNbBooks;
	}

	public int getMaxNbBooksPerMember() {
		return maxNbBooksPerMember;
	}
	
	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, String.format(text, this.maxNbBooksPerMember, this.memberName, this.memberNbBooks));
	}
	
}
