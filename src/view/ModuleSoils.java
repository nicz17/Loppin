package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
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
		Soil newObj = new Soil(0, "", "", "RGB {105, 80, 16}");
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
		vecObjects = Controller.getInstance().getSoils(searchBox.getSearchText());
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
	

	@Override
	protected void reloadTable() {
		super.reloadTable();
		
		int iCol = 2;
		for (int iRow = 0; iRow < vecObjects.size(); iRow++) {
			TableItem ti = tblData.getItem(iRow);
			Soil soil = vecObjects.get(iRow);
			RGB rgb = parseRGBString(soil.getColor());
			if (rgb != null) {
				ti.setBackground(iCol, new Color(getDisplay(), rgb));
			}
		}
	}
	
	private void showObject(Soil obj) {
		editor.showObject(obj);
	}
	
	private RGB parseRGBString(String strRGB) {
		Matcher m = Pattern.compile("RGB \\{(\\d+), (\\d+), (\\d+)\\}").matcher(strRGB);
		if (m.find()) {
			int r = Integer.parseInt(m.group(1));
			int g = Integer.parseInt(m.group(2));
			int b = Integer.parseInt(m.group(3));
			return new RGB(r, g, b);
		}
		return null;
	}

}
