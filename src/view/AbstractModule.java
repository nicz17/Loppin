package view;

import java.util.Vector;

import model.DataObject;
import model.Field;
import model.Ordering;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

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
 * </ul>
 *
 * @param <T> the type of objects displayed in this module.
 */
public abstract class AbstractModule<T extends DataObject> extends TabbedModule {
	
	/** the table width in pixels */
	private static final int tblWidth = 800;
	
	protected Table tblData;
	protected Composite cRight, cButtons, cThird;
	protected Button btnNew, btnReload;
	protected Label lblStatus;
	protected SearchBox searchBox = null;
	protected int selCol;
	protected Ordering ordering;
	
	/** the table columns */
	protected Vector<Field> vecColumns;
	
	/** the objects displayed in the table */
	protected Vector<T> vecObjects;
	
	/** ID of the currently selected object */
	protected Integer selIdx;
	

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
		
		this.vecColumns = new Vector<>();
		this.ordering = null;
		
		GridLayout gl =  new GridLayout(nCols, false);
		this.setLayout(gl);
		GridData data;

		tblData = new Table(this, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		tblData.setLinesVisible(true);
		tblData.setHeaderVisible(true);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = tblWidth;
		data.verticalIndent = 10;
		//data.horizontalIndent = 8;
		tblData.setLayoutData(data);
		tblData.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				T selObj = vecObjects.get(tblData.getSelectionIndex());
				selIdx = new Integer(selObj.getIdx());
				onTableSelection(selObj);
			}
		});

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
		
		selCol = -1;
	}
	
	@Override
	public void selectObject(int idx) {
		setSelectedObject(idx);
		reselectObject();
	}
	
	/**
	 * Initializes the table with one column for each specified field.
	 * @param fields  the database fields to display as table columns
	 */
	protected void initTable(Field[] fields) {
		// Clear table columns
		vecColumns.clear();
		
		// Set table columns from fields
		for (Field field : fields) {
			vecColumns.add(field);
			TableColumn column = new TableColumn(tblData, SWT.NONE);
			column.setText(field.getGuiName());
			column.setWidth(field.getWidth());
			column.setData(field);
			column.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					TableColumn column = (TableColumn)e.widget;
					onSorting(column);
				}
			});
		}
		
		// Set initial table sorting
		tblData.setSortColumn(tblData.getColumn(0));
		tblData.setSortDirection(SWT.UP);
	}
	
	protected void reloadTable() {
		tblData.removeAll();
		for (T obj : vecObjects) {
			TableItem item = new TableItem(tblData, SWT.NONE);
			for (int iCol = 0; iCol < vecColumns.size(); ++iCol) {
				String value = obj.getValue(vecColumns.get(iCol));
				if (value == null) {
					value = "<Erreur!>";
				}
				item.setText(iCol, value);
			}
		}
		reselectObject();
	}
	
	protected abstract void onTableSelection(T obj);
	
	/**
	 * Called when a table column was selected
	 * (by clicking in the table header).
	 * Sets the table sorting state.
	 * @param column  the column that was clicked.
	 */
	protected void onSorting(TableColumn column) {
		boolean isUp = true;
		if (tblData.getSortColumn() == column) {
			// flip sort direction
			isUp = (tblData.getSortDirection() != SWT.UP);
		}
		tblData.setSortColumn(column);
		tblData.setSortDirection(isUp ? SWT.UP : SWT.DOWN);
		Field field = (Field)column.getData();
		ordering = new Ordering(field, isUp);
		showObjects();
	}

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
	
	protected void createObject() {}
	
	/**
	 * Set the database index of the object to select.
	 * Used after new object creation or update.
	 * 
	 * @param idx database index of the object to select
	 */
	protected void setSelectedObject(int idx) {
		//if (idx > 0) selIdx = idx;
		selIdx = idx;
	}
	
	/**
	 * Try to reselect the object that was selected before data were reloaded.
	 */
	private void reselectObject() {
		if (selIdx == null) return;
		
		for (int k=0; k<vecObjects.size(); k++) {
			if (vecObjects.get(k).getIdx() == selIdx.intValue()) {
				T selObj = vecObjects.get(k);
				tblData.select(k);
				onTableSelection(selObj);
			}
		}
	}

}
