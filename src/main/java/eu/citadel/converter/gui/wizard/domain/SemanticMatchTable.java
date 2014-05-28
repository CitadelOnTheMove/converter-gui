package eu.citadel.converter.gui.wizard.domain;

import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.localization.Messages;

@SuppressWarnings("serial")
public class SemanticMatchTable extends DefaultTableModel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SemanticMatchTable.class);

	public static final int SEMANTIC_COLUMN_CATEGORY = 2;
	public static final int SEMANTIC_COLUMN_CONTEXT = 3;
	
	private static int INIT_ROWS = 0;
	
	private static String[] INIT_COLUMNS = new String[] {
		Messages.getString("SemanticTableModel.column1", "Colonna sorgente"), 
		Messages.getString("SemanticTableModel.column2", "Esempio"), 
		Messages.getString("SemanticTableModel.column3", "Categoria"), 
		Messages.getString("SemanticTableModel.column4", "Contesto")
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
		String.class, String.class, Object.class, Object.class
	};
	
	public SemanticMatchTable() {
		super(INIT_COLUMNS, INIT_ROWS);
		logger.trace("SemanticMatchTable() - start");

		logger.trace("SemanticMatchTable() - end");
	}	
	
	public SemanticMatchTable(int nrows) {
		super(INIT_COLUMNS, nrows);
		logger.trace("SemanticMatchTable(int) - start");

		logger.trace("SemanticMatchTable(int) - end");
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
