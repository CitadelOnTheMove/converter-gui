package eu.citadel.converter.gui.wizard.domain;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class ItemList extends ArrayList<Item> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ItemList.class);

	@Override
	public boolean add(Item e) {
		logger.trace("add(Item) - start");

		if (!(e.component instanceof CustomText) && this.contains(e)) {
			logger.trace("add(Item) - end");
			return false;
		}
		boolean returnboolean = super.add(e);
		logger.trace("add(Item) - end");
		return returnboolean;
	}
	
	public ArrayList<Item> removeById(Object id) {
		logger.trace("removeById(Object) - start");

		ArrayList<Item> cl = new ArrayList<Item>();
		Item toRemove = null;
		for (Item item : this) {
			if (item.component.equals(id)) {
				toRemove = item;
				break;
			}
		}
		if (toRemove != null && remove(toRemove)) {
			cl.add(toRemove); 
		}

		logger.trace("removeById(Object) - end");
		return cl;
	}
}
