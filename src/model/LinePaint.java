package model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class LinePaint extends PaintObject {

	private Line2D line;
	
	public LinePaint(Point origin) {
		super(origin);
	}

	@Override
	public Shape getShape() {
		return line;
	}

	@Override
	public void draw() {
		line = new Line2D.Double(getOriginPoint(), getEndPoint());
	}
}
