package entity.dto;

public class FormulaCompDTO {
	int materialID;
	double nomNetto;
	double tolerance;

	public FormulaCompDTO(int materialID, double nomNetto, double tolerance) {
		this.materialID = materialID;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}

	public int getMaterialID() {
		return materialID;
	}
	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}

	public double getNomNetto() {
		return nomNetto;
	}
	public void setNomNetto(double nomNetto) {
		this.nomNetto = nomNetto;
	}

	public double getTolerance() {
		return tolerance;
	}
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public String toString() {
		return materialID + "\t" + nomNetto + "\t" + tolerance;
	}
}
