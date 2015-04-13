package zybo_client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import shared.SocketHandler;

public class FTP_Client {

	FTPHandler ftpHandler;

	public boolean connect(String user, String pass) throws IOException, InterruptedException {
		
		ftpHandler = new FTPHandler("2.108.207.65", 21);

		ftpHandler.readLine();

		ftpHandler.send("USER " + user);

		if (ftpHandler.send("PASS " + pass).contains("230")) {
			return true;
		}
		return false;
	}

	private SocketHandler getPassivSocket() throws IOException {
		// Enabling passive to recieve data:
		String adress = ftpHandler.send("PASV");
		// Append tokens with ',':
		StringTokenizer st = new StringTokenizer(adress, "(,)");
		if (st.countTokens() < 7) {
			SimpleDateFormat date = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss - ");
			throw new IOException(
					date.format(new Date())
							+ "Message received does not follow the regular 7-token syntax (MSG.IP.IP.IP.IP.PORT.PORT");
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
		
		return new SocketHandler(ftpHandler, portNr);
	}

	public String getData(String out) throws IOException {

		SocketHandler socket = getPassivSocket();

		String msg = ftpHandler.send(out);
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

		socket.disconnect();
		ftpHandler.readLine();
		return sb.toString();
	}
}
