package unit4_exercise19_networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {

	String name;
	String serverIP;
	
PrintWriter writer;
BufferedReader reader;

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		serverIP = (String) JOptionPane.showInputDialog(null,
				"What is the server's IP?", "Server IP Entry",
				JOptionPane.QUESTION_MESSAGE, null, null, null);

		try {
			Socket socket = new Socket(serverIP, Server.PORT);
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Failed to establish connection");
			e.printStackTrace();
		}
		
		System.out.println("Connected to server at " + serverIP + " on port " + Server.PORT);

		name = (String) JOptionPane.showInputDialog(null, "What is your name?",
				"Name Entry", JOptionPane.QUESTION_MESSAGE, null, null, null);
	}

}
