package eu.citadel.converter.gui.wizard.domain;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

@SuppressWarnings("serial")
public class FileList extends UndoArrayList<File> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileList.class);	

	public String[] toStringArray() {
		logger.trace("toStringArray() - start");

		int size = this.size();
		String[] result = new String[size];		
		for (int i = 0; i < size; i++) {
			result[i] = this.get(i).getPath();
		}

		logger.trace("toStringArray() - end");
		return result;
	}
	
	public String getFullFilename(int i) {
		logger.trace("getFullFilename(int) - start");

		if (i < this.size()) {
			String returnString = this.get(i).getPath();
			logger.trace("getFullFilename(int) - end");
			return returnString;
		}

		logger.trace("getFullFilename(int) - end");
		return null;
	}	
	
	@Override
	public String toString() {		
		return Joiner.on(',').join(toStringArray());
	}
}
