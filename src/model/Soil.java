package model;

/**
 * A kind of soil, for example: 
 * terreau, compost, chemin, étang...
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 */
public class Soil extends DataObject implements Comparable<Soil> {
	
	private int idx;
	private String name;
	private String description;
	private String color;

	/**
	 * Constructor.
	 * 
	 * @param idx   the database index, or 0 for a new unsaved object
	 * @param name  the soil name 8may not be null)
	 * @param description the soil description (may be null)
	 * @param color the soil color (html hex code)
	 */
	public Soil(int idx, String name, String description, String color) {
		this.idx = idx;
		this.name = name;
		this.description = description;
		this.color = color;
	}

	@Override
	public int getIdx() {
		return idx;
	}
	
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String[] getDataRow() {
		return new String[] {
				getName(), getDescription(), getColor()
		};
	}
	
	@Override
	public String toString() {
		return "Soil " + getName();
	}

	/**
	 * Compares alphabetically by name.
	 */
	@Override
	public int compareTo(Soil soil) {
		return getName().compareTo(soil.getName());
	}
}
