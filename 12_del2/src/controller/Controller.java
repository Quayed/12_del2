package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import entity.Data;
import entity.IData;
import entity.dto.FormulaCompDTO;
import entity.dto.FormulaDTO;
import entity.dto.MaterialBatchDTO;
import entity.dto.OperatorDTO;

public class Controller {

	public class RestartException extends Exception {

	}
	
	private void restart() throws RestartException{
		throw new RestartException();
	}

	private IData data = new Data();
	
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8000;

	private Connector connector;
	
	private OperatorDTO currentOperator;
	private MaterialBatchDTO materialBatch;

	private FormulaDTO formula;
	
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
		// do a command, because the first command do not always work
		connector.println("S");
		System.out.println(connector.readLine());
		
		getOperator();
		
		getFormula();
		
		getMaterialBatch();
		weight();
	}

	private void getOperator() throws IOException{

		int oprID = connector.getAnId("oprNr?");
		OperatorDTO operator = data.getOperator(oprID);
		
		do{
		
			while(oprID == 0 || operator == null){
				oprID = connector.getAnId("Ugyldig, oprNr?");
				operator = data.getOperator(oprID);
			}
		
		} while(!connector.confirm(operator + "?"));
		
		currentOperator = operator;
		
	}

	private void getFormula() {
		this.formula = data.getFormula(1);
	}
	
	private void getMaterialBatch() throws IOException{
		int materialBatchId;
		MaterialBatchDTO materialBatch;
		FormulaCompDTO formulaComp = null;
		do{
			
			materialBatchId = connector.getAnId("materialBatch?");
			materialBatch = data.getMaterialBatch(materialBatchId);
			
			while(true){
				if(materialBatchId == 0 || materialBatch == null){
					materialBatchId = connector.getAnId("Ugyldig, materialBatch?");
					materialBatch = data.getMaterialBatch(materialBatchId);
				}
				else{
					if(formulaComp == null){
						materialBatchId = connector.getAnId("Ugyldig, materialBatch?");
						materialBatch = data.getMaterialBatch(materialBatchId);
					}
					else
						break;	
				}
			}
			
		} while(!connector.confirm("Material id: "+materialBatch.getmaterialID() + "?"));
		
		this.materialBatch = materialBatch;
		
	}
	
	private void weight() throws IOException{
		double materialWeight = 1.5;
		double tolerance = 0.1;
		
		connector.rm20("Placer skål");
		connector.getRM20().equals("1");	// verificer kald
				
		double tare = connector.tare();
		System.out.println("tare: "+tare);
		
		connector.rm20("Læg "+materialWeight+" kg på");
		connector.getRM20().equals("1");	// verificer kald
				
		double netto = connector.read();
		double brutto = (netto-tare);
		
		if(Math.abs(brutto-materialWeight) < tolerance){
			// Det går skide godt
		}
		
		System.out.println("netto: "+netto);

		connector.rm20("Fjern");
		connector.getRM20().equals("1");	// verificer kald
				
		double tare2 = connector.tare();
		System.out.println("tare: "+tare2);

		if(tare2 < tolerance){
			connector.rm20("BRUTTO KONTROL OK");
			connector.getRM20();
		}
		
		// Afskriv mængde på lager, og opdatér historik 

	}
}