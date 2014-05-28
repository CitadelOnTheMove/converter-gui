package eu.citadel.converter.gui.wizard.pages;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.domain.FileInput;
import eu.citadel.converter.gui.wizard.domain.FileListModel;
import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.gui.wizard.utility.GuiUtility;

@SuppressWarnings("serial")
public class SelectFilePage extends WizardPage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SelectFilePage.class);
	
	private JList<String> list;
	private JScrollPane scrollPane;
	
	private FileListModel currentModel;

	public SelectFilePage(int id, String title, int order, Wizard context) {
		super(id, title, order, context);
		logger.trace("SelectFilePage(int, String, int, Wizard) - start");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{50, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSelectFiles = new JLabel(Messages.getString("SelectFilePage.lblSelectFiles.text", "Choose dataset to convert"));		 //$NON-NLS-1$ //$NON-NLS-2$
		lblSelectFiles.setFont(new Font("Tahoma", Font.PLAIN, 32));
		GridBagConstraints gbc_lblSelectFiles = new GridBagConstraints();
		gbc_lblSelectFiles.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectFiles.gridx = 3;
		gbc_lblSelectFiles.gridy = 0;
		add(lblSelectFiles, gbc_lblSelectFiles);
		
		JTextPane txtpnMissingTranslation = new JTextPane();
		txtpnMissingTranslation.setEditable(false);
		txtpnMissingTranslation.setEnabled(false);
		txtpnMissingTranslation.setText(Messages.getString("SelectFilePage.subtitle", "missing translation")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_txtpnMissingTranslation = new GridBagConstraints();
		gbc_txtpnMissingTranslation.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnMissingTranslation.fill = GridBagConstraints.BOTH;
		gbc_txtpnMissingTranslation.gridx = 3;
		gbc_txtpnMissingTranslation.gridy = 1;
		add(txtpnMissingTranslation, gbc_txtpnMissingTranslation);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{243, 229, 0};
		gbl_panel.rowHeights = new int[]{198, 23, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		currentModel = new FileListModel(controller.files);
		list = new JList<String>(currentModel);		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				logger.trace("$ListSelectionListener.valueChanged(ListSelectionEvent) - start");

				if (!arg0.getValueIsAdjusting()) {
					Object target = list.getSelectedValue();
					if (target != null) {
						try {
							controller.getCurrentUnitWork().setTargetFileValue(new FileInput(target.toString()));
						} catch (IOException e) {
							logger.warn("$ListSelectionListener.valueChanged(ListSelectionEvent)", e);

							GuiUtility.showInfoBox("Error", "Missing file", JOptionPane.ERROR_MESSAGE);
						}
					}					
				}

				logger.trace("$ListSelectionListener.valueChanged(ListSelectionEvent) - end");
			}
		});
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(list);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);

		logger.trace("SelectFilePage(int, String, int, Wizard) - end");
	}

	@Override
	public void configureButtons() {
		logger.trace("configureButtons() - start");

		// TODO Auto-generated method stub
		
		logger.trace("configureButtons() - end");
	}

	@Override
	protected void update() {
		logger.trace("update() - start");

		currentModel = new FileListModel(controller.files);
		list.setModel(currentModel);
		File target = controller.getCurrentUnitWork().getTargetFileValue();
		if (target != null) {
			list.setSelectedValue(target.getPath(), true);
		} else {
			list.setSelectedIndex(0);
		}

		logger.trace("update() - end");
	}
}
