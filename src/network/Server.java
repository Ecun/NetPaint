package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.PaintsList;

public class Server implements Runnable {
	public static int PORT_NUMBER = 4007;
	ServerSocket ssock = null;
	volatile boolean isRunning = true;
	private static List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList<ObjectOutputStream>());
	private PaintsList paints;

	@Override
	public void run() {
		try {
			ssock = new ServerSocket(PORT_NUMBER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		paints = new PaintsList();
		System.out.println("Listening");
		while (isRunning) {
			Socket sock = null;
			ObjectInputStream fromClient = null;
			try {
				sock = ssock.accept();
			} catch (IOException e) {
				cleanUp();
				return;
			}
			try {
				fromClient = new ObjectInputStream(sock.getInputStream());
				ObjectOutputStream toClient = new ObjectOutputStream(sock.getOutputStream());
				clients.add(toClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Connected-----------");
			new Thread(new ClientHandler(clients, fromClient)).start();
		}
	}

	public void stop() throws IOException {
		isRunning = false;
	}

	public void cleanUp() {
		try {
			ssock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class ClientHandler implements Runnable {

		ObjectInputStream ois;
		List<ObjectOutputStream> clients;
		
		ClientHandler(List<ObjectOutputStream> clients, ObjectInputStream ois) {
			this.clients = clients;
			this.ois = ois;
		}

		@Override
		public void run() {
			try {
	
				writePaintListToClient();
				while (true) {
					try {
						paints = (PaintsList) ois.readObject();
						System.out.println("add paints to server");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					writePaintListToClient();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		private void writePaintListToClient(){
			for(ObjectOutputStream toClient: clients){
				try {
					toClient.writeObject(paints);
					System.out.println("write paints to client");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
