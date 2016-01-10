package frontend.utils.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EmptyPanel extends JPanel {

	public EmptyPanel() {
		this.makeGUI();
	}
	
	public void makeGUI() {
		this.add(new JLabel(TextView.get("emptyPanelText")));
	}
}
