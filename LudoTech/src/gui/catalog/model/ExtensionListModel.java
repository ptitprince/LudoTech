package gui.catalog.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import model.POJOs.Extension;
import model.services.ExtensionServices;

public class ExtensionListModel implements ListModel<String> {

	private ExtensionServices extensionServices;
	private List<Extension> extensionList;
	private List<ListDataListener> listeners;
	
	public ExtensionListModel(ExtensionServices extensionServices) {
		this.extensionServices = extensionServices;
		this.extensionList = new ArrayList<Extension>();
		this.listeners = new ArrayList<ListDataListener>();
	}
	
	public void refresh(int gameID) {
		this.extensionList = this.extensionServices.getExtensionList(gameID);
		for (ListDataListener listener : this.listeners) {
			listener.contentsChanged(null);
		}
	}

	public String getElementAt(int index) {
		return this.extensionList.get(index).getName();
	}
	
	public int getIDAt(int index) {
		return this.extensionList.get(index).getExtensionID();
	}

	public int getSize() {
		return this.extensionList.size();
	}
	
	public void addListDataListener(ListDataListener listener) {
		this.listeners.add(listener);
	}

	public void removeListDataListener(ListDataListener listener) {
		this.listeners.remove(listener);
	}

}
