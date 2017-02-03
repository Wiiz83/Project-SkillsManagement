package db;
import java.util.ArrayList;

public interface Repository<E> {
	public void Add (E e);		
	public ArrayList<E> GetAll();	
}
