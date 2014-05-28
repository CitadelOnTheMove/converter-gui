package eu.citadel.converter.gui.wizard.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import eu.citadel.converter.commons.Type;
import eu.citadel.converter.data.dataset.CsvType;
import eu.citadel.converter.data.dataset.DatasetType;
import eu.citadel.converter.data.dataset.ExcelType;
import eu.citadel.converter.data.metadata.BasicMetadata;
import eu.citadel.converter.data.metadata.BasicMetadataObj;
import eu.citadel.converter.data.metadata.BasicMetadataUtils;
import eu.citadel.converter.gui.wizard.domain.Cell;
import eu.citadel.converter.gui.wizard.domain.ColumnCategoryContext;
import eu.citadel.converter.gui.wizard.domain.CustomComboBox;
import eu.citadel.converter.gui.wizard.domain.CustomText;
import eu.citadel.converter.gui.wizard.domain.Item;
import eu.citadel.converter.gui.wizard.domain.ItemList;
import eu.citadel.converter.gui.wizard.domain.Matrix;
import eu.citadel.converter.gui.wizard.domain.MetadataFile;
import eu.citadel.converter.gui.wizard.domain.Pair;
import eu.citadel.converter.gui.wizard.domain.SchemaMatchTable;
import eu.citadel.converter.gui.wizard.domain.SemanticMatchTable;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.schema.obj.BasicSchemaObjAbstractValue;
import eu.citadel.converter.schema.obj.BasicSchemaObjAttributes;
import eu.citadel.converter.schema.obj.BasicSchemaObjElements;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueInteger;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueList;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueNull;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueObject;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueString;
import eu.citadel.converter.transform.config.BasicTransformationConfig;
import eu.citadel.converter.transform.config.BasicTransformationConfigObj;
import eu.citadel.converter.transform.config.BasicTransformationConfigUtils;

public class JsonBuilder {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(JsonBuilder.class);

	public static BasicMetadata metadataBuild(Matrix<Cell> matrix, MetadataFile metadata) {
		logger.trace("metadataBuild(Matrix<Cell>, MetadataFile) - start");

		String firstRow;
		final List<Pair<String, String>> categoriesMap = GuiUtility.getCategoriesMap();
		final List<Pair<String, String>> contextMap = GuiUtility.getContextMap();
		final String defaultCategory = GuiUtility.getDefaultCategory();
		if (metadata.isFirstRowIsData()) {
			firstRow = "data";
		} else {
			firstRow = "label";
		}
		BasicMetadataObj obj = new BasicMetadataObj();
		BasicSchemaObjElements elements = new BasicSchemaObjElements();
		// id = null
		Type type = metadata.getObjType();
		BasicSchemaObjAttributes generalAttributes = new BasicSchemaObjAttributes();
		
		generalAttributes.put(BasicMetadataUtils.FIRST_ROW, new BasicSchemaObjValueString(firstRow));
		if (type instanceof CsvType) {	
			generalAttributes.put(BasicMetadataUtils.TYPE, new BasicSchemaObjValueString(DatasetType.TYPE_CSV));
			generalAttributes.put(BasicMetadataUtils.CSV_DELIMITER, new BasicSchemaObjValueString(metadata.getDelimiter()));		
			generalAttributes.put(BasicMetadataUtils.CSV_QUOTE, new BasicSchemaObjValueString(metadata.getQuote()));
			generalAttributes.put(BasicMetadataUtils.CSV_NEWLINE, new BasicSchemaObjValueString(metadata.getNewline()));
		} else if (type instanceof ExcelType) {
			generalAttributes.put(BasicMetadataUtils.TYPE, new BasicSchemaObjValueString(DatasetType.TYPE_EXCEL));
		}
		elements.put(new BasicSchemaObjValueNull(), generalAttributes);
		//for i sulle colonne del csv
		int matrixSize = matrix.sizeRows();
		for (int r = 0; r < matrixSize; r++) { 
			BasicSchemaObjAttributes row = new BasicSchemaObjAttributes();
			//Category
			Cell cCat = matrix.getCell(r, SemanticMatchTable.SEMANTIC_COLUMN_CATEGORY);
			String category;
			if (cCat.isEmpty()) {
				//Default last category
				category = defaultCategory;
			} else {
				String categoryTranslated = cCat.getItemList().get(0).toString();
				category = Messages.untranslate(categoryTranslated, categoriesMap);
			}
			if(category == null || category.trim().isEmpty()) {
				row.put(BasicMetadataUtils.CATEGORY, new BasicSchemaObjValueNull());
			}
			else row.put(BasicMetadataUtils.CATEGORY, new BasicSchemaObjValueString(category));
			//Context
			Cell cCon = matrix.getCell(r, SemanticMatchTable.SEMANTIC_COLUMN_CONTEXT);
			switch (cCon.getItemList().size()) {
			case 0:
				row.put(BasicMetadataUtils.CONTEXT, new BasicSchemaObjValueNull());
				break;
			case 1:
				String contextTranslated = cCon.getItemList().get(0).toString();
				String context = Messages.untranslate(contextTranslated, contextMap);
				if(context == null || context.trim().isEmpty()) {
					row.put(BasicMetadataUtils.CONTEXT, new BasicSchemaObjValueNull());
				}
				else row.put(BasicMetadataUtils.CONTEXT, new BasicSchemaObjValueString(context));
				break;
			default:
				BasicSchemaObjValueList contextList = new BasicSchemaObjValueList();
				List<BasicSchemaObjAbstractValue<?>> paramsList = Lists.newArrayList();
				for (Item item : cCon.getItemList()) {
					String curContext = item.toString();
					if(curContext == null || curContext.trim().isEmpty()) {
						paramsList.add(new BasicSchemaObjValueNull());
					}
					else paramsList.add(new BasicSchemaObjValueString(curContext));
				}
				contextList.setValue(paramsList);
				row.put(BasicMetadataUtils.CONTEXT, contextList);
				break;
			}
			elements.put(new BasicSchemaObjValueInteger(r), row);
		}
		obj.setElements(elements);
		logger.debug("metadataBuild(Matrix<Cell>, MetadataFile) - json: {}",obj.toJson());		
		BasicMetadata returnBasicMetadata = new BasicMetadata(obj);
		logger.trace("metadataBuild(Matrix<Cell>, MetadataFile) - end");
		return returnBasicMetadata;
	}

	public static BasicTransformationConfig schemaBuild(Matrix<Cell> matrix, int[] mapIdToRow) throws Exception {
		logger.trace("schemaBuild(Matrix<Cell>, int[]) - start");

		int[] mapRowToId = new int[mapIdToRow.length];
		for (int id = 0; id < mapIdToRow.length; id++) {
			mapRowToId[mapIdToRow[id]] = id;
		}
		
		BasicSchemaObjElements elements = new BasicSchemaObjElements();
		//id = null
		BasicSchemaObjAttributes generalAttributes = new BasicSchemaObjAttributes();
		elements.put(new BasicSchemaObjValueNull(), generalAttributes);

		for (int id = 0; id < mapRowToId.length; id++) {
			ItemList iList = matrix.getCell(mapIdToRow[id], SchemaMatchTable.SCHEMA_COLUMN_MATCH).getItemList();
			BasicSchemaObjAttributes jsonItem = new BasicSchemaObjAttributes();
			//Source
			Map<String, BasicSchemaObjAbstractValue<?>> mapSource = new HashMap<>();			
			mapSource.put(BasicTransformationConfigUtils.DATATYPE, new BasicSchemaObjValueInteger(id));			
			BasicSchemaObjValueObject objSource = new BasicSchemaObjValueObject(mapSource);			
			//Target			
			BasicSchemaObjAbstractValue<?> objTarget = getMetadata(iList);	

			jsonItem.put(BasicTransformationConfigUtils.SOURCE, objSource);
			jsonItem.put(BasicTransformationConfigUtils.TARGET, objTarget);
			elements.put(new BasicSchemaObjValueInteger(id), jsonItem);
		}
		BasicTransformationConfigObj obj = new BasicTransformationConfigObj(elements);
		logger.debug("schemaBuild(Matrix<Cell>, int[]) - json: {}",obj.toJson());
		BasicTransformationConfig returnBasicTransformationConfig = new BasicTransformationConfig(obj);
		logger.trace("schemaBuild(Matrix<Cell>, int[]) - end");
		return returnBasicTransformationConfig;
	}

	private static BasicSchemaObjAbstractValue<?> getMetadata(ItemList iList) throws Exception {
		logger.trace("getMetadata(ItemList) - start");

		int nElements = iList.size();
		switch (nElements) {
		case 0:
			logger.trace("getMetadata(ItemList) - end");
			return new BasicSchemaObjValueNull();
		case 1:
			//una colonna
			Item item1 = iList.get(0);
			BasicSchemaObjAbstractValue<?> obj1 = null;
			if (item1.component instanceof ColumnCategoryContext) {
				BasicSchemaObjValueInteger id1 = ((ColumnCategoryContext<?>) item1.component).getId();
				Map<String, BasicSchemaObjAbstractValue<?>> map1 = new HashMap<>();			
				map1.put(BasicTransformationConfigUtils.METADATA, id1);			
				obj1 = new BasicSchemaObjValueObject(map1);	
			} else if (item1.component instanceof CustomText) {
				obj1 = new BasicSchemaObjValueString(((CustomText) item1.component).getText());
			} else if (item1.component instanceof CustomComboBox) {
				obj1 = new BasicSchemaObjValueString(((CustomComboBox) item1.component).toString());
			} else {
				throw new Exception("Graphics component unknown in getMetadata (Metadata conversion)");
			}
			logger.trace("getMetadata(ItemList) - end");
			return obj1;
		default:
			//Piu colonne
			List<BasicSchemaObjAbstractValue<?>> listObj = new ArrayList<BasicSchemaObjAbstractValue<?>>(nElements);
			for (Item item : iList) {
				BasicSchemaObjValueInteger idN;
				Map<String, BasicSchemaObjAbstractValue<?>> mapN = new HashMap<>();	
				if (item.component instanceof ColumnCategoryContext<?>) {
					idN = ((ColumnCategoryContext<?>) item.component).getId();							
					mapN.put(BasicTransformationConfigUtils.METADATA, idN);			
					BasicSchemaObjValueObject objN = new BasicSchemaObjValueObject(mapN);
					listObj.add(objN);
				} else if (item.component instanceof CustomText) {
					listObj.add(new BasicSchemaObjValueString(((CustomText) item.component).getText()));
				} else if (item.component instanceof CustomComboBox) {
					obj1 = new BasicSchemaObjValueString(((CustomComboBox) item.component).toString());
				} else {
					throw new Exception("Graphics component unknown in getMetadata (Metadata conversion)");
				}
			}
			logger.trace("getMetadata(ItemList) - end");
			return new BasicSchemaObjValueList(listObj);
		}
	}
}
