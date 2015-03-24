package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import entity.IData;
import logic.ILogic;
import boundary.IUI;

public class Controller {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 4567;

	@SuppressWarnings("unused")
	private IUI ui;
	@SuppressWarnings("unused")
	private ILogic logic;
	@SuppressWarnings("unused")
	private IData data;

	private Thread readController;
	private Thread writeController;

	public Controller(IUI ui, ILogic logic, IData data) {
		this.ui = ui;
		this.logic = logic;
		this.data = data;

		// asks for connecting to server on startup
		connect(ui, logic);

		// after connecting, start the Read and Write controller threads
		readController = new ReadController(ui, data);
		readController.start();

		writeController = new WriteController(ui, logic);
		writeController.start();
	}

	// once called, this method will ask the user to connect by specifying a relevant address until a connection has been made
	static void connect(IUI ui, ILogic logic) {
		String[] hostAndPort;
		String host;
		int port;
		boolean connectionError;

		do {
			connectionError = false;
			hostAndPort = ui.getHostAndPort(DEFAULT_HOST, DEFAULT_PORT);
			if (hostAndPort[0].equals("exit"))
				System.exit(0);
			host = hostAndPort[0];

			try {
				port = Integer.parseInt(hostAndPort[1]);
				logic.connect(host, port);
			} catch (NumberFormatException e) {
				connectionError = true;
				ui.message("Unknown port");
			} catch (UnknownHostException e) {
				connectionError = true;
				ui.message("Unknown host");
			} catch (IOException e) {
				connectionError = true;
				ui.message("Could't connect");
			}
		} while (connectionError);
	}

}