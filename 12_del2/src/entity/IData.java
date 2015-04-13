package entity;

import java.io.IOException;
import java.util.List;

import entity.dto.FormulaCompDTO;
import entity.dto.MaterialDTO;
import entity.dto.OperatorDTO;

public interface IData {

	OperatorDTO getOperator(int oprID);

	MaterialDTO getMaterialBatch(int materialId);

	void updateMaterial(int getmaterialID, double netto, int oprID) throws IOException;

}
