package unit4_exercise19_networking;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {

	String name;
	String serverIP;

	String incoming;

	Socket socket;
	PrintWriter writer;
	BufferedReader reader;

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		serverIP = (String) JOptionPane.showInputDialog(null,
				"What is the server's IP?", "Server IP Entry",
				JOptionPane.QUESTION_MESSAGE, null, null, "127.0.0.1");

		try {
			socket = new Socket(serverIP, Server.PORT);
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Failed to establish connection");
			JOptionPane.showConfirmDialog(null, "Failed to connect",
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

		while (!socket.isClosed()) {
			try {
				incoming = reader.readLine();
			} catch (SocketException e) {
				e.printStackTrace();
				JOptionPane.showConfirmDialog(null, "Cannot find server.",
						"Connection Failed", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
				return;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed to receive message.");
				return;
			}
			System.out.println("received message {" + incoming
					+ "} from server");

			frame.getCPanel().addMessage(incoming);
		}
	}

	private void sendMsg(String message) {
		Thread sendThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					writer.println(message);
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
			this.setResizable(true);
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
						sendMsg("***QUIT***");
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

		JTextArea msgPanel;
		JScrollPane msgScroll;
		JTextField entry;

		public ClientPanel() {
			super();
			this.setLayout(new BorderLayout());

			msgPanel = new JTextArea();
			msgPanel.setEditable(false);
			msgPanel.setFocusable(true);
			msgScroll = new JScrollPane(msgPanel);
			this.add(msgScroll, BorderLayout.CENTER);

			entry = new JTextField();
			entry.setFocusable(true);
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
						// TODO do special things depending on different inputs
						Client.this.sendMsg(entry.getText());
						entry.setText("");
					}
				}
			});
			this.add(entry, BorderLayout.SOUTH);
		}

		protected void addMessage(String message) {
			int sepIndex = message.indexOf(Server.SEP_CHAR);
			String userName = message.substring(0, sepIndex);
			message = message.substring(sepIndex + 1, message.length());

			msgPanel.append(userName + "\n\n" + message);
		}
	}
}
