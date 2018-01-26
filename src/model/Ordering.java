package model;

/**
 * Small class to create a database order clause for a Field.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>26.01.2018: nicz - Creation</li>
 * </ul>
 */
public class Ordering {

	private final Field field;
	private final boolean isAscending;
	
	/**
	 * Constructor.
	 * @param field        the database field on which to sort.
	 * @param isAscending  true to sort in ascending order, false for descending.
	 */
	public Ordering(Field field, boolean isAscending) {
		this.field = field;
		this.isAscending = isAscending;
	}
	
	/**
	 * Gets the SQL order clause,
	 * for example ' ORDER BY plName ASC '
	 * @return SQL order clause
	 */
	public String getOrderClause() {
		String sql = "";
		if (field != null) {
			sql = " ORDER BY " + field.getDbName() + " " + (isAscending ? "ASC" : "DESC") + " ";
		}
		return sql;
	}
	
}
