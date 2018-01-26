package view;

import java.util.Vector;

import model.Soil;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

import controller.Controller;

/**
 * Soil object selector.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.01.2018: nicz - Creation</li>
 * </ul>
 */
public class SelectorSoil extends AbstractSelector<Soil> {

	public SelectorSoil(String name, Composite parent, boolean readOnly,
			ModifyListener listener) {
		super(name, parent, readOnly, listener);
	}

	@Override
	protected Vector<Soil> getData() {
		Vector<Soil> data = Controller.getInstance().getSoils(null, null);
		return data;
	}

	@Override
	public String getDisplayValue(Soil obj) {
		String result = "";
		if (obj != null) {
			result = obj.getName();
		}
		return result;
	}

	@Override
	protected Soil newValueFromText(String text) {
		// not supported for now
		return null;
	}

}
