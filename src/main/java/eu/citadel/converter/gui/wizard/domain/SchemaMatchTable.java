package eu.citadel.converter.gui.wizard.domain;

import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.localization.Messages;

@SuppressWarnings("serial")
public class SchemaMatchTable extends DefaultTableModel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SchemaMatchTable.class);

	public static final int SCHEMA_COLUMN_MATCH = 2;
	
	private static int INIT_ROWS = 0;
	
	private static String[] INIT_COLUMNS = new String[] {
		Messages.getString("SchemaTableModel.column1", "Proprietà"), 
		Messages.getString("SchemaTableModel.column2", "Bersaglio"), 
		Messages.getString("SchemaTableModel.column3", "Contenuto"), 
		Messages.getString("SchemaTableModel.column4", "Stato")
	};
	
	private static boolean[] INIT_EDITABLE = new boolean[] {
		false, false, true, true
	};
	
	public String[] getColumnsNames() {
		logger.trace("getColumnsNames() - start");

		logger.trace("getColumnsNames() - end");
		return INIT_COLUMNS;
	}
	
	@SuppressWarnings("rawtypes")
	private static Class[] columnTypes = new Class[] {
		Object.class, Object.class, Object.class, Object.class
	};
	
	public SchemaMatchTable() {
		super(INIT_COLUMNS, INIT_ROWS);
		logger.trace("SchemaMatchTable() - start");

		logger.trace("SchemaMatchTable() - end");
	}	
	
	public SchemaMatchTable(int nrows) {
		super(INIT_COLUMNS, nrows);
		logger.trace("SchemaMatchTable(int) - start");

		logger.trace("SchemaMatchTable(int) - end");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
		logger.trace("getColumnClass(int) - start");

		Class returnClass = columnTypes[columnIndex];
		logger.trace("getColumnClass(int) - end");
		return returnClass;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
		logger.trace("isCellEditable(int, int) - start");

		boolean returnboolean = INIT_EDITABLE[column];
		logger.trace("isCellEditable(int, int) - end");
       return returnboolean;
    }
}
