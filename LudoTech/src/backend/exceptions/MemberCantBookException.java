package backend.exceptions;

import javax.swing.JOptionPane;

import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class MemberCantBookException extends Exception {

	private String memberName;
	
	public MemberCantBookException(String memberName) {
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
