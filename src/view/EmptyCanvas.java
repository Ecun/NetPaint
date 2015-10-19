package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.*;

public class EmptyCanvas extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5793758910425266451L;

	private Canvas canvas;
	private JScrollPane canvasWindow;
	private PaintObject paint;
	private List<PaintObject> shapes;
	private boolean isDrawing;
	private Line2D line;

	private int oldX, oldY, newX, newY;
	private Point newPoint;

	public EmptyCanvas() {
		canvas = new Canvas();
		canvas.setSize(new Dimension(1500, 1000));
		canvas.addMouseListener(new MouseDrawingListener());
		canvas.addMouseMotionListener(new MouseDrawingListener());

		shapes = new ArrayList<PaintObject>();

		isDrawing = false;

		paint = new LinePaint();
		
		line = new Line2D.Double(0, 0, 0, 0);

		canvasWindow = new JScrollPane(canvas);
		setLayout(null);
		canvasWindow.setSize(500, 450);
		add(canvasWindow);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.BLACK);
		g2.draw(line);
	}

	private class MouseDrawingListener implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			newPoint = e.getPoint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (isDrawing) {
				line = new Line2D.Double(oldX, oldY, newX, newY);
				repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			isDrawing = !isDrawing;
			if (isDrawing) {
				oldX = e.getX();
				oldY = e.getY();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			newPoint = e.getPoint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			newPoint = e.getPoint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			newPoint = e.getPoint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			newPoint = e.getPoint();
		}

	}

	public static void main(String[] args) {
		JFrame test = new JFrame();
		test.add(new EmptyCanvas());
		test.setSize(500, 500);
		test.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		/**
		 * paint = (PaintObject) arg; shapes.add(paint);
		 */
	}
}
