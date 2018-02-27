package controller;

import java.util.Vector;

import model.Garden;

import common.base.Logger;

/**
 * Cache for Garden objects, to avoid frequent database requests.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>27.02.2018: nicz - Creation</li>
 * </ul>
 */
public class CacheGarden extends AbstractCache<Garden> {
	
	private static final Logger log = new Logger("CacheGarden", true);
	
	/** the singleton instance */
	private static CacheGarden _instance = null;
	
	
	/**
	 * Gets a garden by its database index.
	 * @param idx  the database index.
	 * @return  the garden, or null if not found.
	 */
	public Garden getGarden(int idx) {
		return getObject(idx);
	}
	
	@Override
	public void loadAll() {
		clear();
		Vector<Garden> gardens = DataAccess.getInstance().fetchGardens(null, null);
		log.info("Loaded " + gardens.size() + " gardens");
		
		for (Garden garden : gardens) {
			addObject(garden);
		}
	}
	
	
	@Override
	public void refresh(int idxGarden) {
		if (idxGarden > 0) {
			String where = "idxGarden = " + idxGarden;
			Vector<Garden> gardens = DataAccess.getInstance().fetchGardens(where, null);
			if (gardens != null && !gardens.isEmpty()) {
				Garden garden = gardens.firstElement();
				log.info("Refreshing cache for " + garden);
				addObject(garden);
			}
		}
	}

	
	/** Gets the singleton instance. */
	public static CacheGarden getInstance() {
		if (_instance == null)
			_instance = new CacheGarden();
		return _instance;
	}

	/** Private singleton constructor */
	private CacheGarden() {
		super();
	}

}
