package controller.validation;

import common.exceptions.ValidationException;

import model.Association;

/**
 * Validator for Association objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>01.02.2018: nicz - Creation</li>
 * </ul>
 */
public class ValidatorAssociation extends Validator<Association> {

	@Override
	public void validateSave(Association obj) throws ValidationException {
		if (obj == null) {
			onError("Impossible d'enregistrer une association indéfinie !");
		}
		
		if (obj.getPlant1() == null || obj.getPlant2() == null) {
			onError("Les deux plantes doivent être définies : " + obj);
		}
		
		if (obj.getPlant1().getIdx() == obj.getPlant2().getIdx()) {
			onError("Les deux plantes doivent être différentes : " + obj);
		}
		
		if (obj.getPlant1().getIdx() > obj.getPlant2().getIdx()) {
			onError("Les plantes doivent être dans le bon ordre !");
		}
		
		if (obj.getKind() == null) {
			onError("Veuillez choisir un type d'association !");
		}
	}

	@Override
	public void validateDelete(Association obj) throws ValidationException {
		checkDeleteUnsaved(obj);
		checkDependencies(obj);
	}
	

}
