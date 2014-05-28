package eu.citadel.converter.gui.wizard.pages;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.domain.FileListModel;
import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.gui.wizard.utility.GuiUtility;

@SuppressWarnings("serial")
public class LoadFilesPage extends WizardPage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoadFilesPage.class);
	
	private JList<String> list;
	private JScrollPane scrollPane;
	private JButton btnDeleteFiles;
	
	private FileListModel currentModel;

	public LoadFilesPage(int id, String title, int order, Wizard context) {
		super(id, title, order, context);
		logger.trace("LoadFilesPage(int, String, int, Wizard) - start");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{50, 0, 422, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSelectFiles = new JLabel(Messages.getString("LoadFilesPage.lblSelectFiles.text", "Select source dataset"));		 //$NON-NLS-1$ //$NON-NLS-2$
		lblSelectFiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectFiles.setFont(new Font("Tahoma", Font.PLAIN, 32));
		GridBagConstraints gbc_lblSelectFiles = new GridBagConstraints();
		gbc_lblSelectFiles.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSelectFiles.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectFiles.gridx = 3;
		gbc_lblSelectFiles.gridy = 0;
		add(lblSelectFiles, gbc_lblSelectFiles);
		
		JTextPane textPane = new JTextPane();
		textPane.setText(Messages.getString("LoadFilesPage.subtitle","missing translation"));
		textPane.setEnabled(false);
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 5);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 3;
		gbc_textPane.gridy = 1;
		add(textPane, gbc_textPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{243, 229, 0};
		gbl_panel.rowHeights = new int[]{198, 23, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		currentModel = new FileListModel(controller.files);
		list = new JList<String>(currentModel);		
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(list);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
		
		JPanel btnAddPanel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.SOUTHEAST;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel.add(btnAddPanel, gbc_panel_1);
		
		JButton btnAddFiles = new JButton(Messages.getString("LoadFilesPage.btnAddFiles.text", "Add")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAddPanel.add(btnAddFiles);
		btnAddFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				openFileChooser();

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		
		JPanel btnDeleteFilesPanel = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.SOUTHWEST;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panel.add(btnDeleteFilesPanel, gbc_panel_2);
		
		btnDeleteFiles = new JButton(Messages.getString("LoadFilesPage.btnDeleteFiles.text", "Remove")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDeleteFilesPanel.add(btnDeleteFiles);
		btnDeleteFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				removeFile();

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		
		configureButtons();

		logger.trace("LoadFilesPage(int, String, int, Wizard) - end");
	}

	protected void removeFile() {
		logger.trace("removeFile() - start");

		int iSelected = list.getSelectedIndex();
		if (iSelected > -1) {
			controller.files.remove(iSelected);
			FileListModel model = new FileListModel(controller.files);
			list.setModel(model);
			currentModel = model;
			configureButtons();
		}

		logger.trace("removeFile() - end");
	}

	protected void openFileChooser() {
		logger.trace("openFileChooser() - start");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		int fcReturn = fileChooser.showOpenDialog(LoadFilesPage.this);
		switch (fcReturn) {
		case JFileChooser.APPROVE_OPTION:
			File[] selectedFiles = fileChooser.getSelectedFiles();
			for (File file : selectedFiles) {
				if (!controller.files.contains(file)) {
					controller.files.add(file);
				} else {
					GuiUtility.showInfoBox("Warning", "File " + file.getName() + " already added", JOptionPane.WARNING_MESSAGE);
				}
			}
			FileListModel model = new FileListModel(controller.files);
			list.setModel(model);	
			currentModel = model;
			configureButtons();
			break;
		default:
			break;
		}

		logger.trace("openFileChooser() - end");
	}
	
	@Override
	public void configureButtons() {
		logger.trace("configureButtons() - start");

		btnDeleteFiles.setEnabled(currentModel.getSize()>0);

		logger.trace("configureButtons() - end");
	}

	@Override
	protected void update() {
		logger.trace("update() - start");

		// TODO Auto-generated method stub
		
		logger.trace("update() - end");
	}
}
