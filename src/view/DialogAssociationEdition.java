package view;

import java.util.Iterator;
import java.util.Vector;

import model.Field;
import model.Plant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.view.MessageBox;
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
	
	private Shell parent;
	private DataTable<Plant> tblPlants;
	private EditorAssociation editor;
	private Button btnSave;
	
	/**
	 * Constructor.
	 * @param parent  the parent shell
	 * @param plant   the plant for which to define associations
	 */
	public DialogAssociationEdition(Shell parent, Plant plant) {
		this.parent = parent;
		this.plant = plant;
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
				
			}
		};
		tblPlants.initTable(new Field[] {Field.PLANT_NAME, Field.PLANT_FAMILY});
		
		editor = new EditorAssociation(shell);
				
		Composite cButtons = widgetsFactory.createComposite(shell, 2, true, 12);
		
		btnSave = widgetsFactory.createOkButton(cButtons, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				save();
				shell.dispose();
			}
		});
		
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
		Vector<Plant> vecPlants = Controller.getInstance().getPlants(null, tblPlants.getOrdering());
		
		// remove our plant
		Iterator<Plant> it = vecPlants.iterator();
		while (it.hasNext()) {
			if (it.next().getIdx() == plant.getIdx()) {
				it.remove();
				break;
			}
		}
		
		tblPlants.showObjects(vecPlants);
	}
	
	private void save() {
		MessageBox.info("Pas encore implémenté !");
	}
	
	private void enableButtons() {
		btnSave.setEnabled(false);
	}

}
