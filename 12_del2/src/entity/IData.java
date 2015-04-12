package entity;

import entity.dto.FormulaDTO;
import entity.dto.MaterialBatchDTO;
import entity.dto.OperatorDTO;

public interface IData {

	MaterialBatchDTO getMaterialBatch(int materialId);

	OperatorDTO getOperator(int oprID);

	FormulaDTO getFormula(int formulaId);

}
