package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import logic.ILogic;
import entity.IData;

public class Controller {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 4567;
	
	private int currentOperator;
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
		currentOperator = getOperatorId();
		logic.readMessage("Indtast raavare nr.");
		while(!data.hasData());
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

	private int getOperatorId(){
		boolean isNotANumber = false;
		int operator = 0;
		do {
			if(isNotANumber){
				logic.readMessage("Ikke en int, indtast nr.");
			} else{
				logic.readMessage("Indtast nr.");
			}
			//conector.getData();
			
			while(!data.hasData());
			try{
				operator = Integer.parseInt(data.pullData());
			} catch (NumberFormatException e){
				isNotANumber = true;
			}
		}while(isNotANumber);
		return operator;
		// Operator skal måske verificeres (ID slås op)
	}
	
}