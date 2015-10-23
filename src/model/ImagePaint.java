package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagePaint extends PaintObject {

	private Image image;
	
	public ImagePaint(Point origin, Color color) throws IOException{
		super(origin, color);
		image = ImageIO.read(new File("./image/doge.jpeg"));
	}
	
	private int getWidth() {
		return Math.abs(getEndPoint().x - getOriginPoint().x);
	}

	private int getHeight() {
		return Math.abs(getEndPoint().y - getOriginPoint().y);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawImage(image, Math.min(getOriginPoint().x, getEndPoint().x),
				Math.min(getOriginPoint().y, getEndPoint().y), getWidth(), getHeight(), null);
	}

}
