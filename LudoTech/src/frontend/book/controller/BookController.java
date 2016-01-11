package frontend.book.controller;

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

import backend.POJOs.Book;
import backend.POJOs.Extension;
import backend.POJOs.Game;
import backend.POJOs.Member;
import backend.exceptions.BookAlreadyExistException;
import backend.exceptions.BorrowAlreadyExistException;
import backend.exceptions.DatesOrderException;
import backend.exceptions.ExtensionNotAvailableException;
import backend.exceptions.IntervalBetweenNowAndStartDateException;
import backend.exceptions.IntervalBetweenStartDateAndEndDateException;
import backend.exceptions.MemberCantBookException;
import backend.exceptions.MemberCantBorrowException;
import backend.exceptions.MemberNbBooksException;
import backend.exceptions.MemberNbBorrowsException;
import backend.exceptions.NoneItemAvailableException;
import backend.services.BookServices;
import backend.services.ExtensionServices;
import backend.services.GameServices;
import backend.services.ItemServices;
import backend.services.MemberServices;
import backend.services.ParametersServices;
import frontend.book.model.BookListModel;
import frontend.book.view.BookListView;
import frontend.book.view.BookView;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class BookController extends JPanel {

	private int currentMemberID;
	
	private BookServices bookServices;
	private GameServices gameServices;
	private ItemServices itemServices;
	private ExtensionServices extensionServices;
	private MemberServices memberServices;
	private ParametersServices parametersServices;

	private BookListModel bookListModel;

	private BookListView bookListView;
	private BookView bookView;

	public BookController(int currentMemberID) {
		this.currentMemberID = currentMemberID;
		this.bookServices = new BookServices();
		this.gameServices = new GameServices();
		this.itemServices = new ItemServices();
		this.extensionServices = new ExtensionServices();
		this.memberServices = new MemberServices();
		this.parametersServices = new ParametersServices();
		this.bookListModel = new BookListModel(this.bookServices, this.itemServices);
		
		this.setLayout(new BorderLayout());
		this.makeGUI();
		this.makeListeners();
		this.loadLists();
	}

	private void makeGUI() {
		this.bookListView = new BookListView(this.bookListModel, this.memberServices.isAdmin(currentMemberID));
		this.add(bookListView, BorderLayout.CENTER);

		this.bookView = new BookView(this.parametersServices.getDurationOfBorrowingsInWeeks(), this.parametersServices.getDurationBetweenBookingAndBorrowingInWeeks());
		this.bookView.setLocationRelativeTo(this);
	}

	private void makeListeners() {

		// Clic sur le bouton "Ajouter une réservation" de la liste des réservations
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

		// Clic sur le bouton "Valider" de la fenêtre de création de réservation
		this.bookView.getValidateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game selectedGame = bookView.getSelectedGame();
				Member selectedMember = bookView.getSelectedMember();
				if (selectedGame == null || selectedMember == null) {
					JOptionPane.showMessageDialog(null, TextView.get("bookNoGameOrMemberSelectedException"));
				} else {
					try {
						Date startDate = bookView.getStartDate();
						Date endDate = bookView.getEndDate();
						Extension selectedExtension = bookView.getSelectedExtension();
						Book book = bookServices.addBook(selectedGame, selectedMember, startDate, endDate, selectedExtension);
						if (book == null) {
							JOptionPane.showMessageDialog(null, TextView.get("bookErrorDuringCreation"));
						}
						bookView.setVisible(false);
						refreshBookList();
					} catch (ParseException parseException) {
						showInvalidDatesException();
					} catch (MemberCantBookException e1) {
						e1.show();
					} catch (MemberNbBooksException e2) {
						e2.show();
					} catch (DatesOrderException e3) {
						e3.show();
					} catch (IntervalBetweenNowAndStartDateException e4) {
						e4.show();
					} catch (IntervalBetweenStartDateAndEndDateException e5) {
						e5.show();
					} catch (NoneItemAvailableException e6) {
						e6.show();
					} catch (ExtensionNotAvailableException e7) {
						e7.show();
					} catch (BookAlreadyExistException e8) {
						e8.show();
					} 
				}
			}
		});

		// Clic sur le bouton "Annuler" de la fenêtre de création de réservation
		this.bookView.getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookView.setVisible(false);
			}
		});
		
		// Sélection d'un élément dans la liste des jeux de la fenêtre de création de réservartion
		this.bookView.getGameComboBox().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadExtensionListAccordingToGame();
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
								Integer extensionID = (Integer) table.getModel().getValueAt(selectedRowIndex, 2);
								boolean created = bookServices.convertBookIntoBorrow(itemID, memberID, startDate, endDate, extensionID);
								if (!created) {
									JOptionPane.showMessageDialog(null, TextView.get("borrowErrorDuringCreation"));
								}
								refreshBookList();
							} catch (ParseException e1) {
								showInvalidDatesException();
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
				// Pour déselectionner le premier élément des deux listes déroulantes
				bookView.clear(); 
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
	
	private void showInvalidDatesException() {
		String text = TextView.get("bookDatesFormatException");
		JOptionPane.showMessageDialog(null, text);
	}
	
	private boolean showCancelBookConfirmation() {
		String text = TextView.get("bookConfirmCancel") + "\n";
		if (this.memberServices.isAdmin(this.currentMemberID)) {
			text += TextView.get("bookConfirmCancelAdvertAdmin");
		} else {
			text += TextView.get("bookConfirmCancelAdvertUser");
		}
		int result = JOptionPane.showConfirmDialog(null, text, "", JOptionPane.YES_OPTION);
		return (result == 0);
	}

	private boolean showComeGetGameConfirmation() {
		String text = TextView.get("bookConfirmComeGet");
		int result = JOptionPane.showConfirmDialog(null, text, "", JOptionPane.YES_OPTION);
		return (result == 0);
	}
}
