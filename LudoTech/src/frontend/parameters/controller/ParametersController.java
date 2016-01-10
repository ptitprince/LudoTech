package frontend.parameters.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import backend.services.ParametersServices;
import frontend.parameters.view.ParametersView;
import frontend.utils.exceptions.NotValidNumberFieldException;
import frontend.utils.gui.TextView;

@SuppressWarnings("serial")
public class ParametersController extends JPanel {

	private ParametersServices parametersServices;

	private ParametersView parametersView;

	public ParametersController() {
		this.parametersServices = new ParametersServices();
		this.parametersView = new ParametersView();
		this.makeGUI();
		this.makeListeners();
		this.loadParameters();

	}

	private void makeGUI() {
		this.parametersView = new ParametersView();
		this.add(this.parametersView);

	}

	private void makeListeners() {
		this.parametersView.getValidateButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String text = TextView.get("confirmation");
							String champ = TextView.get("validation");
							String cancel = TextView.get("annulation");
							int result = JOptionPane.showConfirmDialog(null,
									text, "", JOptionPane.YES_OPTION);
							if (result == 0) {
								parametersServices.saveAllParameters(
										parametersView.getNbBorrowings(),
										parametersView.getNbBookings(),
										parametersView
												.getDurationOfBorrowings(),
										parametersView
												.getDurationBetweenBookingandBorrowing(),
										parametersView
												.getDurationOfCotisation());
								JOptionPane.showMessageDialog(null, champ, "validation", JOptionPane.INFORMATION_MESSAGE);	
							} else {
								JOptionPane.showMessageDialog(null, cancel , "annulation", JOptionPane.INFORMATION_MESSAGE);	
								loadParameters();
							}
						} catch (NotValidNumberFieldException exception) {
							showInvalidFieldsException(exception);
						}

					}
				});
		this.parametersView.getCancelButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						loadParameters();
					}
				});
	}

	private void loadParameters() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				parametersView.load(parametersServices.getAllParameters());

			}
		});

	}

	private void showInvalidFieldsException(
			NotValidNumberFieldException exception) {
		String text = TextView.get("invalidField")
				+ "\"" + exception.getFieldName() + "\"" + ".\n"
				+ TextView.get("valueInInvalidField")
				+ ((exception.getFieldValue().equals("")) ? TextView
						.get("emptyValue") : "\"" + exception.getFieldValue()
						+ "\" ")
				+ TextView.get("typeOfValidValue")
				+ ((exception.getFieldValue().equals("")) ? TextView
						.get("notEmptyValue") : exception.getFieldType()) + ".";
		JOptionPane.showMessageDialog(null, text);
	}
}
