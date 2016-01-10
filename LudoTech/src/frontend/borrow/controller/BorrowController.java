package frontend.borrow.controller;

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

import backend.POJOs.Borrow;
import backend.POJOs.Extension;
import backend.POJOs.Game;
import backend.POJOs.Member;
import backend.exceptions.BorrowAlreadyExistException;
import backend.exceptions.DatesOrderException;
import backend.exceptions.ExtensionNotAvailableException;
import backend.exceptions.IntervalBetweenStartDateAndEndDateException;
import backend.exceptions.MemberCantBorrowException;
import backend.exceptions.MemberNbBorrowsException;
import backend.exceptions.NoneItemAvailableException;
import backend.services.BorrowServices;
import backend.services.ExtensionServices;
import backend.services.GameServices;
import backend.services.ItemServices;
import backend.services.MemberServices;
import backend.services.ParametersServices;
import frontend.borrow.model.BorrowListModel;
import frontend.borrow.view.BorrowListView;
import frontend.borrow.view.BorrowView;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class BorrowController extends JPanel {

	private int currentMemberID;
	
	private BorrowServices borrowServices;
	private GameServices gameServices;
	private ItemServices itemServices;
	private ExtensionServices extensionServices;
	private MemberServices memberServices;
	private ParametersServices parametersServices;

	private BorrowListModel borrowListModel;

	private BorrowListView borrowListView;
	private BorrowView borrowView;

	public BorrowController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.borrowServices = new BorrowServices();
		this.gameServices = new GameServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new ExtensionServices();
		this.memberServices = new MemberServices();
		this.parametersServices = new ParametersServices();
		this.borrowListModel = new BorrowListModel(this.borrowServices, this.itemServices);
		
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
		this.loadLists();
	}

	private void makeGUI() {
		this.borrowListView = new BorrowListView(this.borrowListModel, this.memberServices.isAdmin(currentMemberID));
		this.add(borrowListView, BorderLayout.CENTER);

		this.borrowView = new BorrowView(this.parametersServices.getDurationOfBorrowingsInWeeks(), this.parametersServices.getDurationBetweenBookingAndBorrowingInWeeks());
		this.borrowView.setLocationRelativeTo(this);
	}

	private void makeListeners() {
		if (memberServices.isAdmin(this.currentMemberID)) {
			
			// Clic sur le bouton "Ajout un emprunt" de la liste des prêts
			this.borrowListView.getAddBorrowButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrowView.setVisible(true);
					borrowView.clear();
				}
			});

			// Clic sur le bouton "Retour d'un emprunt" de la liste des prêts
			this.borrowListView.getReceiveBorrowButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTable table = borrowListView.getTable();
					int selectedRowIndex = table.getSelectedRow();
					if (selectedRowIndex > -1) {
						// True si comfirmé par l'utilisateur
						if (showDeleteBorrowConfirmation()) { 
							try {
								int itemID = (Integer) table.getModel().getValueAt(selectedRowIndex, 0);
								int memberID = (Integer) table.getModel().getValueAt(selectedRowIndex, 1);
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								Date startDate = sdf.parse((String) table.getModel().getValueAt(selectedRowIndex, 5));
								Date endDate = sdf.parse((String) table.getModel().getValueAt(selectedRowIndex, 6));
								borrowServices.removeBorrow(itemID, memberID, startDate, endDate);
								refreshBorrowList();
							} catch (ParseException e1) {
								showInvalidDatesException();
							}
						}
					}
				}
			});
			
			// Clic sur le bouton "Valider" de la fenêtre de création d'un prêt
			this.borrowView.getValidateButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Game selectedGame = borrowView.getSelectedGame();
					Member selectedMember = borrowView.getSelectedMember();
					if (selectedGame == null || selectedMember == null) {
						JOptionPane.showMessageDialog(null, TextView.get("borrowNoGameOrMemberSelectedException"));
					} else {
						try {
							Date startDate = borrowView.getStartDate();
							Date endDate = borrowView.getEndDate();
							Extension selectedExtension = borrowView.getSelectedExtension();
							Borrow borrow = borrowServices.addBorrow(selectedGame, selectedMember, startDate, endDate,
									selectedExtension);
							if (borrow == null) {
								JOptionPane.showMessageDialog(null, TextView.get("borrowErrorDuringCreation"));
							}
							borrowView.setVisible(false);
							refreshBorrowList();
						} catch (ParseException parseException) {
							showInvalidDatesException();
						} catch (NoneItemAvailableException noneItemAvailableException) {
							noneItemAvailableException.show();
						} catch (MemberCantBorrowException e1) {
							e1.show();
						} catch (MemberNbBorrowsException e2) {
							e2.show();
						} catch (DatesOrderException e3) {
							e3.show();
						} catch (IntervalBetweenStartDateAndEndDateException e4) {
							e4.show();
						} catch (ExtensionNotAvailableException e5) {
							e5.show();
						} catch (BorrowAlreadyExistException e6) {
							e6.show();
						}
					}
				}
			});
			
			// Clic sur le bouton "Annuler" de la fenêtre de création d'un prêt
			this.borrowView.getCancelButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrowView.setVisible(false);
				}
			});
			
			// Sélection d'un jeu dans la liste des jeux de la fenêtre de création d'un prêt
			this.borrowView.getGameComboBox().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					loadExtensionListAccordingToGame();
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
				// Pour déselectionner le premier élément des deux listes déroulantes
				borrowView.clear(); 
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

	private void showInvalidDatesException() {
		String text = TextView.get("borrowDatesFormatException");
		JOptionPane.showMessageDialog(null, text);
	}

	private boolean showDeleteBorrowConfirmation() {
		String text = TextView.get("borrowConfirmDeleting");
		int result = JOptionPane.showConfirmDialog(null, text, "", JOptionPane.YES_OPTION);
		return (result == 0);
	}
}
