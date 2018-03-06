package view;

import model.Field;
import model.Journal;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import common.view.MessageBox;

import controller.Controller;

/**
 * Editor for {@link Journal} objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>05.03.2018: nicz - Creation</li>
 * </ul>
 */
public class EditorJournal extends AbstractEditor {
	
	private Text txtTitle;
	private Text txtText;
	private Label lblDate;
	private Label lblGarden;
	
	private Journal theObject;

	/**
	 * Constructor.
	 * @param parent  the parent composite.
	 */
	public EditorJournal(Composite parent) {
		super(parent);
		
		addLabel(Field.JOURNAL_DATE);
		lblDate = widgetsFactory.createLabel(cMain);
		
		addLabel(Field.JOURNAL_GARDEN);
		lblGarden = widgetsFactory.createLabel(cMain);
		
		addLabel(Field.JOURNAL_TITLE);
		txtTitle = widgetsFactory.createText(cMain, Field.JOURNAL_TITLE.getMax(), modifListener);

		widgetsFactory.createLabel(cMain, Field.JOURNAL_TEXT.getGuiName(), true);
		txtText = widgetsFactory.createMultilineText(cMain, 
				Field.JOURNAL_TEXT.getMax(), 125, modifListener);

		Controller.getInstance().addDataListener(this);
	}
	
	/**
	 * Displays the specified object in the editor.
	 * Loads the object properties in the widgets.
	 * @param object  the object under edition. May be null.
	 */
	public void showObject(Journal object) {
		theObject = object;
		
		if (object != null) {
			lblDate.setText(object.getValue(Field.JOURNAL_DATE));
			lblGarden.setText(object.getValue(Field.JOURNAL_GARDEN));
			txtTitle.setText(object.getValue(Field.JOURNAL_TITLE));
			txtText.setText(object.getValue(Field.JOURNAL_TEXT));
		} else {
			lblDate.setText("");
			lblGarden.setText("");
			txtTitle.setText("");
			txtText.setText("");
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
			theObject.setTitle(txtTitle.getText());
			theObject.setText(txtText.getText());

			try {
				Controller.getInstance().saveJournal(theObject);
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
		if (!txtTitle.getText().equals(theObject.getTitle())) return true;
		if (!txtText.getText().equals(theObject.getText())) return true;
		return false;
	}

	@Override
	protected void deleteObject() {
		if (MessageBox.askYesNo("Voulez-vous vraiment effacer l'entrée\n" + theObject.getTitle() + " ?")) {
			try {
				Controller.getInstance().deleteJournal(theObject);
				reset();
			} catch (Exception exc) {
				MessageBox.error(exc);
			}
		}
	}

}
