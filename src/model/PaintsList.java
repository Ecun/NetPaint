package model;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Vector;

public class PaintsList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1965115173819343413L;
	private Vector<PaintObject> lockedPaints;
	
	public PaintsList (){
		lockedPaints = new Vector<PaintObject>();
	}
	
	public void add(PaintObject paint){
		lockedPaints.add(paint);
	}
	
	public void drawEach(Graphics g){
		for(PaintObject paint: lockedPaints)
			paint.draw(g);
	}
}
