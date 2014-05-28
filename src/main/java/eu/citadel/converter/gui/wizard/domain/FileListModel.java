package eu.citadel.converter.gui.wizard.domain;

import javax.swing.AbstractListModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class FileListModel extends AbstractListModel<String> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileListModel.class);
	
	private String[] files;
	
	public FileListModel(FileList filesList) {
		logger.trace("FileListModel(FileList) - start");

		this.files = filesList.toStringArray();

		logger.trace("FileListModel(FileList) - end");
	}

	@Override
	public String getElementAt(int i) {
		logger.trace("getElementAt(int) - start");

		String returnString = files[i];
		logger.trace("getElementAt(int) - end");
		return returnString;
	}

	@Override
	public int getSize() {
		logger.trace("getSize() - start");

		logger.trace("getSize() - end");
		return files.length;
	}
}
