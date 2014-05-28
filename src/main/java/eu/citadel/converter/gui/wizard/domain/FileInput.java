package eu.citadel.converter.gui.wizard.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class FileInput extends File {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileInput.class);
	
	private MetadataFile metadata;
	private List<List<String>> content; 
	
	public FileInput(String pathname) throws IOException {
		super(pathname);
		logger.trace("FileInput(String) - start");
		logger.debug("setTargetFileValue(FileInput) - input file selected path: {}",pathname);
		this.metadata = new MetadataFile(this);

		logger.trace("FileInput(String) - end");
	}
	
	public MetadataFile getMetadata() {
		logger.trace("getMetadata() - start");

		logger.trace("getMetadata() - end");
		return this.metadata;
	}

	public List<List<String>> getContent() {
		logger.trace("getContent() - start");

		logger.trace("getContent() - end");
		return content;
	}

	public void setContent(List<List<String>> content) {
		logger.trace("setContent(List<List<String>>) - start");

		this.content = content;

		logger.trace("setContent(List<List<String>>) - end");
	}	
	
	public ArrayList<String> getColumnsIdNames() {
		logger.trace("getColumnsIdNames() - start");

		int nColumnsFile = getColumnsSize();
		ArrayList<String> columnsList = new ArrayList<String>(nColumnsFile);
		if (metadata.isFirstRowIsData()) {			
			for (int i = 0; i < nColumnsFile; i++) {
				columnsList.add(new Integer(i+1).toString());
			}
		} else {
			for (int i = 0; i < nColumnsFile; i++) {
				columnsList.add(content.get(0).get(i));
			}
		}

		logger.trace("getColumnsIdNames() - end");
		return columnsList;
	}
	
	public int getColumnsSize() {
		logger.trace("getColumnsSize() - start");

		if (content.size() > 0) {
			logger.trace("getColumnsSize() - end");
			return content.get(0).size();
		}

		logger.trace("getColumnsSize() - end");
		return 0;
	}
	
	public String[] getRowExample(int nrow) {
		logger.trace("getRowExample(int) - start");

		if (content.size() > nrow) {
			String[] returnStringArray = content.get(nrow).toArray(new String[getColumnsSize()]);
			logger.trace("getRowExample(int) - end");
			return returnStringArray;
		}
		String[] returnStringArray = new String[0];
		logger.trace("getRowExample(int) - end");
		return returnStringArray;
	}
}
