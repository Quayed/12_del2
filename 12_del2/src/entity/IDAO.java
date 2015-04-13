package entity;

import java.io.IOException;

import entity.dto.MaterialDTO;
import entity.dto.OperatorDTO;

public interface IDAO {

	OperatorDTO getOperator(int oprID);

	MaterialDTO getMaterialBatch(int materialId);

	void updateMaterial(int getmaterialID, double netto, int oprID) throws IOException;

}
