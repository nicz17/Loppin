package view;


import view.Loppin;

import common.view.BaseModule;
import controller.DataListener;

/**
 * A SWT {@link BaseModule} that acts as a {@link DataListener}.
 * 
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 *
 */
public abstract class TabbedModule extends BaseModule implements DataListener {
	

	public TabbedModule() {
		super(Loppin.getInstance().getFolder());
	}
	
	@Override
	public void soilUpdated(int idx) {};

	@Override
	public void plantUpdated(int idx) {};

	@Override
	public void gardenUpdated(int idx) {};

}
