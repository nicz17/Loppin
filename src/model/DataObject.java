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
	 * Gets the name of the object, or a compact textual representation
	 * equivalent to a name.
	 * @return the object name
	 */
	public abstract String getName();
	
	/**
	 * Gets a textual value for the specified field.
	 * @param field  the database field
	 * @return a textual representation of the field value
	 */
	public abstract String getValue(Field field);
	
	/**
	 * Checks if the object is saved in database or not.
	 * New objects have index <= 0.
	 * @return true if object has not been saved yet.
	 */
	public boolean isUnsaved() {
		return getIdx() < 1;
	}
	
}
