package data;

import java.util.ArrayList;

import models.Employee;

public class Employes extends CSVRequests<Employee> {
	
	Employes(Data data) throws DataException {
		super(data, Employee.class);
	}
	
	public Employee parID(int ID) throws DataException {
		return parID(Integer.toString(ID));
	}
	
	public ArrayList<Employee> Autres(ArrayList<Employee> autres) throws DataException {
		ArrayList<String> ids = new ArrayList<>();
		for (Employee e : autres)
			ids.add(e.csvID());
		return filtrer(e -> !ids.contains(e.csvID()));
	}
	
}
