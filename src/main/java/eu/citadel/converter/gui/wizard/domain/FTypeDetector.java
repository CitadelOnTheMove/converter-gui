package eu.citadel.converter.gui.wizard.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.data.dataset.DatasetType;

public class FTypeDetector extends FileTypeDetector {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FTypeDetector.class);

	@Override
	public String probeContentType(Path path) throws IOException {
		logger.trace("probeContentType(Path) - start");

		String returnString = DatasetType.detect(path);
		logger.trace("probeContentType(Path) - end");
		return returnString;
	}

}
