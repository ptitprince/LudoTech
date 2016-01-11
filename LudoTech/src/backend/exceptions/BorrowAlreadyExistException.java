package backend.exceptions;

import javax.swing.JOptionPane;

import backend.POJOs.Borrow;
import frontend.utils.gui.TextView;

/**
 * Exception lorsqu'un emprunt existe déjà
 */
@SuppressWarnings("serial")
public class BorrowAlreadyExistException extends Exception {

	private Borrow borrow;

	public BorrowAlreadyExistException(Borrow borrow) {
		this.borrow = borrow;
	}

	public Borrow getBook() {
		return borrow;
	}
	
	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, String.format(text, this.borrow.getMember().getFirstName() + " " + this.borrow.getMember().getLastName(), this.borrow.getStartDate().toLocaleString()));
	}
	
}
