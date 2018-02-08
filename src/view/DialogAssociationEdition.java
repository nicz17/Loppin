package view;

import model.Plant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import common.view.MessageBox;
import common.view.WidgetsFactory;

/**
 * A dialog window used to define the association of a selected plant with other plants.
 * 
 * <p><b>Modifications:</b>
 * <ul>
 * <li>08.02.2018: nicz - Creation</li>
 * </ul>
 *
 */
public class DialogAssociationEdition {
	
	private static final WidgetsFactory widgetsFactory =
			WidgetsFactory.getInstance();

	private final Plant plant;
	
	private Shell  parent;
	private Table  tblPlants;
	private Button btnSave;
	
	/**
	 * Constructor.
	 * @param parent  the parent shell
	 * @param plant   the plant for which to define associations
	 */
	public DialogAssociationEdition(Shell parent, Plant plant) {
		this.parent = parent;
		this.plant = plant;
	}
	
	public void open() {
		Display display = parent.getDisplay();
		final Shell shell =
			new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setText("Définir les associations de " + plant.getName());
		shell.setLayout(new GridLayout(2, false));
		
		GridData data;

		tblPlants = new Table(shell, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		tblPlants.setLinesVisible(true);
		tblPlants.setHeaderVisible(true);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 400;
		data.heightHint = 600;
		data.verticalIndent = 10;
		//data.horizontalIndent = 8;
		tblPlants.setLayoutData(data);
		
		widgetsFactory.createLabel(shell, "Pas encore implémenté");
				
		Composite cButtons = widgetsFactory.createComposite(shell, 2, true, 12);
		
		btnSave = widgetsFactory.createOkButton(cButtons, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				save();
				shell.dispose();
			}
		});
		
		widgetsFactory.createCancelButton(cButtons, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});

		enableButtons();
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
	}
	
	private void save() {
		MessageBox.info("Pas encore implémenté !");
	}
	
	private void enableButtons() {
		btnSave.setEnabled(false);
	}

}
