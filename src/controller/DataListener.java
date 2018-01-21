package controller;

import model.Soil;

/**
 * Listener for changes to data objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
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
}
