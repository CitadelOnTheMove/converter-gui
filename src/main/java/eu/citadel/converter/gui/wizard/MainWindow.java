package eu.citadel.converter.gui.wizard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.controller.GuiController;
import eu.citadel.converter.gui.wizard.domain.ObserverJTable;
import eu.citadel.converter.gui.wizard.domain.UnitWorkTable;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.gui.wizard.utility.GuiUtility;

public class MainWindow {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
	private final static Icon iconIT = new ImageIcon(ObserverJTable.class.getClassLoader().getResource("images/icons/italy.png"));
	private final static Icon iconEN = new ImageIcon(ObserverJTable.class.getClassLoader().getResource("images/icons/english.png"));
	private static Rectangle DEFAULT_DIMENSIONS = new Rectangle(100, 100, 800, 600);

	private GuiController controller;
	private JFrame frmCitadelConverter;
	private JPanel statusBar;
	private JLabel statusLabel;
	private JTable uwTable;
	private JMenuBar menuBar;

	/**
	 * @wbp.parser.constructor
	 */
	public MainWindow() {
		logger.trace("MainWindow() - start");

		this.controller = GuiController.getInstance();
		initialize(DEFAULT_DIMENSIONS);

		logger.trace("MainWindow() - end");
	}
	
	public MainWindow(final Rectangle rect) {
		logger.trace("MainWindow(Rectangle) - start");

		this.controller = GuiController.getInstance();
		initialize(rect);

		logger.trace("MainWindow(Rectangle) - end");
	}

	private void initialize(final Rectangle rect) {
		logger.trace("initialize(Rectangle) - start");

		try {
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) {
			logger.warn("initialize(Rectangle)", e);

	    	try {
				UIManager.setLookAndFeel(
				        UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e1) {
				logger.warn("initialize(Rectangle)", e1);

				e1.printStackTrace();
			}
	    }
		this.frmCitadelConverter = new JFrame();
		frmCitadelConverter.setTitle(Messages.getString("MainWindow.frmCitadelConverter.title", "Convertitore Citadel")); //$NON-NLS-1$ //$NON-NLS-2$
		this.frmCitadelConverter.setBounds(rect);
		this.frmCitadelConverter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Menu		
		createMenu(null);
		
		// Status bar
		statusBar = new JPanel();		
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar.setPreferredSize(new Dimension(frmCitadelConverter.getWidth(), 16));
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		frmCitadelConverter.getContentPane().add(statusBar, BorderLayout.SOUTH);
		statusLabel = new JLabel();
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusBar.add(statusLabel);
		
		JScrollPane scrollPaneTable = new JScrollPane();
		frmCitadelConverter.getContentPane().add(scrollPaneTable, BorderLayout.CENTER);
		
		uwTable = new JTable();
		uwTable.setModel(new UnitWorkTable());
		uwTable.getColumnModel().getColumn(0).setResizable(false);
		uwTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		uwTable.getColumnModel().getColumn(0).setMinWidth(25);
		uwTable.getColumnModel().getColumn(0).setMaxWidth(25);
		uwTable.getColumnModel().getColumn(1).setMinWidth(40);
		uwTable.getColumnModel().getColumn(1).setMaxWidth(9999);
		uwTable.getColumnModel().getColumn(2).setMinWidth(40);
		uwTable.getColumnModel().getColumn(2).setMaxWidth(9999);
		uwTable.getColumnModel().getColumn(3).setMinWidth(40);
		uwTable.getColumnModel().getColumn(3).setMaxWidth(9999);
		uwTable.getColumnModel().getColumn(4).setMinWidth(40);
		uwTable.getColumnModel().getColumn(4).setMaxWidth(9999);
		uwTable.getColumnModel().getColumn(5).setResizable(false);
		uwTable.getColumnModel().getColumn(5).setMaxWidth(9999);
		uwTable.getColumnModel().getColumn(6).setResizable(false);
		uwTable.getColumnModel().getColumn(6).setMaxWidth(9999);
		
		scrollPaneTable.setViewportView(uwTable);
		scrollPaneTable.setVisible(false);
		
		GuiUtility.centerWindow(frmCitadelConverter);
		this.frmCitadelConverter.setVisible(true);

		logger.trace("initialize(Rectangle) - end");
	}

	private void createMenu(String locale) {
		logger.trace("createMenu(String) - start");

		if(locale == null) locale = "noLocale";
		menuBar = new JMenuBar();
		frmCitadelConverter.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmStartWizard = new JMenuItem(Messages.getString("MainWindow.mntmStartWizard.text", "Start wizard...")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmStartWizard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				try {
					controller.startWizard(MainWizard.class, new Object[]{"Wizard"});
				} catch (Exception e1) {
					logger.warn("$ActionListener.actionPerformed(ActionEvent)", e1);

					e1.printStackTrace();
				}

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		mnFile.add(mntmStartWizard);
		
		JMenuItem mntmExit = new JMenuItem(Messages.getString("MainWindow.mntmExit.text", "Exit")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				controller.exit();

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnLanguage = new JMenu(Messages.getString("MainWindow.mnLanguage.text", "Language"));
		menuBar.add(mnLanguage);
		
		JMenuItem mntmSystemDefault = new JMenuItem(Messages.getString("MainWindow.mntmSystemDefault.text", "System default"));
		mntmSystemDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				GuiController.setLocale(GuiController.DEFAULT_LOCALE);
				repaint(null);

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		mnLanguage.add(mntmSystemDefault);
		
		JMenuItem mntmEnglish = new JMenuItem(Messages.getString("MainWindow.mntmEnglish.text", "English"), iconEN);
		mntmEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				GuiController.setLocale(Locale.ENGLISH);
				repaint("en");

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		mnLanguage.add(mntmEnglish);
		
		JMenuItem mntmItalian = new JMenuItem(Messages.getString("MainWindow.mntmItalian.text", "Italian"), iconIT);
		mntmItalian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				GuiController.setLocale(Locale.ITALIAN);	
				repaint("it");

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		mnLanguage.add(mntmItalian);
		
		JMenu mnAbout = new JMenu(Messages.getString("MainWindow.mnAbout.text", "Info")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(mnAbout);
		
		JMenuItem mntmInfo = new JMenuItem(Messages.getString("MainWindow.mntmInfo.text", "Approposito")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				GuiUtility.showInfoBox("Citadel converter 1.0", 
						"Based on Java 7" + System.lineSeparator() + System.lineSeparator() +
						"1.3 (23/05/2014) - Json validation, added fields for parkings, image and video" + System.lineSeparator() +
						"1.2 (17/02/2014) - Better unicode handling, help texts, tooltips, improved translation and bug fix" + System.lineSeparator() +
						"1.1 (27/01/2014) - Multilanguage and datatype selection step added, bug fix" + System.lineSeparator() +
						"1.0 (20/01/2014) - First release" + System.lineSeparator() + System.lineSeparator(), 
					JOptionPane.PLAIN_MESSAGE);

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		mnAbout.add(mntmInfo);
		
		switch (locale) {
		case "en":
			mntmEnglish.setSelected(true);
			mntmEnglish.setArmed(true);
			break;
		case "it":
			mntmItalian.setSelected(true);
			mntmItalian.setArmed(true);
			break;
		default:
			mntmSystemDefault.setSelected(true);
			mntmSystemDefault.setArmed(true);
			break;
		}

		logger.trace("createMenu(String) - end");
	}

	public void setStatusBar(String message){
		logger.trace("setStatusBar(String) - start");

		statusLabel.setText(message);

		logger.trace("setStatusBar(String) - end");
	}
	
	public String getStatusBar(){
		logger.trace("getStatusBar() - start");

		String returnString = statusLabel.getText();
		logger.trace("getStatusBar() - end");
		return returnString;
	}

	public void repaint(String locale) {
		logger.trace("repaint(String) - start");

		createMenu(locale);
		menuBar.revalidate();
		menuBar.repaint();
		frmCitadelConverter.setTitle(Messages.getString("MainWindow.frmCitadelConverter.title", "Citadel Converter"));

		logger.trace("repaint(String) - end");
	}
}
