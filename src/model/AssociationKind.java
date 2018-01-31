package model;

/**
 * Enumeration of plant association kinds.
 * For now, only GOOD, NEUTRAL and BAD are supported.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>31.01.2018: nicz - Creation</li>
 * </ul>
 */
public enum AssociationKind {

	GOOD   ("Bon",      1.0),
	NEUTRAL("Neutre",   0.0),
	BAD    ("Mauvais", -1.0);
	
	private String sGuiName;
	private double value;
	
	private AssociationKind(String sGuiName, double value) {
		this.sGuiName = sGuiName;
		this.value    = value;
	}
	
	/**
	 * Gets the translated kind for GUI display.
	 * @return a GUI translation
	 */
	public String getGuiName() {
		return sGuiName;
	}
	
	
	/**
	 * Gets this association's value.
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Gets the default kind.
	 * @return the default kind
	 */
	public static AssociationKind getDefault() {
		return NEUTRAL;
	}
	
	/**
	 * Gets an enum value from the specified name.
	 * Returns null if no such enum value.
	 * @param sDbName an association kind as found in database
	 * @return an enum value, or null
	 */
	public static AssociationKind getFromDbName(String sDbName) {
		AssociationKind kind = null;
		for (AssociationKind aKind : AssociationKind.values()) {
			if (aKind.name().equals(sDbName)) {
				kind = aKind;
				break;
			}
		}
		return kind;
	}
}
