package data;

import csv.CSVConfig;
import csv.CSVException;
import models.Employee;

public class Test {
	public static void main(String[] args)
			throws DataException, InstantiationException, IllegalAccessException, CSVException {
		
		CSVConfig config = AppCSVConfig.getInstance();
		Data data = new Data(config);
		System.out.println(data.Competences().tous().size());
		System.out.println(data.Competences().manquantesEmploye("4").size());
		System.out.println(data.Employes().parID("4").getCompetences().size());
		System.out.println(data.Employes().tous());
		Employee emp = data.Employes().parID("4");
		System.out.println(emp);
		emp.setLastName("test_modif52");
		
		data.Employes().modifier(emp);
		System.out.println(data.Employes().parID(4));
		
	}
}
