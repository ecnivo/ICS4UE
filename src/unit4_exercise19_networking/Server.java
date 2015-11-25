package unit4_exercise19_networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicMenuUI.ChangeHandler;

public class Server {

	protected static final int PORT = 5500;

	private Socket clientSocket;
	private ServerSocket server;

	private ArrayList<UserClient> clients = new ArrayList<UserClient>();

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		System.out.println("Listening for connection on " + PORT);
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				clientSocket = server.accept();
				System.out.println("Connected to "
						+ clientSocket.getRemoteSocketAddress());
				UserClient client = new UserClient(clientSocket);
				clients.add(client);
				Thread clientThread = new Thread(client);
				clientThread.start();
			} catch (IOException e) {
				System.out.println("Failed to accept connection");
				e.printStackTrace();
			}
		}
	}

	private void broadcastMessage(UserClient user, String message)
			throws IOException {
		int length = clients.size();
		for (int i = 0; i < length; i++) {
			clients.get(i).sendMessage(user, message);
		}
	}

	private void newUser(UserClient user) throws IOException {
		broadcastMessage(user, " just joined the chat");
	}

	private void removeUser(UserClient client) {
		int length = clients.size();
		for (int i = 0; i < length; i++) {
			if (clients.get(i) == client) {
				clients.remove(i);
				return;
			}
		}
	}

	class UserClient implements Runnable {

		private Socket user;
		private String message;
		private String userName;

		PrintWriter clientWriter;
		BufferedReader clientReader;

		public UserClient(Socket client) {
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
				if (!userName.matches("[A-Za-z0-9 ]*")
						|| !(userName.length() > 0))
					userName = user.getRemoteSocketAddress().toString();
				Server.this.newUser(this);
			} catch (IOException e1) {
				System.out.println("Failed to contact other "
						+ "users to broadcast new user.");
				e1.printStackTrace();
			}

			while (!user.isClosed()) {
				try {
					message = clientReader.readLine();
				} catch (SocketException e) {
					removeUser(this);
					System.out.println("connection to user lost");
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Receieved message from "
						+ this.getUserName());

				try {
					if (!message.equals("***QUIT***"))
						Server.this.broadcastMessage(this, message);
					else {
						clientWriter.close();
						clientReader.close();
						user.close();
						removeUser(this);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private String getUserName() {
			return userName;
		}

		private void sendMessage(UserClient origin, String message)
				throws IOException {
			clientWriter.println(origin.getUserName() + "\n" + message);
			clientWriter.flush();
			System.out.println("Message {" + message + "} sent from server to "
					+ UserClient.this.getUserName());
		}
	}
}
