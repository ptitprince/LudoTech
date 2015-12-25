package gui.catalog.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gui.catalog.model.GameListModel;
import gui.utils.TextView;

@SuppressWarnings("serial")
public class GameListView extends JPanel {

	private JTable table;
	
	private JButton addGameButton;
	private JButton deleteGameButton;

	public GameListView(GameListModel model) {
		this.setLayout(new BorderLayout());
		this.makeGUI(model);
	}

	private void makeGUI(GameListModel model) {
		JLabel title = new JLabel(TextView.get("catalogListTitle"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		this.table = new JTable(model);
		this.table.removeColumn(table.getColumnModel().getColumn(0)); // Cacher la colonne des identifiants
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel actionsPanel = new JPanel();
		this.addGameButton = new JButton(TextView.get("add"));
		actionsPanel.add(this.addGameButton);
		this.deleteGameButton = new JButton(TextView.get("delete"));
		actionsPanel.add(this.deleteGameButton);
		this.add(actionsPanel, BorderLayout.SOUTH);
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public JButton getAddGameButton() {
		return this.addGameButton;
	}
	
	public JButton getDeleteGameButton() {
		return this.deleteGameButton;
	}
}
