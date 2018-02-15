package model;

/**
 * An Association between two plants.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>31.01.2018: nicz - Creation</li>
 * <li>15.02.2018: nicz - Added tooltip method</li>
 * </ul>
 */
public class Association extends DataObject {
	
	private int idx;
	private Plant plant1;
	private Plant plant2;
	private AssociationKind kind;
	private String description;
	
	/**
	 * Creates a new neutral association between the two specified plants.
	 * @param plant1  the first plant
	 * @param plant2  the second plant
	 * @return  the created association
	 */
	public static Association create(Plant plant1, Plant plant2) {
		Plant p1 = plant1;
		Plant p2 = plant2;
		if (plant2.getIdx() < plant1.getIdx()) {
			p1 = plant2;
			p2 = plant1;
		}
		Association result = new Association(0, p1, p2, AssociationKind.NEUTRAL, null);
		return result;
	}
	
	/**
	 * Constructor.
	 * @param idx     the database index. 0 for new objects.
	 * @param plant1  the first plant
	 * @param plant2  the second plant
	 * @param kind    the association kind (good, bad, neutral)
	 * @param description  optional description of this association
	 */
	public Association(int idx, Plant plant1, Plant plant2,
			AssociationKind kind, String description) {
		super();
		this.idx = idx;
		this.plant1 = plant1;
		this.plant2 = plant2;
		this.kind = kind;
		this.description = description;
	}

	@Override
	public int getIdx() {
		return idx;
	}

	@Override
	public String getName() {
		return plant1.getName() + " - " + plant2.getName();
	}

	public Plant getPlant1() {
		return plant1;
	}

	public void setPlant1(Plant plant1) {
		this.plant1 = plant1;
	}

	public Plant getPlant2() {
		return plant2;
	}

	public void setPlant2(Plant plant2) {
		this.plant2 = plant2;
	}
	
	public Plant getOtherPlant(Plant plant) {
		if (plant == null) {
			return plant1;
		}
		if (plant1.getIdx() == plant.getIdx()) {
			return plant2;
		} else {
			return plant1;
		}
	}

	public AssociationKind getKind() {
		return kind;
	}

	public void setKind(AssociationKind kind) {
		this.kind = kind;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the tooltip text for this association.
	 * @return the tooltip text (never null)
	 */
	public String getTooltip() {
		String tooltip = "";
		switch (getKind()) {
		case GOOD:
			tooltip += "Bonne association";
			break;
		case BAD:
			tooltip += "Mauvaise association";
			break;
		case NEUTRAL:
			tooltip += "Association neutre";
			break;
		}
		tooltip += "\n";
		if (getDescription() != null) {
			tooltip += getDescription();
		}
		return tooltip;
	}

	@Override
	public String getValue(Field field) {
		String value = null;
		switch (field) {
		case ASSOC_NAME:
			value = getName();
			break;
		case ASSOC_DESC:
			value = (getDescription() == null ? "" : getDescription());
			break;
		case ASSOC_KIND:
			value = getKind().getGuiName();
			break;
		case ASSOC_PLANT1:
			value = getPlant1().getName();
			break;
		case ASSOC_PLANT2:
			value = getPlant2().getName();
			break;
		default:
			break;
		}
		return value;
	}
	
	@Override
	public String toString() {
		return "Association " + plant1.getName() + "-" + plant2.getName() + " : " + kind.getGuiName();
	}

}
