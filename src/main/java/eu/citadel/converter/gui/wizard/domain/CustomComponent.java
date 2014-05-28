package eu.citadel.converter.gui.wizard.domain;

import java.awt.Color;
import java.awt.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomComponent<T extends Component> implements ICustomComponent<T> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(CustomComponent.class);
	private String tooltip;

	private T component;
	
	public CustomComponent(T c) {
		logger.trace("CustomComponent(T) - start");

		this.component = c;

		logger.trace("CustomComponent(T) - end");
	}
	private String name;

	@Override
	public String getName() {
		logger.trace("getName() - start");

		logger.trace("getName() - end");
		return this.name;
	}

	@Override
	public T getComponent() {
		logger.trace("getComponent() - start");

		logger.trace("getComponent() - end");
		return this.component;
	}

	@Override
	public void setName(String name) {
		logger.trace("setName(String) - start");

		this.name = name;

		logger.trace("setName(String) - end");
	}

	@Override
	public Color getColorBackground() {
		logger.trace("getColorBackground() - start");

		logger.trace("getColorBackground() - end");
		return null;
	}

	@Override
	public void setColorBackground(Color c) {
		logger.trace("setColorBackground(Color) - start");

		this.setColorBackground(c);

		logger.trace("setColorBackground(Color) - end");
	}

	@Override
	public String getTooltip() {
		return this.tooltip;
	}

	@Override
	public void setToolTip(String txt) {
		this.tooltip = txt;
	}

}
