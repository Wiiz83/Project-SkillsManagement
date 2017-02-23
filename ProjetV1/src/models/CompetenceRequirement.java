<<<<<<< HEAD
package models;

import java.util.ArrayList;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

public class CompetenceRequirement extends CSVEntity {
	private int			id	= -1;
	private Competence	competence;
	private int			requiredEmployees;
	
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
	
	public String csvID() {
		return Integer.toString(id);
	}
	
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
	
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		assert (false);
	}
}
=======
package models;

import java.util.ArrayList;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

public class CompetenceRequirement extends CSVEntity {
	private int			id	= -1;
	private Competence	competence;
	private int			requiredEmployees;
	
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
	
	public String csvID() {
		return Integer.toString(id);
	}
	
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
	
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		assert (false);
	}
}
>>>>>>> refs/remotes/origin/v0
