package view;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class RunGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1965445292967640388L;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new RunGUI();
			}
		});
	}
	
	public RunGUI(){
		setSize(400,400);
		setVisible(true);
		setLayout(new GridLayout(4,1,15,15));
		setLocationRelativeTo(null);
		
		add(new JLabel("Server Controller"));
		add(new ServerPanel());
		add(new JLabel("Client Start"));
		add(new ClientPanel());
	}
}
