package backend.exceptions;

import javax.swing.JOptionPane;

import frontend.utils.gui.TextView;

/**
 * Exception lorsque pour un emprunt ou une réservation, la date de début se situe chronologiquement après celle de fin
 */
@SuppressWarnings("serial")
public class DatesOrderException extends Exception {

	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, text);
	}
	
}
