package gui.catalog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import gui.utils.TextView;
import model.POJOs.Game;
import model.services.GameServices;

@SuppressWarnings("serial")
public class GameListModel extends AbstractTableModel {

	private final String[] HEADERS = { TextView.get("gameID"), TextView.get("gameName"), TextView.get("gameCategory"),
			TextView.get("gameEditor"), TextView.get("gamePublishingYear"), TextView.get("gameMinAge"),
			TextView.get("gamePlayers"), TextView.get("gameAvailable") };

	private GameServices gameServices;

	private List<Game> gameList;

	public GameListModel(GameServices gameServices) {
		this.gameServices = gameServices;
		this.gameList = new ArrayList<Game>();
	}

	public void refresh(HashMap<String, String> filter) {
		this.gameList = this.gameServices.getGameListAccordingToFilter(filter);
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
			return this.gameList.get(rowIndex).getGameID();
		case 1:
			return this.gameList.get(rowIndex).getName();
		case 2:
			return TextView.makeFirstLetterUpper(this.gameList.get(rowIndex).getCategory());
		case 3:
			return TextView.makeFirstLetterUpper(this.gameList.get(rowIndex).getEditor());
		case 4:
			int publishingYear = this.gameList.get(rowIndex).getPublishingYear();
			return (publishingYear != 0) ? publishingYear : "";
		case 5:
			return this.gameList.get(rowIndex).getMinimumAge();
		case 6:
			return this.gameList.get(rowIndex).getMinimumPlayers() + " - "
					+ this.gameList.get(rowIndex).getMaximumPlayers();
		case 7:
			return true;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 1:
		case 2:
		case 3:
			return String.class;
		case 0:
		case 4:
		case 5:
		case 6:
			return Integer.class;
		case 7:
			return Boolean.class;
		default:
			return Object.class;
		}
	}

}
