package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public abstract class PaintObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8138054518284502873L;
	private Point origin;
	private Point end;
	private Color color;
	
	
	public PaintObject(Point a, Color color){
		setOriginPoint(a);
		this.color = color;
	}
	public void setOriginPoint(Point p){
		this.origin = p;
	}
	
	public Point getOriginPoint(){
		return origin;
	}
	
	public void setEndPoint(Point p){
		this.end = p;
	}
	
	public Point getEndPoint(){
		return end;
	}
	
	public Color getColor(){
		return color;
	}
	
	public abstract void draw(Graphics g);
}
