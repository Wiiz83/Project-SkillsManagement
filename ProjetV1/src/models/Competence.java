package models;

import java.util.ArrayList;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

/**
 * Représente une compétence
 */
public class Competence extends CSVEntity {
	private CompetenceCode code;
	private ArrayList<String> Names;

	/**
	 * @param code,
	 *            les codes compétences
	 * @param names,
	 *            les libellés d'une compétence dans plusieurs langues
	 */
	public Competence(CompetenceCode code, ArrayList<String> names) {
		super();
		this.code = code;
		Names = names;
		setAutoGeneratedID(false);
	}

	public CompetenceCode getCode() {
		return code;
	}

	public void setCode(CompetenceCode code) {
		this.code = code;
	}

	public void setCsvID(String ID) throws InvalidDataException {
		this.code = new CompetenceCode(ID);
	}

	public ArrayList<String> getNames() {
		return Names;
	}

	public void setNames(ArrayList<String> names) {
		Names = names;
	}

	public String toString() {
		return "[code=" + code + ", Names=" + Names + "]";
	}

	public String csvID() {
		return code.toString();
	}

	public HashMap<Class<? extends CSVEntity>, ArrayList<String>> getReferencedObjectsIDS() {
		assert (false);
		return null;
	}

	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		assert (false);
	}
}
