package models;

import java.util.ArrayList;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

public abstract class Personne extends CSVEntity {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2745605530862607613L;
	protected String			name;
	protected String			lastName;
	protected int				ID;
	
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
	
	@Override
	public String csvID() {
		return Integer.toString(ID);
	}
	
	@Override
	public void setCsvID(String iD) throws InvalidDataException {
		try {
			setID(Integer.parseInt(iD));
		} catch (NumberFormatException e) {
			throw new InvalidDataException(e);
		}
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
