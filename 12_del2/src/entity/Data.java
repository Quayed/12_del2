package entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Data implements IData {

	private BufferedReader reader;

	@Override
	public String findMaterial(int productId) {
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
					if(line.startsWith(String.valueOf(productId))){
						productName = line.substring(line.indexOf(",")+2, line.indexOf(",", line.indexOf(",") +1));
						return productName;
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

}
