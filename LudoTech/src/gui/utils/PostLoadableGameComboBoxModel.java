package gui.utils;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import model.POJOs.Game;

@SuppressWarnings("serial")
public class PostLoadableGameComboBoxModel extends DefaultComboBoxModel<Game> {

	@Override
	public Game getSelectedItem() {
		return (Game) super.getSelectedItem();
	}
	
	public void loadData(List<Game> items) {
		for (Game item : items) {
			super.addElement(item);
		}
	}

}
