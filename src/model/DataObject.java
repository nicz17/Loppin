package model;

/**
 * Abstract superclass for database objects,
 * with methods to get the database index
 * and a table row representation.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 */
public abstract class DataObject {
	
	/**
	 * @return the database index of the object.
	 */
	public abstract int getIdx();
	
	/**
	 * Describes how the object should be displayed in tables.
	 * @return a list of data to display in tables
	 */
	public abstract String[] getDataRow();
	
}
