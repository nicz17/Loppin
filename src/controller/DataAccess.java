package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import model.Association;
import model.Field;
import model.Ordering;
import model.Plant;
import model.Soil;
import common.base.Logger;
import common.exceptions.AppException;

/**
 * Main database access class.
 * Handles fetching, updating and deleting objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * <li>20.01.2018: nicz - Added plant methods</li>
 * </ul>
 */
public class DataAccess {
	private static final Logger log = new Logger("DataAccess", true);
	
	private DataObjectFactory objectFactory;
	private DatabaseTools dbTools;
	
	/** the singleton instance */
	private static DataAccess instance;
	
	
	public static DataAccess getInstance() {
		if (instance == null)
			instance = new DataAccess();
		return instance;
	}

	/**
	 * Closes the database connection.
	 */
	public void terminate() {
		dbTools.closeConnection();
	}
	
	/**
	 * Fetches Plant objects from database.
	 * @param where  optional SQL where clause (without where keyword). May be null.
	 * @param ordering  optional sorting object. May be null.
	 * @return  the fetched Plant objects.
	 */
	protected Vector<Plant> fetchPlants(String where, Ordering ordering) {
		Vector<Plant> vecResult = new Vector<>();
		
		String query = "SELECT * FROM Plant ";
		if (where != null && !where.isEmpty()) {
			query += "WHERE " + where;
		}
		if (ordering == null) {
			ordering = new Ordering(Field.PLANT_NAME, true);
		}
		query += ordering.getOrderClause();
		
		try {
			Connection conn = dbTools.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.beforeFirst();
			while (rs.next()) {
				Plant obj = objectFactory.createPlant(rs);
				vecResult.add(obj);
			}
			log.debug("Returning " + vecResult.size() + " plants");
			stmt.close();
		} catch (SQLException e) {
			log.error("Fetching plants failed: " + e.getMessage());
		}

		return vecResult;
	}
	
	/**
	 * Fetches Soil objects from database.
	 * @param where  optional SQL where clause (without where keyword). May be null.
	 * @param ordering  optional sorting object. May be null.
	 * @return  the fetched Soil objects.
	 */
	protected Vector<Soil> fetchSoils(String where, Ordering ordering) {
		Vector<Soil> vecResult = new Vector<>();
		
		String query = "SELECT * FROM Soil ";
		if (where != null && !where.isEmpty()) {
			query += "WHERE " + where;
		}
		if (ordering == null) {
			ordering = new Ordering(Field.SOIL_NAME, true);
		}
		query += ordering.getOrderClause();

		try {
			Connection conn = dbTools.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.beforeFirst();
			while (rs.next()) {
				Soil obj = objectFactory.createSoil(rs);
				vecResult.add(obj);
			}
			log.debug("Returning " + vecResult.size() + " soils");
			stmt.close();
		} catch (SQLException e) {
			log.error("Fetching soils failed: " + e.getMessage());
		}

		return vecResult;
	}
	
	/**
	 * Fetches Association objects from database.
	 * @param where  optional SQL where clause (without where keyword). May be null.
	 * @return  the fetched Association objects.
	 */
	protected Vector<Association> fetchAssociations(String where) {
		Vector<Association> vecResult = new Vector<>();
		
		String query = "SELECT * FROM Association";
		if (where != null && !where.isEmpty()) {
			query += " WHERE " + where;
		}

		try {
			Connection conn = dbTools.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.beforeFirst();
			while (rs.next()) {
				Association obj = objectFactory.createAssociation(rs);
				vecResult.add(obj);
			}
			log.debug("Returning " + vecResult.size() + " associations");
			stmt.close();
		} catch (SQLException e) {
			log.error("Fetching associations failed: " + e.getMessage());
		}

		return vecResult;
	}

	/**
	 * Saves the specified {@link Plant} to database.
	 * 
	 * @param obj  the plant to save
	 * @return the database index of saved object
	 * @throws AppException if saving failed
	 */
	protected int savePlant(Plant obj) throws AppException {
		Connection conn = dbTools.getConnection();
		log.info("Saving " + obj);

		int idx = -1;
		
		try {
			Statement stmt = conn.createStatement();
			
			if (obj.getIdx() > 0) {
				// update existing
				String query = String.format("UPDATE Plant SET plName = %s, " +
						"plNameLatin = %s, plFamily = %s, plDescription = %s, plKind = %s, " +
						"plSowing = %d, plPlanting = %d, plHarvest1 = %d, plHarvest2 = %d, " +
						"plSoil = %d, plPhoto = %s " +
						"WHERE idxPlant = %d", 
						DatabaseTools.toSQLstring(obj.getName()), 
						DatabaseTools.toSQLstring(obj.getNameLatin()), 
						DatabaseTools.toSQLstring(obj.getFamily().name()), 
						DatabaseTools.toSQLstring(obj.getDescription()),
						DatabaseTools.toSQLstring(obj.getKind().name()),
						obj.getDateSowing(), obj.getDatePlanting(), 
						obj.getDateHarvest1(), obj.getDateHarvest2(),
						obj.getSoil().getIdx(),
						DatabaseTools.toSQLstring(obj.getPhoto()),
						obj.getIdx() );
				log.debug("SQL: " + query);
				stmt.execute(query);
				idx = obj.getIdx();
			} else {
				// create new
				String query = String.format("INSERT INTO Plant " +
						"(idxPlant, plName, plNameLatin, plFamily, " +
						"plDescription, plKind, plSowing, plPlanting, " +
						"plHarvest1, plHarvest2, plSoil, plPhoto) " +
						"VALUES (null, %s, %s, %s, " +
						"%s, %s, %d, %d, %d, %d, %d, %s)",
						DatabaseTools.toSQLstring(obj.getName()), 
						DatabaseTools.toSQLstring(obj.getNameLatin()), 
						DatabaseTools.toSQLstring(obj.getFamily().name()), 
						DatabaseTools.toSQLstring(obj.getDescription()),
						DatabaseTools.toSQLstring(obj.getKind().name()),
						obj.getDateSowing(), obj.getDatePlanting(), 
						obj.getDateHarvest1(), obj.getDateHarvest2(),
						obj.getSoil().getIdx(),
						DatabaseTools.toSQLstring(obj.getPhoto()));
				log.debug("SQL: " + query);
				stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				
				// Get inserted index
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next())
					idx = rs.getInt(1);
				rs.close();
			}
			stmt.close();
		} catch (SQLException e) {
			log.error("Saving Plant failed: " + e.getMessage());
			throw new AppException("Echec de sauvegarde de " + obj + "\n" + e.getMessage());
		}
		return idx;
	}

	/**
	 * Saves the specified {@link Soil} to database.
	 * 
	 * @param obj  the soil to save
	 * @return the database index of saved object
	 * @throws AppException if saving failed
	 */
	protected int saveSoil(Soil obj) throws AppException {
		Connection conn = dbTools.getConnection();
		log.info("Saving " + obj);

		int idx = -1;
		
		try {
			Statement stmt = conn.createStatement();
			
			if (obj.getIdx() > 0) {
				// update existing
				String query = String.format("UPDATE Soil SET soName = %s, " +
						"soDescription = %s, soColor = %s " +
						"WHERE idxSoil = %d", 
						DatabaseTools.toSQLstring(obj.getName()), 
						DatabaseTools.toSQLstring(obj.getDescription()),
						DatabaseTools.toSQLstring(obj.getColor()),
						obj.getIdx() );
				log.debug("SQL: " + query);
				stmt.execute(query);
				idx = obj.getIdx();
			} else {
				// create new
				String query = String.format("INSERT INTO Soil " +
						"(idxSoil, soName, soDescription, soColor) " +
						"VALUES (null, %s, %s, %s)", 
						DatabaseTools.toSQLstring(obj.getName()), 
						DatabaseTools.toSQLstring(obj.getDescription()),
						DatabaseTools.toSQLstring(obj.getColor()));
				log.debug("SQL: " + query);
				stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				
				// Get inserted index
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next())
					idx = rs.getInt(1);
				rs.close();
			}
			stmt.close();
		} catch (SQLException e) {
			log.error("Saving Soil failed: " + e.getMessage());
			throw new AppException("Echec de sauvegarde de " + obj + "\n" + e.getMessage());
		}
		return idx;
	}

	/**
	 * Saves the specified {@link Association} to database.
	 * 
	 * @param obj  the association to save
	 * @return the database index of saved object
	 * @throws AppException if saving failed
	 */
	protected int saveAssociation(Association obj) throws AppException {
		Connection conn = dbTools.getConnection();
		log.info("Saving " + obj);
		int idx = -1;
		
		try {
			Statement stmt = conn.createStatement();
			
			if (obj.getIdx() > 0) {
				// update existing
				String query = String.format("UPDATE Association SET " +
						"asDescription = %s, asKind = %s " +
						"asPlant1 = %d, asPlant2 = %d " +
						"WHERE idxAssociation = %d", 
						DatabaseTools.toSQLstring(obj.getDescription()),
						DatabaseTools.toSQLstring(obj.getKind().name()),
						obj.getPlant1().getIdx(), obj.getPlant2().getIdx(), 
						obj.getIdx() );
				log.debug("SQL: " + query);
				stmt.execute(query);
				idx = obj.getIdx();
			} else {
				// create new
				String query = String.format("INSERT INTO Association " +
						"(idxAssociation, asDescription, asKind, asPlant1, asPlant2) " +
						"VALUES (null, %s, %s, %d, %d)", 
						DatabaseTools.toSQLstring(obj.getDescription()),
						DatabaseTools.toSQLstring(obj.getKind().name()),
						obj.getPlant1().getIdx(), obj.getPlant2().getIdx());
				log.debug("SQL: " + query);
				stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				
				// Get inserted index
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next())
					idx = rs.getInt(1);
				rs.close();
			}
			stmt.close();
		} catch (SQLException e) {
			log.error("Saving Association failed: " + e.getMessage());
			throw new AppException("Echec de sauvegarde de " + obj + "\n" + e.getMessage());
		}
		return idx;
	}
	
	/**
	 * Deletes the specified plant from database.
	 * 
	 * @param obj the plant to delete
	 * @throws AppException if failed to delete object
	 */
	protected void deletePlant(Plant obj) throws AppException {
		Connection conn = dbTools.getConnection();
		log.info("Deleting " + obj);
		try {
			Statement stmt = conn.createStatement();
			if (obj.getIdx() > 0) {
				String query = String.format("DELETE FROM Plant WHERE idxPlant = %d", obj.getIdx());
				log.debug("SQL: " + query);
				stmt.execute(query);
			} else {
				log.error("Plant to delete has invalid idx: " + obj);
			}
			stmt.close();
		} catch (SQLException e) {
			log.error("Deleting Plant failed: " + e.getMessage());
			throw new AppException("Echec de suppression de la plante " + obj + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Deletes the specified soil from database.
	 * 
	 * @param obj the soil to delete
	 * @throws AppException if failed to delete object
	 */
	protected void deleteSoil(Soil obj) throws AppException {
		Connection conn = dbTools.getConnection();
		log.info("Deleting " + obj);
		try {
			Statement stmt = conn.createStatement();
			if (obj.getIdx() > 0) {
				String query = String.format("DELETE FROM Soil WHERE idxSoil = %d", obj.getIdx());
				log.debug("SQL: " + query);
				stmt.execute(query);
			} else {
				log.error("Soil to delete has invalid idx: " + obj);
			}
			stmt.close();
		} catch (SQLException e) {
			log.error("Deleting Soil failed: " + e.getMessage());
			throw new AppException("Echec de suppression du sol " + obj + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Deletes Associations from database.
	 * 
	 * @param where  the SQL where-clause (without where keyword)
	 * @throws AppException if failed to delete object
	 */
	protected void deleteAssociations(String where) throws AppException {
		Connection conn = dbTools.getConnection();
		log.info("Deleting associations");
		
		if (where == null || where.isEmpty()) {
			log.error("Invalid where-clause to delete associations, skipping");
			return;
		}
		String query = "DELETE FROM Association WHERE " + where;
		
		try {
			Statement stmt = conn.createStatement();
			log.debug("SQL: " + query);
			stmt.execute(query);
			stmt.close();
		} catch (SQLException e) {
			log.error("Deleting Associations failed: " + e.getMessage());
			throw new AppException("Echec de suppression d'associations\n" + e.getMessage());
		}
	}
	
	
	/**
	 * Private singleton constructor.
	 */
	private DataAccess() {
		objectFactory = DataObjectFactory.getInstance();
		dbTools = new DatabaseTools();
	}
	

}
