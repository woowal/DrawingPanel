package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JToolBar;

import main.GConstants;

public class GToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	// components
	private ActionHadler actionHadler;
	private Vector<JButton> buttons;
	
	// associations
	private GDrawingPanel drawingPanel;
	
	public GToolBar() {
		super();
		// create components
		this.actionHadler = new ActionHadler();
		
		this.buttons = new Vector<JButton>();		
		for (GConstants.EToolbar eTool: GConstants.EToolbar.values()) {
			JButton button = new JButton(eTool.getTitle());
			button.setActionCommand(eTool.toString());
			button.addActionListener(this.actionHadler);
			this.buttons.add(button);
			this.add(button);
		}		
	}
	
	public void setAssociation(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;		
	}
	public void initialize() {
		// set associations
		
		// set associative attributes
		this.buttons.get(0).doClick();
		
		// initialize components
	}
	
	class ActionHadler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			drawingPanel.setCurrentTool(GConstants.EToolbar.valueOf(event.getActionCommand()));
		}		
	}


}
