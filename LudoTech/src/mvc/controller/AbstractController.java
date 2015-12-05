package mvc.controller;

import mvc.model.AbstractModel;

public abstract class AbstractController {

	protected AbstractModel model;

	public AbstractController(AbstractModel model) {
		this.model = model;
	}

}
