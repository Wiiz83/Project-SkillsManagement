package data;

import csv.CSVException;
import models.Employee;

public class Employes extends Requests<Employee> {
	
	Employes(Data data) throws CSVException {
		super(data, Employee.class);
	}
	
	public Employee parID(int ID) throws CSVException {
		return parID(Integer.toString(ID));
	}
	
}
