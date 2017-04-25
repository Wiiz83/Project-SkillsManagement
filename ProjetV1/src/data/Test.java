package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import csv.CSVConfig;
import csv.CSVException;
import models.Competence;
import models.CompetenceRequirement;
import models.Language;
import models.Mission;

@SuppressWarnings("unused")
public class Test {
	public static void main(String[] args)
			throws DataException, InstantiationException, IllegalAccessException, CSVException, ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		CSVConfig config = new AppCSVConfig();
		Data data = new Data(config);
		Competence c = data.Competences().parID("A.1.");
		c.getNames().set(0, "xd");
		data.Competences().modifier(c);
		
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
	}
}
