package model;

/**
 * Enumeration of database fields.
 * Used on GUI to configure tables
 * and in SQL queries to sort and filter.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>23.01.2018: nicz - Creation</li>
 * </ul>
 */
public enum Field {
	
	PLANT_NAME  ("plName", "Nom", 150, 64),
	PLANT_LATIN ("plNameLatin", "Nom latin", 150, 64),
	PLANT_DESC  ("plDescription", "Description", 250, 512),
	PLANT_FAMILY("plFamily", "Famille", 120, 64),
	PLANT_KIND  ("plKind", "Type", 120, 10),
	PLANT_SOIL  ("plSoil", "Sol", 120, 64),
	
	SOIL_NAME ("soName", "Nom", 150, 64),
	SOIL_DESC ("soDescription", "Description", 250, 512),
	SOIL_COLOR("soColor", "Couleur", 100, 64);
	
	private String dbName;
	private String guiName;
	private int width;
	private int max;
	
	private Field(String dbName, String guiName, int width, int max) {
		this.dbName = dbName;
		this.guiName = guiName;
		this.width = width;
		this.max = max;
	}

	public String getDbName() {
		return dbName;
	}

	public String getGuiName() {
		return guiName;
	}

	public int getWidth() {
		return width;
	}

	public int getMax() {
		return max;
	}
	
}
