package view;

import java.util.Vector;

import model.Association;
import model.AssociationKind;
import model.Plant;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import common.view.IconManager;
import common.view.WidgetsFactory;

import controller.Controller;

/**
 * A widget displaying the associations of a plant.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>05.02.2018: nicz - Creation</li>
 * <li>14.02.2018: nicz - Display associations with color icons and description</li>
 * </ul>
 */
public class ListAssociations extends Composite {
	
	private Plant plant;
	
	private Group  grpAssoc;
	private Composite cItems;
	private Button btnEdit;
	
	private final Color colorGood = new Color(getDisplay(), new RGB(0, 255, 0));
	private final Color colorBad  = new Color(getDisplay(), new RGB(255, 0, 0));

	/**
	 * Constructor.
	 * 
	 * @param parent  the parent composite.
	 */
	public ListAssociations(Composite parent) {
		super(parent, 0);
		
		this.plant = null;
		
		this.setLayout(new GridLayout());
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 200;
		this.setLayoutData(gd);
		
		grpAssoc = WidgetsFactory.getInstance().createGroup(this, "Associations");
		
		cItems = WidgetsFactory.getInstance().createComposite(grpAssoc, 2, false, 2);
		
		btnEdit = WidgetsFactory.getInstance().createPushButton(grpAssoc, "Editer", "edit", 
				"Ajouter ou modifier les associations", true, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DialogAssociationEdition dlgEdit = new DialogAssociationEdition(getShell(), plant);
				dlgEdit.open();
			}
		});
		
		showObject(null);
	}
	
	
	public void showObject(Plant p) {
		this.plant = p;
		
		for (Control control : cItems.getChildren()) {
			control.dispose();
		}
		cItems.pack();
		
		if (plant == null) {
			WidgetsFactory.getInstance().createLabel(cItems, "Veuillez choisir\nune plante");
		} else if (plant.isUnsaved()) {
			WidgetsFactory.getInstance().createLabel(cItems, "Veuillez d'abord\nenregistrer la plante");
		} else {
			Vector<Association> vecAssoc = Controller.getInstance().getAssociations(plant);
			for (Association assoc : vecAssoc) {
				Label lblColor = WidgetsFactory.getInstance().createLabel(cItems, 16);
				boolean isGood = (assoc.getKind() == AssociationKind.GOOD);
				lblColor.setImage(IconManager.createColorIcon(16, isGood ? colorGood : colorBad));
				
				Label lblName = WidgetsFactory.getInstance().createLabel(cItems, assoc.getOtherPlant(plant).getName());
				
				String sTooltip = assoc.getTooltip();
				lblColor.setToolTipText(sTooltip);
				lblName.setToolTipText(sTooltip);
			}
			if (vecAssoc.isEmpty()) {
				WidgetsFactory.getInstance().createLabel(cItems, "Aucune association");
			}
		}
		
		cItems.pack();
		enableWidgets();
	}
	
	/**
	 * Enables or disables the subwidgets.
	 */
	private void enableWidgets() {
		btnEdit.setEnabled(plant != null && plant.getIdx() > 0);
	}

}
