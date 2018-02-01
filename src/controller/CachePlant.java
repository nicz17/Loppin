package controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import model.Plant;

import common.base.Logger;

/**
 * Cache for Plant objects, to avoid frequent database requests.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>01.02.2018: nicz - Creation</li>
 * </ul>
 */
public class CachePlant {
	
	private static final Logger log = new Logger("CachePlant", true);
	
	/** Map of Plants by database index. */
	private final Map<Integer, Plant> mapById;

	/** the singleton instance */
	private static CachePlant _instance = null;
	
	
	/**
	 * Gets a plant by its database index.
	 * @param idx  the database index.
	 * @return  the plant, or null if not found.
	 */
	public Plant getPlant(int idx) {
		if (idx > 0) {
			return mapById.get(new Integer(idx));
		} else {
			return null;
		}
	}
	
	/** 
	 * Reloads the cache.
	 * Fetches all plants from database and fills the cache. 
	 */
	public void loadAll() {
		clear();
		Vector<Plant> plants = DataAccess.getInstance().fetchPlants(null, null);
		log.info("Loaded " + plants.size() + " plants");
		
		for (Plant plant : plants) {
			addPlant(plant);
		}
	}
	
	public Collection<Plant> getAll() {
		return mapById.values();
	}
	
	
	/**
	 * Refresh the cache for the specified plant.
	 * @param idxPlant  the index of the plant to refresh.
	 */
	public void refresh(int idxPlant) {
		if (idxPlant > 0) {
			String where = "idxPlant = " + idxPlant;
			Vector<Plant> plants = DataAccess.getInstance().fetchPlants(where, null);
			if (plants != null && !plants.isEmpty()) {
				Plant plant = plants.firstElement();
				log.info("Refreshing cache for " + plant);
				addPlant(plant);
			}
		}
	}

	/**
	 * Gets the size of the cache.
	 * Size is the number of Plants with different IDs.
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
	 * Adds the specified Plant to cache.
	 * 
	 * @param plant  the Plant to add
	 */
	private void addPlant(Plant plant) {
		if (plant != null) {
			mapById.put(new Integer(plant.getIdx()), plant);
		}
	}

	
	/** Gets the singleton instance. */
	public static CachePlant getInstance() {
		if (_instance == null)
			_instance = new CachePlant();
		return _instance;
	}

	/** Private singleton constructor */
	private CachePlant() {
		mapById = new HashMap<>();
	}

}
