package model;

/**
 * An Association between two plants.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>31.01.2018: nicz - Creation</li>
 * </ul>
 */
public class Association extends DataObject {
	
	private int idx;
	private Plant plant1;
	private Plant plant2;
	private AssociationKind kind;
	private String description;
	
	
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
		return plant1.getName() + "-" + plant2.getName() + " : " + kind.getGuiName();
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

	@Override
	public String getValue(Field field) {
		return null;
	}
	
	@Override
	public String toString() {
		return "Association " + plant1.getName() + "-" + plant2.getName() + " : " + kind.getGuiName();
	}

}
