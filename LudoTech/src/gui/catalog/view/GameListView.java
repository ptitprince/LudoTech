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
		this.addGameButton = new JButton(TextView.get("catalogAddGame"));
		actionsPanel.add(this.addGameButton);
		this.add(actionsPanel, BorderLayout.SOUTH);
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public JButton getAddGameButton() {
		return this.addGameButton;
	}
	
}
