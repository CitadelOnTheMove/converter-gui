package eu.citadel.converter.gui.wizard.pages;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xerces.impl.dv.DatatypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.data.datatype.BasicDatatype;
import eu.citadel.converter.exceptions.ConverterException;
import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.localization.Messages;

public class DatatypeSelection extends WizardPage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatatypeSelection.class);

	public DatatypeSelection(int id, String title, int order, Wizard context) {
		super(id, title, order, context);
		logger.trace("DatatypeSelection(int, String, int, Wizard) - start");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSelectionDatatype = new JLabel(Messages.getString("DatatypeSelection.lblSelectionDatatype.text", "Formato di esportazione")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSelectionDatatype.setFont(new Font("Tahoma", Font.PLAIN, 34));
		GridBagConstraints gbc_lblSelectionDatatype = new GridBagConstraints();
		gbc_lblSelectionDatatype.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectionDatatype.gridx = 1;
		gbc_lblSelectionDatatype.gridy = 1;
		add(lblSelectionDatatype, gbc_lblSelectionDatatype);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		List<BasicDatatype> list = controller.getCurrentUnitWork().getDatatypeList();
		controller.getCurrentUnitWork().setAvailableDatatypes(list);
		for (BasicDatatype bdt : list) {
			comboBox.addItem(bdt.getName());
		}
		comboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

		    	int dtIndex = ((JComboBox<String>) e.getSource()).getSelectedIndex();
		    	controller.getCurrentUnitWork().setDataType(controller.getCurrentUnitWork().getDatatypeList().get(dtIndex));

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
		    }
		});
		controller.getCurrentUnitWork().setDataType(controller.getCurrentUnitWork().getDatatypeList().get(0));
		
		JLabel lblNewLabel = new JLabel(Messages.getString("DatatypeSelection.lblNewLabel.text", "Scegli il formato di esportazione dei dati")); //$NON-NLS-1$ //$NON-NLS-2$
		panel.add(lblNewLabel);
		panel.add(comboBox);

		logger.trace("DatatypeSelection(int, String, int, Wizard) - end");
	}

	@Override
	protected void configureButtons() {
		logger.trace("configureButtons() - start");

		logger.trace("configureButtons() - end");
	}

	@Override
	protected void update() throws IOException, ConverterException,
			InvalidFormatException, DatatypeException, Exception {
		logger.trace("update() - start");

		logger.trace("update() - end");
	}

}
