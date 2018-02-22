package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Association;
import model.AssociationKind;
import model.Garden;
import model.Journal;
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
 * <li>01.02.2018: nicz - Added Association</li>
 * <li>17.02.2018: nicz - Added Garden</li>
 * <li>22.02.2018: nicz - Added Journal</li>
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
	 * Creates a Garden object from the specified ResultSet.
	 * @param rs  a ResultSet containing Garden info.
	 * @return  the created garden.
	 * @throws SQLException
	 */
	public Garden createGarden(ResultSet rs) throws SQLException {
		Garden obj = new Garden(
				rs.getInt("idxGarden"), 
				rs.getString("gaName"), 
				rs.getString("gaDescription"), 
				rs.getString("gaPhoto"),
				rs.getInt("gaSizeX"),
				rs.getInt("gaSizeY"),
				rs.getInt("gaSizeTile"));
		return obj;
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
	 * Creates an Association object from the specified ResultSet.
	 * @param rs  a ResultSet containing Association info.
	 * @return  the created association.
	 * @throws SQLException
	 */
	public Association createAssociation(ResultSet rs) throws SQLException {
		Association obj = new Association(
				rs.getInt("idxAssociation"),
				CachePlant.getInstance().getPlant(rs.getInt("asPlant1")),
				CachePlant.getInstance().getPlant(rs.getInt("asPlant2")),
				AssociationKind.getFromDbName(rs.getString("asKind")),
				rs.getString("asDescription"));
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
	 * Creates a Journal object from the specified ResultSet.
	 * @param rs  a ResultSet containing Journal info.
	 * @return  the created soil.
	 * @throws SQLException
	 */
	public Journal createJournal(ResultSet rs) throws SQLException {
		Journal obj = new Journal(
				rs.getInt("idxJournal"), 
				rs.getString("joTitle"), 
				rs.getString("joText"), 
				rs.getTimestamp("joDate"),
				null);
		return obj;
	}
	

	/** 
	 * Private singleton constructor
	 */
	private DataObjectFactory() {
	};

}
