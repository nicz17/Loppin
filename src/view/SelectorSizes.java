package view;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

import common.view.WidgetsFactory;

/**
 * Widgets to select a 2-dimensional size.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.02.2018: nicz - Creation</li>
 * </ul>
 */
public class SelectorSizes extends Composite {
	
	//private final int iMin;
	//private final int iMax;
	
	private final Spinner spiSizeX;
	private final Spinner spiSizeY;

	/**
	 * Constructor.
	 * @param parent the parent composite
	 * @param iMin   the minimum size
	 * @param iMax   the maximum size
	 * @param sUnit  the unit of the sizes
	 * @param modifListener  the listener that will be notified of value changes
	 */
	public SelectorSizes(Composite parent, int iMin, int iMax, String sUnit, ModifyListener modifListener) {
		super(parent, 0);
		//this.iMin = iMin;
		//this.iMax = iMax;
		
		setLayout(new GridLayout(4, false));
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		spiSizeX = WidgetsFactory.getInstance().createSpinner(this, iMin, iMax, 1, modifListener);
		WidgetsFactory.getInstance().createLabel(this, "x");
		spiSizeY = WidgetsFactory.getInstance().createSpinner(this, iMin, iMax, 1, modifListener);
		WidgetsFactory.getInstance().createLabel(this, sUnit == null ? "" : sUnit);
	}
	
	public int getSizeX() {
		return spiSizeX.getSelection();
	}
	
	public int getSizeY() {
		return spiSizeY.getSelection();
	}
	
	public void setValues(int x, int y) {
		spiSizeX.setSelection(x);
		spiSizeY.setSelection(y);
	}
	
}
