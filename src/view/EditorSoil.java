package view;

import model.Soil;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import common.view.MessageBox;

import controller.Controller;

/**
 * Editor for {@link Soil} objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 */
public class EditorSoil extends AbstractEditor {
	
	private Text txtName;
	private Text txtDesc;
	private Text txtColor;
	
	private Soil theObject;

	/**
	 * Constructor.
	 * @param parent  the parent composite.
	 */
	public EditorSoil(Composite parent) {
		super(parent);
		
		widgetsFactory.createLabel(cMain, "Nom");
		txtName = widgetsFactory.createText(cMain, 64, modifListener);

		widgetsFactory.createLabel(cMain, "Description", true);
		txtDesc = widgetsFactory.createMultilineText(cMain, 
				512, 125, modifListener);

		widgetsFactory.createLabel(cMain, "Couleur");
		txtColor = widgetsFactory.createText(cMain, 64, modifListener);

		Controller.getInstance().addDataListener(this);
	}
	
	/**
	 * Displays the specified object in the editor.
	 * Loads the object properties in the widgets.
	 * @param object  the object under edition. May be null.
	 */
	public void showObject(Soil object) {
		theObject = object;
		
		if (object != null) {
			txtName.setText(object.getName());
			txtDesc.setText(object.getDescription() == null ? "" : object.getDescription());
			txtColor.setText(object.getColor() == null ? "" : object.getColor());
		} else {
			txtName.setText("");
			txtDesc.setText("");
			txtColor.setText("");
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
			theObject.setColor(txtColor.getText());

			try {
				Controller.getInstance().saveSoil(theObject);
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
		if (!txtColor.getText().equals(theObject.getColor())) return true;
		return false;
	}

	@Override
	protected void deleteObject() {
		if (MessageBox.askYesNo("Effacer le sol " + theObject.getName() + " ?")) {
			try {
				Controller.getInstance().deleteSoil(theObject);
			} catch (Exception exc) {
				MessageBox.error(exc);
			}
		}
	}

}
