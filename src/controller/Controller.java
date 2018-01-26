package controller;

import java.util.Vector;

import view.Loppin;

import model.DataObject;
import model.Field;
import model.Ordering;
import model.Plant;
import model.Soil;

import common.base.Logger;
import common.exceptions.ValidationException;
import controller.DatabaseTools.UpdateType;
import controller.validation.ValidatorPlant;
import controller.validation.ValidatorSoil;

/**
 * Main Controller singleton, acting as hub between the different parts
 * of the program (Database, GUI, processing).
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 */
public class Controller {
	private static final Logger log = new Logger("Controller", true);
	
	private static Controller singleton;
	
	private final Vector<DataListener> vecDataListeners;
	private final ValidatorPlant validatorPlant;
	private final ValidatorSoil  validatorSoil;

	/**
	 * Gets the singleton instance.
	 * 
	 * @return the singleton instance.
	 */
	public static Controller getInstance() {
		if (singleton == null) 
			singleton = new Controller();
		return singleton;
	}
	
	/**
	 * Gets the list of Plant objects saved in database.
	 * @param filter  a text filter (may be null)
	 * @param ordering  optional sorting object. May be null.
	 * @return the list of Plant objects
	 */
	public Vector<Plant> getPlants(String filter, Ordering ordering) {
		String where = null;
		if (filter != null && !filter.isEmpty()) {
			where = Field.PLANT_NAME.getDbName() + " like '%" + filter + "%' ";
		}
		Vector<Plant> vecPlants = DataAccess.getInstance().fetchPlants(where, ordering);
		return vecPlants;
	}
	
	/**
	 * Gets the list of Soil objects saved in database.
	 * @param filter  a text filter (may be null)
	 * @param ordering  optional sorting object. May be null.
	 * @return the list of Soil objects
	 */
	public Vector<Soil> getSoils(String filter, Ordering ordering) {
		String where = null;
		if (filter != null && !filter.isEmpty()) {
			where = Field.SOIL_NAME.getDbName() + " like '%" + filter + "%' ";
		}
		Vector<Soil> vecSoils = DataAccess.getInstance().fetchSoils(where, ordering);
		return vecSoils;
	}

	/**
	 * Saves the specified plant to database.
	 * 
	 * @param plant  the plant to save
	 * @return  the database index
	 * @throws ValidationException  if saving is invalid
	 */
	public int savePlant(Plant plant) throws Exception {
		log.info("Saving " + plant);
		validatorPlant.validateSave(plant);
		int idx = DataAccess.getInstance().savePlant(plant);
		notifyDataListeners(UpdateType.PLANT, idx);
		return idx;
	}

	/**
	 * Saves the specified soil to database.
	 * 
	 * @param soil  the soil to save
	 * @return  the database index
	 * @throws ValidationException  if saving is invalid
	 */
	public int saveSoil(Soil soil) throws Exception {
		log.info("Saving " + soil);
		validatorSoil.validateSave(soil);
		int idx = DataAccess.getInstance().saveSoil(soil);
		notifyDataListeners(UpdateType.SOIL, idx);
		return idx;
	}
	
	/**
	 * Deletes the specified Plant from database.
	 * @param plant  the plant to delete
	 * @throws Exception  if delete is invalid or fails
	 */
	public void deletePlant(Plant plant) throws Exception {
		log.info("Request to delete " + plant);
		validatorPlant.validateDelete(plant);
		DataAccess.getInstance().deletePlant(plant);
		notifyDataListeners(UpdateType.PLANT, -1);
	}
	
	/**
	 * Deletes the specified Soil from database.
	 * @param soil  the soil to delete
	 * @throws Exception  if delete is invalid or fails
	 */
	public void deleteSoil(Soil soil) throws Exception {
		log.info("Request to delete " + soil);
		validatorSoil.validateDelete(soil);
		DataAccess.getInstance().deleteSoil(soil);
		notifyDataListeners(UpdateType.SOIL, -1);
	}
	
	/**
	 * Adds the specified listener to the list of listeners
	 * that will be notified of updates in data.
	 * @param listener  the listener to add (must no be null).
	 */
	public void addDataListener(DataListener listener) {
		vecDataListeners.add(listener);
		log.info("Registered data listeners: " + vecDataListeners.size());
	}
	
	/**
	 * Get the list of objects referencing the specified object in database.
	 * Used to check if the object can be deleted.
	 * @param obj  the object to get dependencies for
	 * @return  the list of objects referencing the object
	 */
	public Vector<DataObject> getDependencies(DataObject obj) {
		Vector<DataObject> vecDeps = new Vector<>();
		
		if (obj == null) {
			return vecDeps;
		} else if (obj instanceof Plant) {
			// No dependencies yet
			// TODO Add dependencies to Culture
		} else if (obj instanceof Soil) {
			// Add dependencies to Plant
			String where = "plSoil = " + obj.getIdx();
			vecDeps.addAll(DataAccess.getInstance().fetchPlants(where, null));
		} else {
			log.error("Unhandled DataObject " + obj);
		}
		
		return vecDeps;
	}
	
	/**
	 * Notifies all registered data listeners of update in data
	 * of the specified kind.
	 * @param updateType  the kind of data that was updated
	 * @param idx         the database index of the updated object
	 */
	public void notifyDataListeners(final DatabaseTools.UpdateType updateType, final int idx) {
		if (vecDataListeners == null) return;

		log.info("Notifying " + vecDataListeners.size() + 
				" data listeners of update in " + updateType);
		for (DataListener li : vecDataListeners) {
			switch(updateType) {
			case PLANT:
				li.plantUpdated(idx);
				break;
			case GARDEN:
				li.gardenUpdated(idx);
				break;
			case SOIL:
				li.soilUpdated(idx);
				break;
			default:
			}
		}
	}
	
	/**
	 * Reloads all caches.
	 */
	public void reloadCaches() {
		CacheSoil.getInstance().loadAll();
	}
	
	/**
	 * Gets the credits displayed in the About dialog.
	 * @return the program credits
	 */
	public String getCredits() {
		String sCredits = "A propos de Loppin\n\n" +
			"Loppin est un Outil de Planification de Potager INformatique.\n\n" +
			"Version " + Loppin.getInstance().getAppVersion() + "\n\n" +
			"Conception : Gilles Descloux\n" +
			"Réalisation : Nicolas Zwahlen\n\n" +
			"https://github.com/nicz17/Loppin\n\n" +
			"Copyright (c) 2018 G. Descloux, N. Zwahlen";
		return sCredits;
	}
	
	/**
	 * Clears and closes everything.
	 */
	public void terminate() {
		DataAccess.getInstance().terminate();
		log.info("Au revoir !");
	}
	
	/**
	 * Private singleton constructor.
	 */
	private Controller() {
		Logger.setGlobalDebug(true);
		vecDataListeners = new Vector<DataListener>();
		validatorSoil = new ValidatorSoil();
		validatorPlant = new ValidatorPlant();
		
		reloadCaches();
	}

}
