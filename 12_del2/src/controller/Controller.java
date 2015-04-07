package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import logic.ILogic;
import entity.IData;

public class Controller {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8000;

	private ILogic logic;
	private IData data;

	public Controller(ILogic logic, IData data) {
		this.logic = logic;
		this.data = data;

		// asks for connecting to server on startup
		connect(logic);
		
		start();
	}
	
	private void start(){
		logic.readMessage("Indtast nr.");
		while(!data.hasData())
			data.pullData();
		
		logic.readMessage("Indtast raavare nr.");
		while(!data.hasData())
			data.pullData();
	}

	// once called, this method will ask the user to connect by specifying a relevant address until a connection has been made
	static void connect(ILogic logic) {
		boolean connectionError;

		do {
			connectionError = false;
			try {
				logic.connect(DEFAULT_HOST, DEFAULT_PORT);
			} catch (UnknownHostException e) {
				connectionError = true;
				
			} catch (IOException e) {
				connectionError = true;;
			}
		} while (connectionError);
	}

}