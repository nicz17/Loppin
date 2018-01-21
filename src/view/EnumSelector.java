package view;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Simple read-only selector for enumerations.
 * Just a combo filled from enum values.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.01.2018: nicz - Creation</li>
 * </ul>
 */
public abstract class EnumSelector<T> extends AbstractSelector<T> {

	/**
	 * Constructs a new enumeration selector.
	 * 
	 * @param name   the selector name
	 * @param parent the parent composite
	 * @param listener the listener to notify when the selection is modified (may be null)
	 */
	public EnumSelector(String name, Composite parent, ModifyListener listener) {
		super(name, parent, true, listener);
		load();
	}
	
	protected T newValueFromText(String text) {
		return null;
	}
}
