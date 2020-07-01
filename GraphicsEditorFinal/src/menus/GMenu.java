package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.GDrawingPanel;

public abstract class GMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	protected GDrawingPanel drawingpanel;
	protected ActionHandler actionHandler;
	protected Vector<JMenuItem> menuItems;
	
	public GMenu(String name) {
		super(name);
		
		this.menuItems = new Vector<JMenuItem>();
		this.actionHandler = new ActionHandler();
	}
	
	public abstract void initialize();
	public int getReply() {return 2;}
	public void save() {};
	public void checkSave() {};

	public void setAssociation(GDrawingPanel drawingPanel) {
		this.drawingpanel = drawingPanel;
	}
	private void invokeMethod(String methodName) {
		try {
			this.getClass().getMethod(methodName).invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	protected class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			String methodName = event.getActionCommand();
			invokeMethod(methodName);
		}
	}
}
