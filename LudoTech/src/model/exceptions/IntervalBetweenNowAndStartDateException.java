package model.exceptions;

import java.util.Date;

import javax.swing.JOptionPane;

import gui.utils.TextView;

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
		JOptionPane.showMessageDialog(null, String.format(text, this.expectedInterval, this.actualInterval, this.startDate.toLocaleString()));
	}
	
}
