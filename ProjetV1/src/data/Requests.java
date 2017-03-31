package data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import csv.CSVEntity;
import csv.CSVException;
import csv.CSVObjects;

public class Requests<E extends CSVEntity> {
	protected CSVObjects<E>	csvobjects;
	protected Data			data;
	
	public Requests(Data data, Class<E> entityClass) throws CSVException {
		this.csvobjects = new CSVObjects<E>(entityClass, data.getConfig());
		this.data = data;
	}
	
	public ArrayList<E> tous() throws CSVException {
		return csvobjects.GetFiltered(o -> true, (E e1, E e2) -> e1.csvID().compareTo(e2.csvID()));
	}
	
	public E parID(String ID) throws CSVException {
		return csvobjects.getByID(ID);
	}
	
	public ArrayList<E> filtrer(Predicate<E> predicate) throws CSVException {
		return csvobjects.getFiltered(predicate);
	}
	
	public ArrayList<E> filtrerTrier(Predicate<E> predicate, Comparator<E> c) throws CSVException {
		return csvobjects.GetFiltered(predicate, c);
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
