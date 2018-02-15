package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import common.view.IconManager;
import common.view.WidgetsFactory;

/**
 * Widget allowing to select a color and display its RGB values.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>22.01.2018: nicz - Creation</li>
 * </ul>
 */
public class SelectorColor extends Composite {
	
	private String sRGB;
	private Color color;
	private final Button btnColor;
	private final Label lblValue;
	private final String sTitle;
	private final RGB rgbDefault;
	private final ModifyListener modifListener;

	public SelectorColor(Composite parent, RGB rgbDefault, String sTitle, ModifyListener modifListener) {
		super(parent, 0);
		this.rgbDefault = rgbDefault;
		this.sTitle = sTitle;
		this.modifListener = modifListener;
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		this.btnColor = WidgetsFactory.getInstance().createPushButton(this, null, "colors", sTitle, 
				false, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showColorDialog();
			}
		});
		
		lblValue = WidgetsFactory.getInstance().createLabel(this);
		setValue(rgbDefault.toString());
	}

	public String getValue() {
		return sRGB;
	}

	public void setValue(String sRGB) {
		if (sRGB == null || sRGB.isEmpty()) {
			this.sRGB = rgbDefault.toString();
		} else {
			this.sRGB = sRGB;
		}
		updateDisplay();
	}
	
	private void updateDisplay() {
		if (sRGB != null && !sRGB.isEmpty()) {
			RGB rgb = parseRGBString(sRGB);
			if (rgb != null) {
				// NB: color will be disposed by IconManager
				color = new Color(getDisplay(), rgb);
				lblValue.setText(sRGB);
				btnColor.setImage(IconManager.createColorIcon(20, color));
				if (modifListener != null) {
					modifListener.modifyText(null);
				}
			}
		}
	}
	
	/**
	 * Displays a dialog to choose the soil color.
	 */
	private void showColorDialog() {
		ColorDialog dlg = new ColorDialog(getShell());

        // Set the selected color in the dialog from soil color
		if (color != null) {
			dlg.setRGB(color.getRGB());
		}

        // Change the title bar text
        dlg.setText(sTitle);

        // Open the dialog and retrieve the selected color
        RGB rgb = dlg.open();
        if (rgb != null) {
        	sRGB = rgb.toString();
        	updateDisplay();
        }
	}
	
	private RGB parseRGBString(String strRGB) {
		Matcher m = Pattern.compile("RGB \\{(\\d+), (\\d+), (\\d+)\\}").matcher(strRGB);
		if (m.find()) {
			int r = Integer.parseInt(m.group(1));
			int g = Integer.parseInt(m.group(2));
			int b = Integer.parseInt(m.group(3));
			return new RGB(r, g, b);
		}
		return null;
	}
	
}
