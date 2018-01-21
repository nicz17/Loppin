package controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import model.Soil;

import common.base.Logger;

/**
 * Cache for Soil objects, to avoid frequent database requests.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.01.2018: nicz - Creation</li>
 * </ul>
 */
public class CacheSoil {
	
	private static final Logger log = new Logger("CacheSoil", true);
	
	/** Map of Soils by database index. */
	private final Map<Integer, Soil> mapById;

	/** the singleton instance */
	private static CacheSoil _instance = null;
	
	
	/**
	 * Gets a soil by its database index.
	 * @param idx  the database index.
	 * @return  the soil, or null if not found.
	 */
	public Soil getSoil(int idx) {
		if (idx > 0) {
			return mapById.get(new Integer(idx));
		} else {
			return null;
		}
	}
	
	/** 
	 * Reloads the cache.
	 * Fetches all soils from database and fills the cache. 
	 */
	public void loadAll() {
		clear();
		Vector<Soil> soils = DataAccess.getInstance().fetchSoils();
		log.info("Loaded " + soils.size() + " soils");
		
		for (Soil soil : soils) {
			addSoil(soil);
		}
	}
	
	public Collection<Soil> getAll() {
		return mapById.values();
	}
	
	
	/**
	 * Refresh the cache for the specified soil.
	 * @param idxSoil  the index of the soil to refresh.
	 */
	public void refresh(int idxSoil) {
		// TODO DB fetch by id
//		if (idxSoil > 0) {
//			Soil soil = DataAccess.getInstance().getSoil(idxSoil);
//			log.info("Refreshing cache for " + soil);
//			addSoil(soil);
//		}
	}

	/**
	 * Gets the size of the cache.
	 * Size is the number of Soils with different IDs.
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
	 * Adds the specified Soil to cache.
	 * 
	 * @param soil  the Soil to add
	 */
	private void addSoil(Soil soil) {
		if (soil != null) {
			mapById.put(new Integer(soil.getIdx()), soil);
		}
	}

	
	/** Gets the singleton instance. */
	public static CacheSoil getInstance() {
		if (_instance == null)
			_instance = new CacheSoil();
		return _instance;
	}

	/** Private singleton constructor */
	private CacheSoil() {
		mapById = new HashMap<>();
	}

}
