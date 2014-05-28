package eu.citadel.converter.gui.wizard.domain;

import java.awt.Color;
import java.awt.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.schema.obj.BasicSchemaObjAbstractValue;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueInteger;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueString;

public class ColumnCategoryContext<T extends Component> implements ICustomComponent<T>{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ColumnCategoryContext.class);

	private ICustomComponent<?> component;
	
	private BasicSchemaObjValueInteger id;
	private String name, tooltip;
	private BasicSchemaObjAbstractValue<?> category;
	private BasicSchemaObjAbstractValue<?> context;

	public ColumnCategoryContext() {
		logger.trace("ColumnCategoryContext() - start");

		component = new JLabelComponent("");

		logger.trace("ColumnCategoryContext() - end");
	}
	
	public BasicSchemaObjValueInteger getId() {
		logger.trace("getId() - start");

		logger.trace("getId() - end");
		return id;
	}
	
	@Override
	public String getTooltip() {
		return this.tooltip;
	}

	@Override
	public void setToolTip(String txt) {
		this.tooltip = txt;
	}

	public void setId(BasicSchemaObjValueInteger id) {
		logger.trace("setId(BasicSchemaObjValueInteger) - start");

		if (id != null && id.getValue() != null) {
			this.id = (BasicSchemaObjValueInteger) id;
		}

		logger.trace("setId(BasicSchemaObjValueInteger) - end");
	}

	public BasicSchemaObjAbstractValue<?> getCategory() {
		logger.trace("getCategory() - start");

		logger.trace("getCategory() - end");
		return category;
	}

	public void setCategory(BasicSchemaObjAbstractValue<?> category) {
		logger.trace("setCategory(BasicSchemaObjAbstractValue<?>) - start");

		if (category != null && category.getValue() != null) {
			this.category = (BasicSchemaObjValueString) category;
		}

		logger.trace("setCategory(BasicSchemaObjAbstractValue<?>) - end");
	}

	public BasicSchemaObjAbstractValue<?> getContext() {
		logger.trace("getContext() - start");

		logger.trace("getContext() - end");
		return context;
	}

	public void setContext(BasicSchemaObjAbstractValue<?> context) {
		logger.trace("setContext(BasicSchemaObjAbstractValue<?>) - start");

		if (context != null && context.getValue() != null) {
			this.context = context;
		}

		logger.trace("setContext(BasicSchemaObjAbstractValue<?>) - end");
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getName() {
		logger.trace("getName() - start");

		logger.trace("getName() - end");
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj instanceof ColumnCategoryContext){
			return this.id.equals(((ColumnCategoryContext<?>) obj).id);
		}
		return false;
	}

	@Override
	public void setName(String name) {
		logger.trace("setName(String) - start");

		this.name = name;
		component.setName(name);

		logger.trace("setName(String) - end");
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getComponent() {
		logger.trace("getComponent() - start");

		T returnT = (T) component;
		logger.trace("getComponent() - end");
		return returnT;
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
}
