package controller;

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
public class CacheSoil extends AbstractCache<Soil> {
	
	private static final Logger log = new Logger("CacheSoil", true);
	
	/** the singleton instance */
	private static CacheSoil _instance = null;
	
	
	/**
	 * Gets a soil by its database index.
	 * @param idx  the database index.
	 * @return  the soil, or null if not found.
	 */
	public Soil getSoil(int idx) {
		return getObject(idx);
	}
	
	@Override
	public void loadAll() {
		clear();
		Vector<Soil> soils = DataAccess.getInstance().fetchSoils(null, null);
		log.info("Loaded " + soils.size() + " soils");
		
		for (Soil soil : soils) {
			addObject(soil);
		}
	}
	
	
	@Override
	public void refresh(int idxSoil) {
		if (idxSoil > 0) {
			String where = "idxSoil = " + idxSoil;
			Vector<Soil> soils = DataAccess.getInstance().fetchSoils(where, null);
			if (soils != null && !soils.isEmpty()) {
				Soil soil = soils.firstElement();
				log.info("Refreshing cache for " + soil);
				addObject(soil);
			}
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
		super();
	}

}
