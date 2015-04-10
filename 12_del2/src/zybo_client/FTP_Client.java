package zybo_client;

import java.util.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

import controller.SocketHandler;

public class FTP_Client {

	private FTPHandler ftpHandler;

	public String send(String in) throws IOException {
		ftpHandler.println(in);
		return ftpHandler.readLine();
	}

	private String getAnswer() throws IOException {
		return ftpHandler.readLine();
	}

	public boolean connect(String ip, String user, String pass)
			throws IOException, InterruptedException {
		ftpHandler = new FTPHandler(ip, 21);

		ftpHandler.readLine();

		ftpHandler.println("USER " + user);
		ftpHandler.readLine();

		if (send("PASS " + pass).contains("230")) {
			return true;
		}
		return false;
	}

	private Socket getData() throws IOException {
		// Enabling passive to recieve data:
		String adress = send("PASV");
		// Append tokens with ',':
		StringTokenizer st = new StringTokenizer(adress, "(,)");
		if (st.countTokens() < 7) {
			SimpleDateFormat sdataSocket = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss - ");
			throw new IOException(
					sdataSocket.format(new Date())
							+ "Message receved does not follow the regular 7-token syntax (MSG.IP.IP.IP.IP.PORT.PORT");
		}
		// Saving the first five tokens (Message + ip-adress):
		for (int i = 0; i < 5; i++) {
			st.nextToken();
		}
		// Parsing the last two tokens as integers to calculate port number, by
		// multiplying the sixth byte by 256 and adding the seventh byte (256 *
		// token6 + token7):
		int portNr = 256 * Integer.parseInt(st.nextToken())
				+ Integer.parseInt(st.nextToken());
		return new Socket(ftpHandler.getInetAddress(), portNr);
	}

	public void sendData(String out, String data) throws IOException {
		Socket dataSocket = FTP_Client.this.getData();
		PrintStream dataOut = new PrintStream(dataSocket.getOutputStream());
		send(out);
		dataOut.print(data);
		dataOut.close();
		dataSocket.close();
		getAnswer();
	}

	public String getData(String out) throws IOException {
		
		SocketHandler socket = new SocketHandler(getData());
		
		String msg = send(out);
		if (msg.startsWith("550")) {
			return "File not found";
		} else if (msg.startsWith("250")) {
			return "File deleted";
		}
		
		StringBuilder sb = new StringBuilder();
		
		String input = socket.readLine();
		while (input != null) {
			sb.append(input).append("\n");
			input = socket.readLine();
		}
		
		socket.close();
		getAnswer();
		return sb.toString();
	}
}
