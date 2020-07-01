package shape;

import java.awt.Point;
import java.awt.geom.Line2D;

public class GLine extends GShape implements Cloneable {

	private static final long serialVersionUID = 1L;

	public GLine() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		this.shape = new Line2D.Float();
	}
	
	public boolean contains(int x, int y) {
		boolean bContains = false;
		this.eSelectedAnchor = null;
		if (this.bSelected) {
			this.eSelectedAnchor = this.anchors.contains(x, y);
		}
		if (this.eSelectedAnchor == null) {
			if (this.shape.getBounds().contains(new Point(x, y))) {
				this.eSelectedAnchor = GAnchors.EAnchors.MM;
				bContains = true;
			}
		} else {
			bContains = true;
		}
		return bContains;
	}

	@Override
	public void setOrigin(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x, y, x, y);
	}

	@Override
	public void setPoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}

	@Override
	public void addPoint(int x, int y) {
	}

}
