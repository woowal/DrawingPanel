package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

public class GGroup extends GShape {
	private Vector<GShape> gShapes;
	private int rX,rY;
	public GGroup() {
		this.eDrawingStyle = EDrawingStyle.eGroup;
		this.shape = new Rectangle();
	}
	
	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		float[] dash=new float[]{5,5,5,5};
		graphics2d.setStroke(new BasicStroke(1, 0, 1, 10, dash, 0));
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
	@Override
	public void setOrigin(int x, int y) {

		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0, 0);
		this.shape = new Rectangle();
		rX = x;
		rY = y;
	}

	@Override
	public void setPoint(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		int w = Math.abs(x - rX);
		int h = Math.abs(y - rY);
		rectangle.setLocation(Math.min(x, rX), Math.min(y, rY));
		rectangle.setSize(w,h);
	}
	@Override
	public void addPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}


}
