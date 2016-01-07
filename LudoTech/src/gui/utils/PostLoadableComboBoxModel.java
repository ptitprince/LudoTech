package gui.utils;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class PostLoadableComboBoxModel extends DefaultComboBoxModel<String> {

	@Override
	public String getSelectedItem() {
		return (String) super.getSelectedItem();
	}
	
	public void loadData(List<String> items) {
		super.removeAllElements();
		for (String item : items) {
			super.addElement(item);
		}
	}

}
