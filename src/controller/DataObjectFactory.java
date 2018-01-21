package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Plant;
import model.PlantKind;
import model.Family;
import model.Soil;

/**
 * Factory for {@link DataObject}s, from SQL result sets.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 */
public class DataObjectFactory {
	
	/**
	 * Singleton instance.
	 */
	private static DataObjectFactory instance;
	
	
	/** Get the singleton instance */
	public static DataObjectFactory getInstance() {
		if (instance == null)
			instance = new DataObjectFactory();
		return instance;
	}
	
	/**
	 * Creates a Plant object from the specified ResultSet.
	 * @param rs  a ResultSet containing Plant info.
	 * @return  the created plant.
	 * @throws SQLException
	 */
	public Plant createPlant(ResultSet rs) throws SQLException {
		Plant obj = new Plant(
				rs.getInt("idxPlant"), 
				rs.getString("plName"), 
				rs.getString("plNameLatin"), 
				PlantKind.getFromDbName(rs.getString("plKind")),
				Family.getFromDbName(rs.getString("plFamily")),
				CacheSoil.getInstance().getSoil(rs.getInt("plSoil")));
		obj.setDescription(rs.getString("plDescription"));
		obj.setPhoto(rs.getString("plPhoto"));
		obj.setDateSowing(rs.getInt("plSowing"));
		obj.setDatePlanting(rs.getInt("plPlanting"));
		obj.setDateHarvest1(rs.getInt("plHarvest1"));
		obj.setDateHarvest2(rs.getInt("plHarvest2"));
		return obj;
	}
	
	/**
	 * Creates a Soil object from the specified ResultSet.
	 * @param rs  a ResultSet containing Soil info.
	 * @return  the created soil.
	 * @throws SQLException
	 */
	public Soil createSoil(ResultSet rs) throws SQLException {
		Soil obj = new Soil(
				rs.getInt("idxSoil"), 
				rs.getString("soName"), 
				rs.getString("soDescription"), 
				rs.getString("soColor"));
		return obj;
	}
	

	/** 
	 * Private singleton constructor
	 */
	private DataObjectFactory() {
	};

}
