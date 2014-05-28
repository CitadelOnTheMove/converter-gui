package eu.citadel.converter.gui.wizard.domain;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cell implements ITableCell{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Cell.class);

	private int row = -1;
	private int column = -1;
	private JPanelCell panel = null;
	private ItemList list = new ItemList();
	
	public int size() {
		logger.trace("size() - start");

		int returnint = list.size();
		logger.trace("size() - end");
		return returnint;
	}
	
	public boolean isEmpty() {
		logger.trace("isEmpty() - start");

		boolean returnboolean = this.size() == 0;
		logger.trace("isEmpty() - end");
		return returnboolean;
	}
	
	@Override
	public ArrayList<Item> removeById(Object id) {
		logger.trace("removeById(Object) - start");

		ArrayList<Item> result = this.list.removeById(id);
		if (!result.isEmpty()) {
			Item di = result.get(0);
			getPanel().removeItems(id,di.component, di.button);
		}

		logger.trace("removeById(Object) - end");
		return result;
	}
	
	public JPanelCell getPanel() {
		logger.trace("getPanel() - start");

		if (panel == null) {
			panel = new JPanelCell();
		}

		logger.trace("getPanel() - end");
		return panel;
	}
	
	public void add(Item item) {
		logger.trace("add(Item) - start");

		boolean toAdd = list.add(item);
		if (toAdd) {			
			getPanel().addItems(item.component, item.button);
		}

		logger.trace("add(Item) - end");
	}

	public int getColumn() {
		logger.trace("getColumn() - start");

		logger.trace("getColumn() - end");
		return column;
	}

	public void setColumn(int column) {
		logger.trace("setColumn(int) - start");

		this.column = column;

		logger.trace("setColumn(int) - end");
	}

	public int getRow() {
		logger.trace("getRow() - start");

		logger.trace("getRow() - end");
		return row;
	}

	public void setRow(int row) {
		logger.trace("setRow(int) - start");

		this.row = row;

		logger.trace("setRow(int) - end");
	}
	
	public ItemList getItemList() {
		logger.trace("getItemList() - start");

		logger.trace("getItemList() - end");
		return list;
	}

	public void clear() {
		logger.trace("clear() - start");

		list.clear();
		panel = null;

		logger.trace("clear() - end");
	}
}