package shape;

public class GEraser extends GShape implements Cloneable{
	
	public GEraser() {
		this.eDrawingStyle = EDrawingStyle.eErase;
	}
	@Override
	public void setOrigin(int x, int y) {}
	@Override
	public void setPoint(int x, int y) {}
	@Override
	public void addPoint(int x, int y) {}

}
