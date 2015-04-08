package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Queue;

public class Connector extends Thread {

	private DataOutputStream out;
	private BufferedReader in;
	private Socket clientSocket;
	private boolean connected;
	private Queue<String> dataBuffer = new ArrayDeque<String>();

	// this thread continuously reads server inputs on the sockets and stores them in a data stack to be handled later
	@Override
	public void run() {
		String input;
		int sleep = 100;
		
		while (true) {
			while(true){
				try {
					input = in.readLine();
					dataBuffer.add(input);
					break;
				} catch (IOException e) {
					connected = false;
				}
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// initialises the socket and in/out reader/stream and starts the socket thread (if not already started)
	public void connect(String host, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(host, port);
		out = new DataOutputStream(clientSocket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		connected = true;
		
		if (!isAlive())
			start();
	}

	// sends a message to the server
	public boolean sendMessage(String msg) {
		try {
			out.writeBytes(msg + "\r\n");
		} catch (IOException e) {
			// System.out.println("Not connected!");
			connected = false;
			return false;
		}
		return true;
	}

	public String getData() {
		while(dataBuffer.isEmpty()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return dataBuffer.poll();
	}

	public void readMessage(String msg) {
		sendMessage("RM20 8 \""+msg+"\" \"\" \"&3\"");
	}

	public boolean isConnected() {
		return connected;
	}

}