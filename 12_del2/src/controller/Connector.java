package controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector extends SocketHandler{

	public Connector(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
	}
	
	public Connector(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public void sendData(String msg) throws IOException {
		super.sendData(msg + "\r\n");
	}

	public void rm20(String msg) throws IOException {
		sendData("RM20 8 \""+msg+"\" \"\" \"&3\"");
	}

	public String read() throws IOException {
		sendData("S");
		return getData();
	}

	public void tare() throws IOException {
		sendData("T");
	}

	public void zero() throws IOException {
		sendData("Z");
	}

	public void displayText(String msg) throws IOException {
		sendData("D " + msg);
	}

	public void displayWeight() throws IOException {
		sendData("DW");
	}

	
}