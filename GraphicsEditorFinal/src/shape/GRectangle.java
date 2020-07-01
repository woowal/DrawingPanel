package shape;

import java.awt.Rectangle;

public class GRectangle extends GShape implements Cloneable {
	private static final long serialVersionUID = 1L;
	private int rX,rY;
	public GRectangle() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		
		this.shape = new Rectangle();
		
	}

	@Override
	public void setOrigin(int x, int y) {

		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0, 0);
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
	}

}
