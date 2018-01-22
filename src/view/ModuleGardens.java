package view;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;


/**
 * Module to edit garden objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>21.01.2018: nicz - Creation</li>
 * </ul>
 */
public class ModuleGardens extends TabbedModule {
	
	public ModuleGardens() {
		super();
	
		loadWidgets();
		loadData();
	}

	@Override
	protected void loadWidgets() {
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData());
		
		widgetsFactory.createLabel(this, "Ce module n'est pas encore implémenté !");
	}

	@Override
	protected void loadData() {

	}

}
