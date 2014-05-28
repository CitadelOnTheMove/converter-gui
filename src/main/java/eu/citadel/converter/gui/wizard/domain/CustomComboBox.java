package eu.citadel.converter.gui.wizard.domain;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.schema.obj.BasicSchemaObjAbstractValue;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueBoolean;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueDouble;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueInteger;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueObject;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueString;

public class CustomComboBox extends JComboBox<ComboBoxItem> implements ICustomComponent<JComboBox<ComboBoxItem>> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(CustomComboBox.class);
	private String tooltip;
	private static final long serialVersionUID = 1L;

	public CustomComboBox(List<BasicSchemaObjAbstractValue<?>> list, BasicSchemaObjValueBoolean mandatory) {
		super(init(list, mandatory));
		logger.trace("CustomComboBox(List<BasicSchemaObjAbstractValue<?>>, BasicSchemaObjValueBoolean) - start");

		this.setRenderer(new ComboBoxItemRenderer());

		logger.trace("CustomComboBox(List<BasicSchemaObjAbstractValue<?>>, BasicSchemaObjValueBoolean) - end");
	}

	private static Vector<ComboBoxItem> init(List<BasicSchemaObjAbstractValue<?>> list, BasicSchemaObjValueBoolean mandatory) {
		logger.trace("init(List<BasicSchemaObjAbstractValue<?>>, BasicSchemaObjValueBoolean) - start");

		Vector<ComboBoxItem> model;
		int nOptions = list.size();
		if (mandatory == null || ( mandatory != null && !mandatory.getValue() )) {
			 model = new Vector<>(++nOptions);
			 model.addElement(new ComboBoxItem("", ""));
		} else {
			model = new Vector<>(nOptions);
		}
		for (BasicSchemaObjAbstractValue<?> v : list) {
			String id = null,value = null;
			if(v instanceof BasicSchemaObjValueString || 
					v instanceof BasicSchemaObjValueInteger ||
					v instanceof BasicSchemaObjValueDouble ||
					v instanceof BasicSchemaObjValueBoolean) {
				id = value = v.getValue().toString();
			} else if (v instanceof BasicSchemaObjValueObject) {
				Map<String, BasicSchemaObjAbstractValue<?>> map = ((BasicSchemaObjValueObject)v).getValue();
				for (Entry<String, BasicSchemaObjAbstractValue<?>> es : map.entrySet()) {
					id = es.getKey();
					value = es.getValue().toString();
				}
			}
			model.addElement(new ComboBoxItem(id, value));
		}

		logger.trace("init(List<BasicSchemaObjAbstractValue<?>>, BasicSchemaObjValueBoolean) - end");
		return model;
	}

	@Override
	public JComboBox<ComboBoxItem> getComponent() {
		logger.trace("getComponent() - start");

		logger.trace("getComponent() - end");
		return this;
	}
	
	@Override
	public String toString() {
		return ((ComboBoxItem) this.getSelectedItem()).getDescription();
	}
	
	@Override
	public String getName() {
		logger.trace("getName() - start");

		String returnString = ((ComboBoxItem) this.getSelectedItem()).getDescription();
		logger.trace("getName() - end");
		return returnString;
	}

	@Override
	public Color getColorBackground() {
		logger.trace("getColorBackground() - start");

		// TODO Auto-generated method stub

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

class ComboBoxItemRenderer extends BasicComboBoxRenderer
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ComboBoxItemRenderer.class);

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
		logger.trace("getListCellRendererComponent(JList, Object, int, boolean, boolean) - start");

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null)
        {
        	ComboBoxItem item = (ComboBoxItem)value;
            setText( item.getId() );
        }

        if (index == -1)
        {
        	ComboBoxItem item = (ComboBoxItem)value;
            setText( "" + item.getId() );
        }

		logger.trace("getListCellRendererComponent(JList, Object, int, boolean, boolean) - end");
       return this;
    }
}

class ComboBoxItem
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ComboBoxItem.class);

    private String id;
    private String description;

    public ComboBoxItem(String id, String description)
    {
		logger.trace("ComboBoxItem(String, String) - start");

        this.id = eu.citadel.converter.localization.Messages.getString(id);
        this.description = description;

		logger.trace("ComboBoxItem(String, String) - end");
    }

    public String getId()
    {
		logger.trace("getId() - start");

		logger.trace("getId() - end");
        return id;
    }

    public String getDescription()
    {
		logger.trace("getDescription() - start");

		logger.trace("getDescription() - end");
        return description;
    }

    public String toString()
    {
        return description;
    }
}
