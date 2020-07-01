package shape;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Vector;

public class GPen extends GShape implements Cloneable{
	
	private Vector<Point> points;
	private Vector<Line2D> lines;
	
	public GPen() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		this.points = new Vector<Point>();
		this.lines = new Vector<Line2D>();
	}

	public boolean contains(int x, int y) {
		for(Line2D line : lines) {
			if (line.ptSegDist(x,y) <= 3) {
				return true;
			}
		}
		return false;
	}
	public boolean getbSelected() {
		return false;
	}
	
	
	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		if (this.lineColor != null) {
			graphics2d.setColor(this.lineColor);
			for (int i = 0; i < points.size()-1; i++) {
				graphics2d.setStroke(new BasicStroke(this.thick));
				graphics2d.drawLine(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y);
				lines.add(new Line2D.Float(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y));
			}
		}
	}
	
	@Override
	public void setOrigin(int x, int y) {
		points.add(new Point (x,y));
		points.add(new Point (x,y));
		points.add(new Point (x,y));
	}

	@Override
	public void setPoint(int x, int y) {
		points.add(new Point (x,y));
	}

	@Override
	public void addPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
