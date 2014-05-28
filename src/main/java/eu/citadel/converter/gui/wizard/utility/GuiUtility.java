package eu.citadel.converter.gui.wizard.utility;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import eu.citadel.converter.commons.Type;
import eu.citadel.converter.data.dataset.CsvDataset;
import eu.citadel.converter.data.dataset.CsvType;
import eu.citadel.converter.data.dataset.ExcelDataset;
import eu.citadel.converter.data.dataset.ExcelType;
import eu.citadel.converter.data.metadata.BasicMetadataUtils;
import eu.citadel.converter.exceptions.DatasetException;
import eu.citadel.converter.gui.wizard.domain.Cell;
import eu.citadel.converter.gui.wizard.domain.CustomComponent;
import eu.citadel.converter.gui.wizard.domain.Datatype;
import eu.citadel.converter.gui.wizard.domain.ICustomComponent;
import eu.citadel.converter.gui.wizard.domain.ItemList;
import eu.citadel.converter.gui.wizard.domain.JLabelComponent;
import eu.citadel.converter.gui.wizard.domain.ObserverJTable;
import eu.citadel.converter.gui.wizard.domain.Pair;
import eu.citadel.converter.gui.wizard.domain.RoundButton;
import eu.citadel.converter.gui.wizard.domain.Status;
import eu.citadel.converter.gui.wizard.domain.UnitWork;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.schema.obj.BasicSchemaObjAbstractValue;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueBoolean;

public class GuiUtility {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(GuiUtility.class);

	private final static Icon iconError = new ImageIcon(ObserverJTable.class.getClassLoader().getResource("images/icons/error.png"));
	private final static Icon iconWarning = new ImageIcon(ObserverJTable.class.getClassLoader().getResource("images/icons/warning.png"));
	private final static Icon iconOk = new ImageIcon(ObserverJTable.class.getClassLoader().getResource("images/icons/ok.png"));
	private final static Icon iconMandatory = new ImageIcon(ObserverJTable.class.getClassLoader().getResource("images/icons/mandatory.png"));
	
	private static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	private static List<Pair<String, String>> categoriesMap = Messages.getStringMap(BasicMetadataUtils.getMap(BasicMetadataUtils.CATEGORY));
	private static List<Pair<String, String>> contextMap = Messages.getStringMap(BasicMetadataUtils.getMap(BasicMetadataUtils.CONTEXT));
	private static List<String> categoriesList = Messages.getString(Lists.newArrayList(BasicMetadataUtils.getMap(BasicMetadataUtils.CATEGORY).values()));
	private static List<String> contextList = Messages.getString(Lists.newArrayList(BasicMetadataUtils.getMap(BasicMetadataUtils.CONTEXT).values()));
	private static final String originalDefaultCategory = Lists.newArrayList(BasicMetadataUtils.getMap(BasicMetadataUtils.DEFAULT_CATEGORY).keySet()).get(0);
	
	public static void initCategoriesContext(){
		categoriesMap = Messages.getStringMap(BasicMetadataUtils.getMap(BasicMetadataUtils.CATEGORY));
		contextMap = Messages.getStringMap(BasicMetadataUtils.getMap(BasicMetadataUtils.CONTEXT));
		categoriesList = Messages.getString(Lists.newArrayList(BasicMetadataUtils.getMap(BasicMetadataUtils.CATEGORY).values()));
		contextList = Messages.getString(Lists.newArrayList(BasicMetadataUtils.getMap(BasicMetadataUtils.CONTEXT).values()));
	}
	
	public static List<String> getCategorieslist() {
		logger.trace("getCategorieslist() - start");

		logger.trace("getCategorieslist() - end");
		return categoriesList;
	}

	public static List<String> getContextList() {
		logger.trace("getContextList() - start");

		logger.trace("getContextList() - end");
		return contextList;
	}

	public static void centerWindow(Component comp) {
		logger.trace("centerWindow(Component) - start");
		
		comp.setLocation(SCREEN_DIMENSION.width/2-comp.getSize().width/2, SCREEN_DIMENSION.height/2-comp.getSize().height/2);

		logger.trace("centerWindow(Component) - end");
	}
	
	public static void showInfoBox(String title, String message, int icon)
    {
		logger.trace("showInfoBox(String, String, int) - start");

        JOptionPane.showMessageDialog(null, message, title, icon);

		logger.trace("showInfoBox(String, String, int) - end");
    }
	
	public static int showConfirmationBox(String title, String message, int icon)
    {
		logger.trace("showConfirmationBox(String, String, int) - start");

		int returnint = JOptionPane.showConfirmDialog(null, message, title, icon);
		logger.trace("showConfirmationBox(String, String, int) - end");
        return returnint;
    }

	public static int findPosition(Container parent, Component target) {
		logger.trace("findPosition(Container, Component) - start");

		Component[] cs = parent.getComponents();
		for (int i = 0; i < cs.length; i++) {
			if (cs[i].equals(target)) {
				logger.trace("findPosition(Container, Component) - end");
				return i;
			}
		}
		int returnint = -1;
		logger.trace("findPosition(Container, Component) - end");
		return returnint;
	}
	
	public static int validSemanticMatch(UnitWork uw) throws DatasetException {
		logger.trace("validSemanticMatch(UnitWork) - start");

		int n = 0;
		ArrayList<Cell> cells = uw.semanticMatrix.getCells(new Boolean[]{false, false, true, false});	
		int nColumnsFile = 0;
		try {
			Type t = uw.getTargetFileValue().getMetadata().getObjType();			
			if (t instanceof CsvType) {
				nColumnsFile = ((CsvDataset) uw.getDataset()).getFirstRow().size();
			} else if (t instanceof ExcelType) {
				nColumnsFile = ((ExcelDataset) uw.getDataset()).getFirstRow().size();
			}
			
		} catch (IOException e) {
			logger.warn("validSemanticMatch(UnitWork) - exception ignored", e);
			
		}
		for (Cell cell : cells) {
			if (cell.isEmpty()) n++;
		}
		n += (nColumnsFile - cells.size());

		logger.trace("validSemanticMatch(UnitWork) - end");
		return n;
	}
	
	public static Status[] validSchemaMatch(UnitWork uw, int[] idToRowMap) {
		logger.trace("validSchemaMatch(UnitWork, int[]) - start");

		ArrayList<Cell> cells = uw.datatypeMatrix.getCells(new Boolean[]{false, false, true, false});
		int nCells = cells.size();
		Status[] result = new Status[uw.datatypeList.size()];
		int id = -1;
		for (Datatype dt : uw.datatypeList) {
			id++;
			boolean isEmpty = true;
			int tableRow = idToRowMap[id];
			if (tableRow<nCells) {
				ItemList itemList = cells.get(tableRow).getItemList();
				isEmpty = itemList.isEmpty();				
			}
			if (dt.getMandatory() != null) {
				if (dt.getMandatory().getValue()) {
					if (isEmpty)
						result[id] = Status.Error;
					else
						result[id] = Status.Ok;
				}
				else if (isEmpty){
					result[id] = Status.Warning;
				}else result[id] = Status.Ok;
			} 
		}

		logger.trace("validSchemaMatch(UnitWork, int[]) - end");
		return result;
	}

		public static String getDefaultCategory() {
		logger.trace("getDefaultCategory() - start");

		logger.trace("getDefaultCategory() - end");
			return originalDefaultCategory;
		}

	public static CustomComponent<JButton> displayMandatory(BasicSchemaObjValueBoolean mandatory) {
		logger.trace("displayMandatory(BasicSchemaObjValueBoolean) - start");

		if (mandatory != null) {
			if (mandatory.getValue()) {
				CustomComponent<JButton> returnCustomComponent = new CustomComponent<JButton>(new RoundButton(iconMandatory));
				returnCustomComponent.setToolTip(Messages.getString("Tooltip.icon.mandatory.true", "Tooltip.icon.mandatory.true"));
				logger.trace("displayMandatory(BasicSchemaObjValueBoolean) - end");
				return returnCustomComponent;
			}		
		}

		logger.trace("displayMandatory(BasicSchemaObjValueBoolean) - end");
		return null;
	}
	
	public static Object[][] getTableData (JTable table) {
		logger.trace("getTableData(JTable) - start");

	    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
	    Object[][] tableData = new Object[nRow][nCol];
	    for (int i = 0 ; i < nRow ; i++)
	        for (int j = 0 ; j < nCol ; j++)
	            tableData[i][j] = dtm.getValueAt(i,j);

		logger.trace("getTableData(JTable) - end");
	    return tableData;
	}

	public static CustomComponent<JButton> displayStatus(Status status) {
		logger.trace("displayStatus(Status) - start");

		Icon ic = null;
		String translationKey = "Tooltip.icon.status.";
		switch (status) {
		case Error:
			ic = iconError;
			translationKey+="error";
			break;
		case Warning:
			ic = iconWarning;
			translationKey+="warning";
			break;
		case Ok:
			ic = iconOk;
			translationKey+="ok";
			break;
		default:
			logger.trace("displayStatus(Status) - end");
			return null;
		}
		CustomComponent<JButton> returnCustomComponent = new CustomComponent<JButton>(new RoundButton(ic));
		returnCustomComponent.setToolTip(Messages.getString(translationKey, translationKey));
		logger.trace("displayStatus(Status) - end");
		return returnCustomComponent;
	}
	
	public static List<List<String>> transformList(List<List<Object>> oList) {
		logger.trace("transformList(List<List<Object>>) - start");

		List<List<String>> sList = new ArrayList<List<String>>(oList.size());
		int i = -1;
		for (List<Object> arrayList : oList) {
			sList.add(new ArrayList<String>(arrayList.size()));
			i++;
			for (Object object : arrayList) {
				if (object == null) sList.get(i).add("");
				else sList.get(i).add(object.toString());
			}
		}

		logger.trace("transformList(List<List<Object>>) - end");
		return sList;
	}

	public static ICustomComponent<JLabel> displayMultiplicity(BasicSchemaObjAbstractValue<?> m) {
		logger.trace("displayMultiplicity(BasicSchemaObjAbstractValue<?>) - start");

		if(m!=null){
			String value = m.getValue().toString();
			ICustomComponent<JLabel> l =  new JLabelComponent(value);

			logger.trace("displayMultiplicity(BasicSchemaObjAbstractValue<?>) - end");
			return l;
		}

		logger.trace("displayMultiplicity(BasicSchemaObjAbstractValue<?>) - end");
		return null;
	}

	public static List<Pair<String, String>> getCategoriesMap() {
		return categoriesMap;
	}

	public static List<Pair<String, String>> getContextMap() {
		return contextMap;
	}
}
