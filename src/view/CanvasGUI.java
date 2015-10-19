package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.*;

public class CanvasGUI extends Observable{
	
	static String lineString = "Line";
	static String rectangleString = "Rectangle";
	static String ovalString = "Oval";
	static String imageString = "Image";

	JPanel canvas;
	JPanel optionMenu;
	PaintObject paint;

	public CanvasGUI() {
		optionMenu = new JPanel();
		optionMenu.setLayout(new GridLayout(1, 0));
		optionMenu.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		
		// Create the radio buttons.
		JRadioButton lineButton = new JRadioButton(lineString);
		lineButton.setMnemonic(KeyEvent.VK_B);
		lineButton.setActionCommand(lineString);
		lineButton.setSelected(true);

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
		lineButton.addActionListener(new LineButtonListener());
		rectangleButton.addActionListener(new RectangleButtonListener());
		ovalButton.addActionListener(new OvalButtonListener());
		imageButton.addActionListener(new ImageButtonListener());

		// The preferred size is hard-coded to be the width of the
		// widest image and the height of the tallest image.
		// A real program would compute this.
		optionMenu.add(lineButton);
		optionMenu.add(rectangleButton);
		optionMenu.add(ovalButton);
		optionMenu.add(imageButton);
	}

	/** Listens to the radio buttons. */
	private class LineButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			paint = new LinePaint();
			setChanged();
			notifyObservers(paint);
		}
	}
	
	private class RectangleButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			paint = new RectanglePaint();
			setChanged();
			notifyObservers(paint);
		}
		
	}
	private class OvalButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			paint = new OvalPaint();
			setChanged();
			notifyObservers(paint);
		}
		
	}
	private class ImageButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = CanvasGUI.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("RadioButtonDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
	
		frame.add(optionMenu, BorderLayout.SOUTH);
		
		JPanel canvas = new EmptyCanvas();
		this.addObserver((Observer) canvas);
		frame.add(canvas,BorderLayout.CENTER);

		// Display the window.
		frame.setSize(500,500);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CanvasGUI netPaint = new CanvasGUI();
				netPaint.createAndShowGUI();
			}
		});
	}
}