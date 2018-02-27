package controller.validation;

import common.exceptions.ValidationException;

import model.Journal;

/**
 * Validator for Journal objects.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>27.02.2018: nicz - Creation</li>
 * </ul>
 */
public class ValidatorJournal extends Validator<Journal> {

	@Override
	public void validateSave(Journal obj) throws ValidationException {
		if (obj == null) {
			onError("Impossible d'enregistrer un journal indéfini !");
		}
		
		if (obj.getTitle() == null || obj.getTitle().trim().isEmpty()) {
			onError("Une entrée de journal doit avoir une titre !");
		}
		
		if (obj.getDate() == null) {
			onError("Une entrée de journal doit avoir une date !");
		}
	}

	@Override
	public void validateDelete(Journal obj) throws ValidationException {
		checkDependencies(obj);
	}
	

}
