package entity.dto;

public class MaterialDTO {
	int materialID;
	String materialName;
	double quantity;

	public MaterialDTO(int materialID, String materialName, double quantity) {
		this.materialID = materialID;
		this.materialName = materialName;
		this.quantity = quantity;
	}

	public int getMaterialID() {
		return materialID;
	}
	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}
	
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String toString() {
		return "\t" + materialID + "\t" + materialName + "\t" + quantity;
	}
}
