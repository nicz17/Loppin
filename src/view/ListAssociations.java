package view;

import model.Plant;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;

import common.view.WidgetsFactory;

/**
 * A widget displaying the associations of a plant.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>05.02.2018: nicz - Creation</li>
 * </ul>
 */
public class ListAssociations extends Composite {
	
	private Plant plant;
	
	private Group  grpAssoc;
	private List   lstAssoc;
	private Button btnEdit;

	/**
	 * Constructor.
	 * 
	 * @param parent  the parent composite.
	 */
	public ListAssociations(Composite parent) {
		super(parent, 0);
		
		this.plant = null;
		
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		grpAssoc = WidgetsFactory.getInstance().createGroup(this, "Associations");
		
		lstAssoc = WidgetsFactory.getInstance().createList(grpAssoc, 240, 700);
		
		btnEdit = WidgetsFactory.getInstance().createPushButton(grpAssoc, "Editer", "edit", "Editer", true, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DialogAssociationEdition dlgEdit = new DialogAssociationEdition(getShell(), plant);
				dlgEdit.open();
			}
		});
		
		showObject(null);
	}
	
	
	public void showObject(Plant p) {
		this.plant = p;
		lstAssoc.removeAll();
		
		if (plant == null) {
			lstAssoc.add("Veuillez choisir\nune plante");
		} else if (plant.getIdx() < 1) {
			lstAssoc.add("Veuillez d'abord\nenregistrer la plante");
		} else {
			lstAssoc.add("Associations pour\n" + plant.getName());
		}
		
		enableWidgets();
	}
	
	/**
	 * Enables or disables the subwidgets.
	 */
	private void enableWidgets() {
		btnEdit.setEnabled(plant != null && plant.getIdx() > 0);
	}

}
