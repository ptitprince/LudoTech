package backend.exceptions;

import javax.swing.JOptionPane;

import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class ExtensionNotAvailableException extends Exception {

	private String extentionName;

	public ExtensionNotAvailableException(String extentionName) {
		this.extentionName = extentionName;
	}

	public String getExtentionName() {
		return extentionName;
	}
	
	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, String.format(text, this.extentionName));
	}
}
