package controller.validation;

import model.Field;
import model.Garden;

import common.exceptions.ValidationException;

/**
 * Validator for Garden objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>17.02.2018: nicz - Creation</li>
 * </ul>
 */
public class ValidatorGarden extends Validator<Garden> {

	@Override
	public void validateSave(Garden obj) throws ValidationException {
		if (obj == null) {
			onError("Impossible d'enregistrer un jardin indéfini !");
		}
		
		validateName(obj.getName(), obj);
		
		if (obj.getSizeX() < 1 || obj.getSizeY() < 1) {
			onError("Dimensions non valides:\n" + obj.getValue(Field.GARDEN_SIZE));
		}
		
		if (obj.getSizeTile() < 1) {
			onError("La taille des tuiles doit être d'au moins 1cm");
		}
	}

	@Override
	public void validateDelete(Garden obj) throws ValidationException {
		checkDependencies(obj);
	}

}
