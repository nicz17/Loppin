package model;

/**
 * Enumeration of plant kinds,
 * for example Edible, Ornamental, Green manure, etc.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>17.01.2018: nicz - Creation</li>
 * </ul>
 */
public enum PlantKind {
	
	EDIBLE("Comestible"),
	GREENMANUR("Engrais vert"),
	HERB("Aromatique"),
	ORNAMENTAL("Ornemental"),
	WEED("Mauvaise herbe"),
	OTHER("Autres");
	
	private String sGuiName;
	
	private PlantKind(String sGuiName) {
		this.sGuiName = sGuiName;
	}
	
	/**
	 * Gets the translated plant kind for GUI display.
	 * @return a GUI translation
	 */
	public String getGuiName() {
		return sGuiName;
	}
	
	/**
	 * Gets the default plant kind.
	 * @return the default kind
	 */
	public static PlantKind getDefault() {
		return EDIBLE;
	}
	
	/**
	 * Gets an enum value from the specified name.
	 * Returns null if no such enum value.
	 * @param sDbName a plant kind as found in database
	 * @return an enum value, or null
	 */
	public static PlantKind getFromDbName(String sDbName) {
		PlantKind value = null;
		for (PlantKind aValue : PlantKind.values()) {
			if (aValue.name().equals(sDbName)) {
				value = aValue;
				break;
			}
		}
		return value;
	}
}
