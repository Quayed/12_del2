package zybo_client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import shared.SocketHandler;

public class TCP_Client {

	private String modifiedSentence;
	private final SocketHandler socketHandler;

	public TCP_Client() throws IOException {
		socketHandler = new SocketHandler("localhost", 8001);
	}

	public void send(String output) throws IOException {
		socketHandler.println(output);
		modifiedSentence = socketHandler.readLine();
		System.out.println("\n" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " - FROM SERVER: " + modifiedSentence);	
	}
}
