package mvc.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mvc.controller.AbstractController;

public class SwingView extends AbstractView {

	private JPanel container = new JPanel();
	
	public SwingView(AbstractController controller) {
		super(controller);
		this.setSize(240, 260);
	    this.setTitle("LudoTech");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);               
	    this.setContentPane(this.container);
	    this.setVisible(true);
	}
	
}
