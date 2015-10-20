package model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class OvalPaint extends PaintObject {
	private Ellipse2D oval;

	public OvalPaint(Point origin) {
		super(origin);
	}
	
	private double getWidth(){
		return Math.abs(getEndPoint().x - getOriginPoint().x);
	}
	
	private double getHeight(){
		return Math.abs(getEndPoint().y - getOriginPoint().y);
	}

	@Override
	public Shape getShape() {
		return oval;
	}

	@Override
	public void draw() {
		oval = new Ellipse2D.Double(Math.min(getOriginPoint().x, getEndPoint().x),
				Math.min(getOriginPoint().y, getEndPoint().y), getWidth(), getHeight());
	}
}
