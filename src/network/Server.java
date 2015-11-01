package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.PaintObject;
import model.PaintsList;

public class Server implements Runnable {
	public static int PORT_NUMBER = 4007;
	ServerSocket ssock = null;
	volatile boolean isRunning = true;
	private static List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList<>());
	private PaintsList paints;

	@Override
	public void run() {
		try {
			ssock = new ServerSocket(PORT_NUMBER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Listening");
		paints = new PaintsList();
		while (isRunning) {
			Socket sock = null;
			try {
				sock = ssock.accept();
			} catch (IOException e) {
				cleanUp();
				return;
			}
			System.out.println("Connected-----------");
			new Thread(new ClientHandler(sock)).start();
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

		Socket client;
		ObjectInputStream fromClient = null;
		ObjectOutputStream toClient = null;
		
		ClientHandler(Socket client) {
			this.client = client;
			clients.add(toClient);
		}

		@Override
		public void run() {
			try {
				fromClient = new ObjectInputStream(client.getInputStream());
				toClient = new ObjectOutputStream(client.getOutputStream());
				toClient.writeObject(paints);
				
				PaintObject paint = null;
				while (true) {
					try {
						paint = (PaintObject) fromClient.readObject();
						paints.add(paint);
						System.out.println("add paints to server");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					writePaintsToClient(paint);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		private void writePaintsToClient(PaintObject paint){
			for(ObjectOutputStream client: clients){
				try {
					client.writeObject(paint);
					System.out.println("write paints to client");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
}
