package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHandler {

	private final DataOutputStream out;
	private final BufferedReader in;
	private Socket socket;

	public SocketHandler(String host, int port) throws UnknownHostException, IOException {
		this(new Socket(host, port));
	}

	public SocketHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.out = new DataOutputStream(socket.getOutputStream());
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void print(String msg) throws IOException {
		out.writeBytes(msg);
	}
	
	public void println(String msg) throws IOException {
		print(msg+"\r\n");
	}

	public String readLine() throws IOException {
		String line;
		while(true){
			line = in.readLine();
			if(line != null)
				return line;
		}
	}

	public final void close() throws IOException {
		socket.close();
	}

}