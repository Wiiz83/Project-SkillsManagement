package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

/**
 * Représente un employé
 *
 */
public class Employee extends Personne {
	
	/**
	 * 
	 */
	private static final long		serialVersionUID	= -1356873803668900103L;
	private Date					entryDate;
	protected ArrayList<Competence>	Competences;
	
	// Constructeur basique
	public Employee(String name, String lastName, Date entryDate) {
		this.name = name;
		this.lastName = lastName;
		this.entryDate = entryDate;
		this.ID = -1;
		Competences = new ArrayList<Competence>();
	}
	
	// Constructeur
	public Employee(String name, String lastName, String entryDate) throws ParseException {
		this.name = name;
		this.lastName = lastName;
		this.ID = -1;
		Competences = new ArrayList<Competence>();
		setEntryDate(entryDate, "dd/MM/yyyy");
	}
	
	// Constructeur avec le set de l'ID
	public Employee(String name, String lastName, String entryDate, String iD)
			throws ParseException, InvalidDataException {
		this.name = name;
		this.lastName = lastName;
		setEntryDate(entryDate, "dd/MM/yyyy");
		setCsvID(iD);
		Competences = new ArrayList<Competence>();
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
	
	public Date getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	// Formatage de l'entryDate avant l'insertion dans l'attribut
	public void setEntryDate(String entryDate, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		setEntryDate(format.parse(entryDate));
		
	}
	
	@Override
	public String toString() {
		return "[name=" + name + ", lastName=" + lastName + ", entryDate=" + entryDate + ", ID=" + ID + ", Competences="
				+ Competences + "]";
	}
	
	@Override
	public HashMap<Class<? extends CSVEntity>, ArrayList<String>> getReferencedObjectsIDS() {
		HashMap<Class<? extends CSVEntity>, ArrayList<String>> IDS = new HashMap<>();
		IDS.put(Competence.class, getIDS(Competences));
		return IDS;
	}
	
	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		Competences = castArrayList(hashMap, Competence.class);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Employee))
			return false;
		Employee other = (Employee) obj;
		if (Competences == null) {
			if (other.Competences != null)
				return false;
		} else if (!Competences.equals(other.Competences))
			return false;
		if (entryDate == null) {
			if (other.entryDate != null)
				return false;
		} else if (!entryDate.equals(other.entryDate))
			return false;
		return true;
	}
}
