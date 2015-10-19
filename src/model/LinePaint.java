package model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class LinePaint extends PaintObject {

	private Line2D line;
	
	public LinePaint() {
		setOriginPoint(new Point(0,0));
		setEndPoint(new Point(0,0));
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
