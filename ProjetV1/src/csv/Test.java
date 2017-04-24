package csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import data.AppCSVConfig;

import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;

public class Test {
	public static void main(String[] args) throws CSVException, ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		CSVConfig config = new AppCSVConfig();
		CSVObjects<Mission> mis_csv = new CSVObjects<Mission>(Mission.class, config);
		mis_csv.add(new Mission("Mission_test", dateformatter.parse("08/08/2018"), 20, 9));
		Mission mission_test = new Mission("Mission_test", dateformatter.parse("08/08/2018"), 20, 9);
		
		CSVObjects<Competence> competences_csv = new CSVObjects<Competence>(Competence.class, config);
		CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class, config);
		
		employes_csv.add(new Employee("f", "qsd", "11/11/2011"));
		employes_csv.add(new Employee("f", "qsd", "11/11/2011"));
		System.out.println(competences_csv.getAll());
		
		System.out.println(competences_csv.getByID("B.3."));
		System.out.println(competences_csv.getByID("B.3."));
		
		System.out.println(employes_csv.getAll());
		System.out.println(employes_csv.getAll());
		System.out.println(employes_csv.getAll());
		System.out.println(competences_csv.getAll());
		System.out.println(competences_csv.getAll());
		System.out.println(competences_csv.getAll());
		System.out.println(competences_csv.getAll());
	}
	
}
