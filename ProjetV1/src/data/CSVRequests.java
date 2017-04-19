package data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import csv.CSVEntity;
import csv.CSVException;
import csv.CSVObjects;

public class CSVRequests<E extends CSVEntity> implements IRequete<E> {
	protected CSVObjects<E>	csvobjects;
	protected Data			data;
	
	public CSVRequests(Data data, Class<E> entityClass) throws DataException {
		try {
			this.csvobjects = new CSVObjects<E>(entityClass, data.getCSVConfig());
		} catch (CSVException e) {
			throw new DataException(e);
		}
		this.data = data;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IRequete#tous()
	 */
	@Override
	public ArrayList<E> tous() throws DataException {
		try {
			return csvobjects.getAll();
		} catch (CSVException e) {
			throw new DataException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IRequete#parID(java.lang.String)
	 */
	@Override
	public E parID(String ID) throws DataException {
		try {
			return csvobjects.getByID(ID);
		} catch (CSVException e) {
			throw new DataException(e);
		}
	}
	
	public ArrayList<E> filtrer(Predicate<E> predicate) throws DataException {
		try {
			return csvobjects.getFiltered(predicate);
		} catch (CSVException e) {
			throw new DataException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IRequete#filtrerTrier(java.util.function.Predicate,
	 * java.util.Comparator)
	 */
	@Override
	public ArrayList<E> filtrerTrier(Predicate<E> predicate, Comparator<E> c) throws DataException {
		try {
			return csvobjects.GetFiltered(predicate, c);
		} catch (CSVException e) {
			throw new DataException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IRequete#ajouter(E)
	 */
	@Override
	public void ajouter(E e) throws DataException {
		try {
			csvobjects.add(e);
		} catch (CSVException e1) {
			throw new DataException(e1);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IRequete#supprimer(E)
	 */
	@Override
	public void supprimer(E e) throws DataException {
		try {
			csvobjects.delete(e);
		} catch (CSVException e1) {
			throw new DataException(e1);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IRequete#modifier(E)
	 */
	@Override
	public void modifier(E e) throws DataException {
		try {
			csvobjects.modify(e);
		} catch (CSVException e1) {
			throw new DataException(e1);
		}
	}
	
}
