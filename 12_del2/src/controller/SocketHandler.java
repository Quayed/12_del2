package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class SocketHandler {

	private final DataOutputStream out;
	private final BufferedReader in;

	public SocketHandler(String host, int port) throws UnknownHostException,
			IOException {
		this(new Socket(host, port));
	}

	public SocketHandler(Socket socket) throws IOException {
		this.out = new DataOutputStream(socket.getOutputStream());
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void sendData(String msg) throws IOException {
		out.writeBytes(msg);
	}

	public String getData() throws IOException {
		return in.readLine();
	}

}