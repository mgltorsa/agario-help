package io.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Connection
 */
public class Connection extends Thread {

	private Client client;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public Connection(Client client) throws Exception {

		this.client = client;
		socket = new Socket(client.getHost(), client.getPort());
		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	}

	private void joinToGame() {
		out.println("join:" + client.getUserName());
	}

	@Override
	public synchronized void run() {

		// REVIEW: To join to the game

		joinToGame();

		while (socket.isConnected()) {

			String data = "";
			try {
				while (in.ready()) {
					data += in.readLine() + "\n";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!data.isEmpty()) {
				client.process(data);
			}

		}

	}

	public void close() {
		try {
			socket.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void write(String message) {
		out.println(message);
	}
}