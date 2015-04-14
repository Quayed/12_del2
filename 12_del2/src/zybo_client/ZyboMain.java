package zybo_client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ZyboMain {

	private String answer;
	private final java.util.Scanner in;

	private ZyboMain(String ip) throws IOException {
		in = new java.util.Scanner(System.in);
		while (true) {

			getMenu();
			answer = in.nextLine();

			if (answer.equals("1")) {
				FtpMenu ftp = new FtpMenu(); // FTP CONNECTION
				if (ftp.connectFTP(ip)) {
					ftp.getMenu();
					ftp.getOptions();
				}
			} else if (answer.equals("2")) {
				TcpMenu tcp = new TcpMenu(); // TCP CONNECTION
				if (tcp.connectTCP(ip)) {
					tcp.getTcpMenu();
					tcp.getOptions();
				}
			} else {
				System.out.println("Invalid input");
			}
		}
	}

	private void getMenu() {
		System.out.println("Press '1' for FTP");
		System.out.println("Press '2' for TCPP");
	}

	public static void main(String[] args) throws IOException, InterruptedException, ConnectException, UnknownHostException,
			SocketException {
		if (args.length == 1) {
			new ZyboMain(args[0]);
		} else {
			new ZyboMain("localhost");
		}
	}
}
