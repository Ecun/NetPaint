package model;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public class PaintsList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1965115173819343413L;
	private ArrayList<PaintObject> lockedPaints;
	
	public PaintsList (){
		lockedPaints = new ArrayList<PaintObject>();
	}
	
	public void add(PaintObject paint){
		lockedPaints.add(paint);
	}
	
	public void drawEach(Graphics g){
		for(PaintObject paint: lockedPaints)
			paint.draw(g);
	}
}
