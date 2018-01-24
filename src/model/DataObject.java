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
	 * Gets a textual value for the specified field.
	 * @param field  the database field
	 * @return a textual representation of the field value
	 */
	public abstract String getValue(Field field);
	
}
