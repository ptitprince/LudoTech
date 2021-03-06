package backend.exceptions;

import java.util.Date;

import javax.swing.JOptionPane;

import frontend.utils.gui.TextView;

/**
 * Exception lorsque l'intervalle entre la date du système et la date de début
 * d'un prêt ou d'une réservation ne respecte pas les paramètres de
 * l'application
 */
@SuppressWarnings("serial")
public class IntervalBetweenNowAndStartDateException extends Exception {

	private Date startDate;
	private int actualInterval;
	private int expectedInterval;

	public IntervalBetweenNowAndStartDateException(Date startDate, int actualInterval, int expectedInterval) {
		this.startDate = startDate;
		this.actualInterval = actualInterval;
		this.expectedInterval = expectedInterval;
	}

	public Date getStartDate() {
		return startDate;
	}

	public int getActualInterval() {
		return actualInterval;
	}

	public int getExpectedInterval() {
		return expectedInterval;
	}

	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null,
				String.format(text, this.expectedInterval, this.actualInterval, this.startDate.toLocaleString()));
	}

}
