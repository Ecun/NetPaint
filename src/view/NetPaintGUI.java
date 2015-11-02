/*
 * Holds a panel that can be drawn upon 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.ImagePaint;
import model.LinePaint;
import model.OvalPaint;
import model.PaintObject;
import model.PaintsList;
import model.RectanglePaint;
import network.Server;

public class NetPaintGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final String lineString = "Line";
	static final String rectangleString = "Rectangle";
	static final String ovalString = "Oval";
	static final String imageString = "Image";

	private JPanel drawingPanel;
	private JScrollPane canvasWindow;
	private JPanel optionPanel;
	private JPanel colorPanel;

	private boolean lineDraw, rectDraw, ovalDraw, imageDraw;

	private Color newColor;

	public NetPaintGUI() {
		this.setTitle("Click once to toggle drawing, second click will lock the shape, or draging mouse to draw");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 0);
		setLayout(null);
		setSize(900, 770);
		setVisible(true);

		lineDraw = true;
		rectDraw = false;
		ovalDraw = false;
		imageDraw = false;

		drawingPanel = new DrawingPanel();
		canvasWindow = new JScrollPane(drawingPanel);
		canvasWindow.setSize(900, 400);
		add(canvasWindow);

		optionPanel = new JPanel();
		optionPanel.setSize(800, 40);
		optionPanel.setLocation(0, 400);
		setOptionMenu();
		add(optionPanel);

		colorPanel = new ColorChooser();
		colorPanel.setSize(800, 270);
		colorPanel.setLocation(0, 450);
		add(colorPanel);

	}

	public void setOptionMenu() {
		optionPanel.setLayout(new GridLayout(1, 0));
		optionPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 50));

		// Create the radio buttons.
		JRadioButton lineButton = new JRadioButton(lineString);
		lineButton.setMnemonic(KeyEvent.VK_B);
		lineButton.setActionCommand(lineString);
		lineButton.setSelected(true);
		lineButton.addItemListener(new lineButtonListener());

		JRadioButton rectangleButton = new JRadioButton(rectangleString);
		rectangleButton.setMnemonic(KeyEvent.VK_C);
		rectangleButton.setActionCommand(rectangleString);

		JRadioButton ovalButton = new JRadioButton(ovalString);
		ovalButton.setMnemonic(KeyEvent.VK_D);
		ovalButton.setActionCommand(ovalString);

		JRadioButton imageButton = new JRadioButton(imageString);
		imageButton.setMnemonic(KeyEvent.VK_R);
		imageButton.setActionCommand(imageString);

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(lineButton);
		group.add(rectangleButton);
		group.add(ovalButton);
		group.add(imageButton);

		// Register a listener for the radio buttons.
		lineButton.addItemListener(new lineButtonListener());
		rectangleButton.addItemListener(new RectButtonListener());
		ovalButton.addItemListener(new OvalButtonListener());
		imageButton.addItemListener(new ImageButtonListener());

		// The preferred size is hard-coded to be the width of the
		// widest image and the height of the tallest image.
		// A real program would compute this.
		optionPanel.add(lineButton);
		optionPanel.add(rectangleButton);
		optionPanel.add(ovalButton);
		optionPanel.add(imageButton);
	}

	private class lineButtonListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				lineDraw = true;
			} else
				lineDraw = false;
		}
	}

	private class RectButtonListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				rectDraw = true;
			} else
				rectDraw = false;
		}
	}

	private class OvalButtonListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				ovalDraw = true;
			} else
				ovalDraw = false;
		}
	}

	private class ImageButtonListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				imageDraw = true;
			} else
				imageDraw = false;
		}
	}

	private class DrawingPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Maintain the two points between which we draw lines
		// every time the mouse moves from one point to another
		// Line can be be short if mouse is dragged slowly, or longer if dragged
		// more quickly
		private int oldX, oldY, newX, newY;

		private boolean isDrawing;
		private boolean isDraging;
		private PaintObject paint;
		private Vector<PaintObject> paints;
		private PaintsList lockedPaints;

		Socket socket;
		ObjectOutputStream oos;
		ObjectInputStream ois;

		public DrawingPanel() {
			setLayout(null);
			setPreferredSize(new Dimension(1500, 1000));
			isDrawing = false;
			isDraging = false;

			this.paints = new Vector<PaintObject>();
			lockedPaints = new PaintsList();

			ListenToMouse listener = new ListenToMouse();
			this.addMouseMotionListener(listener);
			this.addMouseListener(listener);

			this.setBackground(Color.white);

			openConnection();

			ServerListener thread1 = new ServerListener();
			thread1.start();
		}

		// This where all the drawing occurs. Run this by calling repaint()
		// that calls a JPanel method that calls the following paintComponent
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			lockedPaints.drawEach(g2);
			for (PaintObject paint : paints)
				paint.draw(g2);
		}

		private void openConnection() {
			try {
				socket = new Socket("localHost", Server.PORT_NUMBER);
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				try {
					lockedPaints = (PaintsList) ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		private class ServerListener extends Thread {

			
			@Override
			public void run() {
				while (true) {
					try {
						lockedPaints = (PaintsList) ois.readObject();
						System.out.println("get paints from server");
						repaint();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		private class ListenToMouse implements MouseListener, MouseMotionListener {

			// Use a mouse click to toggle the drawing
			public void mouseClicked(MouseEvent evt) {
			}

			public void mousePressed(MouseEvent evt) {
				isDraging = true;
				oldX = evt.getX();
				oldY = evt.getY();
				if (!isDrawing) {
					if (lineDraw)
						paint = new LinePaint(new Point(oldX, oldY), newColor);
					if (rectDraw)
						paint = new RectanglePaint(new Point(oldX, oldY), newColor);
					if (ovalDraw)
						paint = new OvalPaint(new Point(oldX, oldY), newColor);
					if (imageDraw)
						try {
							paint = new ImagePaint(new Point(oldX, oldY), newColor);
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}

			public void mouseReleased(MouseEvent evt) {
				isDraging = false;
				if (oldX == evt.getX() && oldY == evt.getY()) {
					if (isDrawing) {
						paint.setEndPoint(new Point(evt.getX(), evt.getY()));
						lockedPaints.add(paint);
						try {
							oos.writeObject(lockedPaints);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					isDrawing = !isDrawing;
				} else {
					lockedPaints.add(paint);
					try {
						oos.writeObject(lockedPaints);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			public void mouseMoved(MouseEvent evt) {
				if (isDrawing) {
					newX = evt.getX();
					newY = evt.getY();
					paint.setEndPoint(new Point(newX, newY));
					paints.clear();
					paints.add(paint);
					repaint();
				}
			}

			public void mouseDragged(MouseEvent evt) {
				if (isDraging) {
					newX = evt.getX();
					newY = evt.getY();
					paint.setEndPoint(new Point(newX, newY));
					paints.clear();
					paints.add(paint);
					repaint();
				}
			}

			public void mouseEntered(MouseEvent evt) {
				newX = evt.getX();
				newY = evt.getY();
			}

			public void mouseExited(MouseEvent evt) {
				newX = evt.getX();
				newY = evt.getY();
			}
		}
	}

	private class ColorChooser extends JPanel implements ChangeListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JColorChooser tcc;

		public ColorChooser() {
			setLayout(new BorderLayout());
			tcc = new JColorChooser();
			tcc.getSelectionModel().addChangeListener(this);
			tcc.setBorder(BorderFactory.createTitledBorder("Choose Text Color"));
			add(tcc, BorderLayout.CENTER);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			newColor = tcc.getColor();
		}

	}

}
