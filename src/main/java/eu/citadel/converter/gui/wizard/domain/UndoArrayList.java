package eu.citadel.converter.gui.wizard.domain;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class UndoArrayList<E> extends ArrayList<E> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(UndoArrayList.class);

	private ArrayList<E> lastCommitted;
	
	@SuppressWarnings("unchecked")
	public void commit() {
		logger.trace("commit() - start");

		lastCommitted = (ArrayList<E>) this.clone();

		logger.trace("commit() - end");
	}
	
	public void undo() {
		logger.trace("undo() - start");

		this.clear();
		if (lastCommitted != null) {
			this.addAll(lastCommitted);
		}

		logger.trace("undo() - end");
	}
}
