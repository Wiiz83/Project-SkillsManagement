package db;


import java.util.ArrayList;

import models.*;

public class CSVDeserializer {

	@SuppressWarnings("unchecked")
	public static <E extends Object> E Deserialize (CSVLine line, Class<?> c) throws InvalidCSVException, InvalidDataException{
		if (c==Competence.class)
			return (E) CSVDeserializer.Competence(line);
		if (c==Employee.class){
			Employee e =  CSVDeserializer.Employee_helper(line);
			return null;
		}
		else
		{
			throw new IllegalArgumentException("Unserializable object");
		}			
	}
	
	private static CompetenceCode CompetenceCode(CSVLine line) throws InvalidDataException{
		return new CompetenceCode(line.get(0));
	}
	private static Competence Competence (CSVLine line) throws InvalidCSVException, InvalidDataException {
		if (line.size()!=Languages.size+1)
			throw new InvalidCSVException();
		CompetenceCode code = CompetenceCode(line);
		line.remove(0);
		return new Competence(code,line);
	}
	
	private static Employee Employee_helper (CSVLine line) throws InvalidCSVException, InvalidDataException {
		if (line.size()!=4){
			throw new InvalidCSVException();
		}
		Employee e = new Employee(line.get(0),line.get(1),line.get(2),line.get(3));	
		return e;
	}	
/*	private static ArrayList<Competence> Employee_Competences (CSVLine line) throws InvalidCSVException{
		if (line.size()<2){
			throw new InvalidCSVException();
		}
		ArrayList<Competence> competences= new ArrayList<Competence>();
		for (String str : line) {
			
		}
		
	}
*/}
