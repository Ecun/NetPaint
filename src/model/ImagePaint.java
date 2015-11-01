package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImagePaint extends PaintObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7517779599011317377L;
	private ImageIcon image;
	
	public ImagePaint(Point origin, Color color) throws IOException{
		super(origin, color);
		image = new ImageIcon(ImageIO.read(new File("./image/doge.jpeg")));
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
		Image imageDrawing = image.getImage();
		g.drawImage(imageDrawing, Math.min(getOriginPoint().x, getEndPoint().x),
				Math.min(getOriginPoint().y, getEndPoint().y), getWidth(), getHeight(), null);
	}

}
