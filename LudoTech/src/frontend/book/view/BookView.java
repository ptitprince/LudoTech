package frontend.book.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import backend.POJOs.Extension;
import backend.POJOs.Game;
import backend.POJOs.Member;
import frontend.LudoTechApplication;
import frontend.utils.gui.DateFormatter;
import frontend.utils.gui.PostLoadableExtensionComboBoxModel;
import frontend.utils.gui.PostLoadableGameComboBoxModel;
import frontend.utils.gui.PostLoadableMemberComboBoxModel;
import frontend.utils.gui.SpringUtilities;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class BookView extends JDialog {

	private static final double WINDOW_RATIO = 1.5;

	private int durationOfBorrowingsInWeeks;
	private int durationBetweenBookingandBorrowingInWeeks;

	private JComboBox<Game> gameComboBox;
	private JComboBox<Member> memberComboBox;
	private JDatePickerImpl startDatePicker;
	private JDatePickerImpl endDatePicker;
	private JComboBox<Extension> extensionComboBox;

	private JButton validateButton;
	private JButton cancelButton;

	public BookView(int durationOfBookingsInWeeks, int durationBetweenBookingandBorrowingInWeeks) {
		this.durationOfBorrowingsInWeeks = durationOfBookingsInWeeks;
		this.durationBetweenBookingandBorrowingInWeeks = durationBetweenBookingandBorrowingInWeeks;
		this.setSize((int) (LudoTechApplication.WINDOW_WIDTH / WINDOW_RATIO),
				(int) (LudoTechApplication.WINDOW_HEIGHT / WINDOW_RATIO));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	private void makeGUI() {
		JLabel title = new JLabel(TextView.get("bookAdd"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel bookPanel = new JPanel(new SpringLayout());

		// Nom du jeu
		JLabel bookGameNameLabel = new JLabel(TextView.get("bookGame"));
		bookPanel.add(bookGameNameLabel);
		this.gameComboBox = new JComboBox<Game>();
		this.gameComboBox.setModel(new PostLoadableGameComboBoxModel());
		this.gameComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		bookGameNameLabel.setLabelFor(this.gameComboBox);
		bookPanel.add(this.gameComboBox);

		// Nom du membre
		JLabel bookMemberNameLabel = new JLabel(TextView.get("bookMember"));
		bookPanel.add(bookMemberNameLabel);
		this.memberComboBox = new JComboBox<Member>();
		this.memberComboBox.setModel(new PostLoadableMemberComboBoxModel());
		this.memberComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		bookMemberNameLabel.setLabelFor(this.memberComboBox);
		bookPanel.add(this.memberComboBox);

		// Date de début
		JLabel beginningDateLabel = new JLabel(TextView.get("bookBeginningDate"));
		bookPanel.add(beginningDateLabel);
		UtilDateModel startDateModel = new UtilDateModel();
		startDateModel.setSelected(true);
		startDateModel.addDay(7 * durationBetweenBookingandBorrowingInWeeks);
		Properties startDateProperties = new Properties();
		startDateProperties.put("text.today", "Today");
		startDateProperties.put("text.month", "Month");
		startDateProperties.put("text.year", "Year");
		JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, startDateProperties);
		this.startDatePicker = new JDatePickerImpl(startDatePanel, new DateFormatter());
		beginningDateLabel.setLabelFor(this.startDatePicker);
		bookPanel.add(startDatePicker);

		// Date de fin
		JLabel endingDateLabel = new JLabel(TextView.get("bookEndingDate"));
		bookPanel.add(endingDateLabel);
		UtilDateModel endDateModel = new UtilDateModel();
		endDateModel.setSelected(true);
		endDateModel.addDay(7 * (durationBetweenBookingandBorrowingInWeeks + durationOfBorrowingsInWeeks));
		Properties endDateProperties = new Properties();
		endDateProperties.put("text.today", "Today");
		endDateProperties.put("text.month", "Month");
		endDateProperties.put("text.year", "Year");
		JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, endDateProperties);
		this.endDatePicker = new JDatePickerImpl(endDatePanel, new DateFormatter());
		beginningDateLabel.setLabelFor(this.endDatePicker);
		bookPanel.add(endDatePicker);

		// Nom de l'extension
		JLabel bookExtensionNameLabel = new JLabel(TextView.get("bookExtension"));
		bookPanel.add(bookExtensionNameLabel);
		this.extensionComboBox = new JComboBox<Extension>();
		this.extensionComboBox.setModel(new PostLoadableExtensionComboBoxModel());
		this.extensionComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		bookExtensionNameLabel.setLabelFor(this.extensionComboBox);
		bookPanel.add(this.extensionComboBox);

		SpringUtilities.makeCompactGrid(bookPanel, 5, 2, 6, 6, 6, 6);

		this.add(bookPanel, BorderLayout.CENTER);

		JPanel actionsPanel = new JPanel();
		this.validateButton = new JButton(TextView.get("validate"));
		actionsPanel.add(this.validateButton);
		this.cancelButton = new JButton(TextView.get("cancel"));
		actionsPanel.add(this.cancelButton);
		this.add(actionsPanel, BorderLayout.SOUTH);

	}

	public void loadGames(List<Game> games) {
		((PostLoadableGameComboBoxModel) this.gameComboBox.getModel()).loadData(games);
	}

	public void loadMembers(List<Member> members) {
		((PostLoadableMemberComboBoxModel) this.memberComboBox.getModel()).loadData(members);
	}

	public void loadExtensions(List<Extension> extensions) {
		((PostLoadableExtensionComboBoxModel) this.extensionComboBox.getModel()).loadData(extensions);
	}

	public JComboBox<Game> getGameComboBox() {
		return this.gameComboBox;
	}

	public Game getSelectedGame() {
		return (Game) this.gameComboBox.getSelectedItem();
	}

	public Member getSelectedMember() {
		return (Member) this.memberComboBox.getSelectedItem();
	}
	
	public Date getStartDate() throws ParseException {
		return (Date) this.startDatePicker.getModel().getValue();
	}

	public Date getEndDate() throws ParseException {
		return (Date) this.endDatePicker.getModel().getValue();
	}

	public Extension getSelectedExtension() {
		return (Extension) this.extensionComboBox.getSelectedItem();
	}

	public JButton getValidateButton() {
		return this.validateButton;
	}

	public JButton getCancelButton() {
		return this.cancelButton;
	}

	public void clear() {
		this.gameComboBox.getModel().setSelectedItem(null);
		this.memberComboBox.getModel().setSelectedItem(null);
		this.clearDates();
		this.extensionComboBox.removeAllItems();
	}

	public void clearDates() {
		Calendar calendar = Calendar.getInstance();
		this.startDatePicker.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		this.startDatePicker.getModel().addDay(7 * durationBetweenBookingandBorrowingInWeeks);
		this.endDatePicker.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		this.endDatePicker.getModel().addDay(7 * (durationBetweenBookingandBorrowingInWeeks + durationOfBorrowingsInWeeks));
	}

	public void clearExtensions() {
		this.extensionComboBox.getModel().setSelectedItem(null);
	}

	public void setMemberComboBoxValue(int memberID, boolean admin) {
		if (!admin) {
			((PostLoadableMemberComboBoxModel) this.memberComboBox.getModel()).selectItemByID(memberID);
		}
		this.memberComboBox.setEnabled(admin);
	}
}
