package entity.dto;

public class OperatorDTO {
	int oprID;
	String oprName;

	public OperatorDTO(int oprID, String oprName) {
		this.oprID = oprID;
		this.oprName = oprName;
	}

	public int getOprID() {
		return oprID;
	}
	public void setOprID(int oprID) {
		this.oprID = oprID;
	}

	public String getOprName() {
		return oprName;
	}
	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	public String toString() {
		return oprID + "\t" + oprName;
	}
}
