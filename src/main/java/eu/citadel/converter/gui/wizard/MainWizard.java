package eu.citadel.converter.gui.wizard;

import java.awt.Rectangle;
import java.awt.event.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.pages.DatatypeSelection;
import eu.citadel.converter.gui.wizard.pages.ExportSchemaPage;
import eu.citadel.converter.gui.wizard.pages.LoadFilesPage;
import eu.citadel.converter.gui.wizard.pages.ResultFileSave;
import eu.citadel.converter.gui.wizard.pages.ResultPreviewPage;
import eu.citadel.converter.gui.wizard.pages.SelectFilePage;
import eu.citadel.converter.gui.wizard.pages.SemanticMatchPage;

@SuppressWarnings("serial")
public class MainWizard extends Wizard{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(MainWizard.class);

	private static String title = "Wizard";
	
	public MainWizard() throws SecurityException, NoSuchMethodException {		
		this(title);
		logger.trace("MainWizard() - start");

		logger.trace("MainWizard() - end");
	}
	
	public MainWizard(String title) throws SecurityException, NoSuchMethodException {
		this(title, null);
		logger.trace("MainWizard(String) - start");

		logger.trace("MainWizard(String) - end");
	}

	public MainWizard(String title, Rectangle rect) throws SecurityException, NoSuchMethodException {
		super(title, rect, false);
		logger.trace("MainWizard(String, Rectangle) - start");

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		// Aggiungo le pagine
		WizardPage firstPage = new LoadFilesPage(0, "Load files", 0, this);		
		addPage(firstPage);
		addPage(new SelectFilePage(1, "Select file", 1, this));
		addPage(null);		
		addPage(new SemanticMatchPage(3, "Semantic match", 3, this));
		addPage(new DatatypeSelection(4, "Datatype selection", 4, this));
		addPage(new ExportSchemaPage(5, "Export schema", 5, this));
		addPage(new ResultPreviewPage(6, "Result preview", 6, this));
		addPage(new ResultFileSave(7, "Result save", 7, this));
		ShowPage(firstPage);
		this.setVisible(true);

		logger.trace("MainWizard(String, Rectangle) - end");
	}

	@Override
	public void windowActivated(WindowEvent e) {
		logger.trace("windowActivated(WindowEvent) - start");

		// TODO Auto-generated method stub
		
		logger.trace("windowActivated(WindowEvent) - end");
	}

	@Override
	public void windowClosing(WindowEvent e) {
		logger.trace("windowClosing(WindowEvent) - start");

		this.abort();

		logger.trace("windowClosing(WindowEvent) - end");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		logger.trace("windowDeactivated(WindowEvent) - start");

		// TODO Auto-generated method stub		

		logger.trace("windowDeactivated(WindowEvent) - end");
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		logger.trace("windowDeiconified(WindowEvent) - start");

		// TODO Auto-generated method stub

		logger.trace("windowDeiconified(WindowEvent) - end");
	}

	@Override
	public void windowIconified(WindowEvent e) {
		logger.trace("windowIconified(WindowEvent) - start");

		// TODO Auto-generated method stub		

		logger.trace("windowIconified(WindowEvent) - end");
	}

	@Override
	public void windowOpened(WindowEvent e) {
		logger.trace("windowOpened(WindowEvent) - start");

		// TODO Auto-generated method stub		

		logger.trace("windowOpened(WindowEvent) - end");
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		logger.trace("windowClosed(WindowEvent) - start");

		// TODO Auto-generated method stub		

		logger.trace("windowClosed(WindowEvent) - end");
	}
}
