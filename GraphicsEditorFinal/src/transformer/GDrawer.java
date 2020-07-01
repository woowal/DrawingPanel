package transformer;

import java.awt.Graphics2D;

import shape.GShape;

public class GDrawer extends GTransformer {

	public GDrawer(GShape shape) {
		super(shape);
		// TODO Auto-generated constructor stub
	}
	public void initTransforming(Graphics2D g2D, int x, int y) {
		this.shape.setOrigin(x, y);
	}
	public void keepTransforming(Graphics2D g2D, int x, int y) {
		this.shape.setPoint(x, y);
	}
	
}
