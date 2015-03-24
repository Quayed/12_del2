package logic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import entity.IData;

public class Connector extends Thread {

	private DataOutputStream out;
	private BufferedReader in;
	private Socket clientSocket;
	private IData data;

	public Connector(IData data) {
		this.data = data;
	}

	// this thread continuously reads server inputs on the sockets and stores them in a data stack to be handled later
	@Override
	public void run() {
		String input;
		while (true) {
			int sleep;
			sleep = 100;
			try {

				Thread.sleep(sleep);
			} catch (InterruptedException e) {
			}
			try {
				input = in.readLine();
				data.putData(input);
			} catch (IOException e) {
				// System.out.println("Not connected!");
				data.setConnected(false);
			}
		}
	}

	// initialises the socket and in/out reader/stream and starts the socket thread (if not already started)
	public void connect(String host, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(host, port);
		out = new DataOutputStream(clientSocket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		data.setConnected(true);
		if (!isAlive())
			start();
	}

	// sends a message to the server
	public boolean sendMessage(String msg) {
		try {
			out.writeBytes(msg + "\r\n");
		} catch (IOException e) {
			// System.out.println("Not connected!");
			data.setConnected(false);
			return false;
		}
		return true;
	}

}