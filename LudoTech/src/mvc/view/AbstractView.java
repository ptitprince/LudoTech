package mvc.view;

import javax.swing.JFrame;

import mvc.controller.AbstractController;
import observerPattern.Observer;

public abstract class AbstractView extends JFrame implements Observer {

	private AbstractController controller;

	public AbstractView(AbstractController controller) {
		this.controller = controller;
	}

	public void update(String str) {
		// Do something
	}

}
