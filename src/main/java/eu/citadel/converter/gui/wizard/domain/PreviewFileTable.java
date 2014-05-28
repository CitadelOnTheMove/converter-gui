package eu.citadel.converter.gui.wizard.domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class PreviewFileTable extends DefaultTableModel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(PreviewFileTable.class);

	private static int INIT_ROWS = 0;

	private static ArrayList<String> INIT_COLUMNS = new ArrayList<String>();

	public PreviewFileTable(List<String> firstRow, boolean firstRowIsData) {
		super(fillColumns(firstRow, firstRowIsData), INIT_ROWS);
		logger.trace("PreviewFileTable(List<String>, boolean) - start");

		logger.trace("PreviewFileTable(List<String>, boolean) - end");
	}

	private static void initColumns(final List<String> firstRow, final boolean firstRowIsData) {
		logger.trace("initColumns(List<String>, boolean) - start");

		if(firstRow != null) {
			INIT_COLUMNS.clear();
			Integer i = 0;		
			for (Object nameColumn : firstRow) {
				if (firstRowIsData) {
					i++;
					INIT_COLUMNS.add(i.toString());
				}else {
					INIT_COLUMNS.add(nameColumn.toString());				
				}
			}
		}

		logger.trace("initColumns(List<String>, boolean) - end");
	}

	private static String[] fillColumns(final List<String> firstRow, final boolean firstRowIsData){
		logger.trace("fillColumns(List<String>, boolean) - start");

		initColumns(firstRow, firstRowIsData);
		String[] returnStringArray = getColumnsNames();
		logger.trace("fillColumns(List<String>, boolean) - end");
		return returnStringArray;
	}

	public static String[] getColumnsNames() {
		logger.trace("getColumnsNames() - start");

		String[] returnStringArray = INIT_COLUMNS.toArray(new String[INIT_COLUMNS.size()]);
		logger.trace("getColumnsNames() - end");
		return returnStringArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
		logger.trace("getColumnClass(int) - start");

		logger.trace("getColumnClass(int) - end");
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		logger.trace("isCellEditable(int, int) - start");

		logger.trace("isCellEditable(int, int) - end");
		return false;
	}	
}
