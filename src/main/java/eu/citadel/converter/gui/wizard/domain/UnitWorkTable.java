package eu.citadel.converter.gui.wizard.domain;

import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class UnitWorkTable extends DefaultTableModel{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(UnitWorkTable.class);
	
	private static int INIT_ROWS = 0;
	
	private static String[] INIT_COLUMNS = new String[] {
		"", "Filename", "Input type", "Match", "Schema", "Preview", "Download"
	};
	
	private static boolean[] INIT_EDITABLE = new boolean[] {
		true, false, false, false, false, false, false
	};
	
	@SuppressWarnings("rawtypes")
	private static Class[] columnTypes = new Class[] {
		Boolean.class, String.class, String.class, Object.class, Object.class, Object.class, Object.class
	};
	
	public UnitWorkTable() {
		super(INIT_COLUMNS, INIT_ROWS);
		logger.trace("UnitWorkTable() - start");

		logger.trace("UnitWorkTable() - end");
	}	
	
	public UnitWorkTable(int nrows) {
		super(INIT_COLUMNS, nrows);
		logger.trace("UnitWorkTable(int) - start");

		logger.trace("UnitWorkTable(int) - end");
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
