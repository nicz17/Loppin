package model;

/**
 * A plant, cultivated in a garden.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>17.01.2018: nicz - Creation</li>
 * </ul>
 */
public class Plant extends DataObject implements Comparable<Plant> {

	private int idx;
	private String name;
	private String nameLatin;
	private String description;
	private PlantKind kind;
	private Family family;
	private Soil soil;
	private String photo;
	private int dateSowing;
	private int datePlanting;
	private int dateHarvest1;
	private int dateHarvest2;
	
	
	/**
	 * Constructor.
	 * @param idx        the database index
	 * @param name       the GUI name
	 * @param nameLatin  the latin name
	 * @param kind       the plant kind
	 * @param family     the plant family
	 * @param soil       the preferred soil to grow
	 */
	public Plant(int idx, String name, String nameLatin, PlantKind kind,
			Family family, Soil soil) {
		this.idx = idx;
		this.name = name;
		this.nameLatin = nameLatin;
		this.kind = kind;
		this.family = family;
		this.soil = soil;
		this.dateSowing   = 10;
		this.datePlanting = 16;
		this.dateHarvest1 = 23;
		this.dateHarvest2 = 30;
	}

	@Override
	public int getIdx() {
		return idx;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNameLatin() {
		return nameLatin;
	}

	public void setNameLatin(String nameLatin) {
		this.nameLatin = nameLatin;
	}

	public PlantKind getKind() {
		return kind;
	}

	public void setKind(PlantKind kind) {
		this.kind = kind;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public Soil getSoil() {
		return soil;
	}

	public void setSoil(Soil soil) {
		this.soil = soil;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getDateSowing() {
		return dateSowing;
	}

	public void setDateSowing(int dateSowing) {
		this.dateSowing = dateSowing;
	}

	public int getDatePlanting() {
		return datePlanting;
	}

	public void setDatePlanting(int datePlanting) {
		this.datePlanting = datePlanting;
	}

	public int getDateHarvest1() {
		return dateHarvest1;
	}

	public void setDateHarvest1(int dateHarvest1) {
		this.dateHarvest1 = dateHarvest1;
	}

	public int getDateHarvest2() {
		return dateHarvest2;
	}

	public void setDateHarvest2(int dateHarvest2) {
		this.dateHarvest2 = dateHarvest2;
	}

	@Override
	public String getValue(Field field) {
		String value = null;
		switch(field) {
		case PLANT_DESC:
			value = (getDescription() == null ? "" : getDescription());
			break;
		case PLANT_NAME:
			value = getName();
			break;
		case PLANT_FAMILY:
			value = getFamily().getGuiName();
			break;
		case PLANT_KIND:
			value = getKind().getGuiName();
			break;
		case PLANT_LATIN:
			value = getNameLatin();
			break;
		case PLANT_SOIL:
			if (soil != null) {
				value = soil.getName();
			} else {
				value = "";
			}
			break;
		default:
			break;
		}
		return value;
	}
	
	@Override
	public String toString() {
		return "Plante " + getName() + " (" + getFamily().getGuiName() + ")";
	}

	/**
	 * Compares alphabetically by name.
	 */
	@Override
	public int compareTo(Plant plant) {
		return getName().compareTo(plant.getName());
	}

}
