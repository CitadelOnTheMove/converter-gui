package eu.citadel.converter.gui.wizard.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class WizardPageList extends ArrayList<WizardPage>{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(WizardPageList.class);

	private int min = 0;
	private int max = 0;
	
	public int getMin() {
		logger.trace("getMin() - start");

		logger.trace("getMin() - end");
		return min;
	}

	public int getMax() {
		logger.trace("getMax() - start");

		logger.trace("getMax() - end");
		return max;
	}

	@Override
	public boolean add(WizardPage e) {
		logger.trace("add(WizardPage) - start");

		if (e != null) setMinMax(e.order);
		boolean returnboolean = super.add(e);
		logger.trace("add(WizardPage) - end");
		return returnboolean;
	}
	
	@Override
	public void add(int index, WizardPage element) {
		logger.trace("add(int, WizardPage) - start");

		if (element != null) setMinMax(element.order);
		super.add(index, element);

		logger.trace("add(int, WizardPage) - end");
	}
	
	@Override
	public boolean addAll(Collection<? extends WizardPage> c) {
		logger.trace("addAll(Collection<? extends WizardPage>) - start");

		logger.trace("addAll(Collection<? extends WizardPage>) - end");
		return false;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends WizardPage> c) {
		logger.trace("addAll(int, Collection<? extends WizardPage>) - start");

		logger.trace("addAll(int, Collection<? extends WizardPage>) - end");
		return false;
	}
	
	private void setMinMax(int val) {
		logger.trace("setMinMax(int) - start");

		if (val > max) {
			max = val;
		}else if (val < min) {
			min = val;
		}

		logger.trace("setMinMax(int) - end");
	}
}
