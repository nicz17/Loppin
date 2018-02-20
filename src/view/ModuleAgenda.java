package view;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import common.view.MessageBox;
import controller.Controller;


/**
 * Module to view upcoming tasks.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.02.2018: nicz - Creation (empty)</li>
 * </ul>
 */
public class ModuleAgenda extends TabbedModule {
	
	public ModuleAgenda() {
		super();
	
		loadWidgets();
		loadData();
	}

	@Override
	protected void loadWidgets() {
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData());
		
		widgetsFactory.createLabel(this, "Ce module n'est pas encore implémenté !");
		widgetsFactory.createPushButton(this, "A propos de Loppin", "idea", null, false, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox.info("A propos", Controller.getInstance().getCredits());
			}
		});
	}

	@Override
	protected void loadData() {

	}

}
