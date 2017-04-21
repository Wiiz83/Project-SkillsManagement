package models;

import java.util.ArrayList;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

public class Language extends CSVEntity {
	
	private static final long	serialVersionUID	= -3055633002989418620L;
	int							id;
	String						name;
	String						country;
	
	public Language(String name, String country) {
		super();
		this.name = name;
		this.country = country;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCountry() {
		return country;
	}
	
	@Override
	public String csvID() {
		return Integer.toString(id);
	}
	
	@Override
	public void setCsvID(String ID) throws InvalidDataException {
		id = Integer.parseInt(ID);
		
	}
	
	@Override
	public HashMap<Class<? extends CSVEntity>, ArrayList<String>> getReferencedObjectsIDS() {
		return new HashMap<>();
	}
	
	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		
	}
	
	@Override
	public String toString() {
		return "Language [id=" + id + ", name=" + name + ", country=" + country + "]";
	}
}
