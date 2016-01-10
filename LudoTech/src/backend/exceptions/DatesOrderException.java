package backend.exceptions;

import javax.swing.JOptionPane;

import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class DatesOrderException extends Exception {

	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, text);
	}
	
}
