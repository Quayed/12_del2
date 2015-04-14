package zybo_client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import shared.SocketHandler;

public class TCPHandler extends SocketHandler {

	public TCPHandler(String host) throws IOException {
		super(host, 8001);
	}

	public void send(String output) throws IOException {
		println(output);
		String modifiedSentence = readLine();
		System.out.println("\n" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " - FROM SERVER: "
				+ modifiedSentence);
	}

}
