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
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8558289675788393543L;
	private int					id					= -1;
	private Competence			competence;
	private int					requiredEmployees;
	
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
		HashMap<Class<? extends CSVEntity>, ArrayList<String>> ids = new HashMap<>();
		ArrayList<String> id = new ArrayList<>();
		id.add(competence.csvID());
		ids.put(Competence.class, id);
		return ids;
	}
	
	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		ArrayList<Competence> competences = castArrayList(hashMap, Competence.class);
		if (competences.size() > 0) {
			assert (competences.size() == 1);
			competence = competences.get(0);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof CompetenceRequirement))
			return false;
		CompetenceRequirement other = (CompetenceRequirement) obj;
		if (competence == null) {
			if (other.competence != null)
				return false;
		} else if (!competence.equals(other.competence))
			return false;
		if (id != other.id)
			return false;
		if (requiredEmployees != other.requiredEmployees)
			return false;
		return true;
	}
}
