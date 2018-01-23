package model;

/**
 * Enumeration of database fields.
 * Used on GUi to configure tables
 * and in SQL queries to sort and filter.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>23.01.2018: nicz - Creation</li>
 * </ul>
 */
public enum Field {
	
	PLANT_NAME  ("plName", "Nom", 100, 64),
	PLANT_LATIN ("plNameLatin", "Nom latin", 100, 64),
	PLANT_DESC  ("plDescription", "Description", 200, 512),
	PLANT_FAMILY("plFamily", "Famille", 64, 100),
	PLANT_KIND  ("plKind", "Type", 10, 100),
	PLANT_SOIL  ("plSoil", "Sol", 64, 100),
	
	SOIL_NAME ("soName", "Nom", 100, 64),
	SOIL_DESC ("soDescription", "Description", 200, 512),
	SOIL_COLOR("soColor", "Couleur", 50, 64);
	
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
