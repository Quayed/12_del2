package zybo_client;

import java.io.IOException;
import java.net.UnknownHostException;

import shared.SocketHandler;

public class FTPHandler extends SocketHandler {

	public FTPHandler(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
	}

	@Override
	public String readLine() throws IOException {
		while (true) {
			String input = super.readLine();
			if (input.length() >= 3 && input.charAt(3) != '-'
					&& Character.isDigit(input.charAt(0))
					&& Character.isDigit(input.charAt(1))
					&& Character.isDigit(input.charAt(2))) {
				return input;
			}
		}
	}

	public String send(String in) throws IOException {
		println(in);
		return readLine();
	}

}
