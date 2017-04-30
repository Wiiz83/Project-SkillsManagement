package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public interface IRequete<E extends Serializable> {
	
	ArrayList<E> tous() throws DataException;
	
	E parID(String ID) throws DataException;
	
	ArrayList<E> filtrerTrier(Predicate<E> predicate, Comparator<E> c) throws DataException;
	
	public ArrayList<E> filtrer(Predicate<E> predicate) throws DataException;
	
	void ajouter(E e) throws DataException;
	
	void supprimer(E e) throws DataException;
	
	void modifier(E e) throws DataException;
	
}