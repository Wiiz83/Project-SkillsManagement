package db;

import java.util.ArrayList;

import models.Entity;

public class CSVRepository<E extends Entity> implements Repository<E>{
	private CSVDocument doc;
	
	public CSVRepository(CSVDocument doc) {
		super();
		this.doc = doc;
	}
	
	public   void Add (Entity e){
	//  System.out.println( E.ID() );
	}
	public ArrayList<E> GetAll(){
		return null;
	}
}
