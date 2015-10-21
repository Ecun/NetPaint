package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class OvalPaint extends PaintObject {
	private Ellipse2D oval;

	public OvalPaint(Point origin,Color color) {
		super(origin,color);
	}
	
	private int getWidth(){
		return getEndPoint().x - getOriginPoint().x;
	}
	
	private int getHeight(){
		return Math.abs(getEndPoint().y - getOriginPoint().y);
	}

	@Override
	public Shape getShape() {
		return oval;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(Math.min(getOriginPoint().x, getEndPoint().x),
				Math.min(getOriginPoint().y, getEndPoint().y), getWidth(), getHeight());
	}
}
