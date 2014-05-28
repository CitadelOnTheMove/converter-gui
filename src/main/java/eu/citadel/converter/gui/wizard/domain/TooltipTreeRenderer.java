package eu.citadel.converter.gui.wizard.domain;

import java.awt.Component;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.utility.GuiUtility;
import eu.citadel.converter.schema.BasicSchemaUtils;

@SuppressWarnings("serial")
public class TooltipTreeRenderer  extends DefaultTreeCellRenderer  {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(TooltipTreeRenderer.class);
	
	private static Map<String, String> map = BasicSchemaUtils.getMap(BasicSchemaUtils.CATEGORY_DESCRIPTION);
	
	  @Override
	  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		logger.trace("getTreeCellRendererComponent(JTree, Object, boolean, boolean, boolean, int, boolean) - start");

	    final Component rc = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
	    
	    String untraslated = eu.citadel.converter.gui.wizard.localization.Messages.untranslate(value.toString(), GuiUtility.getCategoriesMap());
	    String tooltipKey = map.get(untraslated);
	    if (tooltipKey != null) {
		    String tooltip = eu.citadel.converter.localization.Messages.getString(tooltipKey);
		    this.setToolTipText(tooltip);
	    }

		logger.trace("getTreeCellRendererComponent(JTree, Object, boolean, boolean, boolean, int, boolean) - end");
	    return rc;
	  }
	}