package gui.book.controller;

import gui.book.model.BookListModel;
import gui.book.view.BookListView;
import gui.book.view.BookView;
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
import model.POJOs.Item;
import model.POJOs.Member;
import model.services.BookServices;
import model.services.ExtensionServices;
import model.services.GameServices;
import model.services.ItemServices;
import model.services.MemberServices;
import model.services.ParametersServices;

@SuppressWarnings("serial")
public class BookController extends JPanel {

	private BookServices bookServices;
	private ItemServices itemServices;
	private MemberServices memberServices;
	private GameServices gameServices;
	private ExtensionServices extensionServices;
	private ParametersServices parametersServices;

	private BookListModel bookListModel;

	private BookListView bookListView;

	private BookView bookView;

	private int currentMemberID;

	public BookController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.bookServices = new BookServices();
		this.itemServices = new ItemServices();
		this.memberServices = new MemberServices();
		this.gameServices = new GameServices();
		this.extensionServices = new ExtensionServices();
		this.parametersServices = new ParametersServices();
		this.bookListModel = new BookListModel(this.bookServices, this.itemServices);
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
		this.loadLists();
	}

	public void makeGUI() {
		this.bookListView = new BookListView(this.bookListModel, this.memberServices.isAdmin(currentMemberID));
		this.add(bookListView, BorderLayout.CENTER);

		this.bookView = new BookView(this.parametersServices.getDurationOfBorrowingsInWeeks());
		this.bookView.setLocationRelativeTo(this);
	}

	public void makeListeners() {

		this.bookListView.getAddBookButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookView.clear();
				bookView.setMemberComboBoxValue(currentMemberID, memberServices.isAdmin(currentMemberID));
				bookView.setVisible(true);
			}
		});

		// Clic sur le bouton "Annuler réservation" de la liste des réservations
		this.bookListView.getCancelBookButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTable table = bookListView.getTable();
				int selectedRowIndex = table.getSelectedRow();
				if (selectedRowIndex > -1) {
					// Vrai si confirmé par l'utilisateur
					if (showCancelBookConfirmation()) {
						try {
							int itemID = (Integer) table.getModel().getValueAt(selectedRowIndex, 0);
							int memberID = (Integer) table.getModel().getValueAt(selectedRowIndex, 1);
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							Date startDate = sdf.parse((String) table.getModel().getValueAt(selectedRowIndex, 5));
							bookServices.cancelBook(itemID, memberID, startDate);
							refreshBookList();
						} catch (ParseException e1) {
							showInvalidDatesException();
						}
					}
				}
			}
		});

		this.bookView.getValidateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game selectedGame = bookView.getSelectedGame();
				
				Member selectedMember = bookView.getSelectedMember();
				if (selectedGame == null || selectedMember == null) {
					showNoGameOrMemberSelectedException();
				} else {
					try {
						Date startDate = bookView.getStartDate();
						Date endDate = bookView.getEndDate();
						Extension selectedExtension = bookView.getSelectedExtension();
						//bookServices.addBook(selectedGame, selectedMember, startDate, endDate, selectedExtension);
						bookView.setVisible(false);
					} catch (ParseException e1) {
						showInvalidDatesException();
					}
				}
			}
		});

		this.bookView.getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookView.setVisible(false);
			}
		});

		if (memberServices.isAdmin(this.currentMemberID)) {

			// Clic sur le bouton "Enregistrer le départ d'un jeu" de la liste
			// des réservations
			this.bookListView.getComeGetGameButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTable table = bookListView.getTable();
					int selectedRowIndex = table.getSelectedRow();
					if (selectedRowIndex > -1) {
						// Vrai si confirmé par l'utilisateur
						if (showComeGetGameConfirmation()) {
							try {
								int itemID = (Integer) table.getModel().getValueAt(selectedRowIndex, 0);
								int memberID = (Integer) table.getModel().getValueAt(selectedRowIndex, 1);
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								Date startDate = sdf.parse((String) table.getModel().getValueAt(selectedRowIndex, 5));
								Date endDate = sdf.parse((String) table.getModel().getValueAt(selectedRowIndex, 6));
								int extensionID = (Integer) table.getModel().getValueAt(selectedRowIndex, 2);
								bookServices.convertBookIntoBorrow(itemID, memberID, startDate, endDate, extensionID);
								refreshBookList();
							} catch (ParseException e1) {
								showInvalidDatesException();
							}
						}
					}
				}
			});

			this.bookView.getGameComboBox().addActionListener(new ActionListener() {
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
				bookView.loadGames(games);
				bookView.loadMembers(members);
				bookView.clear(); // Pour déselectionner le premier élément
									// des deux listes déroulantes
			}
		});
	}

	private void loadExtensionListAccordingToGame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Game selectedGame = (Game) bookView.getGameComboBox().getSelectedItem();
				if (selectedGame != null) {
					List<Extension> extensions = extensionServices.getExtensions(selectedGame.getGameID());
					bookView.loadExtensions(extensions);
					bookView.clearExtensions();
				}
			}
		});
	}

	public void refreshBookList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (memberServices.isAdmin(currentMemberID)) {
					bookListModel.refresh();
				} else {
					bookListModel.refreshForSimpleUser(currentMemberID);
				}

			}
		});
	}

	public void showNoGameOrMemberSelectedException() {
		String text = TextView.get("bookNoGameOrMemberSelectedException");
		JOptionPane.showMessageDialog(null, text);
	}

	public void showInvalidDatesException() {
		String text = TextView.get("bookDatesFormatException");
		JOptionPane.showMessageDialog(null, text);
	}
	
	public boolean showCancelBookConfirmation() {
		String text = TextView.get("bookConfirmCancel") + "\n";
		if (this.memberServices.isAdmin(this.currentMemberID)) {
			text += TextView.get("bookConfirmCancelAdvertAdmin");
		} else {
			text += TextView.get("bookConfirmCancelAdvertUser");
		}
		int result = JOptionPane.showConfirmDialog(null, text, "", JOptionPane.YES_OPTION);
		return (result == 0);
	}

	public boolean showComeGetGameConfirmation() {
		String text = TextView.get("bookConfirmComeGet");
		int result = JOptionPane.showConfirmDialog(null, text, "", JOptionPane.YES_OPTION);
		return (result == 0);
	}
}
