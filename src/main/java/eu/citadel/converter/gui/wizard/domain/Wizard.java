package eu.citadel.converter.gui.wizard.domain;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.controller.GuiController;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.gui.wizard.utility.GuiUtility;

@SuppressWarnings("serial")
public abstract class Wizard extends JDialog implements WindowListener{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Wizard.class);

	private static Rectangle DEFAULT_DIMENSIONS = new Rectangle(100, 100, 850, 600);
	private JPanel navigationPanel;
	public JPanel contentPanel;
	
	private WizardPageList pages;
	private WizardPage currentPage;
	private JButton btnNext;
	private JButton btnPrevious;
	private JButton btnFinish;
	private JButton btnAbort;
	
	private GuiController controller;
	
	/**
	 * @wbp.parser.constructor
	 */
	public Wizard(String title, Rectangle rect, boolean show) {
		logger.trace("Wizard(String, Rectangle, boolean) - start");

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		
		this.controller = GuiController.getInstance();
		this.controller.setCurrentWizard(this);
		this.setTitle(title);
		this.pages = new WizardPageList();
		setBounds(rect==null?DEFAULT_DIMENSIONS:rect);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		navigationPanel = new JPanel();
		getContentPane().add(navigationPanel, BorderLayout.SOUTH);
		
		btnAbort = new JButton(Messages.getString("Wizard.btnAbort.text", "Annulla")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAbort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				abort();

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		
		btnPrevious = new JButton(Messages.getString("Wizard.btnPrevious.text", "Indietro")); //$NON-NLS-1$ //$NON-NLS-2$
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				previousPage();

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		
		btnNext = new JButton(Messages.getString("Wizard.btnNext.text", "Avanti")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				try {
					nextPage();
				} catch (Exception e) {
					logger.warn("$ActionListener.actionPerformed(ActionEvent)", e);

					GuiUtility.showInfoBox("Error", e.getMessage().replace(". ", ".\n"), JOptionPane.ERROR_MESSAGE);
				}

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		
		btnFinish = new JButton(Messages.getString("Wizard.btnFinish.text", "Fine")); //$NON-NLS-1$ //$NON-NLS-2$
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("$ActionListener.actionPerformed(ActionEvent) - start");

				finish();

				logger.trace("$ActionListener.actionPerformed(ActionEvent) - end");
			}
		});
		
		navigationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		navigationPanel.add(btnAbort);
		navigationPanel.add(btnPrevious);
		navigationPanel.add(btnNext);
		navigationPanel.add(btnFinish);
		
		contentPanel = new JPanel();
		contentPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent panel) {
				logger.trace("$ComponentAdapter.componentResized(ComponentEvent) - start");

				resizeWizardPage();

				logger.trace("$ComponentAdapter.componentResized(ComponentEvent) - end");
			}
		});
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		addWindowListener(this);
		
		GuiUtility.centerWindow(this);
		this.setVisible(show);

		logger.trace("Wizard(String, Rectangle, boolean) - end");
	}
	
	protected void abort(){
		logger.trace("abort() - start");

		if (controller.abort(this)) {
			this.dispose();
		}

		logger.trace("abort() - end");
	}
	protected void finish(){
		logger.trace("finish() - start");

		if (controller.finish(this)) {
			this.dispose();
		}

		logger.trace("finish() - end");
	}

	protected void nextPage() throws Exception {
		logger.trace("nextPage() - start");

		if (controller.nextPage(this, currentPage)) {
			int nextOrderPage = currentPage.order + 1;
			WizardPage nextPage = null;
			for (WizardPage page : pages) {
				if (page.order == nextOrderPage){				
					nextPage = page;
					break;
				}			
			}
			nextPage.update();
			ShowPage(nextPage);
		}

		logger.trace("nextPage() - end");
	}
	
	protected void configureButtons(){
		logger.trace("configureButtons() - start");

		boolean isLastPage = currentPage.order == pages.getMax();
		boolean isFirstPage = currentPage.order == pages.getMin();
		btnNext.setEnabled(!isLastPage);
		btnPrevious.setEnabled(!isFirstPage);
		btnFinish.setEnabled(isLastPage);

		logger.trace("configureButtons() - end");
	}

	protected void previousPage() {
		logger.trace("previousPage() - start");

		if (controller.previousPage(this, currentPage)) {
			int prevOrderPage = currentPage.order - 1;
			WizardPage prevPage = null;
			for (WizardPage page : pages) {
				if (page.order == prevOrderPage){				
					prevPage = page;
					break;
				}			
			}
			//prevPage.update();
			ShowPage(prevPage);
		}

		logger.trace("previousPage() - end");
	}
	
	public Wizard(String title){		
		this(title, DEFAULT_DIMENSIONS, true);
		logger.trace("Wizard(String) - start");

		logger.trace("Wizard(String) - end");
	}
	
	private void resizeWizardPage() {
		logger.trace("resizeWizardPage() - start");

		int h = contentPanel.getHeight();
		int w = contentPanel.getWidth();
		Dimension size = new Dimension(w,h);
		for (Component wp : contentPanel.getComponents()) {
			wp.setSize(size);
		}

		logger.trace("resizeWizardPage() - end");
	}

	public void addPage(WizardPage page) {
		logger.trace("addPage(WizardPage) - start");

		pages.add(page);

		logger.trace("addPage(WizardPage) - end");
	}
	
	public void addPage(int i, WizardPage page) {
		logger.trace("addPage(int, WizardPage) - start");

		pages.add(i, page);

		logger.trace("addPage(int, WizardPage) - end");
	}
	
	protected void ShowPage(WizardPage page){
		logger.trace("ShowPage(WizardPage) - start");

		this.contentPanel.removeAll();
		this.contentPanel.add(page);
		this.currentPage = page;		
		this.configureButtons();
		this.contentPanel.revalidate();
		this.contentPanel.repaint();

		logger.trace("ShowPage(WizardPage) - end");
	}

	public WizardPage removePage(int page) {
		logger.trace("removePage(int) - start");

		WizardPage returnWizardPage = this.pages.remove(page);
		logger.trace("removePage(int) - end");
		return returnWizardPage;
	}
}
