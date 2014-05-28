package eu.citadel.converter.gui.wizard.domain;

import java.awt.Color;
import java.awt.Component;

public interface ICustomComponent<T extends Component> {
	
	String getTooltip();
	
	void setToolTip(String txt);

	String getName();
	
	T getComponent();

	void setName(String columnName);

	Color getColorBackground();
	
	void setColorBackground(Color c);
	
}
