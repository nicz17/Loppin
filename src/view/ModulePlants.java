package view;

import model.Family;
import model.Field;
import model.Plant;
import model.PlantKind;

import common.base.Logger;
import common.view.IncrementalSearchBox;

import controller.Controller;


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
	private ListAssociations lstAssoc;

	public ModulePlants() {
		super(3);
		
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
	public void associationUpdated(int idx) {
		showObject(getSelectedObject());
	}
	
	@Override
	protected void createObject() {
		Plant newObj = new Plant(0, "", "", PlantKind.getDefault(), Family.getDefault(), null);
		vecObjects.add(newObj);
		
		// show object in table
		setSelectedObject(0);
		reloadTable();
		
		// show object in editor
		showObject(newObj);
	}

	@Override
	protected void onTableSelection(Plant obj) {
		showObject(obj);
	}

	@Override
	protected void showObjects() {
		vecObjects = Controller.getInstance().getPlants(searchBox.getSearchText(), ordering);
		reloadTable();
	}

	@Override
	protected void loadWidgets() {
		initTable(new Field[] {Field.PLANT_NAME,   Field.PLANT_LATIN, Field.PLANT_DESC, 
							   Field.PLANT_FAMILY, Field.PLANT_KIND,  Field.PLANT_SOIL});
		
	    editor = new EditorPlant(cRight);
	    
	    lstAssoc = new ListAssociations(cThird);
	    
	    searchBox = new IncrementalSearchBox(cButtons) {
	    	public void onSearch() {
	    		showObjects();
	    	}
	    };
	    
	    btnNew.setToolTipText("Ajouter une plante");
		
		Controller.getInstance().addDataListener(this);
	}

	@Override
	protected void loadData() {
		showObjects();
		if (!vecObjects.isEmpty()) {
			tblData.select(0);
			showObject(vecObjects.firstElement());
		}
	}
	
	private void showObject(Plant obj) {
		editor.showObject(obj);
		lstAssoc.showObject(obj);
	}

}
