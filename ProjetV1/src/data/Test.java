package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import csv.CSVConfig;
import csv.CSVException;
import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Language;
import models.Mission;

@SuppressWarnings("unused")
public class Test {
	static int			cnt				= 1;
	static SimpleDateFormat	dateformatter	= new SimpleDateFormat("dd/MM/yyyy");;
	public static void main(String[] args)
			throws DataException, InstantiationException, IllegalAccessException, CSVException, ParseException {
		
		CSVConfig config = new AppCSVConfig();
		Data data = new Data(config);
		
		Employee e = data.Employes().parID(1);
		System.out.println(e);
		Competence c = data.Competences().parID("B.3.");
		System.out.println(c);
		c.getNames().set(0, "844");
		data.Competences().modifier(c);
		
		// System.out.println(data.Competences().parID("B.3."));
		// System.out.println(data.Employes().parID(1));
		
		// data.Langues().ajouter(new Language("English", "USA"));
		// System.out.println(data.Competences().tous());
		// System.out.println(data.Competences().tous());
		/*
		 * CSVRequests<CompetenceRequirement> crCSV = new CSVRequests<>(data,
		 * CompetenceRequirement.class);
		 * 
		 * System.out.println(crCSV.tous());
		 * 
		 * System.out.println(data.Competences().tous().size());
		 * System.out.println(data.Competences().manquantesEmploye("4").size());
		 * System.out.println(data.Employes().parID("4").getCompetences().size()
		 * ); System.out.println(data.Employes().tous());
		 * 
		 * Mission mission_test = new Mission("Mission_test",
		 * dateformatter.parse("08/08/2018"), 20, 9);
		 * 
		 * Competence competence1 = data.Competences().parID("B.1."); Competence
		 * competence2 = data.Competences().parID("C.2."); CompetenceRequirement
		 * cr1 = new CompetenceRequirement(competence1, 1);
		 * CompetenceRequirement cr2 = new CompetenceRequirement(competence2,
		 * 2); mission_test.addCompetenceReq(cr1);
		 * 
		 * data.Missions().ajouter(mission_test); String id =
		 * mission_test.csvID();
		 * System.out.println(mission_test.equals(data.Missions().parID(id)));
		 * mission_test.addCompetenceReq(cr2);
		 * data.Missions().modifier(mission_test);
		 * System.out.println(mission_test.equals(data.Missions().parID(id)));
		 * mission_test.affectEmployee(data.Employes().parID("2"));
		 * mission_test.affectEmployee(data.Employes().parID("6"));
		 * mission_test.removeCompetenceReq(competence1);
		 * data.Missions().modifier(mission_test);
		 * System.out.println(mission_test.equals(data.Missions().parID(id)));
		 * System.out.println(mission_test); //
		 * System.out.println(data.Missions().parID(id)); //
		 * System.out.println(data.Missions().tous());
		 */
		ArrayList<Employee> emp = data.Employes().tous();
		
		for (int i = 0; i < 5; i++) {
		// Missions en prépartion
		Mission mission_test = createMission("29/07/2017", data);
		data.Missions().ajouter(mission_test);
		
		// Missions en prépartion , alerte (15 jours)
		mission_test = createMission("29/05/2017", data);
		data.Missions().ajouter(mission_test);
		
		// Mission planifiée
		mission_test = createMission("29/05/2017", data);
		mission_test.affectEmployee(emp.get(0));
		mission_test.affectEmployee(emp.get(1));
		mission_test.affectEmployee(emp.get(2));
		data.Missions().ajouter(mission_test);

		
		// mission en cours
		mission_test = createMission("20/05/2017", data);
		mission_test.affectEmployee(emp.get(4));
		mission_test.affectEmployee(emp.get(5));
		mission_test.affectEmployee(emp.get(6));
		data.Missions().ajouter(mission_test);
		
		// mission en cours et en retard
		mission_test = createMission("20/05/2017", data);
		mission_test.affectEmployee(emp.get(4));
		mission_test.affectEmployee(emp.get(5));
		mission_test.affectEmployee(emp.get(6));
			// mission_test.setDateFinRelle(60);
		data.Missions().ajouter(mission_test);
		
		// mission terminée
		mission_test = createMission("20/05/2016", data);
		mission_test.affectEmployee(emp.get(4));
		mission_test.affectEmployee(emp.get(5));
		mission_test.affectEmployee(emp.get(6));
			// mission_test.setDateFinRelle(60);
			data.Missions().ajouter(mission_test);
		}
	}
	
	private static Mission createMission(String date, Data data) {
		cnt++;
		Mission mission_test = null;
		try {
			mission_test = new Mission("Mission_test_" + cnt, dateformatter.parse(date), 20, 3);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Competence competence1 = null;
		try {
			competence1 = data.Competences().parID("B.1.");
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Competence competence2 = null;
		try {
			competence2 = data.Competences().parID("C.2.");
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CompetenceRequirement cr1 = new CompetenceRequirement(competence1, 1);
		CompetenceRequirement cr2 = new CompetenceRequirement(competence2, 2);
		mission_test.addCompetenceReq(cr1);
		mission_test.addCompetenceReq(cr2);
		return mission_test;
	}
	
}
