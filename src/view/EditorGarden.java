package view;

import model.Field;
import model.Garden;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import common.view.MessageBox;

import controller.Controller;

/**
 * Editor for {@link Garden} objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>17.02.2018: nicz - Creation</li>
 * </ul>
 */
public class EditorGarden extends AbstractEditor {
	
	private Text txtName;
	private Text txtDesc;
	private SelectorSizes selSizes;
	
	private Garden theObject;

	/**
	 * Constructor.
	 * @param parent  the parent composite.
	 */
	public EditorGarden(Composite parent) {
		super(parent);
		
		addLabel(Field.GARDEN_NAME);
		txtName = widgetsFactory.createText(cMain, Field.GARDEN_NAME.getMax(), modifListener);

		widgetsFactory.createLabel(cMain, Field.GARDEN_DESC.getGuiName(), true);
		txtDesc = widgetsFactory.createMultilineText(cMain, 
				Field.GARDEN_DESC.getMax(), 125, modifListener);
		
		addLabel(Field.GARDEN_SIZE);
		selSizes = new SelectorSizes(cMain, 2, 50, "tuiles", modifListener);

		Controller.getInstance().addDataListener(this);
	}
	
	/**
	 * Displays the specified object in the editor.
	 * Loads the object properties in the widgets.
	 * @param object  the object under edition. May be null.
	 */
	public void showObject(Garden object) {
		theObject = object;
		
		if (object != null) {
			txtName.setText(object.getName());
			txtDesc.setText(object.getDescription() == null ? "" : object.getDescription());
			selSizes.setValues(object.getSizeX(), object.getSizeY());
		} else {
			txtName.setText("");
			txtDesc.setText("");
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
			theObject.setSizeX(selSizes.getSizeX());
			theObject.setSizeY(selSizes.getSizeY());

			try {
				Controller.getInstance().saveGarden(theObject);
				enableWidgets(false);
			} catch (Exception e) {
				MessageBox.error(e);
			}
		} else {
			MessageBox.error("Impossible de sauver un jardin non défini !");
		}
	}

	@Override
	protected boolean hasUnsavedData() {
		if (theObject == null) return false;
		if (!txtName.getText().equals(theObject.getName())) return true;
		if (!txtDesc.getText().equals(theObject.getDescription())) return true;
		if (selSizes.getSizeX() != theObject.getSizeX()) return true;
		if (selSizes.getSizeY() != theObject.getSizeY()) return true;
		return false;
	}

	@Override
	protected void deleteObject() {
		if (MessageBox.askYesNo("Voulez-vous vraiment effacer le jardin\n" + theObject.getName() + " ?")) {
			try {
				Controller.getInstance().deleteGarden(theObject);
				reset();
			} catch (Exception exc) {
				MessageBox.error(exc);
			}
		}
	}

}
