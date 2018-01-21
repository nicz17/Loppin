package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import common.base.Logger;
import common.view.IncrementalSearchBox;

import controller.Controller;
import model.Soil;


/**
 * Module to display and edit Soil objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 */
public class ModuleSoils extends AbstractModule<Soil> {
	private static final Logger log = new Logger("ModuleSoils", true);
	
	private EditorSoil editor;

	public ModuleSoils() {
		super(2);
		
		loadWidgets();
		loadData();
		log.info("Done constructor");
	}
	
	@Override
	public void soilUpdated(int idx) {
		setSelectedObject(idx);
		showObjects();
	}
	
	@Override
	protected void createObject() {
		Soil newObj = new Soil(0, "", "", "");
		vecObjects.add(newObj);
		
		// show object in table
		TableItem item = new TableItem(tblData, SWT.NONE);
		item.setText(newObj.getDataRow());
		tblData.setSelection(item);
		
		// show object in editor
		showObject(newObj);
	}

	@Override
	protected void onTableSelection(Soil obj) {
		showObject(obj);
	}

	@Override
	protected void showObjects() {
		vecObjects = Controller.getInstance().getSoils();
		reloadTable();
	}

	@Override
	protected void loadWidgets() {
		initTable(new String[] {"Nom", "Description", "Couleur"}, 
				  new double[] {0.30, 0.40, 0.30} );
		
	    editor = new EditorSoil(cRight);
	    
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
	
	private void showObject(Soil obj) {
		editor.showObject(obj);
	}

}
