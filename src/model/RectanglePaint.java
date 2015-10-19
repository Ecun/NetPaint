package model;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class RectanglePaint extends PaintObject {

	private Rectangle2D rectangle;

	public RectanglePaint() {
	}

	private double getWidth() {
		return Math.abs(getEndPoint().x - getOriginPoint().x);
	}

	private double getHeight() {
		return Math.abs(getEndPoint().y - getOriginPoint().y);
	}

	@Override
	public Shape getShape() {
		return rectangle;
	}

	@Override
	public void draw() {
		rectangle = new Rectangle2D.Double(Math.min(getOriginPoint().x, getEndPoint().x),
				Math.min(getOriginPoint().y, getEndPoint().y), getWidth(), getHeight());
	}

}
