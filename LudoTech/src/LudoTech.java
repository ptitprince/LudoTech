import mvc.controller.AbstractController;
import mvc.controller.SwingController;
import mvc.model.AbstractModel;
import mvc.model.ServicesModel;
import mvc.view.AbstractView;
import mvc.view.SwingView;

public class LudoTech {

	public static void main(String[] args) {
		AbstractModel model = new ServicesModel();
		AbstractController controller = new SwingController(model);
		AbstractView view = new SwingView(controller);
		model.addObserver(view);
	}
}
