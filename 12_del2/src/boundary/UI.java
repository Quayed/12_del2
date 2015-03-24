package boundary;

import java.util.Scanner;

public class UI implements IUI {

	private Scanner consoleScanner;

	public UI() {
		consoleScanner = new Scanner(System.in);
	}

	@Override
	public String[] getHostAndPort(String defaultHost, int defaultPort) {
		String[] hostAndPort = new String[2];
		int option = getInput("Default host: " + defaultHost + ", default port: " + defaultPort + " other connection?");
		if (option == 2)
			return new String[] { "exit" };
		if (option == 1) {
			hostAndPort[0] = defaultHost;
			hostAndPort[1] = String.valueOf(defaultPort);
		} else {
			hostAndPort[0] = getString("Host:");
			hostAndPort[1] = getString("Port:");
		}

		return hostAndPort;
	}

	@Override
	public void message(String string) {
		System.out.println(string);
	}

	@Override
	public String userInteraction() {

		message("S\t Read\n" + "T\t Tara\n" + "Z\t Zero\n" + "D\t Display text on Scale\n" + "DW\t Display weight on Scale\n" + "RM20 S\t Get string\n" + "RM20 I\t Get int\n" + "Enter command:");

		return getInput().toLowerCase();
	}

	@Override
	public String getDisplayText() {

		return getString("Type what you want to display on the weight");
	}

	private String getInput() {
		return consoleScanner.nextLine();
	}

	private int getInput(String before) {

		System.out.println(before + " Y/N/E (Yes/No/Exit)");

		String input;
		do {
			input = getInput().toLowerCase();
		} while (!(input.equals("y") || input.equals("n") || input.equals("e")));

		if (input.equals("y"))
			return 0;
		if (input.equals("n"))
			return 1;
		return 2;

	}

	private String getString(String before) {
		System.out.print(before + " ");
		return consoleScanner.nextLine();
	}

	@Override
	public void disconnected() {
		System.out.println("You have been disconnected. You may try to reconnect.");
	}

}