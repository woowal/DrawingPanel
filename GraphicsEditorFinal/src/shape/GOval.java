package shape;

import java.awt.geom.Ellipse2D;

public class GOval extends GShape implements Cloneable {

	private static final long serialVersionUID = 1L;
	private int rX,rY;

	public GOval() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		this.shape = new Ellipse2D.Float();
	}
	

	@Override
	public void setOrigin(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
		rX = x;
		rY = y;
	}

	@Override
	public void setPoint(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		int w = (int)Math.abs(x - rX);
		int h = (int)Math.abs(y - rY);
		ellipse.setFrame(Math.min(x, rX), Math.min(y, rY), w, h);
	}
	@Override
	public void addPoint(int x, int y) {
	}

}
