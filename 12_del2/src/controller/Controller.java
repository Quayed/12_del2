package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

import logic.ILogic;

public class Controller {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8000;
	
	private final String host;
	private final int port;

	private Connector connector;
	
	private int currentOperator;
	private int productId;
	private String productName;
	
	public Controller() {
		this(DEFAULT_HOST, DEFAULT_PORT);
	}
	
	public Controller(String host) {
		this(host, DEFAULT_PORT);
	}

	public Controller(int port) {
		this(DEFAULT_HOST, port);
	}
	
	public Controller(String host, int port) {
		this.host = host;
		this.port = port;
		
		this.connector = new Connector();

		// asks for connecting to server on startup
		connect();
		
		start();
	}
	
	private void start(){
		connector.sendMessage("S");
		connector.getData();
		currentOperator = getOperatorId();
		String[] productArray = getProduct();
		productId = Integer.parseInt(productArray[0]);
		productName = productArray[1];
	}

	// once called, this method will ask the user to connect by specifying a relevant address until a connection has been made
	private void connect() {
		boolean connectionError;

		do {
			connectionError = false;
			try {
				connector.connect(host, port);
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
				connector.readMessage("Ikke en int, indtast nr.");
			} else{
				connector.readMessage("Indtast nr.");
			}
			try{
				System.out.println(connector.getData());
				String gottenData = connector.getData();
				
				operator = Integer.parseInt(gottenData.substring(8, gottenData.length()-1));
				
				System.out.println(operator);
				
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
					connector.readMessage("Ikke en int, indtast raavare nr.");
				} else if(notCorrect){
					connector.readMessage("Raavare findes ik, indtast raavare nr.");
				} else{
					connector.readMessage("Indtast raavare nr.");
				}
				try{
					System.out.println(connector.getData());
					String gottenData = connector.getData();
					productId = Integer.parseInt(gottenData.substring(8, gottenData.length()-1));
					System.out.println(productId);
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
					while(true){
						line = reader.readLine();
						if(line != null){
							notCorrect = true;
							if(line.startsWith(String.valueOf(productId))){
								productName = line.substring(line.indexOf(",")+2, line.indexOf(",", line.indexOf(",") +1));
								System.out.println("Product found: "+productName);
								notCorrect = false;
								break;
							}
						}
						else
							break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			} while(isNotANumber || notCorrect);
			
			connector.readMessage("Er " + productName + " rigtigt? 1/0");
			System.out.println(connector.getData());
			String gottenData = connector.getData();
			System.out.println(gottenData);
			if(gottenData.substring(8, gottenData.length()-1).equals("1")){
				String[] productArray = {String.valueOf(productId), productName};
				return productArray;
			} 
		}
	}
	
}