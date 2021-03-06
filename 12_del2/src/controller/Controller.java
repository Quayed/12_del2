package controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import entity.DAO;
import entity.IDAO;
import entity.dto.MaterialDTO;
import entity.dto.OperatorDTO;

public class Controller {
	
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8000;
	
	private IDAO dao = new DAO();
	private Connector connector;

	private OperatorDTO currentOperator;
	private MaterialDTO material;

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
		Scanner in = new Scanner(System.in);
		try {

			this.connector = new Connector(host, port);

			start();

		} catch (UnknownHostException e) {
			System.out.println("Server Error");

		} catch (IOException e) {
			System.out.println("Server Error");
		}
		finally{
			System.out.println("Press any key to exit");
			in.next();
			in.close();
		}
	}

	private void start() throws IOException {
		// do a command, because the first command do not always work
		connector.println("S");
		System.out.println(connector.readLine());
		boolean running = true;
		do {
			try {
				getOperator();
				getMaterialBatch();
				weight();

			} catch (RestartException e) {
				continue;
			}
		} while (running);
	}

	private void getOperator() throws IOException, RestartException {

		int oprID = connector.getAnId("oprNr?");
		OperatorDTO operator = dao.getOperator(oprID);

		do {

			while (oprID == 0 || operator == null) {
				oprID = connector.getAnId("Ugyldig, oprNr?");
				operator = dao.getOperator(oprID);
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
				material = dao.getMaterialBatch(materialId);

				if (materialId == 0 || material == null) {

					materialId = connector.getAnId("Ugyldig, material?");

				} else
					break;

			}

		} while (!connector.confirm("Material name: "
				+ material.getMaterialName() + "?"));

		this.material = material;

	}

	private void weight() throws IOException, RestartException {
		double materialWeight = 1, // Kunne hentes fra en database
		tolerance = 0.1, // kunne hentes fra en database
		tare, netto, gross, tare2;

		if (!connector.confirm("Placer skål")) {
			// Afvist
		}
		tare = connector.tare();

		if (!connector.confirm("Læg " + materialWeight + " kg på")) {
			// Afvist
		}
		while (true) {
			netto = connector.read();
			gross = netto - tare;
			if (Math.abs(gross - materialWeight) / materialWeight > tolerance) {
				// Afvejningen er ikke inde for tolerancen
				if (!connector.confirm("Læg " + materialWeight + " kg på")) {
					// Afvist
				}
			} else {
				break;
			}
		}

		if (!connector.confirm("Fjern skål")) {
			// Afvist
		}
		tare2 = connector.tare();

		// #### Ved ikke om det her skal være der ####
		if (tare2 < tolerance) {
			connector.rm20("BRUTTO KONTROL OK");
			connector.getRM20();
		}

		dao.updateMaterial(material.getMaterialID(), netto,
				currentOperator.getOprID());
	}
}