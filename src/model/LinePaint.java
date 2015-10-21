package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class LinePaint extends PaintObject {

	private Line2D line;

	public LinePaint(Point origin, Color color) {
		super(origin,color);
	}

	@Override
	public Shape getShape() {
		return line;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(getOriginPoint().x, getOriginPoint().y, getEndPoint().x, getEndPoint().y);
	}
}
