package eu.citadel.converter.gui.wizard.pages;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.commons.Type;
import eu.citadel.converter.data.dataset.ExcelDataset;
import eu.citadel.converter.data.dataset.ExcelDatasetContentBuilder;
import eu.citadel.converter.data.dataset.ExcelType;
import eu.citadel.converter.data.metadata.BasicMetadataUtils;
import eu.citadel.converter.exceptions.ExcelDatasetException;
import eu.citadel.converter.gui.wizard.domain.ExcelSheet;
import eu.citadel.converter.gui.wizard.domain.FileInput;
import eu.citadel.converter.gui.wizard.domain.PreviewFileTable;
import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.gui.wizard.utility.GuiUtility;

@SuppressWarnings("serial")
public class ExcelConfigPage extends WizardPage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExcelConfigPage.class);

	private static final Integer[] ROWS_PREVIEW = new Integer[] {10, 25, 50, 100};
	private boolean firstRowIsData = false;
	private JTable table;
	private PreviewFileTable modelTable;
	private List<List<String>> content;
	private ExcelType sheet = ExcelType.DEFAULT;
	private int nrows = ROWS_PREVIEW[0];
	private FileInput file;
	private Map<Integer, String> mapSheets;

	public ExcelConfigPage(int id, String title, int order, Wizard context) throws ExcelDatasetException, IOException {
		super(id, title, order, context);
		logger.trace("ExcelConfigPage(int, String, int, Wizard) - start");

		file = controller.getCurrentUnitWork().getTargetFileValue();
		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setAutoCreateRowSorter(true);
		table.setBackground(UIManager.getColor("Panel.background"));
		updateContent();
		mapSheets = ((ExcelDataset) controller.getCurrentUnitWork().getDataset()).getSheetMap();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 88, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JTextPane txtpnMissingTranslation = new JTextPane();
		txtpnMissingTranslation.setEditable(false);
		txtpnMissingTranslation.setEnabled(false);
		txtpnMissingTranslation.setText(Messages.getString("ExcelConfigPage.subtitle", "missing translation")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_txtpnMissingTranslation = new GridBagConstraints();
		gbc_txtpnMissingTranslation.gridwidth = 4;
		gbc_txtpnMissingTranslation.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnMissingTranslation.fill = GridBagConstraints.BOTH;
		gbc_txtpnMissingTranslation.gridx = 0;
		gbc_txtpnMissingTranslation.gridy = 0;
		add(txtpnMissingTranslation, gbc_txtpnMissingTranslation);
		
		JPanel rdDataLabelPanel = new JPanel();
		GridBagConstraints gbc_rdDataLabelPanel = new GridBagConstraints();
		gbc_rdDataLabelPanel.insets = new Insets(0, 0, 5, 5);
		gbc_rdDataLabelPanel.gridx = 0;
		gbc_rdDataLabelPanel.gridy = 1;
		add(rdDataLabelPanel, gbc_rdDataLabelPanel);
		
		JLabel lblFirstRowIs = new JLabel(Messages.getString("ExcelConfigPage.lblFirstRowIs.text", "La prima riga \u00E8")); //$NON-NLS-1$ //$NON-NLS-2$
		rdDataLabelPanel.add(lblFirstRowIs);
		
		Map<String, String> supportedFirstRow = BasicMetadataUtils.getMap(BasicMetadataUtils.FIRST_ROW);
		
		String dataText = "data";
		String labelText = "label";
		if (supportedFirstRow.size() < 2) {
			logger.warn("CSVConfigPage(int, String, int, Wizard) - missing first-row");
		}
		else {
			Set<Entry<String, String>> entries = supportedFirstRow.entrySet();
			for (Entry<String, String> entry : entries) {
				if ("data".equalsIgnoreCase(entry.getKey())) {
					dataText = eu.citadel.converter.localization.Messages.getString(entry.getValue());
				}
				else if ("label".equalsIgnoreCase(entry.getKey())) {
					labelText = eu.citadel.converter.localization.Messages.getString(entry.getValue());
				}
				else {
					logger.warn("CSVConfigPage(int, String, int, Wizard) - unexpected first-row: {}", entry.getKey());
				}
			}
		}
		
		final JRadioButton rdbtnData = new JRadioButton(dataText); //$NON-NLS-1$ //$NON-NLS-2$
		rdDataLabelPanel.add(rdbtnData);
		rdbtnData.setSelected(false);
		final JRadioButton rdbtnLabel = new JRadioButton(labelText); //$NON-NLS-1$ //$NON-NLS-2$
		rdDataLabelPanel.add(rdbtnLabel);
		rdbtnLabel.setSelected(true);
		rdbtnLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				if (rdbtnLabel.isSelected()) {
					firstRowIsData = false;
					rdbtnData.setSelected(false);
					updateContent();
				} else {
					rdbtnLabel.setSelected(true);
				}

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		rdbtnData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				if (rdbtnData.isSelected()) {
					firstRowIsData = true;
					rdbtnLabel.setSelected(false);
					updateContent();
				} else {
					rdbtnData.setSelected(true);
				}

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		
		JPanel cbSeparatorPanel = new JPanel();
		GridBagConstraints gbc_cbSeparatorPanel = new GridBagConstraints();
		gbc_cbSeparatorPanel.insets = new Insets(0, 0, 5, 5);
		gbc_cbSeparatorPanel.gridx = 1;
		gbc_cbSeparatorPanel.gridy = 1;
		add(cbSeparatorPanel, gbc_cbSeparatorPanel);
		
		JLabel label = new JLabel(Messages.getString("ExcelConfigPage.label.text", "")); //$NON-NLS-1$ //$NON-NLS-2$
		cbSeparatorPanel.add(label);
		
		final JComboBox<ExcelSheet> cbSheet = new JComboBox<ExcelSheet>();
		label.setLabelFor(cbSheet);
		cbSheet.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

		    	sheet = new ExcelType(((ExcelSheet) cbSheet.getSelectedItem()).getSheetNumber());
				updateContent();

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
		    }
		});
		cbSeparatorPanel.add(cbSheet);
		
		int nSheets = mapSheets.keySet().size();
		ExcelSheet[] es = new ExcelSheet[nSheets];
		for (int i = 0; i < nSheets; i++) {
			es[i] = new ExcelSheet(i, mapSheets.get(i));
		}
		
		cbSheet.setModel(new DefaultComboBoxModel<ExcelSheet>(es));
		
		JPanel cbNRowPanel = new JPanel();
		GridBagConstraints gbc_cbNRowPanel = new GridBagConstraints();
		gbc_cbNRowPanel.insets = new Insets(0, 0, 5, 5);
		gbc_cbNRowPanel.gridx = 2;
		gbc_cbNRowPanel.gridy = 1;
		add(cbNRowPanel, gbc_cbNRowPanel);
		
		JLabel lblNRowPreview = new JLabel(Messages.getString("ExcelConfigPage.lblNRowPreview.text", "")); //$NON-NLS-1$ //$NON-NLS-2$
		cbNRowPanel.add(lblNRowPreview);
		
		final JComboBox<Integer> cbNRows = new JComboBox<Integer>();
		lblNRowPreview.setLabelFor(cbNRows);
		cbNRows.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

		    	nrows = (int) cbNRows.getSelectedItem();
				updateContent();

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
		    }
		});
		cbNRowPanel.add(cbNRows);
		cbNRows.setModel(new DefaultComboBoxModel<Integer>(ROWS_PREVIEW));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(2, 2, 2, 2);
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);		

		logger.trace("ExcelConfigPage(int, String, int, Wizard) - end");
	}

	protected void updateContent() {
		logger.trace("updateContent() - start");
		
		file.getMetadata().setObjType((Type) sheet);
		controller.getCurrentUnitWork().getTargetFileValue().getMetadata().setObjType(sheet);
		file.getMetadata().setFirstRowIsData(firstRowIsData);
		content = initData(sheet, nrows, null);
		int nRows = content.size();
		file.setContent(content);
		modelTable = new PreviewFileTable(nRows>0?content.get(0):null, firstRowIsData);
		fillTable();
		if (!firstRowIsData && nRows > 0) {			
			modelTable.removeRow(0);
		}
		table.setModel(modelTable);
		table.revalidate();
		table.repaint();

		logger.trace("updateContent() - end");
	}

	private void fillTable() {
		logger.trace("fillTable() - start");

		for (List<String> row : content) {
			modelTable.addRow(row.toArray(new Object[row.size()]));
		}

		logger.trace("fillTable() - end");
	}

	private List<List<String>> initData(ExcelType type, int nrows, Charset charset) {
		logger.trace("initData(ExcelType, int, Charset) - start");
		
		List<List<Object>> content = null;
		try {
			content = new ExcelDatasetContentBuilder()
				.setPath(file.toPath())
				.setExcelType(type)
				.setLines(nrows)
				.build();
		}
		catch (IOException e) {
			logger.warn("initData(ExcelType, int, Charset)", e);

			GuiUtility.showInfoBox("Error", "Error loading file", JOptionPane.ERROR_MESSAGE);
		} catch (ExcelDatasetException e) {
			logger.warn("initData(ExcelType, int, Charset)", e);

			GuiUtility.showInfoBox("Error", "Invalid format", JOptionPane.ERROR_MESSAGE);
		}
		
		List<List<String>> returnList = GuiUtility.transformList(content);
		logger.trace("initData(ExcelType, int, Charset) - end");
		return returnList;
	}

	@Override
	protected void configureButtons() {
		logger.trace("configureButtons() - start");

		// TODO Auto-generated method stub

		logger.trace("configureButtons() - end");
	}

	@Override
	protected void update() {
		logger.trace("update() - start");

		// TODO Auto-generated method stub

		logger.trace("update() - end");
	}

}
