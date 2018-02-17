package view;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import model.DataObject;
import model.Field;
import model.Ordering;

/**
 * A SWT Table displaying a collection of {@link DataObject}s.
 * Provides methods to sort and select.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>10.02.2018: nicz - Creation (extracted from AbstractModule)</li>
 * </ul>
 */
public abstract class DataTable<T extends DataObject> {
	
	/** the SWT table */
	protected final Table tblData;
	
	/** the table columns */
	protected final Vector<Field> vecColumns;
	
	/** the objects displayed in the table */
	protected Vector<T> vecObjects;
	
	/** Index of the currently selected object */
	protected Integer selIdx;
	
	/** the table width in pixels */
	protected int width = 800;
	
	/** the table ordering state */
	protected Ordering ordering;
	
	/**
	 * Constructor.
	 * @param parent  the parent composite
	 * @param width   the table width in pixels
	 */
	public DataTable(Composite parent, int width) {
		this.width = width;
		this.vecColumns = new Vector<>();
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = width;
		data.verticalIndent = 10;
		//data.horizontalIndent = 8;
		
		this.tblData = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		tblData.setLinesVisible(true);
		tblData.setHeaderVisible(true);
		tblData.setLayoutData(data);
		tblData.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				T selObj = vecObjects.get(tblData.getSelectionIndex());
				selIdx = new Integer(selObj.getIdx());
				onSelection(selObj);
			}
		});
	}
	
	/**
	 * Initializes the table with one column for each specified field.
	 * @param fields  the database fields to display as table columns
	 */
	public void initTable(Field[] fields) {
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
	
	/**
	 * Displays the specified list of objects in the table.
	 * @param vecObjects  the objects to display
	 */
	public void showObjects(Vector<T> vecObjects) {
		this.vecObjects = vecObjects;
		reloadTable();
	}
	
	/**
	 * Called when a table row has been selected.
	 * @param obj  the selected object
	 */
	public abstract void onSelection(T obj);
	
	/**
	 * Adds a new object to the table.
	 * Reloads the table. 
	 * Selects the new object if required.
	 * @param newObj  the object to add
	 * @param bSelectIt  true to select new object
	 */
	public void addObject(T newObj, boolean bSelectIt) {
		vecObjects.add(newObj);
		if (bSelectIt) {
			selIdx = newObj.getIdx();
		}
		reloadTable();
	}
	
	/**
	 * Called when the table sorting has been modified.
	 */
	public abstract void onSorting();
	
	public T getSelectedObject() {
		if (selIdx == null) {
			return null;
		}
		for (T obj : vecObjects) {
			if (obj.getIdx() == selIdx.intValue()) {
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * Set the database index of the object to select.
	 * Used after new object creation or update.
	 * 
	 * @param idx database index of the object to select
	 */
	public void setSelectedObject(int idx) {
		//if (idx > 0) selIdx = idx;
		selIdx = idx;
		reselectObject();
	}
	
	/**
	 * Selects the first object displayed in the table.
	 * If the table is empty, does nothing.
	 */
	public void selectFirstObject() {
		if (vecObjects != null && !vecObjects.isEmpty()) {
			setSelectedObject(vecObjects.firstElement().getIdx());
		}
	}
	
	/**
	 * @return  the table ordering state, describing which column
	 * is sorted in which direction
	 */
	public Ordering getOrdering() {
		return this.ordering;
	}
	
	/**
	 * Clears and reloads the table.
	 * Tries to re-select the previously selected object.
	 */
	protected void reloadTable() {
		tblData.removeAll();
		for (T obj : vecObjects) {
			TableItem item = new TableItem(tblData, SWT.NONE);
			for (int iCol = 0; iCol < vecColumns.size(); ++iCol) {
				String value = obj.getValue(vecColumns.get(iCol));
				if (value == null) {
					value = "-";
				}
				item.setText(iCol, value);
			}
		}
		reselectObject();
	}
	
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
		onSorting();
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
				onSelection(selObj);
			}
		}
	}
	
}
