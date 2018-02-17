package view;

import common.view.IsModule;

/**
 * Enumeration of the application modules,
 * with their class, title and icon.
 * 
 * <p><b>Modifications:</b>
 * <ul>
 * <li>14.01.2018: nicz - Creation</li>
 * </ul>
 *
 */
public enum Module implements IsModule {
	
	GARDENS(ModuleGardens.class.getName(),  "Jardins",   "fence"),
	PLANTS (ModulePlants.class.getName(),   "Plantes",   "leaf"),
    SOILS  (ModuleSoils.class.getName(),    "Sols",      "rocks"),
    JOURNAL(ModuleJournal.class.getName(),  "Journal",   "edit");

	private String moduleClass;
	private String title;
	private String icon;
	
	private Module(String moduleClass, String name, String icon) {
		this.moduleClass = moduleClass;
		this.title = name;
		this.icon  = icon;
	}
	
	@Override
	public String getModuleClass() {
		return moduleClass;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getIcon() {
		return icon;
	}

}
