package eu.citadel.converter.gui.wizard.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rits.cloning.Cloner;

public class Matrix<E extends ITableCell> extends Observable{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Matrix.class);

	private Row<Column<E>> matrix;
	private final E prototype;
	private Cloner cloner;
	
	public Matrix(E prototype) {
		this(prototype, null, new String[0]);
		logger.trace("Matrix(E) - start");

		logger.trace("Matrix(E) - end");
	}
	
	public Matrix(E prototype, Observer observer) {
		this(prototype, observer, new String[0]);
		logger.trace("Matrix(E, Observer) - start");		

		logger.trace("Matrix(E, Observer) - end");
	}

	public Matrix(E prototype, Observer observer, String[] rowsNames) {
		logger.trace("Matrix(E, Observer, String[]) - start");

		this.prototype = prototype;
		matrix = new Row<Column<E>>();
		cloner = new Cloner();
		this.addObserver(observer);

		logger.trace("Matrix(E, Observer, String[]) - end");
	}

	public void addCell(int row, int column, E elem) {
		logger.trace("addCell(int, int, E) - start");

		Column<E> col = getColumns(row);
		col.add(column, elem);

		logger.trace("addCell(int, int, E) - end");
	}
	
	public E resetCell(int row, int column) {
		logger.trace("resetCell(int, int) - start");

		E returnE = resetCell(row, column, null);
		logger.trace("resetCell(int, int) - end");
		return returnE;
	}
	
	public E resetCell(int row, int column, E newItem) {
		logger.trace("resetCell(int, int, E) - start");

		Column<E> cols = getColumns(row);
		E oldItem = cols.get(column);
		cols.remove(column);
		if (newItem == null) {
			newItem = cloner.deepClone(prototype);
			newItem.setRow(row);
			newItem.setColumn(column);
		}
		cols.add(column, newItem);

		logger.trace("resetCell(int, int, E) - end");
		return oldItem;
	}
	
	public Column<E> getColumns(int row) {
		logger.trace("getColumns(int) - start");

		checkRowCapacity(row);
		Column<E> returnColumn = matrix.get(row);
		logger.trace("getColumns(int) - end");
		return returnColumn;
	}
	
	public int sizeRows() {
		logger.trace("sizeRows() - start");

		int returnint = matrix.size();
		logger.trace("sizeRows() - end");
		return returnint;
	}
	
	public int sizeColumns() {
		logger.trace("sizeColumns() - start");

		int returnint = getColumns(0).size();
		logger.trace("sizeColumns() - end");
		return returnint;
	}
	
	public E getCell(int row, int column) {
		logger.trace("getCell(int, int) - start");

		checkRowCapacity(row);
		checkColumnCapacity(row, column);
		E returnE = matrix.get(row).get(column);
		logger.trace("getCell(int, int) - end");
		return returnE;
	}
	
	private void checkRowCapacity(int iRow) {
		logger.trace("checkRowCapacity(int) - start");

		int elemToCreate = iRow - matrix.size();
		for (int i = 0; i <= elemToCreate; i++) {
			matrix.add(new Column<E>());
		}

		logger.trace("checkRowCapacity(int) - end");
	}
	
	private void checkColumnCapacity(int row, int column) {
		logger.trace("checkColumnCapacity(int, int) - start");

		Column<E> col = matrix.get(row);
		int elemToCreate = column - col.size();
		for (int i = 0; i <= elemToCreate; i++) {			
			E clone = cloner.deepClone(prototype);
			clone.setRow(row);
			clone.setColumn(column);
			col.add(clone);
		}

		logger.trace("checkColumnCapacity(int, int) - end");
	}

	public boolean removeById(int row, int column, Object id) {
		logger.trace("removeById(int, int, Object) - start");

		boolean r = false;
		E cell = getCell(row, column);
		ArrayList<Item> result = cell.removeById(id);
		if (!result.isEmpty()) {					
			r = true;
		}
		notifyObserver(r);

		logger.trace("removeById(int, int, Object) - end");
		return r;
	}
	
	private void notifyObserver(boolean flag) {
		logger.trace("notifyObserver(boolean) - start");

		setChanged();
		notifyObservers(flag);

		logger.trace("notifyObserver(boolean) - end");
	}
	
	public ArrayList<E> getCells() {
		logger.trace("getCells() - start");

		int ncolumns = sizeColumns();
		Boolean[] array = new Boolean[ncolumns];
		Arrays.fill(array, Boolean.TRUE);
		ArrayList<E> returnArrayList = getCells(array);
		logger.trace("getCells() - end");
		return returnArrayList;
	}
	
	public ArrayList<E> getCells(Boolean[] onlyThisColumns) {
		logger.trace("getCells(Boolean[]) - start");
		
		int nrows = sizeRows();
		int ncolumns = sizeColumns();
		ArrayList<E> cells = new ArrayList<E>(nrows*ncolumns);
		for (int r = 0; r < nrows; r++) {
			for (int c = 0; c < ncolumns; c++) {
				if (onlyThisColumns[c]) {
					cells.add(getCell(r, c));
				}
			}
		}

		logger.trace("getCells(Boolean[]) - end");
		return cells;
	}
}

@SuppressWarnings("serial")
class Row<E> extends ArrayList<E> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Row.class);
}

@SuppressWarnings("serial")
class Column<E> extends ArrayList<E> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Column.class);
}