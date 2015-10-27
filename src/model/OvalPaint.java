package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class OvalPaint extends PaintObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8582497749014173408L;

	public OvalPaint(Point origin,Color color) {
		super(origin,color);
	}
	
	private int getWidth(){
		return Math.abs(getEndPoint().x - getOriginPoint().x);
	}
	
	private int getHeight(){
		return Math.abs(getEndPoint().y - getOriginPoint().y);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(Math.min(getOriginPoint().x, getEndPoint().x),
				Math.min(getOriginPoint().y, getEndPoint().y), getWidth(), getHeight());
	}
}
