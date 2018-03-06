package view;

import java.util.Date;
import java.util.Vector;

import model.Field;
import model.Journal;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Group;

import common.view.IncrementalSearchBox;
import common.view.MessageBox;

import controller.Controller;


/**
 * Module to edit journal entries.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>21.01.2018: nicz - Creation</li>
 * <li>06.03.2018: nicz - Creation</li>
 * </ul>
 */
public class ModuleJournal extends AbstractModule<Journal> {
	
	
	private EditorJournal editor;
	
	public ModuleJournal() {
		super(2);
	
		loadWidgets();
		loadData();
	}

	@Override
	protected void loadWidgets() {
		dataTable.initTable(new Field[] {Field.JOURNAL_DATE, Field.JOURNAL_TITLE, Field.JOURNAL_GARDEN});
		
	    editor = new EditorJournal(cRight);
	    
	    searchBox = new IncrementalSearchBox(cButtons) {
	    	public void onSearch() {
	    		showObjects();
	    	}
	    };
		
		Controller.getInstance().addDataListener(this);
		
		
		Group gMisc = widgetsFactory.createGroup(cRight, "Divers");
		
		widgetsFactory.createPushButton(gMisc, "A propos de Loppin", "idea", null, false, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox.info("A propos", Controller.getInstance().getCredits());
			}
		});
		
		widgetsFactory.createPushButton(gMisc, "Recharger les caches", "run", 
				"Recharger les caches peut être utile si les données semblent incohérentes", 
				false, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Controller.getInstance().reloadCaches();
			}
		});
		
		widgetsFactory.createLink(gMisc, "Obtenir des mises à jour sur <a>GitHub</a>", 
				"https://github.com/nicz17/Loppin", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String url = "https://github.com/nicz17/Loppin";
				Program.launch(url);
			}
		});
	}
	
	@Override
	protected void createObject() {
		Journal newObj = new Journal(0, "", "", new Date(), null);
		dataTable.addObject(newObj, true);
	}

	@Override
	protected void loadData() {
		showObjects();
		dataTable.selectFirstObject();
	}

	@Override
	protected void onTableSelection(Journal obj) {
		showObject(obj);
	}

	@Override
	protected void showObjects() {
		Vector<Journal> vecObjects = Controller.getInstance().getJournals(searchBox.getSearchText(), dataTable.getOrdering());
		dataTable.showObjects(vecObjects);
	}

	@Override
	protected void showObject(Journal obj) {
		editor.showObject(obj);
	}

}
