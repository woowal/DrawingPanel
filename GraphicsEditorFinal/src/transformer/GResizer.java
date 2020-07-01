package transformer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import shape.GShape;

public class GResizer extends GTransformer {
	private AffineTransform at;
	private int startX, startY, endX, endY, minX, minY, maxX, maxY;

	public GResizer(GShape shape) {
		super(shape);
		this.at = new AffineTransform();
	}

	public void initTransforming(Graphics2D g2D, int x, int y) {
		this.startX = x;
		this.startY = y;
		this.minX = (int) shape.getBounds().getMinX();
		this.minY = (int) shape.getBounds().getMinY();
		this.maxX = (int) shape.getBounds().getMaxX();
		this.maxY = (int) shape.getBounds().getMaxY();
	}

	public void keepTransforming(Graphics2D g2D, int x, int y) {
		if (shape.getBounds().width > 3 && shape.getBounds().height > 3) {
			this.endX = x;
			this.endY = y;
			switch (shape.getESelectedAnchor()) {
			case NW:
				this.at.setToScale((shape.getBounds().getWidth() - endX + startX) / shape.getBounds().getWidth(),
						(shape.getBounds().getHeight() - endY + startY) / shape.getBounds().getHeight());
				this.at.translate(maxX - this.at.createTransformedShape(shape.getShape()).getBounds().getMaxX(),
						maxY - this.at.createTransformedShape(shape.getShape()).getBounds().getMaxY());
				break;
			case NN:
				this.at.setToScale(1, (shape.getBounds().getHeight() - endY + startY) / shape.getBounds().getHeight());
				this.at.translate(0, maxY - this.at.createTransformedShape(shape.getShape()).getBounds().getMaxY());
				break;
			case NE:
				this.at.setToScale((shape.getBounds().getWidth() + endX - startX) / shape.getBounds().getWidth(),
						(shape.getBounds().getHeight() - endY + startY) / shape.getBounds().getHeight());
				this.at.translate(minX - this.at.createTransformedShape(shape.getShape()).getBounds().getMinX(),
						maxY - this.at.createTransformedShape(shape.getShape()).getBounds().getMaxY());
				break;
			case EE:
				this.at.setToScale((shape.getBounds().getWidth() + endX - startX) / shape.getBounds().getWidth(), 1);
				this.at.translate(minX - this.at.createTransformedShape(shape.getShape()).getBounds().getMinX(), 0);
				break;
			case SE:
				this.at.setToScale((shape.getBounds().getWidth() + endX - startX) / shape.getBounds().getWidth(),
						(shape.getBounds().getHeight() + endY - startY) / shape.getBounds().getHeight());
				this.at.translate(minX - this.at.createTransformedShape(shape.getShape()).getBounds().getMinX(),
						minY - this.at.createTransformedShape(shape.getShape()).getBounds().getMinY());
				break;
			case SS:
				this.at.setToScale(1, (shape.getBounds().getHeight() + endY - startY) / shape.getBounds().getHeight());
				this.at.translate(0, minY - this.at.createTransformedShape(shape.getShape()).getBounds().getMinY());
				break;
			case SW:
				this.at.setToScale((shape.getBounds().getWidth() - endX + startX) / shape.getBounds().getWidth(),
						(shape.getBounds().getHeight() + endY - startY) / shape.getBounds().getHeight());
				this.at.translate(maxX - this.at.createTransformedShape(shape.getShape()).getBounds().getMaxX(),
						minY - this.at.createTransformedShape(shape.getShape()).getBounds().getMinY());
				break;
			case WW:
				if ((shape.getBounds().getWidth() - endX + startX) > 0) {
					this.at.setToScale((shape.getBounds().getWidth() - endX + startX) / shape.getBounds().getWidth(),
							1);
					this.at.translate(maxX - this.at.createTransformedShape(shape.getShape()).getBounds().getMaxX(), 0);
					break;
				}
			}
		this.shape.setShape(this.at.createTransformedShape(shape.getShape()));
		this.startX = this.endX;
		this.startY = this.endY;
		this.shape.draw(g2D);
		}
	}

	public void finishTransforming(Graphics2D g2D, int x, int y) {

	}
}
