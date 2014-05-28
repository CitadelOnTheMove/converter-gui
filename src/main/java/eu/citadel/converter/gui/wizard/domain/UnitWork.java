package eu.citadel.converter.gui.wizard.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.commons.Type;
import eu.citadel.converter.data.Data;
import eu.citadel.converter.data.dataset.CsvDataset;
import eu.citadel.converter.data.dataset.CsvType;
import eu.citadel.converter.data.dataset.Dataset;
import eu.citadel.converter.data.dataset.ExcelDataset;
import eu.citadel.converter.data.dataset.ExcelType;
import eu.citadel.converter.data.dataset.JsonDataset;
import eu.citadel.converter.data.datatype.BasicDatatype;
import eu.citadel.converter.data.metadata.BasicMetadata;
import eu.citadel.converter.exceptions.ConverterException;
import eu.citadel.converter.gui.wizard.utility.JsonBuilder;
import eu.citadel.converter.schema.obj.BasicSchemaObjAbstractValue;
import eu.citadel.converter.transform.CitadelJsonTransform;
import eu.citadel.converter.transform.config.BasicTransformationConfig;
import eu.citadel.converter.utils.Matching;

public class UnitWork {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(UnitWork.class);

	public static int ID = 0;
	public int myId;
	private UndoValue<FileInput> targetFile = new UndoValue<FileInput>();
	public Matrix<Cell> semanticMatrix = null;
	public Matrix<Cell> datatypeMatrix = null;
	private Dataset dataset = null;
	private BasicMetadata semantichMatchResult = null;
	private BasicDatatype datatype = null;
	private Data<Dataset,BasicMetadata> preTransformationData = null;
	public ArrayList<Datatype> datatypeList = null;
	private BasicTransformationConfig transformationConfig;
	public int[] mapIdToRowSchema;

	private Data<?,?> dataTarget;
	private String jsonResult;
	private java.nio.file.Path saveFilePath;
	private Map<BasicSchemaObjAbstractValue<?>, BasicSchemaObjAbstractValue<?>> matchmap;
	private List<BasicDatatype> availableDatatypeList;
	
	public UnitWork() {
		logger.trace("UnitWork() - start");
		logger.debug("UnitWork() - created unit work id:{}", ID);
		myId = ID++;

		logger.trace("UnitWork() - end");
	}
	
	public Dataset getDataset() {
		logger.trace("getDataset() - start");

		if (targetFile.getValue() != null && targetFile.getValue().getMetadata().getObjType()!=null) {
			Type t = targetFile.getValue().getMetadata().getObjType();			
			if (t instanceof CsvType) {
				dataset = new CsvDataset(targetFile.getValue().toPath());
				((CsvDataset) dataset).setCsvType((CsvType) t);
			} else if (t instanceof ExcelType) {
				dataset = new ExcelDataset(targetFile.getValue().toPath());
				((ExcelDataset) dataset).setExcelType((ExcelType) t);
			}
			logger.debug("getDataset() - created dataset {}",t.getClass().getName());
			logger.trace("getDataset() - end");
			return dataset;
		}
		logger.error("getDataset() - no target file selected or dataset type not setted");
		logger.trace("getDataset() - end");
		return null;
	}
	
	public BasicMetadata getBasicMetaData() {
		logger.trace("getBasicMetaData() - start");

		if (semanticMatrix != null && targetFile.getValue() != null) {
			semantichMatchResult = JsonBuilder.metadataBuild(semanticMatrix, targetFile.getValue().getMetadata());
			matchmap = getMatchMap();

			logger.trace("getBasicMetaData() - end");
			return semantichMatchResult;
		}
		logger.error("getBasicMetaData() - semantic matrix is null or target file is null");
		logger.trace("getBasicMetaData() - end");
		return null;
	}
	
	public BasicDatatype getBasicDatatype() {
		logger.trace("getBasicDatatype() - start");

		matchmap = getMatchMap();

		logger.trace("getBasicDatatype() - end");
		return datatype;
	}
	
	public Data<Dataset, BasicMetadata> getPreTransformationData() {
		logger.trace("getPreTransformationData() - start");

		if(getDataset()!=null && semantichMatchResult!=null){
			preTransformationData = new Data<Dataset, BasicMetadata>(dataset, semantichMatchResult);
		}

		logger.trace("getPreTransformationData() - end");
		return preTransformationData;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj instanceof UnitWork) {
			return ((UnitWork)(obj)).myId == this.myId;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Data<?,?> getDataTarget() throws Exception {
		logger.trace("getDataTarget() - start");

		if (getTransformationConfig() != null) {
			Data<Dataset,BasicMetadata> data = new Data<Dataset, BasicMetadata>(getDataset(), getBasicMetaData());
			CitadelJsonTransform transf = new CitadelJsonTransform(data, getBasicDatatype(), getTransformationConfig());
			dataTarget = (Data<JsonDataset, BasicMetadata>) transf.getTarget();
		}

		logger.trace("getDataTarget() - end");
		return dataTarget;
	}

	public String getJsonResult() throws Exception {
		logger.trace("getJsonResult() - start");

		if (getDataTarget()!=null) {
			jsonResult = ((JsonDataset)(getDataTarget().getDataset())).getContent();			
		}
		logger.trace("getJsonResult() - end");
		return jsonResult;
	}
	
	public BasicTransformationConfig getTransformationConfig() throws Exception {
		logger.trace("getTransformationConfig() - start");

		if (datatypeMatrix != null && mapIdToRowSchema != null) {
			transformationConfig = JsonBuilder.schemaBuild(datatypeMatrix, mapIdToRowSchema);
		}

		logger.trace("getTransformationConfig() - end");
		return transformationConfig;
	}
	
	public void saveFile(boolean overwrite) throws Exception {
		logger.trace("saveFile(boolean) - start");
		
		JsonDataset jd = ((JsonDataset)(getDataTarget().getDataset()));
		
		jd.saveAs(saveFilePath, overwrite);
		
		logger.trace("saveFile(boolean) - end");
	}

	public void setSaveFilePath(java.nio.file.Path p) {
		logger.trace("setSaveFilePath(java.nio.file.Path) - start");

		this.saveFilePath = p;
		logger.debug("setSaveFilePath(java.nio.file.Path) - path: {}",saveFilePath.toString());
		logger.trace("setSaveFilePath(java.nio.file.Path) - end");
	}
	
	public java.nio.file.Path getSaveFilePath() {
		logger.trace("getSaveFilePath() - start");

		logger.trace("getSaveFilePath() - end");
		return saveFilePath;
	}

	private Map<BasicSchemaObjAbstractValue<?>, BasicSchemaObjAbstractValue<?>> getMatchMap() {
		logger.trace("getMatchMap() - start");

		try {
			Map<BasicSchemaObjAbstractValue<?>, BasicSchemaObjAbstractValue<?>> returnMap = Matching.getSingleMatch(this.datatype, this.semantichMatchResult);
			logger.trace("getMatchMap() - end");
			return returnMap;
		} catch (ConverterException e) {
			logger.warn("getMatchMap()", e);

			logger.trace("getMatchMap() - end");
			return null;
		}
	}
	
	public BasicSchemaObjAbstractValue<?> getAutoMatch(BasicSchemaObjAbstractValue<?> datatypeId){
		logger.trace("getAutoMatch(BasicSchemaObjAbstractValue<?>) - start");

		BasicSchemaObjAbstractValue<?> returnBasicSchemaObjAbstractValue = matchmap.get(datatypeId);
		logger.trace("getAutoMatch(BasicSchemaObjAbstractValue<?>) - end");
		return returnBasicSchemaObjAbstractValue;
	}

	public List<BasicDatatype> getDatatypeList() {
		logger.trace("getDatatypeList() - start");

		List<BasicDatatype> returnList = BasicDatatype.getAvailableBasicDatatype();
		logger.trace("getDatatypeList() - end");
		return returnList;
	}

	public void setDataType(BasicDatatype selDt) {
		logger.trace("setDataType(BasicDatatype) - start");

		this.datatype = selDt;

		logger.trace("setDataType(BasicDatatype) - end");
	}

	public void setAvailableDatatypes(List<BasicDatatype> list) {
		logger.trace("setAvailableDatatypes(List<BasicDatatype>) - start");

		this.availableDatatypeList = list;
		if(list.size()>0) {
			this.datatype = list.get(0);
		}

		logger.trace("setAvailableDatatypes(List<BasicDatatype>) - end");
	}
	
	public FileInput getTargetFileValue(){
		logger.trace("getTargetFileValue - start");
		logger.trace("getTargetFileValue - end");
		return targetFile.getValue();
	}
	
	public void setTargetFileValue(FileInput f){
		logger.trace("setTargetFileValue(FileInput) - start");
		
		this.targetFile.setValue(f);
		logger.trace("setTargetFileValue(FileInput) - end");
	}
}
