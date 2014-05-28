package eu.citadel.converter.gui.wizard.domain;

import java.awt.Color;

import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class JLabelComponent extends JLabel implements ICustomComponent<JLabel> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(JLabelComponent.class);

	private String name, tooltip;
	
	public JLabelComponent(String label) {
		super(label);
		logger.trace("JLabelComponent(String) - start");

		this.name = label;
		//super.setPreferredSize(new Dimension(80, 15));

		logger.trace("JLabelComponent(String) - end");
	}
	
	@Override
	public String getTooltip() {
		return this.tooltip;
	}

	@Override
	public void setToolTip(String txt) {
		this.tooltip = txt;
	}
	
	@Override
	public String getName() {
		logger.trace("getName() - start");

		logger.trace("getName() - end");
		return this.name;
	}
	
	@Override
	public JLabel getComponent() {
		logger.trace("getComponent() - start");

		logger.trace("getComponent() - end");
		return this;
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
	public String toString() {
		return this.name;
	}

	@Override
	public Color getColorBackground() {
		logger.trace("getColorBackground() - start");

		logger.trace("getColorBackground() - end");
		return Color.LIGHT_GRAY;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof JLabelComponent)) return false;
		return this.name.equals(((JLabelComponent)obj).name);
	}

	@Override
	public void setColorBackground(Color c) {
		logger.trace("setColorBackground(Color) - start");

		this.setBackground(c);

		logger.trace("setColorBackground(Color) - end");
	}
	
}
