package eu.citadel.converter.gui.wizard.domain;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class TableCellRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(TableCellRendererEditor.class);

	private ObserverJTable table;
	private Matrix<Cell> matrix;

	public TableCellRendererEditor(ObserverJTable table){
		logger.trace("TableCellRendererEditor(ObserverJTable) - start");

		this.table = table;
		this.matrix = new Matrix<Cell>(new Cell(), table, table.getRowsNames(0));

		logger.trace("TableCellRendererEditor(ObserverJTable) - end");
	}
	
	public Matrix<Cell> getMatrix() {
		logger.trace("getMatrix() - start");

		logger.trace("getMatrix() - end");
		return matrix;
	}

	public void addOneItem(int row, int column, ICustomComponent<?> value, boolean addButton) {
		logger.trace("addOneItem(int, int, ICustomComponent<?>, boolean) - start");

		Cell cell = matrix.getCell(row, column);
		if (cell.size() > 0) {
			logger.trace("addOneItem(int, int, ICustomComponent<?>, boolean) - end");
			return;
		}
		Item item = new Item(value, new RemoveItemCellAction(matrix, row, column, value), addButton);		
		cell.add(item);
		table.updateTable();

		logger.trace("addOneItem(int, int, ICustomComponent<?>, boolean) - end");
	}
	
	public void addItem(int row, int column, ICustomComponent<?> value, boolean addButton) {
		logger.trace("addItem(int, int, ICustomComponent<?>, boolean) - start");

		Cell cell = matrix.getCell(row, column);
		Item item = new Item(value, new RemoveItemCellAction(matrix, row, column, value), addButton);		
		cell.add(item);
		table.updateTable();

		logger.trace("addItem(int, int, ICustomComponent<?>, boolean) - end");
	}

	private JPanelCell cellToPanel(int row, int column) {
		logger.trace("cellToPanel(int, int) - start");
	
		JPanelCell panel = matrix.getCell(row, column).getPanel();

		logger.trace("cellToPanel(int, int) - end");
		return panel;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object arg1,
			boolean arg2, boolean arg3, int row, int column) {
		logger.trace("getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int) - start");

		Component returnComponent = cellToPanel(row, column);
		logger.trace("getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int) - end");
		return returnComponent;
	}


	@Override
	public Object getCellEditorValue() {
		logger.trace("getCellEditorValue() - start");

		logger.trace("getCellEditorValue() - end");
		return null;
	}	

	@Override
	public Component getTableCellEditorComponent(JTable table, Object arg1,
			boolean arg2, int row, int column) {
		logger.trace("getTableCellEditorComponent(JTable, Object, boolean, int, int) - start");

		Component returnComponent = cellToPanel(row, column);
		logger.trace("getTableCellEditorComponent(JTable, Object, boolean, int, int) - end");
		return returnComponent;
	}
}