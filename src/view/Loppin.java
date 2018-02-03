package view;

import java.text.DateFormat;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import common.base.Logger;
import common.exceptions.AppException;
import common.view.AbstractMain;
import common.view.BaseModule;
import common.view.IconManager;
import common.view.MessageBox;
import common.view.ModuleFactory;
import controller.Controller;

/**
 * The main GUI entry point.
 * 
 * Creates a shell and listens to GUI events until terminated.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>12.01.2018: nicz - Creation</li>
 * </ul>
 *
 */
public class Loppin extends AbstractMain {
	
	private static final Logger log = new Logger("Loppin", false);
	
	/** The application name. Appears in the main shell. */
	private static final String appName = "Loppin";
	
	/** 
	 * The application version. 
	 * Only increment before exporting a new jar.
	 */
	private static final String appVersion = "1.0.0 Dev";

	public static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);

	/** the singleton instance */
	private static Loppin instance;
	
	private CTabFolder folder;
	
	private ModuleFactory moduleFactory;
	private Controller controller;
	
	
	/**
	 * @return the singleton instance.
	 */
	public static Loppin getInstance() {
		if (instance == null)
			instance = new Loppin();
		return instance;
	}

	/**
	 * Gets the folder displaying the modules.
	 * 
	 * @return the folder displaying the modules.
	 */
	public CTabFolder getFolder() {
		return folder;
	}
	
	/**
	 * Navigates to the specified module and tries to select the specified object.
	 * @param module     the module to navigate to
	 * @param idxObject  the database index of the object to select
	 */
	public void navigate(Module module, int idxObject) {
		if (module == null) {
			log.warn("Skipping navigation to null module");
			return;
		}
		
		int idxModule = module.ordinal();
		folder.setSelection(idxModule);
		
		CTabItem tabItem = folder.getItem(idxModule);
		BaseModule bm = null;
		
		if (tabItem != null) {
			buildModule(tabItem);
			bm = (BaseModule) tabItem.getControl();
		}
		
		if (bm != null) {
			bm.selectObject(idxObject);
		} else {
			log.error("Failed to navigate to " + module.getTitle() + " with selection " + idxObject);
		}
	}


	/**
	 * @param args -f to display in fullscreen
	 */
	public static void main(String[] args) {
		boolean maximized = true;
		//if (args.length > 0 && args[0].equals("-f")) maximized = true;
		
		Loppin loppin = Loppin.getInstance();
		loppin.init();
		loppin.open(maximized);
	}

	@Override
	protected void onInit() {
		controller = Controller.getInstance();
		moduleFactory = ModuleFactory.getInstance();
	}

	@Override
	protected void buildWidgets() {
		shell.setLayout(new GridLayout(2, false));

		folder = new CTabFolder(shell, SWT.BORDER);

		GridData data = new GridData(GridData.FILL_BOTH);
	    data.verticalIndent = 16;
	    folder.setLayoutData(data);
	    folder.setSimple(false);
	    folder.setMaximizeVisible(true);
	    //folder.setMinimizeVisible(true);
	    folder.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event event) {
	            final CTabItem item = (CTabItem) event.item;
	            buildModule(item);
	        }
	    }); 
	    
	    // set module titles and icons in folder
	    for (Module module : Module.values()) {
	    	CTabItem item = new CTabItem(folder, 0);
	    	item.setText(module.getTitle());
	    	String icon = module.getIcon();
	    	if (icon != null)
	    		item.setImage(IconManager.getIcon(icon));
	    }
	    
		folder.setFocus();	// loads the first module
		
		shell.setImage(IconManager.getIcon(Module.PLANTS.getIcon()));
	}

	@Override
	protected void onTerminate() {
		log.info("Au revoir !");
		controller.terminate();
	}

	/**
	 * Private singleton constructor.
	 * Creates a new display and shell.
	 */
	private Loppin() {
		super(appName, appVersion);

		shell.setMinimumSize(1200, 500);
		shell.setLocation(50, 50);
	}
	
	private void buildModule(final CTabItem tabItem) {
        if (tabItem.getControl() == null) {
    		BusyIndicator.showWhile(display, new Runnable() {
    			public void run() {
    				try {
    					Module module = Module.values()[folder.indexOf(tabItem)];
						tabItem.setControl(moduleFactory.createModule(module));
					} catch (AppException e) {
						MessageBox.error(e);						
					}
    			}
    		});
       }
	}

}
