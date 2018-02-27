package controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.DataObject;

/**
 * Abstract superclass for DataObject caches.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>27.02.2018: nicz - Creation</li>
 * </ul>
 */
public abstract class AbstractCache<T extends DataObject> {
	
	/** Map of objects by database index. */
	protected final Map<Integer, T> mapById;
	
	/**
	 * Constructor.
	 */
	protected AbstractCache() {
		mapById = new HashMap<>();
	}
	
	
	/**
	 * Gets an object by its database index.
	 * @param idx  the database index.
	 * @return  the object, or null if not found.
	 */
	public T getObject(int idx) {
		if (idx > 0) {
			return mapById.get(new Integer(idx));
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the list of all items in cache.
	 * @return all items
	 */
	public Collection<T> getAll() {
		return mapById.values();
	}

	/**
	 * Gets the size of the cache.
	 * Size is the number of objects with different IDs.
	 * 
	 * @return  cache size
	 */
	public int size() {
		return mapById.size();
	}

	/** 
	 * Clears the cache.
	 */
	public void clear() {
		mapById.clear();
	}
	
	/**
	 * Refreshes the object with the specified index from database.
	 * @param idx  the database index of the object to reload from db
	 */
	public abstract void refresh(int idx);
	
	/**
	 * Reloads the cache.
	 * Fetches all objects from database and fills the cache. 
	 */
	public abstract void loadAll();
	
	/**
	 * Adds the specified object to cache.
	 * 
	 * @param obj  the object to add
	 */
	protected void addObject(T obj) {
		if (obj != null) {
			mapById.put(new Integer(obj.getIdx()), obj);
		}
	}

}
