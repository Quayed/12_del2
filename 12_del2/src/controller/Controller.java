package controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import entity.Data;
import entity.IData;
import entity.dto.FormulaCompDTO;
import entity.dto.MaterialDTO;
import entity.dto.OperatorDTO;

public class Controller {
	
	private void restart() throws RestartException {
		throw new RestartException();
	}

	private IData data = new Data();

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8000;

	private Connector connector;

	private OperatorDTO currentOperator;
	private MaterialDTO material;

	private FormulaCompDTO formulaComp;

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

	private void start() throws IOException {
		// do a command, because the first command do not always work
		connector.println("S");
		System.out.println(connector.readLine());
		boolean running = false;
		do{
			try {
				getOperator();
			
				while(true){
				
					List<FormulaCompDTO> formulaComps = data.getFormulaCompList();
					
					for (FormulaCompDTO formulaComp : formulaComps) {
						this.formulaComp = formulaComp;
						getMaterialBatch();
						weight();
					}
				}
			} catch (RestartException e) {
				running = true;
			}
		} while(running);
	}

	private void getOperator() throws IOException, RestartException {

		int oprID = connector.getAnId("oprNr?");
		OperatorDTO operator = data.getOperator(oprID);

		do {

			while (oprID == 0 || operator == null) {
				oprID = connector.getAnId("Ugyldig, oprNr?");
				operator = data.getOperator(oprID);
			}

		} while (!connector.confirm(operator.getOprName() + "?"));

		currentOperator = operator;

	}

	private void getMaterialBatch() throws IOException, RestartException {
		int materialId;
		MaterialDTO material;
		do {

			materialId = connector.getAnId("material?");

			while (true) {
				material = data.getMaterialBatch(materialId);

				if (materialId == 0 || material == null) {
					materialId = connector
							.getAnId("Ugyldig, material?");
				} else {
					if (formulaComp.getMaterialID() != material.getMaterialID()) {
						materialId = connector.getAnId("Ugyldig, material?");
					} else
						break;
				}
			}

		} while (!connector.confirm("Material name: "+ material.getMaterialName() + "?"));

		this.material = material;

	}

	private void weight() throws IOException, RestartException {
		double
			materialWeight = formulaComp.getNomNetto(),
			tolerance = formulaComp.getTolerance(),
			tare,
			netto,
			gross,
			tare2;
		
		if(!connector.confirm("Placer skål")){
			// Afvist
		}
		tare = connector.tare();
		
		if(!connector.confirm("Læg " + materialWeight + " kg på")){
			// Afvist
		}
		while(true){
			netto = connector.read();
			gross = netto - tare;
			if(Math.abs(gross - materialWeight)/materialWeight > tolerance){
				// Afvejningen er ikke inde for tolerancen
				if(!connector.confirm("Læg " + materialWeight + " kg på")){
					// Afvist
				}
			}
			else{
				break;
			}
		}
		
		if(!connector.confirm("Fjern skål")){
			// Afvist
		}
		tare2 = connector.tare();
		
	
		// #### Ved ikke om det her skal være der ####
		if (tare2 < tolerance) {
			connector.rm20("BRUTTO KONTROL OK");
			connector.getRM20();
		}
				
		data.updateMaterial(material.getMaterialID(), tare, netto, currentOperator.getOprID());

	}
}