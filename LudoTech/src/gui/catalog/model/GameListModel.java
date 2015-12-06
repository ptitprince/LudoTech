package gui.catalog.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.POJOs.Game;
import model.services.GameServices;

@SuppressWarnings("serial")
public class GameListModel extends AbstractTableModel {

	private final String[] HEADERS = { "Nom", "Type de jeu", "Editeur", "Année d'édition", "Joueurs", "Age minimum",
			"Disponible" };

	private GameServices gameServices;
	private List<Game> gameList;

	public GameListModel() {
		this.gameServices = new GameServices();
		this.gameList = new ArrayList<Game>();
	}

	public void refresh() {
		this.gameList = this.gameServices.getGameList();
		this.fireTableDataChanged();
	}

	public int getColumnCount() {
		return HEADERS.length;
	}

	public String getColumnName(int columnIndex) {
		return HEADERS[columnIndex];
	}

	public int getRowCount() {
		return this.gameList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.gameList.get(rowIndex).getName();
		case 1:
			return this.gameList.get(rowIndex).getCategory();
		case 2:
			return this.gameList.get(rowIndex).getEditor();
		case 3:
			return this.gameList.get(rowIndex).getPublishingYear();
		case 4:
			return this.gameList.get(rowIndex).getMinimumPlayers() + " - "
					+ this.gameList.get(rowIndex).getMaximumPlayers();
		case 5:
			return this.gameList.get(rowIndex).getMinimumAge();
		case 6:
			return true;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
		case 4:
			return String.class;
		case 3:
		case 5:
			return Integer.class;
		case 6:
			return Boolean.class;
		default:
			return Object.class;
		}
	}

}
