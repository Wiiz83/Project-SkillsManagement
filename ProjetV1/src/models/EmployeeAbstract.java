package models;

import java.util.ArrayList;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

public abstract class EmployeeAbstract extends CSVEntity {
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 2745605530862607613L;
	protected String				name;
	protected String				lastName;
	protected int					ID;
	protected ArrayList<Competence>	Competences;
	
	public void setCsvID(String iD) throws InvalidDataException {
		try {
			setID(Integer.parseInt(iD));
		} catch (NumberFormatException e) {
			throw new InvalidDataException(e);
		}
	}
	
	public ArrayList<Competence> getCompetences() {
		return Competences;
	}
	
	public void addCompetence(Competence c) {
		Competences.add(c);
	}
	
	public void addCompetences(ArrayList<Competence> list) {
		Competences.addAll(list);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public String csvID() {
		return Integer.toString(ID);
	}
	
	public HashMap<Class<? extends CSVEntity>, ArrayList<String>> getReferencedObjectsIDS() {
		HashMap<Class<? extends CSVEntity>, ArrayList<String>> IDS = new HashMap<>();
		IDS.put(Competence.class, getIDS(Competences));
		return IDS;
	}
	
	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		Competences = castArrayList(hashMap, Competence.class);
	}
}
