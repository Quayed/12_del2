package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.dto.FormulaCompDTO;
import entity.dto.MaterialDTO;
import entity.dto.OperatorDTO;

public class Data implements IData {

	private BufferedReader reader;
	private BufferedWriter writer;
	
	@Override
	public OperatorDTO getOperator(int oprID) {
		return new OperatorDTO(oprID, "Test Operatør");
	}
	
	@Override
	public MaterialDTO getMaterialBatch(int materialBatchId) {
		String productName = null;
		
		try {
			reader = new BufferedReader(new FileReader("store.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line;
		try {
			while(true){
				line = reader.readLine();
				if(line != null){
					if(line.startsWith(String.valueOf(materialBatchId))){
						String[] segments = line.split(",");
						productName = segments[1];
						return new MaterialDTO(materialBatchId, productName, 1);
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
		return null;
	}

	@Override
	public void updateMaterial(int getMaterialID, double netto, int oprID) throws IOException {
		reader = new BufferedReader(new FileReader("store.txt"));
		
		double materialLeft = 0;
		String line;
		ArrayList<String> file= new ArrayList<String>();
		while((line = reader.readLine()) != null){
			file.add(line);
		}
		reader.close();
		
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("store.txt"), "utf-8"));
		for(String singleLine : file){
			if(singleLine.startsWith(String.valueOf(1))){
				String[] segments = singleLine.split(",");
				materialLeft = Double.parseDouble(segments[2]) - 2;
				segments[2] = String.valueOf(materialLeft);
				singleLine = segments[0] + "," + segments[1] + "," + segments[2];
			}
			writer.write(singleLine);
			writer.newLine();
			
		}
		writer.close();
		
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String currentDate = String.valueOf(format.format(new Date()));
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log.txt"), "utf-8"));
		writer.append(currentDate + "," + String.valueOf(oprID) + "," + String.valueOf(getMaterialID) + "," + String.valueOf(netto) + "," + String.valueOf(materialLeft));
	}

}
