package view;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import common.view.MessageBox;


/**
 * Module to edit journal entries.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>21.01.2018: nicz - Creation</li>
 * </ul>
 */
public class ModuleJournal extends TabbedModule {
	
	public ModuleJournal() {
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
				String sAbout = "A propos de Loppin\n\n" +
						"Loppin est un Outil de Planification de Potager INformatique.\n\n" +
						"Version " + Loppin.getInstance().getAppVersion() + "\n\n" +
						"Conception : Gilles Descloux\n" +
						"Réalisation : Nicolas Zwahlen\n\n" +
						"https://github.com/nicz17/Loppin\n\n" +
						"Copyright (c) 2018 G. Descloux, N. Zwahlen";
				MessageBox.info("A propos", sAbout);
			}
		});
	}

	@Override
	protected void loadData() {

	}

}
