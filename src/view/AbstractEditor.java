package view;

import model.Field;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import common.view.WidgetsFactory;

import controller.DataListener;

/**
 * An abstract superclass for editors, 
 * providing save and cancel buttons.
 * 
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 *
 */
public abstract class AbstractEditor extends Composite implements DataListener {
	
	/** Shortcut to the {@link WidgetsFactory}, for convenience */
	protected static final WidgetsFactory widgetsFactory =
		WidgetsFactory.getInstance();

	/** the save button */
	protected Button btnSave;
	
	/** the cancel button */
	protected Button btnCancel;

	/** the delete button */
	protected Button btnDelete;
	
	/** the main composite, where the editing widgets should be placed */
	protected Composite cMain;
	
	/** the composite where the save and cancel button are */
	protected Composite cButtons;
	
	/** the group containing all the editor's widgets */
	protected Group gEdit;
	
	/** listener enabling save/cancel buttons on text modifications */
	protected ModifyListener modifListener;
	
	/**
	 * Constructors.
	 * Creates standard widgets such as save and cancel buttons.
	 * 
	 * @param parent the parent composite
	 */
	public AbstractEditor(Composite parent) {
		super(parent, 0);
		
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		gEdit = widgetsFactory.createGroup(this, "Edition");
		
		int nButtonCols = 4;
		
		cMain    = widgetsFactory.createComposite(gEdit, 2, false, 6);
		cButtons = widgetsFactory.createComposite(gEdit, nButtonCols, true, 6);
	    
		widgetsFactory.createHorizontalSeparator(cButtons, nButtonCols);

		btnSave = widgetsFactory.createSaveButton(cButtons,
				new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				saveObject();
			}
		});
		
		btnCancel = widgetsFactory.createCancelButton(cButtons,
				new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cancel();
			}
		});
		
		btnDelete = widgetsFactory.createDeleteButton(cButtons, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				deleteObject();
			}
		});
		
		modifListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				enableWidgets(true);
			}
		};
		
		enableWidgets(false);
	}
	
	/**
	 * Enable or disable the editor's widgets.
	 * 
	 * @param enabled true to enable widgets.
	 */
	protected void enableWidgets(boolean enabled) {
		boolean hasUnsavedData = hasUnsavedData();
		btnSave.setEnabled(hasUnsavedData);
		btnCancel.setEnabled(hasUnsavedData);
		btnDelete.setEnabled(enabled);
		cMain.setEnabled(enabled);
	}
	
	protected void addLabel(Field field) {
		widgetsFactory.createLabel(cMain, field.getGuiName());
	}
	
	/**
	 * Called when the cancel button is pushed.
	 * Must be implemented by subclasses.
	 */
	protected abstract void cancel();
	
	/**
	 * Called when the save button is pushed.
	 * Must be implemented by subclasses.
	 */
	protected abstract void saveObject();
	
	/**
	 * Called when the delete button is pushed.
	 * Must be implemented by subclasses.
	 */
	protected abstract void deleteObject();
	
	/**
	 * Check if the editor contains unsaved data.
	 * Used to enable or disable the save and cancel buttons.
	 * Must be implemented by subclasses.
	 * 
	 * @return true only if the editor contains unsaved data.
	 */
	protected abstract boolean hasUnsavedData();
	
	@Override
	public void soilUpdated(int idx) {};

	@Override
	public void plantUpdated(int idx) {};

	@Override
	public void gardenUpdated(int idx) {};

	@Override
	public void associationUpdated(int idx) {};

	@Override
	public void journalUpdated(int idx) {};
}
