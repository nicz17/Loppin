package controller.validation;

import common.exceptions.ValidationException;

import model.Soil;

/**
 * Validator for Soil objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>15.01.2018: nicz - Creation</li>
 * </ul>
 */
public class ValidatorSoil extends Validator<Soil> {

	@Override
	public void validateSave(Soil obj) throws ValidationException {
		if (obj == null) {
			onError("Impossible d'enregistrer un sol indéfini !");
		}
		
		validateName(obj.getName(), obj);
	}

	@Override
	public void validateDelete(Soil obj) throws ValidationException {
		checkDependencies(obj);
		//onError("Impossible d'effacer un sol !");
	}
	

}
