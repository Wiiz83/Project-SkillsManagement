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
	
	public ArrayList<Employee> Autres(ArrayList<Employee> autres) {
		return null;
	}
	
}
