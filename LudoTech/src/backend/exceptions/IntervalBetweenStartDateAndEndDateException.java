package backend.exceptions;

import java.util.Date;

import javax.swing.JOptionPane;

import frontend.utils.gui.TextView;

public class IntervalBetweenStartDateAndEndDateException extends Exception {

	private Date startDate;
	private Date endDate;
	private int actualInterval;
	private int expectedInterval;
	
	public IntervalBetweenStartDateAndEndDateException(Date startDate, Date endDate, int actualInterval, int expectedInterval) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.actualInterval = actualInterval;
		this.expectedInterval = expectedInterval;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public int getActualInterval() {
		return actualInterval;
	}
	public int getExpectedInterval() {
		return expectedInterval;
	}
	
	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, String.format(text, this.expectedInterval, this.startDate.toLocaleString(), this.endDate.toLocaleString(), this.actualInterval));
	}
	
}
