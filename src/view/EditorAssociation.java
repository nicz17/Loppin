package view;

import java.util.Arrays;
import java.util.Vector;

import model.Association;
import model.AssociationKind;
import model.Field;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import common.view.MessageBox;

import controller.Controller;

/**
 * Editor for {@link Association} objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>10.02.2018: nicz - Creation</li>
 * </ul>
 */
public class EditorAssociation extends AbstractEditor {
	
	private Text txtDesc;
	private EnumSelector<AssociationKind> selKind;
	
	private Association theObject;

	/**
	 * Constructor.
	 * @param parent  the parent composite.
	 */
	public EditorAssociation(Composite parent) {
		super(parent);
		
		widgetsFactory.createLabel(cMain, Field.ASSOC_DESC.getGuiName(), true);
		txtDesc = widgetsFactory.createMultilineText(cMain, 
				Field.ASSOC_DESC.getMax(), 125, modifListener);

		addLabel(Field.ASSOC_KIND);
		selKind = new EnumSelector<AssociationKind>("SelectorAssocKind", cMain, modifListener) {
			@Override
			public String getDisplayValue(AssociationKind obj) {
				return obj.getGuiName();
			}
			
			@Override
			protected Vector<AssociationKind> getData() {
				return new Vector<AssociationKind>(Arrays.asList(AssociationKind.values()));
			}
		};

		Controller.getInstance().addDataListener(this);
	}
	
	/**
	 * Displays the specified object in the editor.
	 * Loads the object properties in the widgets.
	 * @param object  the object under edition. May be null.
	 */
	public void showObject(Association object) {
		theObject = object;
		
		if (object != null) {
			txtDesc.setText(object.getDescription() == null ? "" : object.getDescription());
			selKind.setValue(object.getKind());
		} else {
			txtDesc.setText("");
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
			theObject.setDescription(txtDesc.getText());
			theObject.setKind(selKind.getValue());

			try {
				//Controller.getInstance().saveAssociation(theObject);
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
		if (!txtDesc.getText().equals(theObject.getDescription())) return true;
		if (!selKind.getValue().equals(theObject.getKind())) return true;
		return false;
	}

	@Override
	protected void deleteObject() {
		
	}

}
