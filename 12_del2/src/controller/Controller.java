package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import entity.Data;
import entity.IData;

public class Controller {

	private IData data = new Data();
	
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8000;

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
		try {
			
			this.connector = new Connector(host, port);
			
			start();
			
		} catch (UnknownHostException e) {
			System.out.println("Server Error");
			
		} catch (IOException e) {
			System.out.println("Server Error");
		}
	}

	private void start() throws IOException{
		connector.sendData("S");
		connector.getData();
		
		getOperatorId();
		getProduct();
		weight();
	}

	private void getOperatorId() throws IOException{
		boolean isNotANumber = false;
		int operator = 0;
		do {
			if(isNotANumber){
				connector.rm20("Ikke en int, indtast nr.");
			} else{
				connector.rm20("Indtast nr.");
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
		
		currentOperator = operator;

		// Operator skal måske verificeres (ID slås op)
	}
	
	private String[] getProduct() throws IOException{
		boolean isNotANumber = false;
		boolean notCorrect = false;
		int productId = 0;
		String productName = "";
		while(true){
			do {
				if(isNotANumber){
					// Ikke en int,  husk det er max 24 karakterer
					connector.rm20("Indtast raavare nr.");
				} else if(notCorrect){
					// Ingen raavare, husk det er max 24 karakterer
					connector.rm20("Indtast raavare nr.");
				} else{
					connector.rm20("Indtast raavare nr.");
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
				
				productName = data.findMaterial(productId);
				if(productName == null){
					notCorrect = true;
				}
				else{
					System.out.println("Product found: "+productName);
					notCorrect = false;
				}
								
			} while(isNotANumber || notCorrect);
			
			connector.rm20(productName + "? 1/0");
			System.out.println(connector.getData());
			String gottenData = connector.getData();
			System.out.println(gottenData);
			if(gottenData.substring(8, gottenData.length()-1).equals("1")){
				String[] productArray = {String.valueOf(productId), productName};
				this.productId = productId;
				this.productName = productName;
				return productArray;
			} 
		}
	}
	
	private void weight() throws IOException{
		connector.rm20("Placer skål");
		connector.getData();
		connector.getData().equals("1");	// verificer kald
		connector.sendData("T");
		
		String tare = connector.getData();
		
		connector.rm20("Læg 1.5 kg på");
		connector.getData();
		connector.getData().equals("1");	// verificer kald
		
		connector.sendData("S");
		
		String netto = connector.getData();

		connector.rm20("Fjern");
		connector.getData();
		connector.getData().equals("1");	// verificer kald
		
		connector.sendData("T");
		
		String tare2 = connector.getData();
	
		// Registrerer minus brutto (inden for en variation på ??)
		
		
		// Udskriv ”BRUTTO KONTROL OK” hvis dette er tilfældet. Ellers ?
		
		
		// Afskriv mængde på lager, og opdatér historik 

	}
}