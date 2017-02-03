package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Employee {
	private String name;
	private String lastName;
	private Date entryDate;
	private int ID;
	private ArrayList<Competence> Competences = new ArrayList<Competence>();
 

	public Employee(String name, String lastName, Date entryDate, int iD) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.entryDate = entryDate;
		ID = iD;
	}
	public Employee(String name, String lastName, String entryDate, int iD) throws InvalidDataException {
		this(name,lastName,(Date)null,iD);
		setEntryDate(entryDate);				
	}
	
	public void addCompetence (Competence c) {
		Competences.add(c);
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

	public void setEntryDate(String entryDate) throws InvalidDataException {
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			setEntryDate (format.parse(entryDate));
		} catch (ParseException e) {
			throw new InvalidDataException(e);
		}
	}

	@Override
	public String toString() {
		return "[name=" + name + ", lastName=" + lastName + ", entryDate=" + entryDate + ", ID=" + ID
				+ ", Competences=" + Competences + "]";
	}
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public void setID(String iD) throws InvalidDataException {
		try {
			setID( Integer.parseInt(iD) );
		}
		catch (NumberFormatException e){
			throw new InvalidDataException(e);
		}
	}


}
