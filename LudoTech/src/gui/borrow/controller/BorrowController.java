package gui.borrow.controller;

import gui.borrow.model.BorrowListModel;
import gui.borrow.view.BorrowListView;
import gui.borrow.view.BorrowView;
import gui.utils.TextView;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Member;
import model.services.BorrowServices;
import model.services.ExtensionServices;
import model.services.GameServices;
import model.services.ItemServices;
import model.services.MemberServices;
import model.services.ParametersServices;

@SuppressWarnings("serial")
public class BorrowController extends JPanel {

	private BorrowServices borrowServices;
	private ItemServices itemServices;
	private MemberServices memberServices;
	private GameServices gameServices;
	private ExtensionServices extensionServices;
	private ParametersServices parametersServices;

	private BorrowListModel borrowListModel;

	private BorrowListView borrowListView;

	private BorrowView borrowView;

	private int currentMemberID;

	public BorrowController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.borrowServices = new BorrowServices();
		this.itemServices = new ItemServices();
		this.memberServices = new MemberServices();
		this.gameServices = new GameServices();
		this.extensionServices = new ExtensionServices();
		this.parametersServices = new ParametersServices();
		this.borrowListModel = new BorrowListModel(this.borrowServices, this.itemServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
		this.loadLists();
	}

	public void makeGUI() {
		this.borrowListView = new BorrowListView(this.borrowListModel, this.memberServices.isAdmin(currentMemberID));
		this.add(borrowListView, BorderLayout.CENTER);

		this.borrowView = new BorrowView(this.parametersServices.getDurationOfBorrowingsInWeeks());
		this.borrowView.setLocationRelativeTo(this);
	}

	public void makeListeners() {
		if (memberServices.isAdmin(this.currentMemberID)) {
			this.borrowListView.getAddBorrowButton().addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							borrowView.setVisible(true);
							borrowView.clear();
						}
					});

			// Clic sur le bouton "Retour d'un emprunt" de la liste des prêts
			this.borrowListView.getReceiveBorrowButton().addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JTable table = borrowListView.getTable();
							int selectedRowIndex = table.getSelectedRow();
							if (selectedRowIndex > -1) {
								if (showDeleteBorrowConfirmation()) { // True si
																		// la
																		// suppression
																		// a été
																		// confirmée
																		// par
																		// l'utilisateur
									try {
										int itemID = (Integer) table
												.getModel()
												.getValueAt(selectedRowIndex, 0);
										int memberID = (Integer) table
												.getModel().getValueAt(
														selectedRowIndex, 1);
										SimpleDateFormat sdf = new SimpleDateFormat(
												"dd/MM/yyyy");
										Date startDate = sdf
												.parse((String) table
														.getModel()
														.getValueAt(
																selectedRowIndex,
																5));
										Date endDate = sdf.parse((String) table
												.getModel().getValueAt(
														selectedRowIndex, 6));
										borrowServices.removeBorrow(itemID,
												memberID, startDate, endDate);
										refreshBorrowList();
									} catch (ParseException e1) {
										showInvalidDatesException();
									}
								}
							}
						}
					});
			this.borrowView.getGameComboBox().addActionListener(
					new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							loadExtensionListAccordingToGame();
						}
					});
			this.borrowView.getValidateButton().addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								Game selectedGame = borrowView
										.getSelectedGame();
								Member selectedMember = borrowView
										.getSelectedMember();
								Date startDate = borrowView.getStartDate();
								Date endDate = borrowView.getEndDate();
								Extension selectedExtension = borrowView
										.getSelectedExtension();
								borrowServices.addBorrow(selectedGame,
										selectedMember, startDate, endDate,
										selectedExtension);
								borrowView.setVisible(false);
								refreshBorrowList();
							} catch (ParseException e1) {
								showInvalidDatesException();
							}
						}
					});
			this.borrowView.getCancelButton().addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							borrowView.setVisible(false);
						}
					});
		}

	}

	private void loadLists() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				List<Game> games = gameServices.getGames(new HashMap<String, String>());
				List<Member> members = memberServices.getMemberList(new HashMap<String, String>());
				borrowView.loadGames(games);
				borrowView.loadMembers(members);
				borrowView.clear(); // Pour déselectionner le premier élément
									// des deux listes déroulantes
			}
		});
	}

	private void loadExtensionListAccordingToGame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Game selectedGame = (Game) borrowView.getGameComboBox().getSelectedItem();
				if (selectedGame != null) {
					List<Extension> extensions = extensionServices.getExtensions(selectedGame.getGameID());
					borrowView.loadExtensions(extensions);
					borrowView.clearExtensions();
				}
			}
		});
	}

	public void refreshBorrowList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (memberServices.isAdmin(currentMemberID)) {
					borrowListModel.refresh();
				} else {
					borrowListModel.refreshForSimpleUser(currentMemberID);
				}

			}
		});
	}

	public void showInvalidDatesException() {
		String text = TextView.get("borrowDatesFormatException");
		JOptionPane.showMessageDialog(null, text);
	}

	public boolean showDeleteBorrowConfirmation() {
		String text = TextView.get("borrowConfirmDeleting");
		int result = JOptionPane.showConfirmDialog(null, text, "", JOptionPane.YES_OPTION);
		return (result == 0);
	}
}
