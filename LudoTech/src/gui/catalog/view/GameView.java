package gui.catalog.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import gui.LudoTechApplication;

@SuppressWarnings("serial")
public class GameView extends JDialog {

	private static final String TITLE = "Fiche d'un jeu";
	private static final String INFOS_BOX_TITLE = "Informations";
	private static final String EXTENSIONS_BOX_TITLE = "Extensions";
	private static final String ITEMS_BOX_TITLE = "Exemplaires";
	private static final double WINDOW_RATIO = 1.25;
	
	public GameView() {
		this.setTitle(TITLE);
		this.setSize((int) (LudoTechApplication.WINDOW_WIDTH / WINDOW_RATIO), (int) (LudoTechApplication.WINDOW_HEIGHT / WINDOW_RATIO));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.makeGUI();
	}

	private void makeGUI() {
		JPanel boxesPanel = new JPanel();
		GridBagLayout boxesLayout = new GridBagLayout();
		boxesPanel.setLayout(boxesLayout);
		
		GridBagConstraints boxesConstraints = new GridBagConstraints();
		boxesConstraints.fill = GridBagConstraints.BOTH;
		boxesConstraints.insets = new Insets(0, 3, 0, 3);
		boxesConstraints.weightx = 2;
		boxesConstraints.weighty = 2;
		
		JPanel infosPanel = new JPanel();
		TitledBorder infosBorder = BorderFactory.createTitledBorder(INFOS_BOX_TITLE);
		infosBorder.setTitleJustification(TitledBorder.LEFT);
		infosPanel.setBorder(infosBorder);
		boxesConstraints.gridx = 0;
		boxesConstraints.gridy = 0;
		boxesConstraints.gridwidth = 2;
		boxesPanel.add(infosPanel, boxesConstraints);
		
		JPanel extensionsPanel = new JPanel();
		TitledBorder extensionsBorder = BorderFactory.createTitledBorder(EXTENSIONS_BOX_TITLE);
		extensionsBorder.setTitleJustification(TitledBorder.LEFT);
		extensionsPanel.setBorder(extensionsBorder);
		boxesConstraints.gridx = 0;
		boxesConstraints.gridy = 1;
		boxesConstraints.gridwidth = 1;
		boxesPanel.add(extensionsPanel, boxesConstraints);

		JPanel itemsPanel = new JPanel();
		TitledBorder itemsBorder = BorderFactory.createTitledBorder(ITEMS_BOX_TITLE);
		itemsBorder.setTitleJustification(TitledBorder.LEFT);
		itemsPanel.setBorder(itemsBorder);
		boxesConstraints.gridx = 1;
		boxesConstraints.gridy = 1;
		boxesConstraints.gridwidth = 1;
		boxesPanel.add(itemsPanel, boxesConstraints);

		this.add(boxesPanel, BorderLayout.CENTER);
	}
	
	public void showPopup() {
		this.setVisible(true);
	}

}
