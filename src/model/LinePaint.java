package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LinePaint extends PaintObject {

	public LinePaint(Point origin, Color color) {
		super(origin,color);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(getOriginPoint().x, getOriginPoint().y, getEndPoint().x, getEndPoint().y);
	}
}
