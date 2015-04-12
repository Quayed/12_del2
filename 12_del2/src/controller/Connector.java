package controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import shared.SocketHandler;

public class Connector extends SocketHandler{

	public Connector(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
	}
	
	public Connector(Socket socket) throws IOException {
		super(socket);
	}

	public void rm20(String msg) throws IOException {
		println("RM20 8 \""+msg+"\" \"\" \"&3\"");
	}
	
	@Override
	public String readLine() throws IOException {
		// removes spaces, only because of heartbeats in the simulator
		return super.readLine().trim();
	}

	public String getRM20() throws IOException {
		System.out.println(readLine());
		String msg = readLine();
		System.out.println(msg);
		return msg.substring(8, msg.length()-1);
	}
	
	public double read() throws IOException {
		println("S");
		String msg = readLine();
		System.out.println(msg);
		if(msg.substring(8, 9).equals("-")){
			return -Double.parseDouble(msg.substring(9, msg.length()-3));
		}
		else{
			return Double.parseDouble(msg.substring(9, msg.length()-3));
		}
	}

	public double tare() throws IOException {
		println("T");
		String msg = readLine();
		System.out.println(msg);
		if(msg.substring(8, 9).equals("-")){
			return -Double.parseDouble(msg.substring(9, msg.length()-3));
		}
		else{
			return Double.parseDouble(msg.substring(9, msg.length()-3));
		}
	}

	public void zero() throws IOException {
		println("Z");
	}

	public void displayText(String msg) throws IOException {
		println("D " + msg);
		readLine();
	}

	public void displayWeight() throws IOException {
		println("DW");
	}

	
}