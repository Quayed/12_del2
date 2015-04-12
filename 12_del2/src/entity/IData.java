package entity;

import java.util.List;

import entity.dto.FormulaCompDTO;
import entity.dto.MaterialDTO;
import entity.dto.OperatorDTO;

public interface IData {

	OperatorDTO getOperator(int oprID);

	List<FormulaCompDTO> getFormulaCompList();

	MaterialDTO getMaterialBatch(int materialId);

	void updateMaterial(int getmaterialID, double tare, double netto, int oprID);

}
