package entity.dto;

public class FormulaCompDTO {
	int formulaID; // auto genereres fra 1..n
	int materialID; // i omraadet 1-99999999
	double nomNetto; // skal vaere positiv og passende stor
	double tolerance; // skal vaere positiv og passende stor

	public FormulaCompDTO(int formulaID, int materialID, double nomNetto, double tolerance) {
		this.formulaID = formulaID;
		this.materialID = materialID;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}

	public int getformulaID() {
		return formulaID;
	}

	public void setformulaID(int formulaID) {
		this.formulaID = formulaID;
	}

	public int getmaterialID() {
		return materialID;
	}

	public void setmaterialID(int materialID) {
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
		return formulaID + "\t" + materialID + "\t" + nomNetto + "\t" + tolerance;
	}
}
