package controller.validation;

import model.Plant;

import common.exceptions.ValidationException;

/**
 * Validator for Plant objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>18.01.2018: nicz - Creation</li>
 * </ul>
 */
public class ValidatorPlant extends Validator<Plant> {

	@Override
	public void validateSave(Plant obj) throws ValidationException {
		if (obj == null) {
			onError("Impossible d'enregistrer une plante indéfinie !");
		}
		
		validateName(obj.getName(), obj);
		validateName(obj.getNameLatin(), obj);
		
		if (obj.getFamily() == null) {
			onError("Veuillez choisir une famille !");
		}
		
		if (obj.getSoil() == null) {
			onError("Veuillez choisir un sol !");
		}
		
		if (obj.getKind() == null) {
			onError("Veuillez choisir un type de plante !");
		}
	}

	@Override
	public void validateDelete(Plant obj) throws ValidationException {
		checkDependencies(obj);
		//onError("Impossible d'effacer une plante !");
	}

}
