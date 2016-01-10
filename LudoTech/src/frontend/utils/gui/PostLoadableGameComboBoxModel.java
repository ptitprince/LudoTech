package frontend.utils.gui;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import backend.POJOs.Game;

@SuppressWarnings("serial")
public class PostLoadableGameComboBoxModel extends DefaultComboBoxModel<Game> {

	@Override
	public Game getSelectedItem() {
		return (Game) super.getSelectedItem();
	}
	
	public void loadData(List<Game> items) {
		super.removeAllElements();
		for (Game item : items) {
			super.addElement(item);
		}
	}

}
