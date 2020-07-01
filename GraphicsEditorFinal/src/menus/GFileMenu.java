package menus;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import file.GFile;
import frames.GDrawingPanel;
import main.GConstants;

public class GFileMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;


	// association
	public void association(GDrawingPanel drawingpanel){
		this.drawingpanel = drawingpanel;
	}

	//working variables
	private int reply;
	File directory;
	File file;

	public GFileMenu(String name) {
		super(name);
		
		for (GConstants.EFileMenu eMenu: GConstants.EFileMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenu.getTitle());
			menuItem.setActionCommand(eMenu.getActionCommand());
			menuItem.addActionListener(this.actionHandler);
			this.menuItems.add(menuItem);
			this.add(menuItem);
		}
		
		this.reply = 1;
		this.directory = new File("./data");
		this.file = null;
	}
	
	public int getReply() {
		return this.reply;
	}

	public void initialize() {

	}
	public void checkSave() {
		if(this.drawingpanel.isUpdated()) {
			this.reply = JOptionPane.showConfirmDialog(this.drawingpanel,"변경내용을 저장할까요?","변경 확인",JOptionPane.YES_NO_CANCEL_OPTION);
		}
	}
	public void nnew() {
		this.checkSave();
		if(reply == JOptionPane.OK_OPTION) {
			this.save();
			this.drawingpanel.clearShapes();
			this.file = null;
		} else if (reply == JOptionPane.NO_OPTION) {
			this.drawingpanel.clearShapes();
			this.file = null;
		} else if (reply == JOptionPane.CANCEL_OPTION || reply == JOptionPane.CLOSED_OPTION) {
			
		}
	}
	public void open() {
		this.checkSave();
		if(reply == JOptionPane.OK_OPTION) {
			this.save();
			JFileChooser fileChooser = new JFileChooser(this.directory);
			int returnValue = fileChooser.showOpenDialog(this.drawingpanel);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				this.drawingpanel.clearShapes();
				this.directory = fileChooser.getCurrentDirectory();
				this.file = fileChooser.getSelectedFile();
				GFile gFile = new GFile();
				Object shapes = gFile.readObject(this.file);
				this.drawingpanel.setShapes(shapes);
			}
		} else if (reply == JOptionPane.NO_OPTION) {
			JFileChooser fileChooser = new JFileChooser(this.directory);
			int returnValue = fileChooser.showOpenDialog(this.drawingpanel);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				this.drawingpanel.clearShapes();
				this.directory = fileChooser.getCurrentDirectory();
				this.file = fileChooser.getSelectedFile();
				GFile gFile = new GFile();
				Object shapes = gFile.readObject(this.file);
				this.drawingpanel.setShapes(shapes);
			}
			
		} else if (reply == JOptionPane.CANCEL_OPTION || reply == JOptionPane.CLOSED_OPTION) {
			
		}
		
	}
	public void save() {
		if(this.file==null) {
			this.saveAs();
		}else {
			GFile gFile = new GFile();
			gFile.saveObject(drawingpanel.getShapes(), this.file);
			this.drawingpanel.setUpdated(false);
		}
	}

	public void saveAs() {
		JFileChooser fileChooser = new JFileChooser(this.directory);
		int returnValue = fileChooser.showOpenDialog(this.drawingpanel);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			this.directory = fileChooser.getCurrentDirectory();
			this.file = fileChooser.getSelectedFile();
			this.save();
		}
	}
	public void print() {}
	public void exit() {
		this.checkSave();
		if(reply == JOptionPane.OK_OPTION) {
			this.save();
			System.exit(0);
		} else if (reply == JOptionPane.NO_OPTION) {
			System.exit(0);
		} else if (reply == JOptionPane.CANCEL_OPTION || reply == JOptionPane.CLOSED_OPTION) {
			
		}
	}
}
