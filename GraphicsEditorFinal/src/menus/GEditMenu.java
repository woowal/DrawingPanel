package menus;
import java.util.Vector;
import javax.swing.JMenuItem;
import main.GConstants;

public class GEditMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	// components
	
	public GEditMenu(String name) {
		super(name);

		this.menuItems = new Vector<JMenuItem>();
		
		for (GConstants.EEditMenu eMenu: GConstants.EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenu.getTitle());
			menuItem.setActionCommand(eMenu.getActionCommand());
			menuItem.addActionListener(this.actionHandler);
			this.menuItems.add(menuItem);
			this.add(menuItem);
		}
	}
	public void copy() {
		this.drawingpanel.copy();
	}
	public void paste() {
		this.drawingpanel.paste();
	}
	public void cut() {
		this.drawingpanel.cut();
	}
	public void undo() {
		this.drawingpanel.undo();
	}
	public void redo() {
		this.drawingpanel.redo();
	}
	
	public void initialize() {
	}
}
