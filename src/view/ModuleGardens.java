package view;

import java.util.Vector;

import model.Field;
import model.Garden;

import common.view.IncrementalSearchBox;

import controller.Controller;


/**
 * Module to edit garden objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>21.01.2018: nicz - Creation as blank module</li>
 * <li>17.02.2018: nicz - Implementation of table and editor</li>
 * </ul>
 */
public class ModuleGardens extends AbstractModule<Garden> {

	private EditorGarden editor;
	
	public ModuleGardens() {
		super(2);
	
		loadWidgets();
		loadData();
	}
	
	@Override
	public void gardenUpdated(int idx) {
		dataTable.setSelectedObject(idx);
		showObjects();
	}
	
	@Override
	protected void createObject() {
		Garden newObj = new Garden(0, "", "", "", 10, 10, 50);
		dataTable.addObject(newObj, true);
	}

	@Override
	protected void onTableSelection(Garden obj) {
		showObject(obj);
	}

	@Override
	protected void showObjects() {
		Vector<Garden> vecObjects = Controller.getInstance().getGardens(searchBox.getSearchText(), dataTable.getOrdering());
		dataTable.showObjects(vecObjects);
	}

	@Override
	protected void showObject(Garden obj) {
		editor.showObject(obj);
	}

	@Override
	protected void loadWidgets() {
		dataTable.initTable(new Field[] {Field.GARDEN_NAME, Field.GARDEN_DESC, Field.GARDEN_SIZE, Field.GARDEN_SIZETILE});
		
	    editor = new EditorGarden(cRight);
	    
	    searchBox = new IncrementalSearchBox(cButtons) {
	    	public void onSearch() {
	    		showObjects();
	    	}
	    };
		
		Controller.getInstance().addDataListener(this);
	}

	@Override
	protected void loadData() {
		showObjects();
		dataTable.selectFirstObject();
	}

}
