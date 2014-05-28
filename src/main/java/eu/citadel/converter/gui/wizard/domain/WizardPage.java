package eu.citadel.converter.gui.wizard.domain;

import java.io.IOException;

import javax.swing.JPanel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xerces.impl.dv.DatatypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.exceptions.ConverterException;
import eu.citadel.converter.gui.wizard.controller.GuiController;

@SuppressWarnings("serial")
public abstract class WizardPage extends JPanel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(WizardPage.class);
	
	protected int id;
	protected String title;
	protected int order;
	protected Wizard context;
	protected GuiController controller;

	public WizardPage(int id, String title, int order, Wizard context) {
		logger.trace("WizardPage(int, String, int, Wizard) - start");

		this.id = id;
		this.title = title;
		this.order = order;
		this.context = context;
		controller = GuiController.getInstance();

		logger.trace("WizardPage(int, String, int, Wizard) - end");
	}
	
	protected abstract void configureButtons();
	protected abstract void update() throws IOException, ConverterException, InvalidFormatException, DatatypeException, Exception;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		WizardPage wp = (WizardPage)obj;
		return wp.id == this.id;
	}
}
