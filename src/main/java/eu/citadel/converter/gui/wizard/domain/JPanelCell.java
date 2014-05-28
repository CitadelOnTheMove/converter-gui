package eu.citadel.converter.gui.wizard.domain;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class JPanelCell extends JPanel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(JPanelCell.class);

	private ArrayList<Object> ids = new ArrayList<Object>();
	private boolean canAdd = true;

	public JPanelCell() {
		logger.trace("JPanelCell() - start");

		setLayout(new MigLayout("", "[center]", "[center]"));

		logger.trace("JPanelCell() - end");
	}
	
	public void enable() {
		logger.trace("enable() - start");

		canAdd = true;

		logger.trace("enable() - end");
	}
	
	public void disable() {
		logger.trace("disable() - start");

		canAdd = false;

		logger.trace("disable() - end");
	}

	public void removeItems(Object id, ICustomComponent<?> component, JButton button) {
		logger.trace("removeItems(Object, ICustomComponent<?>, JButton) - start");

		if (this.ids.remove(id)) {
			for (Component container : this.getComponents()) {
				for (Component c : ((JPanel) container).getComponents()) {
					if (c.equals(component) || c.equals(button)) {
						this.remove(container);
						this.refreshPanel();

						logger.trace("removeItems(Object, ICustomComponent<?>, JButton) - end");
						return;
					}
				}
			}

		}

		logger.trace("removeItems(Object, ICustomComponent<?>, JButton) - end");
	}

	public void addItems(ICustomComponent<?> component, RoundButton button) {
		logger.trace("addItems(ICustomComponent<?>, RoundButton) - start");

		if (canAdd){
			ids.add(component);
			JPanel itemContainer = new JPanel();
			FlowLayout flowLayout = (FlowLayout) itemContainer.getLayout();
			flowLayout.setVgap(2);
			flowLayout.setHgap(2);
			itemContainer.add(component.getComponent());
			if (button != null) {
				itemContainer.add(button);
			}
			itemContainer.setBackground(component.getColorBackground());
			add(itemContainer, "alignx center");
			refreshPanel();
		}

		logger.trace("addItems(ICustomComponent<?>, RoundButton) - end");
	}

	private void refreshPanel() {
		logger.trace("refreshPanel() - start");

		this.revalidate();
		this.repaint();

		logger.trace("refreshPanel() - end");
	}	
}