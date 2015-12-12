package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import gui.utils.TextView;

@SuppressWarnings("serial")
public class LudoTechApplication extends JFrame {

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 500;

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

	public void makeGUI() {
		this.getContentPane().add(this.mainController, BorderLayout.CENTER);
	}

}
