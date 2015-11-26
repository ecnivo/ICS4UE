package unit4_exercise19_networking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ContentHandlerFactory;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {

	String name;
	String serverIP;

	String incomingName;
	String incoming;

	Socket socket;
	PrintWriter writer;
	BufferedReader reader;

	private final static Color BACKGROUND_COLOUR = Color.WHITE;
	private final static Color CONTENT_COLOUR = Color.BLACK;
	private final static Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 14);

	private final static String START_MESSAGE = "\nType something to get started!\n\"/help\" brings up the help commands.\n";
	private final static String HELP_KEYWORD = "/help";
	private final static String HELP_TEXT = "Welcome to help.\n/help to list the help\n/rename to rename\n/listusers lists users\n/quit quits\n\n";

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		serverIP = (String) JOptionPane
				.showInputDialog(
						null,
						"What is the server's IP?\nPressing cancel will default to 127.0.0.1",
						"Server IP Entry", JOptionPane.QUESTION_MESSAGE, null,
						null, "127.0.0.1");

		try {
			socket = new Socket(serverIP, Server.PORT);
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Failed to establish connection");
			JOptionPane.showConfirmDialog(null,
					"Failed to connect.\nIs the server running?",
					"Connection Failed", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return;
		}

		System.out.println("Connected to server at " + serverIP + " on port "
				+ Server.PORT);

		name = (String) JOptionPane.showInputDialog(null, "What is your name?",
				"Name Entry", JOptionPane.QUESTION_MESSAGE, null, null, "");

		writer.println(name);
		writer.flush();

		ClientFrame frame = new ClientFrame();
		frame.setSize(360, 500);

		while (socket.isConnected()) {
			try {
				incomingName = reader.readLine();
				incoming = reader.readLine();
			} catch (SocketException e) {
				e.printStackTrace();
				JOptionPane.showConfirmDialog(null,
						"Cannot find server.\nChat client will now terminate.",
						"Connection Failed", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed to receive message.");
				return;
			}
			System.out.println("received message {" + incoming
					+ "} from server " + " from " + incomingName);

			frame.getCPanel().addMessage(incomingName, incoming);
		}
	}

	private void sendMsg(String message) {
		Thread sendThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					writer.println(message);
					writer.flush();
				} catch (Exception e) {
					System.out.println("Error sending message to server");
					e.printStackTrace();
				}
				System.out.println("message {" + message + "} sent to server");
			}
		});
		sendThread.start();
	}

	class ClientFrame extends JFrame {

		private ClientPanel cPanel;

		public ClientFrame() {
			super("Chat Client");
			this.setSize(360, 500);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			cPanel = new ClientPanel();
			this.add(cPanel);
			this.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// nothing
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// nothing
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						sendMsg(Server.QUIT_KEYWORD);
						System.exit(0);
					}
				}
			});
			this.setVisible(true);
			this.pack();
		}

		protected ClientPanel getCPanel() {
			return cPanel;
		}
	}

	class ClientPanel extends JPanel {

		JTextArea msgText;
		JScrollPane msgScroll;
		JTextField entry;

		public ClientPanel() {
			super();
			this.setLayout(new BorderLayout());
			this.setBackground(BACKGROUND_COLOUR);

			msgText = new JTextArea();
			msgText.setEditable(false);
			msgText.setFocusable(true);
			msgText.setBackground(BACKGROUND_COLOUR);
			msgText.setForeground(CONTENT_COLOUR);
			msgText.setFont(CONTENT_FONT);
			msgText.setWrapStyleWord(true);
			msgText.setEditable(false);
			msgText.setLineWrap(true);
			msgText.setFocusable(false);
			msgScroll = new JScrollPane(msgText);
			this.add(msgScroll, BorderLayout.CENTER);

			entry = new JTextField();
			entry.setFocusable(true);
			entry.setFont(CONTENT_FONT);
			entry.setBackground(BACKGROUND_COLOUR);
			entry.setForeground(CONTENT_COLOUR);
			entry.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// nothing
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// nothing
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						String text = entry.getText();
						if (text.equalsIgnoreCase(HELP_KEYWORD)) {
							msgText.append(HELP_TEXT);
							entry.setText("");
						} else if (text
								.equalsIgnoreCase(Server.CHANGE_NAME_KEYWORD)) {
							Client.this.sendMsg(Server.CHANGE_NAME_KEYWORD);
							msgText.append("Enter your new name:\n");
							entry.setText("");
						} else {
							Client.this.sendMsg(text);
							entry.setText("");
							if (text.equals(Server.QUIT_KEYWORD))
								System.exit(0);
						}
					}
				}
			});
			this.add(entry, BorderLayout.SOUTH);

			msgText.append("WELCOME TO THE CHAT ROOM");
			msgText.append(START_MESSAGE);
		}

		protected void addMessage(String userName, String message) {
			msgText.append(userName + "\n" + message + "\n\n");
			msgScroll.getVerticalScrollBar().setValue(
					msgText.getDocument().getLength());
		}
	}
}
