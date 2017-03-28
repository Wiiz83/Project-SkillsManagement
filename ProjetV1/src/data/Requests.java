package data;

import java.util.ArrayList;

import csv.CSVEntity;
import csv.CSVException;
import csv.CSVObjects;

public abstract class Requests<E extends CSVEntity> {
	protected CSVObjects<E>	csvobjects;
	protected Data			data;
	
	Requests(Data data, Class<E> entityClass) throws CSVException {
		this.csvobjects = new CSVObjects<E>(entityClass, data.getCache());
		this.data = data;
	}
	
	public ArrayList<E> tous() throws CSVException {
		return csvobjects.getAll();
	}
	
	public E parID(String ID) throws CSVException {
		return csvobjects.getByID(ID);
	}
	
	public void ajouter(E e) throws CSVException {
		csvobjects.add(e);
	}
	
	public void supprimer(E e) throws CSVException {
		csvobjects.delete(e);
	}
	
	public void modifier(E e) throws CSVException {
		csvobjects.modify(e);
	}
	
}
