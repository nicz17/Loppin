package view;

import java.util.Iterator;
import java.util.Vector;

import model.Association;
import model.Field;
import model.Plant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.view.WidgetsFactory;

import controller.Controller;

/**
 * A dialog window used to define the association of a selected plant with other plants.
 * 
 * <p><b>Modifications:</b>
 * <ul>
 * <li>08.02.2018: nicz - Creation</li>
 * </ul>
 *
 */
public class DialogAssociationEdition {
	
	private static final WidgetsFactory widgetsFactory =
			WidgetsFactory.getInstance();

	private final Plant plant;
	private Vector<Association> vecAssoc;
	
	private Shell parent;
	private DataTable<Plant> tblPlants;
	private EditorAssociation editor;
	
	/**
	 * Constructor.
	 * @param parent  the parent shell
	 * @param plant   the plant for which to define associations
	 */
	public DialogAssociationEdition(Shell parent, Plant plant) {
		this.parent = parent;
		this.plant = plant;
		this.vecAssoc = new Vector<>();
	}
	
	public void open() {
		Display display = parent.getDisplay();
		final Shell shell =
			new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setText("Définir les associations de " + plant.getName());
		shell.setLayout(new GridLayout(2, false));
		
		GridData data = new GridData();
		data.heightHint = 600;
		shell.setLayoutData(data);
		
		tblPlants = new DataTable<Plant>(shell, 400) {
			@Override
			public void onSorting() {
				loadData();
			}
			
			@Override
			public void onSelection(Plant obj) {
				showObject(obj);
			}
		};
		tblPlants.initTable(new Field[] {Field.PLANT_NAME, Field.PLANT_FAMILY});
		
		Composite cRight = widgetsFactory.createCompositeTop(shell, true);
		editor = new EditorAssociation(cRight);
				
		Composite cButtons = widgetsFactory.createComposite(cRight, 2, true, 12);
		
		widgetsFactory.createCloseButton(cButtons, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});

		enableButtons();
		
		//shell.pack();
		shell.open();
		loadData();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
	}
	
	private void loadData() {
		// load other plants
		Vector<Plant> vecPlants = Controller.getInstance().getPlants(null, tblPlants.getOrdering());
		
		// remove our plant
		Iterator<Plant> it = vecPlants.iterator();
		while (it.hasNext()) {
			if (it.next().getIdx() == plant.getIdx()) {
				it.remove();
				break;
			}
		}
		
		// load associations with our plant
		vecAssoc = Controller.getInstance().getAssociations(plant);
		
		tblPlants.showObjects(vecPlants);
	}
	
	private void showObject(Plant other) {
		Association assoc = getAssociation(other);
		editor.showObject(assoc);
	}
	
	private Association getAssociation(Plant other) {
		for (Association assoc : vecAssoc) {
			if (assoc.getPlant1().getIdx() == other.getIdx() || assoc.getPlant2().getIdx() == other.getIdx()) {
				return assoc;
			}
		}
		Association assoc = Association.create(plant, other);
		vecAssoc.add(assoc);
		return assoc;
	}
	
	private void enableButtons() {
		
	}

}
