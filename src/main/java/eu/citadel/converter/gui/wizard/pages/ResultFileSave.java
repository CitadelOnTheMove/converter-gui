package eu.citadel.converter.gui.wizard.pages;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.exceptions.ConverterException;
import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.localization.Messages;

public class ResultFileSave extends WizardPage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ResultFileSave.class);

	public ResultFileSave(int id, String title, int order, Wizard context) {
		super(id, title, order, context);
		logger.trace("ResultFileSave(int, String, int, Wizard) - start");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		
		JLabel lblSuccessfulFileSave = new JLabel(Messages.getString("ResultFileSave.lblSuccessfulFileSave.text", "Dataset saved")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSuccessfulFileSave.setFont(new Font("Tahoma", Font.PLAIN, 32));
		panel.add(lblSuccessfulFileSave);
		
		JLabel lblNewLabel = new JLabel(Messages.getString("ResultFileSave.lblNewLabel.text", "Well done! You may now upload the dataset on the Citadel Hub and start building applications!")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		add(lblNewLabel, gbc_lblNewLabel);


		/*if(Desktop.isDesktopSupported()) {
			JButton btnOpen = new JButton("Open");
			btnOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.open(new File(controller.getCurrentUnitWork().getSaveFilePath().toString()));
					} catch (IOException e) {
						GuiUtility.showInfoBox("Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			GridBagConstraints gbc_btnOpen = new GridBagConstraints();
			gbc_btnOpen.insets = new Insets(0, 0, 5, 5);
			gbc_btnOpen.gridx = 1;
			gbc_btnOpen.gridy = 2;
			add(btnOpen, gbc_btnOpen);
		}*/
		// TODO Auto-generated constructor stub

		logger.trace("ResultFileSave(int, String, int, Wizard) - end");
	}

	@Override
	protected void configureButtons() {
		logger.trace("configureButtons() - start");

		// TODO Auto-generated method stub

		logger.trace("configureButtons() - end");
	}

	@Override
	protected void update() throws IOException, ConverterException,
			InvalidFormatException {
		logger.trace("update() - start");

		// TODO Auto-generated method stub

		logger.trace("update() - end");
	}

}
