package csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import data.AppCSVConfig;

import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;

@SuppressWarnings("unused")
public class Test {
	public static void main(String[] args) throws CSVException, ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		CSVConfig config = new AppCSVConfig();
		CSVObjects<Mission> mis_csv = new CSVObjects<Mission>(Mission.class, config);
		mis_csv.add(new Mission("Mission_test", dateformatter.parse("08/08/2018"), 20, 9));
		Mission mission_test = new Mission("Mission_test", dateformatter.parse("08/08/2018"), 20, 9);
		
		CSVObjects<Competence> competences_csv = new CSVObjects<Competence>(Competence.class, config);
		CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class, config);
		ArrayList<Competence> list = competences_csv.getAll();
		for (Competence c : list)
			competences_csv.modify(c);
		System.out.println(list);
	}
	
}
