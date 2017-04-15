package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import csv.CSVConfig;
import csv.CSVException;
import csv.CSVObjects;
import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;

public class Test {
	public static void main(String[] args)
			throws DataException, InstantiationException, IllegalAccessException, CSVException, ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		CSVConfig config = AppCSVConfig.getInstance();
		Data data = new Data(config);
		System.out.println(data.Competences().tous().size());
		System.out.println(data.Competences().manquantesEmploye("4").size());
		System.out.println(data.Employes().parID("4").getCompetences().size());
		System.out.println(data.Employes().tous());
		
		Mission mission_test = new Mission("Mission_test", dateformatter.parse("08/08/2018"), 20, 9);
		
		Competence competence1 = data.Competences().parID("A.1.");
		Competence competence2 = data.Competences().parID("A.2.");
		CompetenceRequirement cr1 = new CompetenceRequirement(competence1, 1);
		CompetenceRequirement cr2 = new CompetenceRequirement(competence2, 2);
		mission_test.addCompetenceReq(cr1);
		
		data.Missions().ajouter(mission_test);
		String id = mission_test.csvID();
		System.out.println(mission_test.equals(data.Missions().parID(id)));
		mission_test.addCompetenceReq(cr2);
		data.Missions().modifier(mission_test);
		System.out.println(mission_test.equals(data.Missions().parID(id)));
		
		mission_test.removeCompetenceReq(competence1);
		data.Missions().modifier(mission_test);
		System.out.println(mission_test.equals(data.Missions().parID(id)));
		
	}
}
