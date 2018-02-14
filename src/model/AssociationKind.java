package model;

/**
 * Enumeration of plant association kinds.
 * For now, only GOOD, NEUTRAL and BAD are supported.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>31.01.2018: nicz - Creation</li>
 * <li>14.02.2018: nicz - Added color</li>
 * </ul>
 */
public enum AssociationKind {

	GOOD   ("Bon",      1.0, "#00ff00"),
	NEUTRAL("Neutre",   0.0, "#888888"),
	BAD    ("Mauvais", -1.0, "#ff0000");
	
	private String sGuiName;
	private double value;
	private String sColor;
	
	private AssociationKind(String sGuiName, double value, String sColor) {
		this.sGuiName = sGuiName;
		this.value    = value;
		this.sColor   = sColor;
	}
	
	/**
	 * Gets the translated kind for GUI display.
	 * @return a GUI translation
	 */
	public String getGuiName() {
		return sGuiName;
	}
	
	
	/**
	 * Gets this association kind's value.
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Get the color hex code associated to this kind.
	 * @return a hex color code, for example '#ff0000'.
	 */
	public String getColor() {
		return sColor;
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
