package eu.citadel.converter.gui.wizard.pages;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.domain.DragEvent;
import eu.citadel.converter.gui.wizard.domain.FileInput;
import eu.citadel.converter.gui.wizard.domain.JLabelComponent;
import eu.citadel.converter.gui.wizard.domain.ObserverJTable;
import eu.citadel.converter.gui.wizard.domain.SemanticMatchTable;
import eu.citadel.converter.gui.wizard.domain.TableCellRendererEditor;
import eu.citadel.converter.gui.wizard.domain.TooltipTreeRenderer;
import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.gui.wizard.utility.GuiUtility;

@SuppressWarnings("serial")
public class SemanticMatchPage extends WizardPage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SemanticMatchPage.class);

	private ArrayList<String> columnsIdNames;
	private ObserverJTable table;
	private JList<String> contextJList;
	private JTree categoriesJTree;	
	private TableCellRendererEditor renderer;	
	private DragEvent eventContext, eventCategory, lastEvent;
	private SemanticMatchTable modelTable;
	private JScrollPane scrollPaneTable;

	public SemanticMatchPage(int id, String title, int order, Wizard context) throws SecurityException, NoSuchMethodException {
		super(id, title, order, context);
		logger.trace("SemanticMatchPage(int, String, int, Wizard) - start");
		
		List<String> catList = GuiUtility.getCategorieslist();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 601, 182, 0, 0};
		gridBagLayout.rowHeights = new int[]{22, 0, 343, 79, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPaneContext = new JScrollPane();
		
		contextJList = new JList<String>();
		contextJList.setModel(new AbstractListModel<String>() {
			List<String> contList = GuiUtility.getContextList();
			String[] values = contList.toArray(new String[contList.size()]);
			public int getSize() {
				logger.trace("$AbstractListModel<String>.getSize() - start");

				logger.trace("$AbstractListModel<String>.getSize() - end");
				return values.length;
			}
			public String getElementAt(int index) {
				logger.trace("$AbstractListModel<String>.getElementAt(int) - start");

				String returnString = values[index];
				logger.trace("$AbstractListModel<String>.getElementAt(int) - end");
				return returnString;
			}
		});
		contextJList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				logger.trace("$ListSelectionListener.valueChanged(ListSelectionEvent) - start");

				eventContext = new DragEvent(new JLabelComponent(contextJList.getSelectedValue()));
				lastEvent = eventContext;

				logger.trace("$ListSelectionListener.valueChanged(ListSelectionEvent) - end");
			}
		});
		
		JLabel lblColumnsFile = new JLabel(Messages.getString("SemanticMatchPage.lblColumnsFile.text", "Semantic Match")); //$NON-NLS-1$ //$NON-NLS-2$
		lblColumnsFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblColumnsFile.setFont(new Font("Tahoma", Font.PLAIN, 32));
		GridBagConstraints gbc_lblColumnsFile = new GridBagConstraints();
		gbc_lblColumnsFile.gridwidth = 2;
		gbc_lblColumnsFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblColumnsFile.gridx = 1;
		gbc_lblColumnsFile.gridy = 0;
		add(lblColumnsFile, gbc_lblColumnsFile);
		
		JTextPane textPane = new JTextPane();
		textPane.setText(Messages.getString("SemanticMatchPage.subtitle", "missing translation"));
		textPane.setEditable(false);
		textPane.setEnabled(false);
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.gridwidth = 2;
		gbc_textPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPane.insets = new Insets(0, 0, 5, 5);
		gbc_textPane.gridx = 1;
		gbc_textPane.gridy = 1;
		add(textPane, gbc_textPane);
		
		scrollPaneTable = new JScrollPane();
		table = new ObserverJTable();
		scrollPaneTable.setViewportView(table);
		GridBagConstraints gbc_scrollPaneTable = new GridBagConstraints();
		gbc_scrollPaneTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTable.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneTable.gridheight = 2;
		gbc_scrollPaneTable.gridx = 1;
		gbc_scrollPaneTable.gridy = 2;
		add(scrollPaneTable, gbc_scrollPaneTable);
		
		JScrollPane scrollPaneTree = new JScrollPane();
		categoriesJTree = new JTree(catList.toArray(new String[catList.size()]));
		categoriesJTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				logger.trace("$TreeSelectionListener.valueChanged(TreeSelectionEvent) - start");

				eventCategory = new DragEvent(new JLabelComponent(categoriesJTree.getSelectionPath().getLastPathComponent().toString()));
				lastEvent = eventCategory;

				logger.trace("$TreeSelectionListener.valueChanged(TreeSelectionEvent) - end");
			}
		});
		categoriesJTree.setDragEnabled(true);
		categoriesJTree.setCellRenderer(new TooltipTreeRenderer());
		ToolTipManager.sharedInstance().registerComponent(categoriesJTree);
		scrollPaneTree.setViewportView(categoriesJTree);
		
		JLabel lblCategories = new JLabel(Messages.getString("SemanticMatchPage.lblCategories.text", "Categories")); //$NON-NLS-1$ //$NON-NLS-2$
		lblCategories.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategories.setFont(new Font("Tahoma", Font.BOLD, 13));
		scrollPaneTree.setColumnHeaderView(lblCategories);
		GridBagConstraints gbc_scrollPaneTree = new GridBagConstraints();
		gbc_scrollPaneTree.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTree.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneTree.gridx = 2;
		gbc_scrollPaneTree.gridy = 2;
		add(scrollPaneTree, gbc_scrollPaneTree);
		contextJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contextJList.setDragEnabled(true);
		
		scrollPaneContext.setViewportView(contextJList);
		
		JLabel lblContext = new JLabel(Messages.getString("SemanticMatchPage.lblContext.text", "Context")); //$NON-NLS-1$ //$NON-NLS-2$
		lblContext.setHorizontalAlignment(SwingConstants.CENTER);
		lblContext.setFont(new Font("Tahoma", Font.BOLD, 13));
		scrollPaneContext.setColumnHeaderView(lblContext);	
		GridBagConstraints gbc_scrollPaneContext = new GridBagConstraints();
		gbc_scrollPaneContext.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneContext.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneContext.gridx = 2;
		gbc_scrollPaneContext.gridy = 3;
		add(scrollPaneContext, gbc_scrollPaneContext);

		logger.trace("SemanticMatchPage(int, String, int, Wizard) - end");
	}
	
	private void createTable() {
		logger.trace("createTable() - start");
		
		table.setModel(modelTable);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(200);
		table.getColumnModel().getColumn(SemanticMatchTable.SEMANTIC_COLUMN_CATEGORY).setMinWidth(100);
		table.getColumnModel().getColumn(SemanticMatchTable.SEMANTIC_COLUMN_CATEGORY).setMaxWidth(200);
		table.getColumnModel().getColumn(SemanticMatchTable.SEMANTIC_COLUMN_CONTEXT).setMinWidth(100);
		table.getColumnModel().getColumn(SemanticMatchTable.SEMANTIC_COLUMN_CONTEXT).setMaxWidth(9999);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setDropTarget(new DropTarget(){
			@Override
            public synchronized void drop(DropTargetDropEvent dtde) {
				logger.trace("$DropTarget.drop(DropTargetDropEvent) - start");

                Point point = dtde.getLocation();
                int row = table.rowAtPoint(point);
                if (row < 0 || row > table.getRowCount()) {
                	logger.trace("$DropTarget.drop(DropTargetDropEvent) - end");
                	return;
                }
                if (lastEvent!=null && lastEvent.equals(eventCategory)) {
                	renderer.addOneItem(row, SemanticMatchTable.SEMANTIC_COLUMN_CATEGORY, eventCategory.getValue(), true);
				} else if (lastEvent!=null && lastEvent.equals(eventContext)) {
					renderer.addItem(row, SemanticMatchTable.SEMANTIC_COLUMN_CONTEXT, eventContext.getValue(), true);
				}

				logger.trace("$DropTarget.drop(DropTargetDropEvent) - end");
            }
        });
		table.setRowHeight(30);
		table.setBackground(UIManager.getColor("Panel.background"));
		table.getTableHeader().setReorderingAllowed(false);
		renderer = new TableCellRendererEditor(table);
		controller.getCurrentUnitWork().semanticMatrix = renderer.getMatrix();
		table.getColumnModel().getColumn(SemanticMatchTable.SEMANTIC_COLUMN_CATEGORY).setCellEditor(renderer);
		table.getColumnModel().getColumn(SemanticMatchTable.SEMANTIC_COLUMN_CATEGORY).setCellRenderer(renderer);
		table.getColumnModel().getColumn(SemanticMatchTable.SEMANTIC_COLUMN_CONTEXT).setCellEditor(renderer);
		table.getColumnModel().getColumn(SemanticMatchTable.SEMANTIC_COLUMN_CONTEXT).setCellRenderer(renderer);		

		logger.trace("createTable() - end");
	}
	
	@Override
	protected void configureButtons() {
		logger.trace("configureButtons() - start");

		// TODO Auto-generated method stub
		
		logger.trace("configureButtons() - end");
	}

	@Override
	protected void update() {
		logger.trace("update() - start");

		FileInput file = controller.getCurrentUnitWork().getTargetFileValue();
		columnsIdNames = file.getColumnsIdNames();
		int nColumns = file.getColumnsSize();
		String[] columnsIdNamesArray = columnsIdNames.toArray(new String[nColumns]);		
		String[] rowExample = file.getRowExample(1);
		if (renderer != null) {
			controller.getCurrentUnitWork().semanticMatrix = renderer.getMatrix();
		}
		modelTable = new SemanticMatchTable();
		for (int r = 0; r < nColumns; r++) {
			modelTable.addRow(new Object[]{columnsIdNamesArray[r],rowExample[r],null, null});
		}
		createTable();
		table.updateTable();

		logger.trace("update() - end");
	}
}
