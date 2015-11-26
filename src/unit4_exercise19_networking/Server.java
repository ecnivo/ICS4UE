package unit4_exercise19_networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicMenuUI.ChangeHandler;

import com.sun.org.apache.xpath.internal.compiler.Keywords;

public class Server {

	protected static final int PORT = 5500;

	private Socket clientSocket;
	private ServerSocket server;

	protected final static String QUIT_KEYWORD = "/quit";
	protected final static String CHANGE_NAME_KEYWORD = "/rename";
	protected final static String LIST_KEYWORD = "/listusers";

	private ArrayList<UserClient> clients = new ArrayList<UserClient>();

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		System.out.println("Listening for connection on " + PORT);
		try {
			server = new ServerSocket(PORT);
		} catch (BindException e) {
			System.err.println("Server already running.");
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
				System.err.println("Failed to accept connection");
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

	private void broadcastNewUser(UserClient user) throws IOException {
		broadcastMessage(user, " just joined the chat");
	}

	private void broadcastChangeName(UserClient user, String formerName)
			throws IOException {
		broadcastMessage(user, " changed their name from " + formerName);
	}

	private void broadcastQuit(UserClient user) throws IOException {
		broadcastMessage(user, "has left the chat room");
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
			this.userName = client.getRemoteSocketAddress().toString();
		}

		@Override
		public void run() {
			try {
				clientWriter = new PrintWriter(user.getOutputStream());
				clientReader = new BufferedReader(new InputStreamReader(
						user.getInputStream()));
			} catch (IOException e) {
				System.err.println("Failed to create writer and/or reader");
			}

			changeUserName(true);

			while (user.isConnected()) {
				try {
					message = clientReader.readLine();
				} catch (SocketException e) {
					removeUser(this);
					System.err.println("Connection to user lost");
				} catch (IOException e) {
					System.err
							.println("Something went wrong and I'm too lazy to find out what it is.");
				}

				System.out.println("Receieved message {" + message + "} from "
						+ this.getUserName());

				try {
					if (message.equalsIgnoreCase(QUIT_KEYWORD)) {
						clientWriter.close();
						clientReader.close();
						user.close();
						removeUser(this);
						broadcastQuit(this);
						break;
					} else if (message.equalsIgnoreCase(LIST_KEYWORD)) {
						String listOfUsers = "List of users:\n";
						for (int i = clients.size() - 1; i >= 0; i--) {
							listOfUsers += clients.get(i).getUserName() + "\n";
						}
						clientWriter.println(listOfUsers);
						clientWriter.flush();
					} else if (message.equalsIgnoreCase(CHANGE_NAME_KEYWORD)) {
						changeUserName(false);
					} else {
						Server.this.broadcastMessage(this, message);
					}
				} catch (IOException e) {
					System.err
							.println("Something else went wrong and I'm still lazy.");
				}
			}
			return;
		}

		private String getUserName() {
			return userName;
		}

		private void sendMessage(UserClient origin, String message)
				throws IOException {
			clientWriter.println(origin.getUserName());
			clientWriter.println(message);
			clientWriter.flush();
			System.out.println("Message {" + message + "} sent from server to "
					+ UserClient.this.getUserName());
		}

		private void changeUserName(boolean newUser) {
			String formerName = userName;
			String tempName;
			boolean valid = true;
			try {
				tempName = clientReader.readLine().trim();
				if (!tempName.matches("[A-Za-z0-9 ]*")) {
					sendMessage(this,
							"SERVER SAYS: Invalid username. Cannot contain special characters.");
					valid = false;
				}
				if (tempName.length() < 1) {
					sendMessage(this,
							"SERVER SAYS: Invalid username. Too short.");
					valid = false;
				}
				for (int i = clients.size() - 1; i >= 0 && valid; i--) {
					if (clients.get(i).getUserName().equalsIgnoreCase(tempName)) {
						valid = false;
						sendMessage(this,
								"SERVER SAYS: Invalid username. Username already in use..");
					}
				}

				if (valid)
					userName = tempName;
				else
					userName = user.getRemoteSocketAddress().toString();

				if (newUser)
					Server.this.broadcastNewUser(this);
				else
					Server.this.broadcastChangeName(this, formerName);
			} catch (IOException e1) {
				System.out.println("Failed to contact other "
						+ "users to broadcast new user.");
				e1.printStackTrace();
			}
		}

		public String toString() {
			return userName;
		}
	}
}
