package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

public abstract class GShape implements Serializable {

	private static final long serialVersionUID = 1L;
	private int tMoveX, tMoveY;
	protected Shape shape;
	protected Color lineColor, fillColor;
	protected int thick, cap, join;
	protected float[] line;

	protected GAnchors anchors;
	protected GAnchors.EAnchors eSelectedAnchor;

	public enum EDrawingStyle {
		e2Points, eNPoints, eErase, eGroup
	}

	protected EDrawingStyle eDrawingStyle;
	protected boolean bSelected;

	public GShape() {
		this.thick = 1;
		this.cap = 0;
		this.join = 0;
		this.line = null;
		this.lineColor = null;
		this.fillColor = null;
		this.bSelected = false;
		this.anchors = new GAnchors();
	}

	public EDrawingStyle getEDrawingStyle() {
		return this.eDrawingStyle;
	}

	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
		if (this.bSelected) {
			if(this.shape != null) {
				this.anchors.setBounds(this.shape.getBounds());
			}
		}
	}
	
	public boolean getbSelected() {
		return bSelected;
	}

	public GAnchors.EAnchors getESelectedAnchor() {
		return this.eSelectedAnchor;
	}

	public Shape getShape() {
		return this.shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setStroke(new BasicStroke(this.thick, this.cap, this.join, 10, this.line, 0));
//		graphics2d.setStroke(new BasicStroke(this.thick));
		if (this.fillColor != null) {
			graphics2d.setColor(this.fillColor);
			graphics2d.fill(this.shape);
		}
		if (this.lineColor != null) {
			graphics2d.setColor(this.lineColor);
			graphics2d.draw(this.shape);
		}
		if (this.bSelected) {
			graphics2d.setStroke(new BasicStroke(1));
			graphics2d.setColor(Color.black);
			this.anchors.draw(graphics2d);
			graphics2d.setColor(this.lineColor);
		}
	}

	public GShape clone() {
		try {
			return this.getClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setFillColor(Color fileColor) {
		this.fillColor = fileColor;
	}
	public void setLine(int thick, int cap, int join, float[] line) {
		this.thick = thick;
		this.cap = cap;
		this.join = join;
		this.line = line;
	}

	public boolean contains(int x, int y) {
		boolean bContains = false;
		this.eSelectedAnchor = null;
		if (this.bSelected) {
			this.eSelectedAnchor = this.anchors.contains(x, y);
		}
		if (this.eSelectedAnchor == null) {
			if (this.shape.contains(new Point(x, y))) {
				this.eSelectedAnchor = GAnchors.EAnchors.MM;
				bContains = true;
			}
		} else {
			bContains = true;
		}
		return bContains;
	}

	public Rectangle getBounds() {
		if(this.shape == null) {
			return null;
		}
		return this.shape.getBounds();
	}

	public void initTransforming(int x, int y) {
		this.tMoveX = x;
		this.tMoveY = y;
	}

	public void keepTransforming(int x, int y) {
		this.move(x - this.tMoveX, y - this.tMoveY);
		this.tMoveX = x;
		this.tMoveY = y;
	}

	public void finishTransforming(int x, int y) {
		this.tMoveX = x;
		this.tMoveY = y;
	}

	public void move(int dx, int dy) {

		AffineTransform at = new AffineTransform();
		at.translate(dx, dy);
		this.shape = at.createTransformedShape(this.shape);
	}

	public abstract void setOrigin(int x, int y);

	public abstract void setPoint(int x, int y);

	public abstract void addPoint(int x, int y);

}
