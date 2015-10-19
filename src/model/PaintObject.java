package model;

import java.awt.Point;
import java.awt.Shape;

public abstract class PaintObject {
	private Point origin;
	private Point end;
	
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
	
	public abstract void draw();
	
	public abstract Shape getShape();
}
