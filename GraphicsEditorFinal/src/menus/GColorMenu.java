package menus;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;

import frames.GThickChoice;
import main.GConstants;
import main.GConstants.EColorMenu;

public class GColorMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	// components

	public GColorMenu(String name) {
		super(name);

		for (GConstants.EColorMenu eMenu: GConstants.EColorMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenu.getTitle());
			menuItem.addActionListener(this.actionHandler);
			menuItem.setActionCommand(eMenu.getActionCommand());
			this.menuItems.add(menuItem);
			this.add(menuItem);
		}
	}

	public void initialize() {
	}
	public void setLineColor() {
		Color lineColor = JColorChooser.showDialog(this.drawingpanel,
				EColorMenu.eLineColor.getTitle(), this.drawingpanel.getForeground());
		this.drawingpanel.setLineColor(lineColor);
	}
	public void setFillColor() {
		Color fillColor = JColorChooser.showDialog(this.drawingpanel,
				EColorMenu.eFillColor.getTitle(), this.drawingpanel.getForeground());
		this.drawingpanel.setFillColor(fillColor);
	}
	public void forward() {
		this.drawingpanel.forward();
	}
	public void backward() {
		this.drawingpanel.backward();
	}
	public void setThick() {
		GThickChoice choice = new GThickChoice(this.drawingpanel);
	}

}
