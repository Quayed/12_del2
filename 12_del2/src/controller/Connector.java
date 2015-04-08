package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {

	private DataOutputStream out;
	private BufferedReader in;
	private Socket clientSocket;
	private boolean connected;

	// initialises the socket and in/out reader/stream and starts the socket thread (if not already started)
	public void connect(String host, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(host, port);
		out = new DataOutputStream(clientSocket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		connected = true;

	}

	// sends a message to the server
	public boolean sendMessage(String msg) {
		try {
			out.writeBytes(msg + "\r\n");
		} catch (IOException e) {
			connected = false;
			return false;
		}
		return true;
	}

	// this method reads server inputs on the sockets and return when there is one
	public String getData() {
		String input;
		while(true){
			try {
				input = in.readLine();
				return input;
			} catch (IOException e) {
				connected = false;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void rm20(String msg) {
		sendMessage("RM20 8 \""+msg+"\" \"\" \"&3\"");
	}

	public boolean isConnected() {
		return connected;
	}

}