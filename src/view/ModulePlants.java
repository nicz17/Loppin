package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import common.base.Logger;
import common.view.IncrementalSearchBox;

import controller.Controller;
import model.Family;
import model.Plant;
import model.PlantKind;


/**
 * Module to display and edit Plant objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.01.2018: nicz - Creation</li>
 * </ul>
 */
public class ModulePlants extends AbstractModule<Plant> {
	private static final Logger log = new Logger("ModulePlants", true);
	
	private EditorPlant editor;

	public ModulePlants() {
		super(2);
		
		loadWidgets();
		loadData();
		log.info("Done constructor");
	}
	
	@Override
	public void plantUpdated(int idx) {
		setSelectedObject(idx);
		showObjects();
	}
	
	@Override
	protected void createObject() {
		Plant newObj = new Plant(0, "", "", PlantKind.getDefault(), Family.getDefault(), null);
		vecObjects.add(newObj);
		
		// show object in table
		TableItem item = new TableItem(tblData, SWT.NONE);
		item.setText(newObj.getDataRow());
		tblData.setSelection(item);
		
		// show object in editor
		showObject(newObj);
	}

	@Override
	protected void onTableSelection(Plant obj) {
		showObject(obj);
	}

	@Override
	protected void showObjects() {
		vecObjects = Controller.getInstance().getPlants(searchBox.getSearchText());
		reloadTable();
	}

	@Override
	protected void loadWidgets() {
		initTable(new String[] {"Nom", "Nom latin", "Description", "Famille", "Type"}, 
				  new double[] {0.20, 0.20, 0.40, 0.20, 0.20} );
		
	    editor = new EditorPlant(cRight);
	    
	    searchBox = new IncrementalSearchBox(cButtons) {
	    	public void onSearch() {
	    		showObjects();
	    	}
	    };
		
		Controller.getInstance().addDataListener(this);

		//orderByColumn(0);
		log.info("Done loadWidgets");
	}

	@Override
	protected void loadData() {
		showObjects();
		if (!vecObjects.isEmpty()) {
			tblData.select(0);
			showObject(vecObjects.firstElement());
		}
		log.info("Done loadData");
	}
	
	private void showObject(Plant obj) {
		editor.showObject(obj);
	}

}
