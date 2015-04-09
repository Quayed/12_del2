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

	public String getRM20() throws IOException {
		getData();
		String msg = getData();
		return msg.substring(8, msg.length()-1);
	}
	
	public double read() throws IOException {
		sendData("S");
		String msg = getData();
		if(msg.substring(8, 9).equals("-")){
			return -Double.parseDouble(msg.substring(9, msg.length()-3));
		}
		else{
			return Double.parseDouble(msg.substring(9, msg.length()-3));
		}
	}

	public double tare() throws IOException {
		sendData("T");
		String msg = getData();
		System.out.println(msg);
		if(msg.substring(8, 9).equals("-")){
			return -Double.parseDouble(msg.substring(9, msg.length()-3));
		}
		else{
			return Double.parseDouble(msg.substring(9, msg.length()-3));
		}
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