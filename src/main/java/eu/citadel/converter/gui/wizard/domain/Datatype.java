package eu.citadel.converter.gui.wizard.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.schema.obj.BasicSchemaObjAbstractValue;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueBoolean;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueInteger;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueObject;
import eu.citadel.converter.schema.obj.BasicSchemaObjValueString;

public class Datatype {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Datatype.class);

	private BasicSchemaObjValueInteger id;
	private BasicSchemaObjValueString name;
	private BasicSchemaObjValueString description;
	private BasicSchemaObjValueBoolean mandatory;
	private BasicSchemaObjAbstractValue<?> datatype;
	private BasicSchemaObjAbstractValue<?> defaultValue;
	private BasicSchemaObjAbstractValue<?> multiplicity;
	private BasicSchemaObjValueString category;
	private Status status = Status.In_Progress;
	private BasicSchemaObjValueObject format;
	
	public BasicSchemaObjValueInteger getId() {
		logger.trace("getId() - start");

		logger.trace("getId() - end");
		return id;
	}
	public void setId(BasicSchemaObjAbstractValue<?> id) {
		logger.trace("setId(BasicSchemaObjAbstractValue<?>) - start");

		if (id != null && id.getValue() != null) {
			this.id = (BasicSchemaObjValueInteger) id;
		}

		logger.trace("setId(BasicSchemaObjAbstractValue<?>) - end");
	}
	public BasicSchemaObjValueString getName() {
		logger.trace("getName() - start");

		logger.trace("getName() - end");
		return name;
	}
	public void setName(BasicSchemaObjAbstractValue<?> name) {
		logger.trace("setName(BasicSchemaObjAbstractValue<?>) - start");

		if (name != null && name.getValue() != null) {
			this.name = (BasicSchemaObjValueString) name;
		}

		logger.trace("setName(BasicSchemaObjAbstractValue<?>) - end");
	}
	public BasicSchemaObjValueString getDescription() {
		logger.trace("getDescription() - start");

		logger.trace("getDescription() - end");
		return description;
	}
	public void setDescription(BasicSchemaObjAbstractValue<?> description) {
		logger.trace("setDescription(BasicSchemaObjAbstractValue<?>) - start");

		if (description != null && description.getValue() != null) {
			this.description = (BasicSchemaObjValueString) description;
		}

		logger.trace("setDescription(BasicSchemaObjAbstractValue<?>) - end");
	}
	public BasicSchemaObjValueBoolean getMandatory() {
		logger.trace("getMandatory() - start");

		logger.trace("getMandatory() - end");
		return mandatory;
	}
	public void setMandatory(BasicSchemaObjAbstractValue<?> mandatory) {
		logger.trace("setMandatory(BasicSchemaObjAbstractValue<?>) - start");

		if (mandatory != null && mandatory.getValue() != null) {
			this.mandatory = (BasicSchemaObjValueBoolean) mandatory;
		}

		logger.trace("setMandatory(BasicSchemaObjAbstractValue<?>) - end");
	}
	public BasicSchemaObjAbstractValue<?> getDatatype() {
		logger.trace("getDatatype() - start");

		logger.trace("getDatatype() - end");
		return datatype;
	}
	public void setDatatype(BasicSchemaObjAbstractValue<?> datatype) {
		logger.trace("setDatatype(BasicSchemaObjAbstractValue<?>) - start");

		if (datatype != null && datatype.getValue() != null) {
			this.datatype = datatype;
		}

		logger.trace("setDatatype(BasicSchemaObjAbstractValue<?>) - end");
	}
	public BasicSchemaObjAbstractValue<?> getDefaultValue() {
		logger.trace("getDefaultValue() - start");

		logger.trace("getDefaultValue() - end");
		return defaultValue;
	}
	public void setDefaultValue(BasicSchemaObjAbstractValue<?> defaultValue) {
		logger.trace("setDefaultValue(BasicSchemaObjAbstractValue<?>) - start");

		if (defaultValue != null && defaultValue.getValue() != null) {
			this.defaultValue = defaultValue;
		}

		logger.trace("setDefaultValue(BasicSchemaObjAbstractValue<?>) - end");
	}
	public BasicSchemaObjAbstractValue<?> getMultiplicity() {
		logger.trace("getMultiplicity() - start");

		logger.trace("getMultiplicity() - end");
		return multiplicity;
	}
	public void setMultiplicity(BasicSchemaObjAbstractValue<?> multiplicity) {
		logger.trace("setMultiplicity(BasicSchemaObjAbstractValue<?>) - start");

		if (multiplicity != null && multiplicity.getValue() != null) {
			this.multiplicity = multiplicity;
		}

		logger.trace("setMultiplicity(BasicSchemaObjAbstractValue<?>) - end");
	}
	public BasicSchemaObjValueString getCategory() {
		logger.trace("getCategory() - start");

		logger.trace("getCategory() - end");
		return category;
	}
	public void setCategory(BasicSchemaObjAbstractValue<?> category) {
		logger.trace("setCategory(BasicSchemaObjAbstractValue<?>) - start");

		if (category != null && category.getValue() != null) {
			this.category = (BasicSchemaObjValueString) category;
		}

		logger.trace("setCategory(BasicSchemaObjAbstractValue<?>) - end");
	}
	public Status getStatus() {
		logger.trace("getStatus() - start");

		logger.trace("getStatus() - end");
		return status;
	}
	public void setStatus(Status status) {
		logger.trace("setStatus(Status) - start");

		this.status = status;

		logger.trace("setStatus(Status) - end");
	}
	
	@Override
	public String toString() {
		return getName().toString();
	}
	
	public void setFormat(BasicSchemaObjAbstractValue<?> f) {
		logger.trace("setFormat(BasicSchemaObjAbstractValue<?>) - start");

		if (f != null && f.getValue() != null) {
			this.format = (BasicSchemaObjValueObject) f;
		}

		logger.trace("setFormat(BasicSchemaObjAbstractValue<?>) - end");
	}
	
	public BasicSchemaObjValueObject getFormat() {
		logger.trace("getFormat() - start");

		logger.trace("getFormat() - end");
		return this.format;
	}
}