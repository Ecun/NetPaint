package model;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Observable;
import java.util.Vector;

public class PaintsList extends Observable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1965115173819343413L;
	private Vector<PaintObject> lockedPaints;
	
	public PaintsList (){
		lockedPaints = new Vector<PaintObject>();
	}
	
	public Vector<PaintObject> getAllPaints(){
		return lockedPaints;
	}
	
	public void addAll(PaintsList paints){
		lockedPaints.addAll(paints.getAllPaints());
	}
	
	public void add(PaintObject paint){
		lockedPaints.add(paint);
		setChanged();
		notifyObservers(paint);
	}
	
	public void addFromServer(PaintObject paint){
		lockedPaints.add(paint);
	}
	
	public void drawEach(Graphics g){
		for(PaintObject paint: lockedPaints)
			paint.draw(g);
	}
}
