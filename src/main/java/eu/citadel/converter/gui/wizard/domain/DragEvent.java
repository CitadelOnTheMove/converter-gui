package eu.citadel.converter.gui.wizard.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragEvent {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DragEvent.class);

	private ICustomComponent<?> value;

	public DragEvent(JLabelComponent c) {
		logger.trace("DragEvent(JLabelComponent) - start");

		value = new JLabelComponent(c.getName());

		logger.trace("DragEvent(JLabelComponent) - end");
	}
	
	public DragEvent(CustomText c) {
		logger.trace("DragEvent(CustomText) - start");

		value = new CustomText(c.getName());

		logger.trace("DragEvent(CustomText) - end");
	}

	public DragEvent(ColumnCategoryContext<JLabelComponent> c) {
		logger.trace("DragEvent(ColumnCategoryContext<JLabelComponent>) - start");
		
		value = cloneCCC(c);

		logger.trace("DragEvent(ColumnCategoryContext<JLabelComponent>) - end");
	}
	
	public DragEvent(ICustomComponent<?> c) {
		logger.trace("DragEvent(ICustomComponent<?>) - start");

		if(c instanceof JLabelComponent) {
			value = new JLabelComponent(c.getName());
		} else if (c instanceof CustomText) {
			value = new CustomText(c.getName());
		} else if (c instanceof ColumnCategoryContext) {
			value = cloneCCC((ColumnCategoryContext<JLabelComponent>)c);
		}

		logger.trace("DragEvent(ICustomComponent<?>) - end");
	}

	public ICustomComponent<?> getValue() {
		logger.trace("getValue() - start");

		final ICustomComponent<?> toReturn = value;
		value = new DragEvent(value).value;

		logger.trace("getValue() - end");
		return toReturn;
	}
	
	private ColumnCategoryContext<JLabelComponent> cloneCCC(ColumnCategoryContext<JLabelComponent> c){
		logger.trace("cloneCCC(ColumnCategoryContext<JLabelComponent>) - start");

		ColumnCategoryContext<JLabelComponent> ccc = new ColumnCategoryContext<JLabelComponent>();
		ccc.setId(c.getId());
		ccc.setName(c.getName());
		ccc.setCategory(c.getCategory());
		ccc.setContext(c.getContext());

		logger.trace("cloneCCC(ColumnCategoryContext<JLabelComponent>) - end");
		return ccc;
	}
}