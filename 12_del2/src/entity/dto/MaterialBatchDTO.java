package entity.dto;

public class MaterialBatchDTO {
	int mbID; // i omraadet 1-99999999
	int materialID; // i omraadet 1-99999999
	double quantity; // kan vaere negativ

	public MaterialBatchDTO(int mbID, int materialID, double quantity) {
		this.mbID = mbID;
		this.materialID = materialID;
		this.quantity = quantity;
	}

	public int getmbID() {
		return mbID;
	}

	public void setmbID(int mbID) {
		this.mbID = mbID;
	}

	public int getmaterialID() {
		return materialID;
	}

	public void setmaterialID(int materialID) {
		this.materialID = materialID;
	}

	public double getquantity() {
		return quantity;
	}

	public void setquantity(double quantity) {
		this.quantity = quantity;
	}

	public String toString() {
		return mbID + "\t" + materialID + "\t" + quantity;
	}
}
