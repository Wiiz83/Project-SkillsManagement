package models;

import java.util.ArrayList;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

/**
 * Représente une compétence et le nombre d'employés nécessaires pour cette
 * compétence dans une mission
 */
public class CompetenceRequirement extends CSVEntity {
	private int id = -1;
	private Competence competence;
	private int requiredEmployees;

	/**
	 * @param competence,
	 *            la compétence en question
	 * @param requiredEmployees,
	 *            le nombre d'employés nécéssaires pour cette compétence
	 */
	public CompetenceRequirement(Competence competence, int requiredEmployees) {
		super();
		this.competence = competence;
		this.requiredEmployees = requiredEmployees;
	}

	public Competence getCompetence() {
		return competence;
	}

	public int getNbRequiredEmployees() {
		return requiredEmployees;
	}

	@Override
	public String csvID() {
		return Integer.toString(id);
	}

	@Override
	public void setCsvID(String iD) throws InvalidDataException {
		try {
			id = (Integer.parseInt(iD));
		} catch (NumberFormatException e) {
			throw new InvalidDataException(e);
		}
	}

	@Override
	public String toString() {
		return "CompetenceRequirement [id=" + id + ", competence=" + competence + ", requiredEmployees="
				+ requiredEmployees + "]";
	}

	@Override
	public HashMap<Class<? extends CSVEntity>, ArrayList<String>> getReferencedObjectsIDS() {
		assert (false);
		return null;
	}

	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		assert (false);
	}
}
