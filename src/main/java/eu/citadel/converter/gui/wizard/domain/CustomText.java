package eu.citadel.converter.gui.wizard.domain;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class CustomText extends JTextField implements ICustomComponent<JTextField>{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(CustomText.class);
	
	private String name,tooltip;
	
	public CustomText(String name) {
		super(name);
		logger.trace("CustomText(String) - start");

		this.name = name;
		super.setColumns(10);
		//super.setPreferredSize(new Dimension(100, 17));

		logger.trace("CustomText(String) - end");
	}
	
	public CustomText(String name, Dimension dimension) {
		super(name);
		logger.trace("CustomText(String, Dimension) - start");

		this.name = name;
		super.setSize(dimension);

		logger.trace("CustomText(String, Dimension) - end");
	}
	
	public String getName() {
		logger.trace("getName() - start");

		logger.trace("getName() - end");
		return this.name;
	}
	
	@Override
	public String toString() {
		return super.getText();
	}

	@Override
	public JTextField getComponent() {
		logger.trace("getComponent() - start");

		logger.trace("getComponent() - end");
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public void setName(String name) {
		logger.trace("setName(String) - start");

		super.setName(name);
		super.setText(name);
		this.name = name;

		logger.trace("setName(String) - end");
	}

	@Override
	public Color getColorBackground() {
		logger.trace("getColorBackground() - start");

		logger.trace("getColorBackground() - end");
		return Color.LIGHT_GRAY;
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
