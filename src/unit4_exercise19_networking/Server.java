package unit4_exercise19_networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	protected static final int PORT = 5500;

	private Socket clientSocket;
	private ServerSocket server;

	private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		System.out.println("Listening for connection on " + PORT);
		while (true) {
			try {
				server = new ServerSocket(PORT);
				clientSocket = server.accept();
				System.out.println("Connected to "
						+ clientSocket.getRemoteSocketAddress());
				ClientHandler client = new ClientHandler(clientSocket);
				clients.add(client);
				Thread clientThread = new Thread(client);
				clientThread.start();
			} catch (IOException e) {
				System.out.println("Failed to accept connection");
				e.printStackTrace();
			}
		}
	}

	private void broadcastMessage(ClientHandler user, String message)
			throws IOException {
		int length = clients.size();
		for (int i = 0; i < length; i++) {
			clients.get(i).sendMessage(user, message);
		}
	}

	private void newUser(ClientHandler user) throws IOException {
		broadcastMessage(user, user.getName() + " just joined the chat");
	}

	private void removeUser(ClientHandler client) {
		int length = clients.size();
		for (int i = 0; i < length; i++) {
			if (clients.get(i) == client) {
				clients.remove(i);
				return;
			}
		}
	}

	class ClientHandler implements Runnable {

		private Socket user;
		private String message;
		private String userName;

		PrintWriter clientWriter;
		BufferedReader clientReader;

		public ClientHandler(Socket client) {
			this.user = client;
		}

		@Override
		public void run() {
			try {
				clientWriter = new PrintWriter(user.getOutputStream());
				clientReader = new BufferedReader(new InputStreamReader(
						user.getInputStream()));
			} catch (IOException e) {
				System.out.println("Failed to create writer and/or reader");
				e.printStackTrace();
			}

			try {
				userName = clientReader.readLine();
				if (!userName.matches("[A-Za-z0-9 ]*"))
					userName = user.getRemoteSocketAddress().toString();
				Server.this.newUser(this);
			} catch (IOException e1) {
				System.out.println("Failed to contact other users.");
				e1.printStackTrace();
			}

			while (!user.isClosed()) {
				try {
					message = clientReader.readLine();
					if (!message.equals("*QUIT*"))
						Server.this.broadcastMessage(this, message);
					else {
						clientWriter.close();
						clientReader.close();
						user.close();
						removeUser(this);
					}
				} catch (IOException e) {
					System.out.println("Failed to send message to " + userName);
					e.printStackTrace();
				}
			}
		}

		private String getName() {
			return userName;
		}

		private String getMessage() {
			return message;
		}

		protected void sendMessage(ClientHandler origin, String message)
				throws IOException {
			clientWriter.println(origin.getName() + "\n" + message);
			clientWriter.flush();
		}
	}
}
