package view;

import java.util.Vector;

import model.Field;
import model.Soil;

import common.view.IncrementalSearchBox;

import controller.Controller;


/**
 * Module to display and edit Soil objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 */
public class ModuleSoils extends AbstractModule<Soil> {
	//private static final Logger log = new Logger("ModuleSoils", true);
	
	private EditorSoil editor;

	public ModuleSoils() {
		super(2);
		
		loadWidgets();
		loadData();
	}
	
	@Override
	public void soilUpdated(int idx) {
		dataTable.setSelectedObject(idx);
		showObjects();
	}
	
	@Override
	protected void createObject() {
		Soil newObj = new Soil(0, "", "", "RGB {105, 80, 16}");
		dataTable.addObject(newObj, true);
	}

	@Override
	protected void onTableSelection(Soil obj) {
		showObject(obj);
	}

	@Override
	protected void showObjects() {
		Vector<Soil> vecObjects = Controller.getInstance().getSoils(searchBox.getSearchText(), dataTable.getOrdering());
		dataTable.showObjects(vecObjects);
	}

	@Override
	protected void loadWidgets() {
		dataTable.initTable(new Field[] {Field.SOIL_NAME, Field.SOIL_DESC, Field.SOIL_COLOR});
		
	    editor = new EditorSoil(cRight);
	    
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
	

//	@Override
//	protected void reloadTable() {
//		super.reloadTable();
//		
//		int iCol = 2;
//		for (int iRow = 0; iRow < vecObjects.size(); iRow++) {
//			TableItem ti = tblData.getItem(iRow);
//			Soil soil = vecObjects.get(iRow);
//			RGB rgb = parseRGBString(soil.getColor());
//			if (rgb != null) {
//				ti.setBackground(iCol, new Color(getDisplay(), rgb));
//			}
//		}
//	}
	
	protected void showObject(Soil obj) {
		editor.showObject(obj);
	}
	
//	private RGB parseRGBString(String strRGB) {
//		Matcher m = Pattern.compile("RGB \\{(\\d+), (\\d+), (\\d+)\\}").matcher(strRGB);
//		if (m.find()) {
//			int r = Integer.parseInt(m.group(1));
//			int g = Integer.parseInt(m.group(2));
//			int b = Integer.parseInt(m.group(3));
//			return new RGB(r, g, b);
//		}
//		return null;
//	}

}
