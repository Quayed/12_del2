package entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import entity.dto.FormulaCompDTO;
import entity.dto.MaterialDTO;
import entity.dto.OperatorDTO;

public class Data implements IData {

	private BufferedReader reader;

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
						productName = line.substring(line.indexOf(",")+2, line.indexOf(",", line.indexOf(",") +1));
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
	public List<FormulaCompDTO> getFormulaCompList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMaterial(int getmaterialID, double tare, double netto, int oprID) {
		// TODO Auto-generated method stub
		
	}

}
