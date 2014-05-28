package eu.citadel.converter.gui.wizard.domain;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class ObserverJTable extends JTable implements Observer{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ObserverJTable.class);	
	
	public ObserverJTable(SchemaMatchTable modelTable) {
		super(modelTable);
		logger.trace("ObserverJTable(SchemaMatchTable) - start");

		logger.trace("ObserverJTable(SchemaMatchTable) - end");
	}

	public ObserverJTable() {
		super();
		logger.trace("ObserverJTable() - start");

		logger.trace("ObserverJTable() - end");
	}

	@Override
	public void update(Observable o, Object arg) {
		logger.trace("update(Observable, Object) - start");

		if ((Boolean) arg) {
			updateTable();
		}

		logger.trace("update(Observable, Object) - end");
	}
	
	public void updateTable() {
		logger.trace("updateTable() - start");

		this.revalidate();
		this.repaint();

		logger.trace("updateTable() - end");
	}

	public String[] getRowsNames(int column) {
		logger.trace("getRowsNames(int) - start");

		String[] names = new String[this.getRowCount()];
		for (int i = 0; i < names.length; i++) {
			this.getValueAt(i, column);
		}

		logger.trace("getRowsNames(int) - end");
		return names;
	}	
}
