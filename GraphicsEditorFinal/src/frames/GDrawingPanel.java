package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import main.GConstants;
import main.GConstants.ECursor;
import main.GConstants.EToolbar;
import shape.GAnchors;
import shape.GShape;
import shape.GShape.EDrawingStyle;
import tools.GCopy;
import transformer.GDrawer;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotater;
import transformer.GTransformer;

public class GDrawingPanel extends GAutoRepaintPanel {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	private enum EDrawingState {
		eIdle, eDrawing, eTransforming;
	}

	private EDrawingState eDrawingState;
	private Color lineColor, fillColor;
	private boolean bUpdated;
	private boolean bDrawing;
	private int shapeIndex;
	private int thick;
	private int cap;
	private int join;
	private float[] line;

	// components
	private MouseHandler mouseHandler;
	private Vector<GShape> shapes;
	private Vector<GShape> undoShapes;
	private Vector<GShape> undoShapes2;
	private Vector<GShape> redoShapes;
	private Vector<GShape> redoShapes2;
	private GShape copyShape;
	private Cursor rotateCursor;
	private Cursor eraseCursor;

	// association

	private GCopy copy;
	private GShape currentTool;
	private GShape currentShape;
	private GTransformer transformer;

	// constructors and initializers
	public GDrawingPanel() {
		// attributes
		this.setForeground(Color.BLACK);
		this.setBackground(Color.white);
		this.eDrawingState = EDrawingState.eIdle;
		// components
		this.mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.shapes = new Vector<GShape>();
		this.undoShapes = new Vector<GShape>();
		this.undoShapes2 = new Vector<GShape>();
		this.redoShapes = new Vector<GShape>();
		this.redoShapes2 = new Vector<GShape>();
		this.copy = new GCopy();
		// working variables
		this.currentTool = null;
		this.lineColor = null;
		this.fillColor = null;
		this.currentShape = null;
		this.bUpdated = false;
		this.bDrawing = true;
		this.thick = 1;
		this.cap = 0;
		this.join = 0;
		this.line = null;
	}

	public void initialize() {
		this.lineColor = this.getForeground();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image rotateImage = toolkit.getImage("data/rotate2.png");
		Image eraseImage = toolkit.getImage("data/eraser.png");
		rotateCursor = toolkit.createCustomCursor(rotateImage, new Point(13, 13), "rotateCursor");
		eraseCursor = toolkit.createCustomCursor(eraseImage, new Point(3, 3), "eraseCursor");
	}

	private void makeUndoShapes() {
		undoShapes2 = undoShapes;
		undoShapes = (Vector<GShape>) copy.copy(this.shapes);
	}

	// setters & getters
	public Vector<GShape> getShapes() {
		return this.shapes;
	}

	public void setShapes(Object shapes) {
		this.shapes = (Vector<GShape>) shapes;
		this.repaint();
	}

	public void clearShapes() {
		makeUndoShapes();
		this.shapes.clear();
		this.repaint();
	}
	public void setUpdated(Boolean bUpdated) {
		this.bUpdated = bUpdated;
	}

	public void setCurrentTool(EToolbar eToolBar) {
		this.currentTool = eToolBar.getTool();
	}

	public boolean isUpdated() {
		return this.bUpdated;
	}

	public void setUpdate(boolean bUpdated) {
		this.bUpdated = bUpdated;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
		changeSelectedShapeLineColor();
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		changeSelectedShapeFillColor();
	}

	// methods
	public void paint(Graphics graphics) {
		super.paint(graphics);
		// user defined drawing
		for (GShape shape : this.shapes) {
			shape.draw(graphics);
		}
	}
	
	private void checkErase(int x, int y) {
		GShape selectedShape = this.onShape(x, y);
		if (this.onShape(x, y) == null) {
			this.setCursor(ECursor.eDefault.getCursor());
		} else {
			this.setCursor(eraseCursor);
		}
	}

	private void checkCursor(int x, int y) {
		GShape selectedShape = this.onShape(x, y);
		if (this.onShape(x, y) == null) {
			this.setCursor(ECursor.eDefault.getCursor());
		} else {
			GAnchors.EAnchors eSelectedAnchor = selectedShape.getESelectedAnchor();
			if (eSelectedAnchor != null) {
				switch (eSelectedAnchor) {
				case NW:
					this.setCursor(ECursor.eNW.getCursor());
					break;
				case NN:
					this.setCursor(ECursor.eNN.getCursor());
					break;
				case NE:
					this.setCursor(ECursor.eNE.getCursor());
					break;
				case EE:
					this.setCursor(ECursor.eEE.getCursor());
					break;
				case SE:
					this.setCursor(ECursor.eSE.getCursor());
					break;
				case SS:
					this.setCursor(ECursor.eSS.getCursor());
					break;
				case SW:
					this.setCursor(ECursor.eSW.getCursor());
					break;
				case WW:
					this.setCursor(ECursor.eWW.getCursor());
					break;
				case RR:
					this.setCursor(rotateCursor);
					break;
				case MM:
					this.setCursor(ECursor.eMove.getCursor());
					break;
				default:
					break;
				}
			}
		}
	}

	private GShape onShape(int x, int y) {
		for (int i = shapes.size() - 1; i > -1; i--) {
			if (shapes.get(i).contains(x, y)) {
				return shapes.get(i);
			}
		}
		return null;
	}

	private void changeSelectedShapeLineColor() {
		makeUndoShapes();
		for (GShape shape : this.shapes) {
			if (shape.getbSelected()) {
				shape.setLineColor(this.lineColor);
			}
		}

		this.repaint();
	}

	private void changeSelectedShapeFillColor() {
		makeUndoShapes();
		for (GShape shape : this.shapes) {
			if (shape.getbSelected()) {
				shape.setFillColor(this.fillColor);
			}
		}
		this.repaint();
	}

	private void checkSelected(GShape selectedShape) {
		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i) == selectedShape) {
				shapeIndex = i;
				break;
			}
			shapeIndex = 9999;
		}
	}

	private void setSelected(GShape selectedShape) {
		for (GShape shape : this.shapes) {
			shape.setSelected(false);
		}
		selectedShape.setSelected(true);
		this.repaint();
	}

	private void initGroup(int x, int y) {
		bDrawing = true;
		this.currentShape = this.currentTool.clone();
		currentShape.setLineColor(Color.BLACK);
		currentShape.setLine(this.thick, this.cap, this.join, line);
		this.transformer = new GDrawer(this.currentShape);
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		this.transformer.initTransforming(g2d, x, y);
		this.shapes.add(this.transformer.getShape());
	}

	private void finishGroup(int x, int y) {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		this.transformer.finishTransforming(g2d, x, y);
		this.bUpdated = true;
		GShape group = this.shapes.get(this.shapes.size() - 1);
		for (GShape shape : this.shapes) {
			shape.setSelected(false);
		}
		for (GShape shape : this.shapes) {
			if (shape.getBounds() != null) {
				if (group.getBounds().contains(shape.getBounds())) {
					shape.setSelected(true);
				}
			}
		}
		this.shapes.remove(this.shapes.size() - 1);
		this.repaint();
	}

	private void initTransforming(GShape shape, int x, int y) {
		makeUndoShapes();
		if (shape == null) {
			// drawing
			bDrawing = true;
			this.currentShape = this.currentTool.clone();
			currentShape.setLineColor(this.lineColor);
			currentShape.setFillColor(this.fillColor);
			currentShape.setLine(this.thick, this.cap, this.join, line);
			this.transformer = new GDrawer(this.currentShape);
			Graphics2D g2d = (Graphics2D) this.getGraphics();
			this.transformer.initTransforming(g2d, x, y);
			this.shapes.add(this.transformer.getShape());

		} else {
			// transformation
			bDrawing = false;
			// moving, resizing, rotating
			this.currentShape = shape;
			if (shape.getESelectedAnchor() != null) {
				switch (shape.getESelectedAnchor()) {
				case MM:
					this.transformer = new GMover(this.currentShape);
					break;
				case RR:
					this.transformer = new GRotater(this.currentShape);
					break;
				default:
					this.transformer = new GResizer(this.currentShape);
					break;
				}
				Graphics2D g2d = (Graphics2D) this.getGraphics();
				this.transformer.initTransforming(g2d, x, y);
				checkSelected(currentShape);
				shapes.set(shapeIndex, this.transformer.getShape());
			}
		}
	}

	private void keepTransforming(int x, int y) {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		if (this.bDrawing) {
			this.transformer.keepTransforming(g2d, x, y);
			shapes.set(shapes.size() - 1, this.transformer.getShape());
		} else {
			this.transformer.keepTransforming(g2d, x, y);
			shapes.set(shapeIndex, this.transformer.getShape());
		}
		this.repaint();
	}

	private void finishTransforming(int x, int y) {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		this.transformer.finishTransforming(g2d, x, y);
		this.bUpdated = true;
		this.setSelected(this.currentShape);
	}

	private void continueTransforming(int x, int y) {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		this.transformer.continueTransforming(g2d, x, y);
		shapes.set(shapes.size() - 1, this.transformer.getShape());
	}

	// inner class
	class MouseHandler implements MouseMotionListener, MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 1) {
				this.mouse1Clicked(event);
			} else if (event.getClickCount() == 2) {
				this.mouse2Clicked(event);
			}
		}

		// n point drawing
		private void mouse1Clicked(MouseEvent event) {
			int x = event.getX();
	        int y = event.getY();
			GShape shape = onShape(x, y);
			if (shape == null) {
				if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState == EDrawingState.eIdle) {
					initTransforming(null, x, y);
					eDrawingState = EDrawingState.eDrawing;
				}
			} else if (eDrawingState == EDrawingState.eIdle) {
				setSelected(shape);
			}
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState == EDrawingState.eDrawing) {
				continueTransforming(x, y);
			}
		}

		private void mouse2Clicked(MouseEvent event) {
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState == EDrawingState.eDrawing) {
				int x = event.getX();
		        int y = event.getY();
				finishTransforming(x, y);
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			int x = event.getX();
	        int y = event.getY();
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState == EDrawingState.eDrawing) {
				keepTransforming(x, y);
			}
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eErase) {
				checkErase(x, y);
			} else {
				checkCursor(x, y);
			}
			repaint();
		}

		// 2 point drawing
		@Override
		public void mousePressed(MouseEvent event) {
			int x = event.getX();
	        int y = event.getY();
			GShape shape = onShape(x, y);
			if (eDrawingState == EDrawingState.eIdle) {
				if (shape == null) {
					if (currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
						initTransforming(null, x, y);
						eDrawingState = EDrawingState.eDrawing;
					} else if (currentTool.getEDrawingStyle() == EDrawingStyle.eGroup) {
						initGroup(x, y);
						eDrawingState = EDrawingState.eDrawing;
					}
				} else if (currentTool.getEDrawingStyle() == EDrawingStyle.eErase) {
					checkSelected(shape);
					if (shapeIndex != 9999) {
						shapes.remove(shapeIndex);
						repaint();
					}
				} else {
					setSelected(shape);
					initTransforming(shape, x, y);
					eDrawingState = EDrawingState.eTransforming;
				}
			}
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent event) {
			int x = event.getX();
	        int y = event.getY();
			if (eDrawingState == EDrawingState.eTransforming) {
				keepTransforming(x, y);
			} else if (eDrawingState == EDrawingState.eDrawing) {
				if (currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
					keepTransforming(x, y);
				} else if (currentTool.getEDrawingStyle() == EDrawingStyle.eGroup) {
					keepTransforming(x, y);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent event) {
	        int x = event.getX();
	        int y = event.getY();
			if (eDrawingState == EDrawingState.eTransforming) {
				finishTransforming(x, y);
				eDrawingState = EDrawingState.eIdle;
			} else if (eDrawingState == EDrawingState.eDrawing) {
				if (currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
					finishTransforming(x, y);
					eDrawingState = EDrawingState.eIdle;
				} else if (currentTool.getEDrawingStyle() == EDrawingStyle.eGroup) {
					finishGroup(x, y);
					eDrawingState = EDrawingState.eIdle;
				}
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent event) {
		}

		@Override
		public void mouseExited(MouseEvent event) {
		}
	}

	public void copy() {
		if (this.currentShape != null) {
			this.copyShape = this.currentShape;
		}
	}

	public void paste() {
		if (this.copyShape != null) {
			makeUndoShapes();
			GShape pasteShape = (GShape) copy.copy(this.copyShape);
			pasteShape.move(10, 10);
			setSelected(pasteShape);
			this.shapes.add(pasteShape);
			this.repaint();
			this.copyShape = (GShape) copy.copy(pasteShape);
		}
	}

	public void cut() {
		if (this.currentShape != null) {
			this.copyShape = this.currentShape;
			this.checkSelected(this.currentShape);
			shapes.remove(shapeIndex);
			this.repaint();
		}
	}

	public void undo() {
		redoShapes2 = redoShapes;
		redoShapes = shapes;
		shapes = undoShapes;
		undoShapes = undoShapes2;
		this.repaint();
	}

	public void redo() {
		undoShapes2 = undoShapes;
		undoShapes = shapes;
		shapes = redoShapes;
		redoShapes = redoShapes2;
		this.repaint();
	}

	public void forward() {
		if (this.currentShape != null) {
			makeUndoShapes();
			this.checkSelected(this.currentShape);
			if (shapeIndex < shapes.size() - 1) {
				GShape temp = shapes.get(shapeIndex);
				shapes.set(shapeIndex, shapes.get(shapeIndex + 1));
				shapes.set(shapeIndex + 1, temp);
				this.repaint();
			}
		}
	}

	public void backward() {
		if (this.currentShape != null) {
			makeUndoShapes();
			this.checkSelected(this.currentShape);
			if (shapeIndex > 0) {
				GShape temp = shapes.get(shapeIndex);
				shapes.set(shapeIndex, shapes.get(shapeIndex - 1));
				shapes.set(shapeIndex - 1, temp);
				this.repaint();
			}
		}
	}
	
	public void setCap(int cap) {
		this.cap = cap;
	}
	public void setJoin(int join) {
		this.join = join;
	}
	public void setLine(boolean chk) {
		if (chk) {
			this.line = null;
		} else {
			this.line = new float[]{this.thick*5,this.thick*5,this.thick*5,this.thick*5};
		}
	}

	public void setLine(int thick) {
		this.thick = thick;
	}

	public void changeThick() {
		makeUndoShapes();
		for (GShape shape : this.shapes) {
			if (shape.getbSelected()) {
				shape.setLine(this.thick, this.cap, this.join, line);
			}
		}
		this.repaint();
	}
}
