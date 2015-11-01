package view;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import network.Server;

public class ServerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1975683331991482686L;
	private JButton startServerButton;
	private JButton terminateServerButton;
	private Server server;
	private Thread thread;

	public ServerPanel() {
		setSize(400, 200);
		setLayout(new GridLayout(1, 2, 15, 15));

		startServerButton = new JButton("Start");
		add(startServerButton);

		terminateServerButton = new JButton("Terminate");
		terminateServerButton.setEnabled(false);
		add(terminateServerButton);
		
		registerListener();
	}

	private void registerListener() {
		startServerButton.addActionListener(new StartServerListener());
		terminateServerButton.addActionListener(new TerminateServerListener());
	}

	private class StartServerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			server = new Server();
			thread = new Thread(server);
			thread.start();
			startServerButton.setEnabled(false);
			terminateServerButton.setEnabled(true);
		}
	}

	private class TerminateServerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			startServerButton.setEnabled(true);
			terminateServerButton.setEnabled(false);
			try {
				server.stop();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			thread.interrupt();
		}
	}
}
