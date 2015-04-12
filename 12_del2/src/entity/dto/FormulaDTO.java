package entity.dto;

/**
 * Recept Data Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class FormulaDTO {
	/** Recept nr i omraadet 1-99999999 */
	int formulaID;
	/** formulaName min. 2 max. 20 karakterer */
	String formulaName;

	/** liste af kompenenter i recepten */

	public FormulaDTO(int formulaID, String formulaName) {
		this.formulaID = formulaID;
		this.formulaName = formulaName;
	}

	public int getformulaID() {
		return formulaID;
	}

	public void setformulaID(int formulaID) {
		this.formulaID = formulaID;
	}

	public String getformulaName() {
		return formulaName;
	}

	public void setformulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	public String toString() {
		return formulaID + "\t" + formulaName;
	}
}
