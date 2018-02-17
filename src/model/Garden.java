package model;

/**
 * A garden, represented as a collection of square tiles.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>17.02.2018: nicz - Creation</li>
 * </ul>
 */
public class Garden extends DataObject implements Comparable<Garden> {
	
	private int idx;
	private String name;
	private String description;
	private String photo;
	private int sizeX;
	private int sizeY;
	private int sizeTile;
	
	
	/**
	 * Constructor.
	 * @param idx    the database index
	 * @param name   the garden name (must be unique)
	 * @param description  an optional description
	 * @param photo  an optional photo filename
	 * @param sizeX  the number of tiles in the x direction
	 * @param sizeY  the number of tiles in the y direction
	 * @param sizeTile  the size of a (square) tile in cm
	 */
	public Garden(int idx, String name, String description, String photo,
			int sizeX, int sizeY, int sizeTile) {
		this.idx = idx;
		this.name = name;
		this.description = description;
		this.photo = photo;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeTile = sizeTile;
	}

	@Override
	public int getIdx() {
		return idx;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getSizeTile() {
		return sizeTile;
	}

	public void setSizeTile(int sizeTile) {
		this.sizeTile = sizeTile;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getValue(Field field) {
		String value = null;
		switch(field) {
		case GARDEN_DESC:
			value = (getDescription() == null ? "" : getDescription());
			break;
		case GARDEN_NAME:
			value = getName();
			break;
		case GARDEN_SIZE:
			value = String.format("%d x %d", sizeX, sizeY);
			break;
		case GARDEN_SIZETILE:
			value = String.format("%d cm", sizeTile);
			break;
		default:
			break;
		}
		return value;
	}
	
	@Override
	public String toString() {
		return "Jardin " + getName();
	}

	/**
	 * Compares alphabetically by name.
	 */
	@Override
	public int compareTo(Garden garden) {
		return getName().compareTo(garden.getName());
	}

}
