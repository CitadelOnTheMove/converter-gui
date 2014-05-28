package eu.citadel.converter.gui.wizard.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveItemCellAction implements ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RemoveItemCellAction.class);

	private final int row;
	private final int column;
	private final Object id;
	private final Matrix<Cell> matrix;

	public RemoveItemCellAction(Matrix<Cell> matrix, int row, int column, Object id) {
		logger.trace("RemoveItemCellAction(Matrix<Cell>, int, int, Object) - start");

		this.row = row;
		this.column = column;
		this.id = id;
		this.matrix = matrix;

		logger.trace("RemoveItemCellAction(Matrix<Cell>, int, int, Object) - end");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		logger.trace("actionPerformed(ActionEvent) - start");

		matrix.removeById(row, column, id);

		logger.trace("actionPerformed(ActionEvent) - end");
	}

}