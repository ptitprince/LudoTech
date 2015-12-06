package gui.utils;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EmptyPanel extends JPanel {

	public EmptyPanel() {
		this.makeGUI();
	}
	
	public void makeGUI() {
		this.add(new JLabel("Just an empty panel view !"));
	}
}
