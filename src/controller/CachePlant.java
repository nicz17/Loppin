package controller;

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
public class CachePlant extends AbstractCache<Plant> {
	
	private static final Logger log = new Logger("CachePlant", true);
	
	/** the singleton instance */
	private static CachePlant _instance = null;
	
	
	/**
	 * Gets a plant by its database index.
	 * @param idx  the database index.
	 * @return  the plant, or null if not found.
	 */
	public Plant getPlant(int idx) {
		return getObject(idx);
	}
	
	@Override
	public void loadAll() {
		clear();
		Vector<Plant> plants = DataAccess.getInstance().fetchPlants(null, null);
		log.info("Loaded " + plants.size() + " plants");
		
		for (Plant plant : plants) {
			addObject(plant);
		}
	}
	
	@Override
	public void refresh(int idxPlant) {
		if (idxPlant > 0) {
			String where = "idxPlant = " + idxPlant;
			Vector<Plant> plants = DataAccess.getInstance().fetchPlants(where, null);
			if (plants != null && !plants.isEmpty()) {
				Plant plant = plants.firstElement();
				log.info("Refreshing cache for " + plant);
				addObject(plant);
			}
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
		super();
	}

}
