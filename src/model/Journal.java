package model;

import java.util.Date;

import view.Loppin;

/**
 * A journal entry with a title, date, text, 
 * and an optional link to a garden.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>22.02.2018: nicz - Creation</li>
 * </ul>
 */
public class Journal extends DataObject implements Comparable<Journal> {
	
	private int idx;
	private String title;
	private String text;
	private Date date;
	private Garden garden;
	
	
	/**
	 * Constructor.
	 * @param idx     the database index
	 * @param title   the journal entry title
	 * @param text    the journal entry text
	 * @param date    the journal entry timestamp
	 * @param garden  optional reference to a garden
	 */
	public Journal(int idx, String title, String text, Date date, Garden garden) {
		this.idx = idx;
		this.title = title;
		this.text = text;
		this.date = date;
		this.garden = garden;
	}

	@Override
	public int getIdx() {
		return idx;
	}

	@Override
	public String getName() {
		return title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Garden getGarden() {
		return garden;
	}

	public void setGarden(Garden garden) {
		this.garden = garden;
	}

	@Override
	public String getValue(Field field) {
		String value = null;
		
		switch(field) {
		case JOURNAL_TITLE:
			value = getTitle();
			break;
		case JOURNAL_TEXT:
			value = getText() == null ? "" : getText();
			break;
		case JOURNAL_DATE:
			value = Loppin.dateFormat.format(getDate());
			break;
		case JOURNAL_GARDEN:
			value = (getGarden() == null ? "-" : getGarden().getName());
			break;
		default: break;
		}
		
		return value;
	}
	
	@Override
	public int compareTo(Journal obj) {
		return date.compareTo(obj.getDate());
	}
	
	@Override
	public String toString() {
		return "Journal " + title;
	}
	
}
