package eu.citadel.converter.gui.wizard.pages;

import java.awt.Font;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.xerces.impl.dv.DatatypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.data.dataset.CsvDataset;
import eu.citadel.converter.data.dataset.CsvType;
import eu.citadel.converter.data.dataset.ExcelDataset;
import eu.citadel.converter.data.dataset.ExcelType;
import eu.citadel.converter.exceptions.DatasetException;
import eu.citadel.converter.gui.wizard.domain.ColumnCategoryContext;
import eu.citadel.converter.gui.wizard.domain.CustomComboBox;
import eu.citadel.converter.gui.wizard.domain.CustomComponent;
import eu.citadel.converter.gui.wizard.domain.CustomText;
import eu.citadel.converter.gui.wizard.domain.Datatype;
import eu.citadel.converter.gui.wizard.domain.DragEvent;
import eu.citadel.converter.gui.wizard.domain.ICustomComponent;
import eu.citadel.converter.gui.wizard.domain.JLabelComponent;
import eu.citadel.converter.gui.wizard.domain.ObserverJTable;
import eu.citadel.converter.gui.wizard.domain.SchemaMatchTable;
import eu.citadel.converter.gui.wizard.domain.Status;
import eu.citadel.converter.gui.wizard.domain.TableCellRendererEditor;
import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.gui.wizard.utility.GuiUtility;
import eu.citadel.converter.schema.obj.BasicSchemaObjAbstractValue;
import eu.citadel.converter.schema.obj.BasicSchemaObjAttributes;
import eu.citadel.converter.schema.obj.BasicSchemaObjElements;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueInteger;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueList;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueNull;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueObject;

@SuppressWarnings("serial")
public class ExportSchemaPage extends WizardPage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExportSchemaPage.class);

	private BasicSchemaObjElements datatypes;
	private BasicSchemaObjElements columnsCatContx;
	private ArrayList<ColumnCategoryContext<JLabelComponent>> columnsCatContxList;
	private ArrayList<Datatype> dtList;
	private ObserverJTable table;
	private JTree elementsJTree;	
	private TableCellRendererEditor renderer;
	private ArrayList<CustomComboBox> comboboxList = new ArrayList<>();
	private DragEvent event;
	private SchemaMatchTable modelTable;
	private JScrollPane scrollPaneTable;
	private JScrollPane scrollPaneTree;
	

	public ExportSchemaPage(int id, String title, int order, Wizard context) throws SecurityException, NoSuchMethodException {
		super(id, title, order, context);
		logger.trace("ExportSchemaPage(int, String, int, Wizard) - start");
		
		scrollPaneTree = new JScrollPane();
		
		scrollPaneTable = new JScrollPane();
		
		JLabel dummySpaceTableCategories = new JLabel("");
		
		JLabel dummySpaceTableContext = new JLabel("");
		
		JPanel panel = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(31)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
						.addComponent(scrollPaneTable, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(dummySpaceTableCategories)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(dummySpaceTableContext)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPaneTree, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPaneTable, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
							.addComponent(dummySpaceTableCategories))
						.addComponent(scrollPaneTree, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(dummySpaceTableContext)
					.addGap(41))
		);
		
		JLabel label = new JLabel(Messages.getString("ExportSchemaPage.label.text", "Export Schema")); //$NON-NLS-1$ //$NON-NLS-2$
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 32));
		panel.add(label);
		
		elementsJTree = new JTree();		
		scrollPaneTree.setViewportView(elementsJTree);
		table = new ObserverJTable();
		scrollPaneTable.setViewportView(table);
		setLayout(groupLayout);	

		logger.trace("ExportSchemaPage(int, String, int, Wizard) - end");
	}
	
	private void createJTree(ArrayList<ColumnCategoryContext<JLabelComponent>> columnsCatContxList) {
		logger.trace("createJTree(ArrayList<ColumnCategoryContext<JLabelComponent>>) - start");
	
		DefaultMutableTreeNode baseRoot = new DefaultMutableTreeNode(Messages.getString("ExportSchemaPage.content", "Content"));
		DefaultMutableTreeNode rootColumns = new DefaultMutableTreeNode(Messages.getString("ExportSchemaPage.sourceColumn", "Source column"));
		DefaultMutableTreeNode customRoot = new DefaultMutableTreeNode(Messages.getString("ExportSchemaPage.extra", "Extra"));
		for (ColumnCategoryContext<JLabelComponent> obj : columnsCatContxList) {
			if (obj.getId() != null && obj.getId().getValue() >=0) rootColumns.add(new DefaultMutableTreeNode(obj));
		}
		CustomText ct = new CustomText(Messages.getString("CustomText", "CustomText"));
		customRoot.add(new DefaultMutableTreeNode(ct));
		baseRoot.add(rootColumns);
		baseRoot.add(customRoot);		
		elementsJTree = new JTree(baseRoot);		
		elementsJTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				logger.trace("$TreeSelectionListener.valueChanged(TreeSelectionEvent) - start");

				Object draggedItemObject = ((DefaultMutableTreeNode) elementsJTree.getSelectionPath().getLastPathComponent()).getUserObject();
				if (draggedItemObject instanceof ICustomComponent) {
					ICustomComponent<?> component = (ICustomComponent<?>) draggedItemObject;
					event = new DragEvent(component);
				} else event = null;				

				logger.trace("$TreeSelectionListener.valueChanged(TreeSelectionEvent) - end");
			}
		});
		elementsJTree.setDragEnabled(true);
		for (int i = 0; i < elementsJTree.getRowCount(); i++) {
			elementsJTree.expandRow(i);
		}
		scrollPaneTree.setViewportView(elementsJTree);

		logger.trace("createJTree(ArrayList<ColumnCategoryContext<JLabelComponent>>) - end");
	}
	
	private void createTable() {
		logger.trace("createTable() - start");
	
		table.setModel(modelTable);
		table.getColumnModel().getColumn(0).setMinWidth(65);
		table.getColumnModel().getColumn(0).setMaxWidth(65);
		table.getColumnModel().getColumn(1).setMinWidth(80);
		table.getColumnModel().getColumn(1).setMaxWidth(150);
		table.getColumnModel().getColumn(SchemaMatchTable.SCHEMA_COLUMN_MATCH).setMinWidth(150);
		table.getColumnModel().getColumn(SchemaMatchTable.SCHEMA_COLUMN_MATCH).setMaxWidth(9999);
		table.getColumnModel().getColumn(3).setMinWidth(45);
		table.getColumnModel().getColumn(3).setMaxWidth(45);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setDropTarget(new DropTarget(){
			@Override
			public synchronized void drop(DropTargetDropEvent dtde) {
				logger.trace("$DropTarget.drop(DropTargetDropEvent) - start");

				if (event != null) {
					Point point = dtde.getLocation();
					int row = table.rowAtPoint(point);
					ICustomComponent<?> ev = event.getValue();
					if(ev instanceof CustomText) ((CustomText) ev).setText("");
					renderer.addItem(row, SchemaMatchTable.SCHEMA_COLUMN_MATCH, ev, true); 
				}

				logger.trace("$DropTarget.drop(DropTargetDropEvent) - end");
            }
        });
		table.setRowHeight(35);
		table.setBackground(UIManager.getColor("Panel.background"));
		table.getTableHeader().setReorderingAllowed(false);
		renderer = new TableCellRendererEditor(table);
		controller.getCurrentUnitWork().datatypeMatrix = renderer.getMatrix();
		// Set renderer for columns
		table.getColumnModel().getColumn(0).setCellEditor(renderer);
		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getColumnModel().getColumn(SchemaMatchTable.SCHEMA_COLUMN_MATCH).setCellEditor(renderer);
		table.getColumnModel().getColumn(SchemaMatchTable.SCHEMA_COLUMN_MATCH).setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellEditor(renderer);
		table.getColumnModel().getColumn(3).setCellRenderer(renderer);
		//Combobox
		int nCombobox = comboboxList.size();
		for (int i = 0; i < nCombobox; i++) {
			CustomComboBox c = comboboxList.get(i);
			if (c!=null) {
				renderer.addItem(i, SchemaMatchTable.SCHEMA_COLUMN_MATCH, c, false);
				//Disable future graphics insert
				renderer.getMatrix().getCell(i, SchemaMatchTable.SCHEMA_COLUMN_MATCH).getPanel().disable();
			}
		}
		int nDt = dtList.size();
		for (int i = 0; i < nDt; i++) {
			Datatype toInsert = dtList.get(i);
			ICustomComponent<JButton> b;
			/*ICustomComponent<JLabel> l = GuiUtility.displayMultiplicity(toInsert.getMultiplicity());
			if(l!=null) {
				renderer.addItem(i, 0, l, false);
			}*/
			b = GuiUtility.displayMandatory(toInsert.getMandatory());
			if(b!=null) {
				renderer.addItem(i, 0, b, false);
				renderer.getMatrix().getCell(i, 0).getPanel().setToolTipText(b.getTooltip());
			}
			//Automatch
			BasicSchemaObjAbstractValue<?> match = controller.getCurrentUnitWork().getAutoMatch(toInsert.getId());
			if(match != null) {
				for (ColumnCategoryContext<JLabelComponent> curItem : columnsCatContxList) {
					BasicSchemaObjValueInteger id = curItem.getId();
					if(id != null) {
						if(id.getValue().equals(match.getValue())){
							ColumnCategoryContext<JLabelComponent> ccc = new ColumnCategoryContext<JLabelComponent>();
							ccc.setId(curItem.getId());
							ccc.setName(curItem.getName());
							ccc.setCategory(curItem.getCategory());
							ccc.setContext(curItem.getContext());
							renderer.addItem(i, SchemaMatchTable.SCHEMA_COLUMN_MATCH, ccc, true);
							break;
						}
					}
				}
			}
		}
		table.updateTable();
		scrollPaneTable.setViewportView(table);

		logger.trace("createTable() - end");
	}
	
	@Override
	protected void configureButtons() {
		logger.trace("configureButtons() - start");

		// TODO Auto-generated method stub
		
		logger.trace("configureButtons() - end");
	}

	@Override
	protected void update() throws IOException, DatasetException, DatatypeException {
		logger.trace("update() - start");

		if (renderer != null) {
			controller.getCurrentUnitWork().datatypeMatrix = renderer.getMatrix();
		}
		//JTree		
		columnsCatContx = controller.getCurrentUnitWork().getBasicMetaData().getValues();
		int nColumns = columnsCatContx.size()-1;
		columnsCatContxList = new ArrayList<ColumnCategoryContext<JLabelComponent>>(nColumns);
		for (int i = 0; i < nColumns; i++) {
			columnsCatContxList.add(new ColumnCategoryContext<JLabelComponent>());
		}
		eu.citadel.converter.commons.Type t = controller.getCurrentUnitWork().getTargetFileValue().getMetadata().getObjType();
		List<String> columnsNames = null;
		if (t instanceof CsvType) {
			columnsNames = ((CsvDataset) controller.getCurrentUnitWork().getDataset()).getFirstRow();
		} else if (t instanceof ExcelType) {
			List<Object> objColumnsNames = ((ExcelDataset) controller.getCurrentUnitWork().getDataset()).getFirstRow();
			columnsNames = new ArrayList<>(objColumnsNames.size());
			for (Object object : objColumnsNames) {
				columnsNames.add(object.toString());
			}
		}
		for (Entry<BasicSchemaObjAbstractValue<?>, BasicSchemaObjAttributes> entry : columnsCatContx.entrySet()) {
			BasicSchemaObjAbstractValue<?> idObj = entry.getKey();	
			BasicSchemaObjAttributes attrs = entry.getValue();
			if (!(idObj instanceof BasicSchemaObjValueNull) && !GuiUtility.getDefaultCategory().equals((String) attrs.get("category").getValue())) {
				ColumnCategoryContext<JLabelComponent> ccc = columnsCatContxList.get((Integer) idObj.getValue());
				ccc.setId((BasicSchemaObjValueInteger) idObj);
				ccc.setName(columnsNames.get(ccc.getId().getValue()));
				ccc.setCategory(attrs.get("category"));
				ccc.setContext(attrs.get("context"));
			}
		}
		createJTree(columnsCatContxList);
		//Table		
		datatypes = controller.getCurrentUnitWork().getBasicDatatype().getValues();
		int nDt = datatypes.size()-1;
		dtList = new ArrayList<>(nDt);
		for (int i = 0; i < nDt; i++) {
			dtList.add(new Datatype());
		}
		modelTable = new SchemaMatchTable();
		for (Entry<BasicSchemaObjAbstractValue<?>, BasicSchemaObjAttributes> entry : datatypes.entrySet()) {
			BasicSchemaObjAbstractValue<?> idObj = entry.getKey();
			if (!(idObj instanceof BasicSchemaObjValueNull)) {
				BasicSchemaObjAttributes attrs = entry.getValue();
				Datatype dt = dtList.get((Integer) idObj.getValue());
				dt.setId(idObj);
				dt.setName(attrs.get("name"));
				dt.setDescription(attrs.get("description"));
				dt.setMandatory(attrs.get("mandatory"));
				dt.setDatatype(attrs.get("datatype"));
				dt.setDefaultValue(attrs.get("default"));
				dt.setMultiplicity(attrs.get("multiplicity"));
				dt.setCategory(attrs.get("category"));
				dt.setFormat(attrs.get("format"));
			}
		}	
		//Order and fill table
		int row = -1;
		comboboxList.clear();
		for (Datatype dt : dtList) {
			row++;
			modelTable.addRow(new Object[]{null,dt,null, null});
			applyDatatypeFormat(dt, row);
		}
		
		createTable();
		controller.getCurrentUnitWork().datatypeList = dtList;

		logger.trace("update() - end");
	}
	
	private void applyDatatypeFormat(Datatype dt, int row) throws DatatypeException {
		logger.trace("applyDatatypeFormat(Datatype, int) - start");

		comboboxList.add(null);
		BasicSchemaObjValueObject format = dt.getFormat();
		if (format != null) {
			if (format instanceof BasicSchemaObjValueObject) {
				BasicSchemaObjAbstractValue<?> df = format.getValue().get("list");
				if (df instanceof BasicSchemaObjValueList) {
					BasicSchemaObjValueList list = (BasicSchemaObjValueList)df;
					comboboxList.add(row, new CustomComboBox(list.getValue(), dt.getMandatory()));
				}
			}
		}		

		logger.trace("applyDatatypeFormat(Datatype, int) - end");
	}

	public void setStatus(Status[] list, int[] map) {
		logger.trace("setStatus(Status[], int[]) - start");

		int nStatus = list.length;
		for (int id = 0; id < nStatus; id++) {
			Status s = list[id];
			int r = map[id];
			renderer.getMatrix().getCell(r, 3).clear();
			CustomComponent<JButton> b = GuiUtility.displayStatus(s);
			if(b!=null) {
				renderer.addItem(id, 3, b, false);
				renderer.getMatrix().getCell(id, 3).getPanel().setToolTipText(b.getTooltip());
			}
		}
		table.updateTable();

		logger.trace("setStatus(Status[], int[]) - end");
	}
	
	public Object[][] getDataTable() {
		logger.trace("getDataTable() - start");

		Object[][] returnObjectArray = GuiUtility.getTableData(table);
		logger.trace("getDataTable() - end");
		return returnObjectArray;
	}
}
