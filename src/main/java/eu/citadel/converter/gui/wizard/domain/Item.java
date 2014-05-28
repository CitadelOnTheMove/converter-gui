package eu.citadel.converter.gui.wizard.domain;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Item {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Item.class);

	private final static Icon icon = new ImageIcon(Item.class.getClassLoader().getResource("images/icons/delete.png"));

	public ICustomComponent<?> component;
	public RoundButton button = null;
	public final String text;
	
	public Item(ICustomComponent<?> obj, RemoveItemCellAction deleteAction, boolean addButton) {
		logger.trace("Item(ICustomComponent<?>, RemoveItemCellAction, boolean) - start");

		this.component = obj;
		this.text = component.getName();
		if (addButton) {
			this.button = new RoundButton(icon);
			this.button.addActionListener(deleteAction);
		}

		logger.trace("Item(ICustomComponent<?>, RemoveItemCellAction, boolean) - end");
	}

	@Override
	public boolean equals(Object item) {
		if (item == null || !(item instanceof Item)) return false;
		Item i = (Item)item;
		return this.component.equals(i.component);
	}
	
	@Override
	public String toString() {
		return this.component.getName();
	}
}
