package eu.citadel.converter.gui.wizard.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UndoValue<E> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(UndoValue.class);

	private E lastValue = null;
	private E value = null;
	
	private boolean isCommitted;
	
	public UndoValue(E value) {
		logger.trace("UndoValue(E) - start");

		this.value = value;
		isCommitted = false;

		logger.trace("UndoValue(E) - end");
	}
	
	public UndoValue() {
		this(null);
		logger.trace("UndoValue() - start");

		logger.trace("UndoValue() - end");
	}

	public boolean isCommitted() {
		logger.trace("isCommitted() - start");

		logger.trace("isCommitted() - end");
		return isCommitted;
	}	
	
	public E getValue() {
		logger.trace("getValue() - start");

		logger.trace("getValue() - end");
		return value;
	}

	public void setValue(E value) {
		logger.trace("setValue(E) - start");

		this.value = value;
		isCommitted = false;

		logger.trace("setValue(E) - end");
	}

	public void commit() {
		logger.trace("commit() - start");
		
		lastValue = value;
		isCommitted = true;

		logger.trace("commit() - end");
	}
	
	public void undo() {
		logger.trace("undo() - start");

		value = lastValue;
		isCommitted = true;

		logger.trace("undo() - end");
	}
	
	public boolean isSync() {
		logger.trace("isSync() - start");

		if (value == null && lastValue == null) {
			logger.trace("isSync() - end");
			return true;
		}
		boolean returnboolean = value.equals(lastValue);
		logger.trace("isSync() - end");
		return returnboolean;
	}
	
}
