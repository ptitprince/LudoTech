package backend.exceptions;

import java.util.Date;

import javax.swing.JOptionPane;

import frontend.utils.gui.TextView;

/**
 * Exception lorsque que pour un certain jeu il n'existe pas d'exemplaires
 * disponible (en comptant un qui restera toujours un en stock)
 */
@SuppressWarnings("serial")
public class NoneItemAvailableException extends Exception {

	private String gameName;
	private Date startDate;
	private Date endDate;

	public NoneItemAvailableException(String gameName, Date startDate, Date endDate) {
		this.gameName = gameName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getGameName() {
		return gameName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null,
				String.format(text, this.gameName, this.startDate.toLocaleString(), this.endDate.toLocaleString()));
	}

}
