package frontend.utils.gui;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import backend.POJOs.Extension;

@SuppressWarnings("serial")
public class PostLoadableExtensionComboBoxModel extends DefaultComboBoxModel<Extension> {

	@Override
	public Extension getSelectedItem() {
		return (Extension) super.getSelectedItem();
	}
	
	public void loadData(List<Extension> extensions) {
		super.removeAllElements();
		for (Extension extension : extensions) {
			super.addElement(extension);
		}
	}

}
