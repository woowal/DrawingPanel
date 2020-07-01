package frames;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GAutoRepaintPanel extends JPanel {

	// Constructor
	public GAutoRepaintPanel() {
		this.addMouseMotionListener(new AutoRepainter());
	}
	
	// Inner Class
	private class AutoRepainter extends MouseMotionAdapter {
		@Override public void mouseDragged(MouseEvent e) {repaint();}
	}
}
