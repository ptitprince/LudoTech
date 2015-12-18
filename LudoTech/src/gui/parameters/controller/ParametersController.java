package gui.parameters.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.parameters.view.ParametersView;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.services.ParametersServices;

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

	public void makeGUI() {
		this.parametersView = new ParametersView();
		this.add(this.parametersView);

	}

	private void makeListeners() {
		this.parametersView.getValidateButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							parametersServices.saveAllParameters(parametersView.getNbBorrowings(),parametersView.getNbBookings(),parametersView.getDurationOfBorrowings(),parametersView.getDurationBetweenBookingandBorrowing(),parametersView.getDurationOfCotisation());
							
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

}
