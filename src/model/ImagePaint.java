package model;

import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagePaint extends PaintObject {

	private Image image;
	
	public ImagePaint(Point origin) throws IOException{
		super(origin);
		image = ImageIO.read(new File("./image/doge.jpeg"));
	}
	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void draw() {
	}

}
