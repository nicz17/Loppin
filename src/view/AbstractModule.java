package view;

import model.DataObject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import common.view.SearchBox;


/**
 * An abstract superclass for modules which display data in a table.
 * 
 * <p>Provides widgets for the table, creation and reload buttons,
 * a search box, and a simple table ordering mechanism.</p>
 * 
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * <li>24.01.2018: nicz - Configure table columns using Fields</li>
 * <li>26.01.2018: nicz - Added sorting mechanism</li>
 * <li>17.02.2018: nicz - Use DataTable class</li>
 * </ul>
 *
 * @param <T> the type of objects displayed in this module.
 */
public abstract class AbstractModule<T extends DataObject> extends TabbedModule {
	
	/** the table width in pixels */
	private static final int tblWidth = 800;
	
	/** The data table */
	protected DataTable<T> dataTable;
	
	protected Composite cRight, cButtons, cThird;
	protected Button btnNew, btnReload;
	protected Label lblStatus;
	protected SearchBox searchBox = null;
	

	/**
	 * Create a module with two columns.
	 * Usually one column for the table, and one for an editor.
	 */
	public AbstractModule() {
		this(2);
	}
	
	/**
	 * Create a module with the given number of columns.
	 * @param nCols the number of columns in the layout
	 */
	public AbstractModule(int nCols) {
		super();
		
		GridLayout gl =  new GridLayout(nCols, false);
		this.setLayout(gl);
		GridData data;
		
		dataTable = new DataTable<T>(this, tblWidth) {
			@Override
			public void onSelection(T obj) {
				showObject(obj);
			}

			@Override
			public void onSorting() {
				loadData();
			}
		};

		cRight = new Composite(this, 0);
		cRight.setLayout(new GridLayout());
	    data = new GridData(GridData.FILL_BOTH);
	    data.verticalAlignment = SWT.TOP;
	    data.verticalSpan = 2;
	    cRight.setLayoutData(data);

	    if (nCols > 2) {
	    	cThird = new Composite(this, 0);
	    	cThird.setLayout(new GridLayout());
	    	data = new GridData(SWT.LEFT, SWT.FILL, false, true);
	    	data.verticalAlignment = SWT.TOP;
	    	data.verticalSpan = 2;
	    	//data.widthHint = 210;
	    	cThird.setLayoutData(data);
	    } else {
	    	cThird = null;
	    }

		gl = new GridLayout(5, false);
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		gl.verticalSpacing = 0;
		
		cButtons = new Composite(this, 0);
		cButtons.setLayout(gl);
	    cButtons.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

	    lblStatus = widgetsFactory.createLabel(cButtons, "");
	    
	    btnReload = widgetsFactory.createPushButton(cButtons, null, 
	    		"refresh", "Recharger depuis la base de donnees", false, 
	    		new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showObjects();
			}
		});

	    btnNew = widgetsFactory.createPushButton(cButtons, null, 
	    		"add", "Nouveau", false, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				createObject();
			}
		});
		
//		selCol = -1;
	}
	
	@Override
	public void selectObject(int idx) {
		dataTable.setSelectedObject(idx);
	}
	
	public T getSelectedObject() {
		return dataTable.getSelectedObject();
	}
	
	/**
	 * Called when a row is selected in table.
	 * @param obj  the selected object
	 */
	protected abstract void onTableSelection(T obj);

	/**
	 * Set the new button's tooltip text.
	 * @param tooltip the tooltip text.
	 */
	protected void setNewButtonTooltip(String tooltip) {
		btnNew.setToolTipText(tooltip);
	}
	
	/**
	 * Display or redisplay the objects.
	 * Must be implemented by subclasses.
	 */
	protected abstract void showObjects();
	
	/**
	 * Display the selected object.
	 * Must be implemented by subclasses.
	 * @param obj  the object to display.
	 */
	protected abstract void showObject(T obj);
	
	protected void createObject() {}

}
