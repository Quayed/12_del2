package zybo_client;

import java.io.IOException;

import shared.SocketHandler;

public class TCP_Client {

	private String modifiedSentence;
	private SocketHandler socketHandler;

	public TCP_Client() throws IOException {
		socketHandler = new SocketHandler("localhost", 8001);
	}

	public void send(String output) throws IOException {
		socketHandler.println(output);
		modifiedSentence = socketHandler.readLine();
		System.out.println("FROM SERVER: " + modifiedSentence);
	
	}
}
