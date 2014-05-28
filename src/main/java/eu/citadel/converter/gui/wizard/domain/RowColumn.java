package eu.citadel.converter.gui.wizard.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowColumn extends Pair<Integer, Integer> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RowColumn.class);

	public RowColumn(Integer row, Integer column) {
		super(row, column);
		logger.trace("RowColumn(Integer, Integer) - start");

		logger.trace("RowColumn(Integer, Integer) - end");
	}
	
	public Integer getRow() {
		logger.trace("getRow() - start");

		Integer returnInteger = getLeft();
		logger.trace("getRow() - end");
		return returnInteger;
	}

	public Integer getColumn() {
		logger.trace("getColumn() - start");

		Integer returnInteger = getRight();
		logger.trace("getColumn() - end");
		return returnInteger;
	}
}
