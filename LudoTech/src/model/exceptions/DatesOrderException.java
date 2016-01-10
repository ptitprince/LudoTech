package model.exceptions;

import javax.swing.JOptionPane;

import gui.utils.TextView;

public class DatesOrderException extends Exception {

	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, text);
	}
	
}
