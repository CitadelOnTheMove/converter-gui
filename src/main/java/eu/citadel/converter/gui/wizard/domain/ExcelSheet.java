package eu.citadel.converter.gui.wizard.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelSheet {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExcelSheet.class);

	private final String sheetName;
	private final Integer sheetNumber;
	
	public ExcelSheet(Integer n, String name) {
		logger.trace("ExcelSheet(Integer, String) - start");

		sheetNumber = n;
		sheetName = name;

		logger.trace("ExcelSheet(Integer, String) - end");
	}
	
	public Integer getSheetNumber() {
		logger.trace("getSheetNumber() - start");

		logger.trace("getSheetNumber() - end");
		return sheetNumber;
	}
	
	@Override
	public String toString() {
		return sheetNumber + " - " + sheetName;
	}
}
