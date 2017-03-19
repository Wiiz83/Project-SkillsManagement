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
public class Employee extends CSVEntity {
	private String					name;
	private String					lastName;
	private Date					entryDate;
	private int						ID;
	private ArrayList<Competence>	Competences;
	
	
	//Constructeur basique 
	public Employee(String name, String lastName, Date entryDate) {
		this.name = name;
		this.lastName = lastName;
		this.entryDate = entryDate;
		this.ID = -1;
		Competences = new ArrayList<Competence>();
	}
	
	
	//Constructeur
	public Employee(String name, String lastName, String entryDate) throws InvalidDataException {
		this.name = name;
		this.lastName = lastName;
		this.ID = -1;
		Competences = new ArrayList<Competence>();
		setEntryDate(entryDate);
	}
	
	
	//Constructeur avec le set de l'ID 
	public Employee(String name, String lastName, String entryDate, String iD) throws InvalidDataException {
		this.name = name;
		this.lastName = lastName;
		setEntryDate(entryDate);
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
	
	public Date getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	//Formatage de l'entryDate avant l'insertion dans l'attribut
	public void setEntryDate(String entryDate) throws InvalidDataException {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			setEntryDate(format.parse(entryDate));
		} catch (ParseException e) {
			throw new InvalidDataException(e);
		}
	}
	
	@Override
	public String toString() {
		return "[name=" + name + ", lastName=" + lastName + ", entryDate=" + entryDate + ", ID=" + ID + ", Competences="
				+ Competences + "]";
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public void setCsvID(String iD) throws InvalidDataException {
		try {
			setID(Integer.parseInt(iD));
		} catch (NumberFormatException e) {
			throw new InvalidDataException(e);
		}
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
