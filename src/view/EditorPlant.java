package view;

import java.util.Arrays;
import java.util.Vector;

import model.Family;
import model.Plant;
import model.PlantKind;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import common.view.MessageBox;

import controller.Controller;

/**
 * Editor for {@link Plant} objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.01.2018: nicz - Creation</li>
 * </ul>
 */
public class EditorPlant extends AbstractEditor {
	
	private Text txtName;
	private Text txtDesc;
	private Text txtLatin;
	private SelectorSoil selSoil;
	private EnumSelector<Family> selFamily;
	private EnumSelector<PlantKind> selKind;
	private SelectorWeekNumber selSowing;
	private SelectorWeekNumber selHarvest;
	
	private Plant theObject;

	/**
	 * Constructor.
	 * @param parent  the parent composite.
	 */
	public EditorPlant(Composite parent) {
		super(parent);
		
		widgetsFactory.createLabel(cMain, "Nom");
		txtName = widgetsFactory.createText(cMain, 64, modifListener);
		
		widgetsFactory.createLabel(cMain, "Nom latin");
		txtLatin = widgetsFactory.createText(cMain, 64, modifListener);

		widgetsFactory.createLabel(cMain, "Description", true);
		txtDesc = widgetsFactory.createMultilineText(cMain, 
				512, 125, modifListener);
		
		widgetsFactory.createLabel(cMain, "Famille");
		selFamily = new EnumSelector<Family>("PlantFamilySelector", cMain, modifListener) {
			@Override
			public String getDisplayValue(Family family) {
				return family.getGuiName();
			}
			
			@Override
			protected Vector<Family> getData() {
				return new Vector<Family>(Arrays.asList(Family.values()));
			}
		};
		
		widgetsFactory.createLabel(cMain, "Type");
		selKind = new EnumSelector<PlantKind>("PlantKindSelector", cMain, modifListener) {
			@Override
			public String getDisplayValue(PlantKind kind) {
				return kind.getGuiName();
			}
			
			@Override
			protected Vector<PlantKind> getData() {
				return new Vector<PlantKind>(Arrays.asList(PlantKind.values()));
			}
		};
		
		widgetsFactory.createLabel(cMain, "Sol");
		selSoil = new SelectorSoil("Plant soil selector", cMain, true, modifListener);
		
		widgetsFactory.createLabel(cMain, "Semis");
		selSowing = new SelectorWeekNumber(cMain);
		
		widgetsFactory.createLabel(cMain, "Récolte");
		selHarvest = new SelectorWeekNumber(cMain);
		
		widgetsFactory.createLink(cButtons, "<a>Wikipedia</a>", "Voir cette plante dans Wikipedia", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (theObject != null) {
					String url = "http://fr.wikipedia.org/wiki/" + theObject.getNameLatin();
					Program.launch(url);
				}
			}
		});

		Controller.getInstance().addDataListener(this);
	    selSoil.load();
	}
	
	/**
	 * Displays the specified object in the editor.
	 * Loads the object properties in the widgets.
	 * @param object  the object under edition. May be null.
	 */
	public void showObject(Plant object) {
		theObject = object;
		
		if (object != null) {
			txtName.setText(object.getName());
			txtLatin.setText(object.getNameLatin());
			txtDesc.setText(object.getDescription() == null ? "" : object.getDescription());
			selSoil.setValue(object.getSoil());
			selFamily.setValue(object.getFamily());
			selKind.setValue(object.getKind());
			selSowing.setValue(object.getDateSowing());
			selHarvest.setValue(object.getDateHarvest1());
		} else {
			txtName.setText("");
			txtDesc.setText("");
			txtLatin.setText("");
			selSoil.setValue(null);
			selFamily.setValue(null);
			selKind.setValue(null);
		}
		
		enableWidgets(object != null);
	}

	@Override
	protected void cancel() {
		showObject(theObject);
	}
	
	/**
	 * Reset the editor, clearing all widgets.
	 */
	public void reset() {
		showObject(null);
	}

	@Override
	protected void saveObject() {
		if (theObject != null) {
			theObject.setName(txtName.getText());
			theObject.setDescription(txtDesc.getText());
			theObject.setNameLatin(txtLatin.getText());
			theObject.setSoil(selSoil.getValue());
			theObject.setFamily(selFamily.getValue());
			theObject.setKind(selKind.getValue());
			theObject.setDateSowing(selSowing.getValue());
			theObject.setDateHarvest1(selHarvest.getValue());

			try {
				Controller.getInstance().savePlant(theObject);
				enableWidgets(false);
			} catch (Exception e) {
				MessageBox.error(e);
			}
		} else {
			MessageBox.error("Impossible de sauver un objet non défini !");
		}
	}

	@Override
	protected boolean hasUnsavedData() {
		if (theObject == null) return false;
		if (!txtName.getText().equals(theObject.getName())) return true;
		if (!txtDesc.getText().equals(theObject.getDescription())) return true;
		if (!txtLatin.getText().equals(theObject.getNameLatin())) return true;
		if (selSoil.getValue() != null && !selSoil.getValue().equals(theObject.getSoil())) return true;
		if (selFamily.getValue() != null && !selFamily.getValue().equals(theObject.getFamily())) return true;
		if (selKind.getValue() != null && !selKind.getValue().equals(theObject.getKind())) return true;
		if (selSowing.getValue() != theObject.getDateSowing()) return true;
		if (selHarvest.getValue() != theObject.getDateHarvest1()) return true;
		return false;
	}

	@Override
	protected void deleteObject() {
		if (MessageBox.askYesNo("Effacer la plante " + theObject.getName() + " ?")) {
			try {
				Controller.getInstance().deletePlant(theObject);
			} catch (Exception exc) {
				MessageBox.error(exc);
			}
		}
	}
	
	@Override
	public void soilUpdated(int idx) {
		selSoil.load();
	}

}
