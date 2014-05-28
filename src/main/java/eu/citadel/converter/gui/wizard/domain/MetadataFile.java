package eu.citadel.converter.gui.wizard.domain;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.commons.Type;

public class MetadataFile {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(MetadataFile.class);

	private String type = null;
	private Type objType;
	private String delimiter = null;
	private boolean firstRowIsData = true;
	private int page = -1;
	
	public MetadataFile(FileInput fileInput) throws IOException {
		logger.trace("MetadataFile(FileInput) - start");

		FTypeDetector ftd = new FTypeDetector();
		this.type = ftd.probeContentType(fileInput.toPath());
		logger.debug("MetadataFile(FileInput) - file type is {}",type);
		logger.trace("MetadataFile(FileInput) - end");
	}
	public String getType() {
		logger.trace("getType() - start");

		logger.trace("getType() - end");
		return type;
	}
	public Type getObjType() {
		logger.trace("getObjType() - start");

		logger.trace("getObjType() - end");
		return objType;
	}
	public String getDelimiter() {
		logger.trace("getDelimiter() - start");

		logger.trace("getDelimiter() - end");
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		logger.trace("setDelimiter(String) - start");
		logger.debug("setDelimiter(String) - delimiter:\"{}\"",delimiter);
		this.delimiter = delimiter;

		logger.trace("setDelimiter(String) - end");
	}
	public boolean isFirstRowIsData() {
		logger.trace("isFirstRowIsData() - start");
		
		logger.trace("isFirstRowIsData() - end");
		return firstRowIsData;
	}
	public void setFirstRowIsData(boolean firstRowIsData) {
		logger.trace("setFirstRowIsData(boolean) - start");
		logger.debug("setFirstRowIsData(boolean) - first row is data?:\"{}\"",firstRowIsData);
		this.firstRowIsData = firstRowIsData;

		logger.trace("setFirstRowIsData(boolean) - end");
	}
	public int getPage() {
		logger.trace("getPage() - start");

		logger.trace("getPage() - end");
		return page;
	}
	public void setPage(int page) {
		logger.trace("setPage(int) - start");
		logger.debug("setPage(int) - selected page excel file: \"{}\"",page);
		this.page = page;

		logger.trace("setPage(int) - end");
	}
	public String getQuote() {
		logger.trace("getQuote() - start");

		logger.trace("getQuote() - end");
		return "\"";
	}
	public String getNewline() {
		logger.trace("getNewline() - start");

		logger.trace("getNewline() - end");
		return "\\r\\n";
	}
	public void setObjType(Type type) {
		logger.trace("setObjType(Type) - start");
		logger.debug("setObjType(Type) - type is: \"{}\"",type.toString());
		this.objType = type;

		logger.trace("setObjType(Type) - end");
	}
}
