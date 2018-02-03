package model;

/**
 * Enumeration of botanical plant families,
 * for example Asteraceae, Apiaceae, Brassicaceae, etc.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>17.01.2018: nicz - Creation</li>
 * </ul>
 */
public enum Family {
	
	AMARANTHACEAE,
	AMARYLLIDACEAE,
	APIACEAE,
	ASTERACEAE,
	BORAGINACEAE,
	BRASSICACEAE,
	CAPRIFOLIACEAE,
	CUCURBITACEAE,
	FABACEAE,
	LAMIACEAE,
	PLANTAGINACEAE,
	POACEAE,
	ROSACEAE,
	SOLANACEAE,
	OTHER;
	
	/**
	 * Gets the translated family name for GUI display.
	 * @return a GUI translation
	 */
	public String getGuiName() {
		String sGuiName = name().substring(0, 1) + name().substring(1).toLowerCase();
		sGuiName = sGuiName.replace("eae", "ées");
		if (OTHER.equals(this)) {
			sGuiName = "Autres";
		}
		return sGuiName;
	}
	
	/**
	 * Gets the default plant family.
	 * @return the default family.
	 */
	public static Family getDefault() {
		return ASTERACEAE;
	}
	
	/**
	 * Gets an enum value from the specified name.
	 * Returns null if no such enum value.
	 * @param sDbName a family name as found in database
	 * @return a family enum value, or null
	 */
	public static Family getFromDbName(String sDbName) {
		Family family = null;
		for (Family aFamily : Family.values()) {
			if (aFamily.name().equals(sDbName)) {
				family = aFamily;
				break;
			}
		}
		return family;
	}
}
