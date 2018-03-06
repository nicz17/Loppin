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
	
	ASSOC_DESC    ("asDescription", "Description", 250, 512),
	ASSOC_NAME    (null, "Plantes", 150, 64),
	ASSOC_KIND    ("asKind", "Type", 50, 10),
	ASSOC_PLANT1  ("asPlant1", "Plante 1", 150, 64),
	ASSOC_PLANT2  ("asPlant2", "Plante 2", 150, 64),
	
	GARDEN_NAME    ("gaName", "Nom", 150, 64),
	GARDEN_DESC    ("gaDescription", "Description", 300, 512),
	GARDEN_SIZE    ("gaSize", "Dimensions", 100, 100),
	GARDEN_SIZETILE("gaSizeTile", "Taille tuiles", 100, 100),
	
	JOURNAL_TITLE  ("joTitle", "Titre", 300, 64),
	JOURNAL_TEXT   ("joText", "Texte", 250, 512),
	JOURNAL_DATE   ("joDate", "Date", 150, 64),
	JOURNAL_GARDEN ("joGarden", "Jardin", 150, 64),
	
	PLANT_NAME    ("plName", "Nom", 150, 64),
	PLANT_LATIN   ("plNameLatin", "Nom latin", 150, 64),
	PLANT_DESC    ("plDescription", "Description", 250, 512),
	PLANT_FAMILY  ("plFamily", "Famille", 120, 64),
	PLANT_KIND    ("plKind", "Type", 120, 10),
	PLANT_SOIL    ("plSoil", "Sol", 120, -1),
	PLANT_SOWING  ("plSowing", "Semis", 100, 52),
	PLANT_PLANTING("plPlanting", "Plantation", 100, 52),
	PLANT_HARVEST1("plHarvest1", "Récolte", 100, 52),
	PLANT_HARVEST2("plHarvest2", "Récolte 2", 100, 52),
	
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

	/**
	 * Gets the database name of this field. For example 'plName'.
	 * @return field database name
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * Gets the user-interface label for this field.
	 * For example 'Nom'.
	 * @return user-interface name
	 */
	public String getGuiName() {
		return guiName;
	}

	/**
	 * Gets the preferred width, in pixels, for displaying
	 * this field in a table column.
	 * @return column width in pixels
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the database maximum field size, if applicable.
	 * For example, 64 chars for names.
	 * @return  database max field size
	 */
	public int getMax() {
		return max;
	}
	
}
