package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import model.PaintObject;
import model.PaintsList;
import view.NetPaintGUI;

public class Client implements Runnable, Observer{

	PaintsList paints;
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	
	public Client(){
		paints = new PaintsList();
		paints.addObserver(this);
		new NetPaintGUI();
	}
	
	private void openConnection(){
		try {
			socket = new Socket("localHost", Server.PORT_NUMBER);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			try {
				PaintsList paintFromServer = (PaintsList) ois.readObject();
				paints.addAll(paintFromServer);
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
	
	@Override
	public void run() {
		openConnection();
		
		ServerListener thread1= new ServerListener();
		thread1.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		PaintObject newPaint = (PaintObject) arg;
		try {
			oos.writeObject(newPaint);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class ServerListener extends Thread{
		
		@Override
		public void run(){
			while(true){
				try {
					PaintObject paint = (PaintObject) ois.readObject();
					paints.addFromServer(paint);
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

}
