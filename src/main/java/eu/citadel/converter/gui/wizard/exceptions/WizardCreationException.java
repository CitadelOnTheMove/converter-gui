package eu.citadel.converter.gui.wizard.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class WizardCreationException extends Exception {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(WizardCreationException.class);

	public WizardCreationException(String message) {
		super(message);
		logger.trace("WizardCreationException(String) - start");

		logger.trace("WizardCreationException(String) - end");
	}
	
}
