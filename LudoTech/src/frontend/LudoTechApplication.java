package frontend;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class LudoTechApplication extends JFrame {

	public static final int WINDOW_WIDTH = 1000;
	public static final int WINDOW_HEIGHT = 600;

	private MainController mainController;

	public LudoTechApplication() {
		this.mainController = new MainController();
	}
	
	public void run() {
		this.setTitle(TextView.get("applicationTitle"));
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);

		this.makeGUI();
		
		this.setVisible(true);
	}

	private void makeGUI() {
		this.getContentPane().add(this.mainController, BorderLayout.CENTER);
	}

}
