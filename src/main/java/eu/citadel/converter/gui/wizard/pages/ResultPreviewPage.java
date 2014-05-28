package eu.citadel.converter.gui.wizard.pages;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.localization.Messages;

@SuppressWarnings("serial")
public class ResultPreviewPage extends WizardPage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ResultPreviewPage.class);

	private JTextPane textPane;
	private JTextField txtSavePath;

	public ResultPreviewPage(int id, String title, int order, Wizard context) {
		super(id, title, order, context);
		logger.trace("ResultPreviewPage(int, String, int, Wizard) - start");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 124, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblPreviewOutput = new JLabel(Messages.getString("ResultPreviewPage.lblPreviewOutput.text", "Preview output")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPreviewOutput.setFont(new Font("Tahoma", Font.PLAIN, 32));
		GridBagConstraints gbc_lblPreviewOutput = new GridBagConstraints();
		gbc_lblPreviewOutput.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreviewOutput.gridx = 2;
		gbc_lblPreviewOutput.gridy = 1;
		add(lblPreviewOutput, gbc_lblPreviewOutput);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JButton btnSaveFile = new JButton(Messages.getString("ResultPreviewPage.btnSaveFile.text", "Select file")); //$NON-NLS-1$ //$NON-NLS-2$
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(true);
				int fcReturn = fileChooser.showOpenDialog(ResultPreviewPage.this);
				switch (fcReturn) {
				case JFileChooser.APPROVE_OPTION:
					Path selectedFilePath = fileChooser.getSelectedFile().toPath();					
					txtSavePath.setText(selectedFilePath.toString());
					break;
				default:
					break;
				}
				
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		
		JLabel lblSD = new JLabel(Messages.getString("ResultPreviewPage.lblSD.text", "Citadel json target path")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSD.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblSD = new GridBagConstraints();
		gbc_lblSD.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSD.insets = new Insets(0, 0, 5, 5);
		gbc_lblSD.gridx = 1;
		gbc_lblSD.gridy = 3;
		add(lblSD, gbc_lblSD);
		
		txtSavePath = new JTextField();
		GridBagConstraints gbc_txtSavePath = new GridBagConstraints();
		gbc_txtSavePath.insets = new Insets(0, 0, 5, 5);
		gbc_txtSavePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSavePath.gridx = 2;
		gbc_txtSavePath.gridy = 3;
		add(txtSavePath, gbc_txtSavePath);
		txtSavePath.setColumns(10);
		txtSavePath.getDocument().addDocumentListener(new DocumentListener() {
			  
				public void changedUpdate(DocumentEvent e) {
				logger.trace("$DocumentListener.changedUpdate(DocumentEvent) - start");

				    notifyController();

				logger.trace("$DocumentListener.changedUpdate(DocumentEvent) - end");
				  }
				  public void removeUpdate(DocumentEvent e) {
				logger.trace("$DocumentListener.removeUpdate(DocumentEvent) - start");

					  notifyController();

				logger.trace("$DocumentListener.removeUpdate(DocumentEvent) - end");
				  }
				  public void insertUpdate(DocumentEvent e) {
				logger.trace("$DocumentListener.insertUpdate(DocumentEvent) - start");

					  notifyController();

				logger.trace("$DocumentListener.insertUpdate(DocumentEvent) - end");
				  }

				  private void notifyController() {
				logger.trace("$DocumentListener.notifyController() - start");

					  controller.getCurrentUnitWork().setSaveFilePath(new File(txtSavePath.getText()).toPath());

				logger.trace("$DocumentListener.notifyController() - end");
				  }
				});
		GridBagConstraints gbc_btnSaveFile = new GridBagConstraints();
		gbc_btnSaveFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveFile.gridx = 3;
		gbc_btnSaveFile.gridy = 3;
		add(btnSaveFile, gbc_btnSaveFile);

		logger.trace("ResultPreviewPage(int, String, int, Wizard) - end");
	}

	@Override
	protected void configureButtons() {
		logger.trace("configureButtons() - start");

		logger.trace("configureButtons() - end");
	}

	@Override
	protected void update() throws Exception {
		logger.trace("update() - start");

		String uglyjson = controller.getCurrentUnitWork().getJsonResult();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyjson);
		String prettyjson = gson.toJson(je);
		textPane.setText(prettyjson);

		logger.trace("update() - end");
	}

}
