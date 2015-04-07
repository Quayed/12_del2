package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	private Connector connector;
	private int productId;
	private String productName;
	public Controller(ILogic logic, IData data) {
		this.logic = logic;
		this.data = data;

		// asks for connecting to server on startup
		connect(logic);
		
		start();
	}
	
	private void start(){
		currentOperator = getOperatorId();
		String[] productArray = getProduct();
		productId = Integer.parseInt(productArray[0]);
		productName = productArray[1];
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
			try{
				operator = Integer.parseInt(connector.getData());
				isNotANumber = false;
			} catch (NumberFormatException e){
				isNotANumber = true;
			}
		}while(isNotANumber);
		return operator;
		// Operator skal måske verificeres (ID slås op)
	}
	
	private String[] getProduct(){
		boolean isNotANumber = false;
		boolean notCorrect = false;
		int productId = 0;
		String productName = "";
		BufferedReader reader = null;
		while(true){
			do {
				if(isNotANumber){
					logic.readMessage("Ikke en int, indtast raavare nr.");
				} else if(notCorrect){
					logic.readMessage("Raavare findes ik, indtast raavare nr.");
				} else{
					logic.readMessage("Indtast raavare nr.");
				}
				try{
					productId = Integer.parseInt(connector.getData());
					isNotANumber = false;
				} catch (NumberFormatException e){
					isNotANumber = true;
					continue;
				}
				
				try {
					reader = new BufferedReader(new FileReader("store.txt"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String line = null;
				try {
					while((line =reader.readLine()) != null){
						notCorrect = true;
						if(line.startsWith(String.valueOf(productId))){
							productName = line.substring(line.indexOf(",")+2, line.indexOf(",", line.indexOf(",") +1));
							notCorrect = false;
							break;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}while(isNotANumber || notCorrect);
			
			logic.readMessage("Er " + productName + " rigtigt? 1/0");
			
			if(connector.getData().equals("1")){
				String[] productArray = {productName, String.valueOf(productId)};
				return productArray;
			} 
		}
	}
	
}