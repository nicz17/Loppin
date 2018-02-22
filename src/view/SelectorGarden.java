package view;

import java.util.Vector;

import model.Garden;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

import controller.Controller;

/**
 * Garden object selector.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>22.02.2018: nicz - Creation</li>
 * </ul>
 */
public class SelectorGarden extends AbstractSelector<Garden> {

	public SelectorGarden(String name, Composite parent, boolean readOnly,
			ModifyListener listener) {
		super(name, parent, readOnly, listener);
	}

	@Override
	protected Vector<Garden> getData() {
		Vector<Garden> data = Controller.getInstance().getGardens(null, null);
		return data;
	}

	@Override
	public String getDisplayValue(Garden obj) {
		String result = "";
		if (obj != null) {
			result = obj.getName();
		}
		return result;
	}

	@Override
	protected Garden newValueFromText(String text) {
		// not supported for now
		return null;
	}

}
