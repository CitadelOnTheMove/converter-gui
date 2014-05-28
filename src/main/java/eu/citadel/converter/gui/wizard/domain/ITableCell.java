package eu.citadel.converter.gui.wizard.domain;

import java.util.ArrayList;

public interface ITableCell {
	ArrayList<Item> removeById(Object id);
	void setRow(int row);
	void setColumn(int column);
}
