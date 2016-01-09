package gui.borrow.view;

import gui.LudoTechApplication;
import gui.utils.DateFormatter;
import gui.utils.PostLoadableExtensionComboBoxModel;
import gui.utils.PostLoadableGameComboBoxModel;
import gui.utils.PostLoadableMemberComboBoxModel;
import gui.utils.SpringUtilities;
import gui.utils.TextView;

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

import model.POJOs.Extension;
import model.POJOs.Game;
import model.POJOs.Member;

@SuppressWarnings("serial")
public class BorrowView extends JDialog {

	private static final double WINDOW_RATIO = 1.5;

	private int durationOfBorrowingsInWeeks;

	private JComboBox<Game> gameComboBox;
	private JComboBox<Member> memberComboBox;
	private JDatePickerImpl startDatePicker;
	private JDatePickerImpl endDatePicker;
	private JComboBox<Extension> extensionComboBox;

	private JButton validateButton;
	private JButton cancelButton;

	public BorrowView(int durationOfBorrowingsInWeeks) {
		this.durationOfBorrowingsInWeeks = durationOfBorrowingsInWeeks;
		this.setSize((int) (LudoTechApplication.WINDOW_WIDTH / WINDOW_RATIO),
				(int) (LudoTechApplication.WINDOW_HEIGHT / WINDOW_RATIO));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.makeGUI();
	}

	private void makeGUI() {
		JLabel title = new JLabel(TextView.get("borrowAdd"));
		Font police = new Font("Arial", Font.BOLD, 16);
		title.setFont(police);
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel borrowPanel = new JPanel(new SpringLayout());

		// Nom du jeu
		JLabel borrowGameNameLabel = new JLabel(TextView.get("borrowGame"));
		borrowPanel.add(borrowGameNameLabel);
		this.gameComboBox = new JComboBox<Game>();
		this.gameComboBox.setModel(new PostLoadableGameComboBoxModel());
		this.gameComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		borrowGameNameLabel.setLabelFor(this.gameComboBox);
		borrowPanel.add(this.gameComboBox);

		// Nom du membre
		JLabel borrowMemberNameLabel = new JLabel(TextView.get("borrowMember"));
		borrowPanel.add(borrowMemberNameLabel);
		this.memberComboBox = new JComboBox<Member>();
		this.memberComboBox.setModel(new PostLoadableMemberComboBoxModel());
		this.memberComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		borrowMemberNameLabel.setLabelFor(this.memberComboBox);
		borrowPanel.add(this.memberComboBox);

		// Date de début
		JLabel beginningDateLabel = new JLabel(TextView.get("borrowBeginningDate"));
		borrowPanel.add(beginningDateLabel);
		UtilDateModel startDateModel = new UtilDateModel();
		startDateModel.setSelected(true);
		Properties startDateProperties = new Properties();
		startDateProperties.put("text.today", "Today");
		startDateProperties.put("text.month", "Month");
		startDateProperties.put("text.year", "Year");
		JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, startDateProperties);
		this.startDatePicker = new JDatePickerImpl(startDatePanel, new DateFormatter());
		beginningDateLabel.setLabelFor(this.startDatePicker);
		borrowPanel.add(startDatePicker);

		// Date de fin
		JLabel endingDateLabel = new JLabel(TextView.get("borrowEndingDate"));
		borrowPanel.add(endingDateLabel);
		UtilDateModel endDateModel = new UtilDateModel();
		endDateModel.setSelected(true);
		endDateModel.addDay(7 * durationOfBorrowingsInWeeks); // 7 jours fois le
																// nombre de
																// semaines
		Properties endDateProperties = new Properties();
		endDateProperties.put("text.today", "Today");
		endDateProperties.put("text.month", "Month");
		endDateProperties.put("text.year", "Year");
		JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, endDateProperties);
		this.endDatePicker = new JDatePickerImpl(endDatePanel, new DateFormatter());
		beginningDateLabel.setLabelFor(this.endDatePicker);
		borrowPanel.add(endDatePicker);

		// Nom de l'extension
		JLabel borrowExtensionNameLabel = new JLabel(TextView.get("borrowExtension"));
		borrowPanel.add(borrowExtensionNameLabel);
		this.extensionComboBox = new JComboBox<Extension>();
		this.extensionComboBox.setModel(new PostLoadableExtensionComboBoxModel());
		this.extensionComboBox.setMaximumSize(new Dimension(LudoTechApplication.WINDOW_WIDTH, 20));
		borrowExtensionNameLabel.setLabelFor(this.extensionComboBox);
		borrowPanel.add(this.extensionComboBox);

		SpringUtilities.makeCompactGrid(borrowPanel, 5, 2, 6, 6, 6, 6);

		this.add(borrowPanel, BorderLayout.CENTER);

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
		this.endDatePicker.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		this.endDatePicker.getModel().addDay(7 * this.durationOfBorrowingsInWeeks);
	}

	public void clearExtensions() {
		this.extensionComboBox.getModel().setSelectedItem(null);
	}
}
