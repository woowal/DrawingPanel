package transformer;

import java.awt.Graphics2D;

import shape.GShape;

public abstract class GTransformer {
	protected GShape shape;
	public GTransformer(GShape shape) {
		this.shape = shape;
		
	}
	public void initTransforming(Graphics2D g2D, int x, int y) {
		this.shape.initTransforming(x, y);
	}
	public void keepTransforming(Graphics2D g2D, int x, int y) {
		this.shape.keepTransforming(x, y);
	}
	public void finishTransforming(Graphics2D g2D, int x, int y) {
		this.shape.finishTransforming(x, y);
	}
	public void continueTransforming(Graphics2D g2D, int x, int y) {
		this.shape.addPoint(x, y);
	}
	public GShape getShape() {
		return this.shape;
	}
}
