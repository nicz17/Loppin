package controller;

import model.Soil;

/**
 * Listener for changes to data objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * <li>13.02.2018: nicz - Added Association update</li>
 * <li>06.03.2018: nicz - Added Journal update</li>
 * </ul>
 */
public interface DataListener {

	/**
	 * A {@link Soil} was updated.
	 * @param idx  the soil database index
	 */
	public void soilUpdated(int idx);
	
	/**
	 * A {@link Plant} was updated.
	 * @param idx  the plant database index
	 */
	public void plantUpdated(int idx);
	
	/**
	 * A {@link Garden} was updated.
	 * @param idx  the garden database index
	 */
	public void gardenUpdated(int idx);
	
	/**
	 * An Association was updated.
	 * @param idx  the association database index
	 */
	public void associationUpdated(int idx);
	
	/**
	 * A Journal entry was updated.
	 * @param idx  the journal database index
	 */
	public void journalUpdated(int idx);
}
