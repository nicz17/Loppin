package view;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import common.text.WeekNumberFormat;
import common.view.WidgetsFactory;

/**
 * Widget allowing to select a week number and display it as human-readable text.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.01.2018: nicz - Creation</li>
 * </ul>
 */
public class SelectorWeekNumber extends Composite {
	
	private int week;
	private final Spinner spiValue;
	private final Label lblValue;
	private final WeekNumberFormat formatter;

	public SelectorWeekNumber(Composite parent) {
		super(parent, 0);
		
		this.formatter = new WeekNumberFormat();
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		spiValue = WidgetsFactory.getInstance().createSpinner(this, 1, 53, 1, new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				week = spiValue.getSelection();
				updateValue();
			}
		});
		
		lblValue = WidgetsFactory.getInstance().createLabel(this);
		setValue(26);
	}

	public int getValue() {
		return week;
	}

	public void setValue(int week) {
		this.week = week;
		this.spiValue.setSelection(week);
		updateValue();
	}
	
	private void updateValue() {
		lblValue.setText(formatter.format(week));
	}
	
}
