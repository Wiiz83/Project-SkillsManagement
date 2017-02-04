package db;
import java.util.ArrayList;

import models.Entity;

public interface Repository<E extends Entity> {
	public void Add (E e);		
	public ArrayList<E> GetAll();	
}
