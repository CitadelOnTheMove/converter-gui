package eu.citadel.converter.gui.wizard.domain;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.data.dataset.DatasetType;
import eu.citadel.converter.exceptions.DatasetException;
import eu.citadel.converter.gui.wizard.pages.CSVConfigPage;
import eu.citadel.converter.gui.wizard.pages.ExcelConfigPage;

public class ConfigFilePageFactory {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigFilePageFactory.class);
	
	public static WizardPage buildPage(String fileType, int id, int order, Wizard context) throws DatasetException, IOException {
		logger.trace("buildPage(String, int, int, Wizard) - start");

		WizardPage page = null;
		String title = "Configure" + fileType + " file";
		if (fileType == null) {
			
		}
		else if (fileType.equalsIgnoreCase(DatasetType.TYPE_CSV)) {
			page = new CSVConfigPage(id, title, order, context);
		}
		else if (fileType.equalsIgnoreCase(DatasetType.TYPE_EXCEL)) {
			page = new ExcelConfigPage(id, title, order, context);
		}

		logger.trace("buildPage(String, int, int, Wizard) - end");
		return page;
	}
	
}
