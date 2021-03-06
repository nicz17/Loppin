package controller.validation;

import java.util.Vector;

import common.base.Logger;
import common.exceptions.ValidationException;
import controller.Controller;

import model.DataObject;

/**
 * Abstract superclass for validators.
 * 
 * <p>Provides methods to validate save and delete, 
 * and throw validation exceptions.
 * 
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>15.01.2018: nicz - Creation</li>
 * <li>25.01.2018: nicz - Added method to check for dependencies</li>
 * <li>15.02.2018: nicz - Improved deletion validation</li>
 * </ul>
 */
public abstract class Validator<T extends DataObject> {
	
	private static Logger log = new Logger("Validator");
	
	/**
	 * Validates the specified object before saving.
	 * 
	 * @param obj  the database object to save
	 * @throws ValidationException  if it is invalid to save object
	 */
	public abstract void validateSave(T obj) throws ValidationException;
	
	/**
	 * Validates the specified object before deleting.
	 * 
	 * @param obj  the database object to delete
	 * @throws ValidationException  if it is invalid to delete object
	 */
	public abstract void validateDelete(T obj) throws ValidationException;
	
	/**
	 * Checks that the specified name follows some rules.
	 * Names shouldn't be empty, start or end with spaces, etc.
	 * @param name  the name to validate
	 * @param obj   the object the name belongs to
	 * @throws ValidationException  if name is invalid
	 */
	protected void validateName(String name, T obj) throws ValidationException {
		if (name == null || name.trim().isEmpty()) {
			onError(obj.toString() + " :\nLe nom ne doit pas être vide.");
		}
		
		if (name.startsWith(" ") || name.endsWith(" ")) {
			onError(obj.toString() + " :\nLe nom ne doit pas commencer ou finir par des espaces.");
		}
	}
	
	/**
	 * Checks that the object to delete has been saved to database.
	 * @param obj  the object to delete
	 * @throws ValidationException  if object is unsaved
	 */
	protected void checkDeleteUnsaved(T obj) throws ValidationException {
		if (obj.isUnsaved()) {
			String sError = "Impossible d'effacer\n" + obj.getName() + "\n" +
					"car il n'a pas été sauvegardé.";
			onError(sError);
		}
	}
	
	/**
	 * Checks if the specified object has dependencies in database.
	 * If there are dependencies, throws a validation exception with 
	 * the names of these dependencies.
	 * Used for checking if the object can be deleted.
	 * @param obj  the object to delete
	 * @throws ValidationException
	 */
	protected void checkDependencies(T obj) throws ValidationException {
		Vector<DataObject> vecDeps = Controller.getInstance().getDependencies(obj);
		if (vecDeps != null && !vecDeps.isEmpty()) {
			String sError = "Impossible d'effacer " + obj.getName() + "\n" +
					"car il est utilisé par les objets suivants:\n\n";
			
			// TODO (nicz, 01.2018) don't display more than 6 items (display number of skipped items)
			for (DataObject dep : vecDeps) {
				sError += dep.getName() + "\n";
			}
			onError(sError);
		}
	}

	/**
	 * Writes an error message to log and throws a {@link ValidationException}
	 * with the specified error message.
	 * 
	 * @param msg  the error message to log and throw
	 * @throws ValidationException  always
	 */
	protected void onError(String msg) throws ValidationException {
		log.error(msg);
		throw new ValidationException(msg);
	}
}
