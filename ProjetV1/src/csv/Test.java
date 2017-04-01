package csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.cache.Caching;

import data.AppCSVDataModel;
import data.AppCSVDeserializer;
import data.AppCSVSerializer;

import javax.cache.Cache.Entry;

import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;

public class Test {
	public static void main(String[] args) throws CSVException, ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		CSVConfig config = null;
		try {
			config = new CSVConfig(
					Caching.getCachingProvider().getCacheManager(), AppCSVDataModel.class, AppCSVDeserializer.class,
					AppCSVSerializer.class
			);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Charger toutes les compétences
		CSVObjects<Competence> competences_csv = new CSVObjects<>(Competence.class, config);
		
		Competence competence1 = competences_csv.getByID("A.1.");
		Competence competence2 = competences_csv.getByID("C.1.");
		System.out.println(competence1);
		for (Entry<String, Competence> entry : config.getCSVCache().getCache(Competence.class))
			System.out.println("key: " + entry.getKey() + " - val: " + entry.getValue());
		// Valeurs cachées
		competences_csv.getAll();
		for (Entry<String, Competence> entry : config.getCSVCache().getCache(Competence.class))
			System.out.println("key: " + entry.getKey() + " - val: " + entry.getValue());
		
		// Ajout d'un employé
		Employee employe_test = new Employee("Employé_test", "_test", dateformatter.parse("11/11/2011"));
		employe_test.addCompetence(competence1);
		employe_test.addCompetence(competence2);
		
		CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class, config);
		employes_csv.add(employe_test);
		
		String genereated_id = employe_test.csvID();
		System.out.println(employes_csv.getByID(genereated_id));
		System.out.println(config.getCSVCache().getCache(Employee.class).get(genereated_id));
		
		// Ajout d'une mission
		Mission mission_test = new Mission("Mission_test", dateformatter.parse("08/08/2018"), 20, 9);
		CompetenceRequirement cr_test = new CompetenceRequirement(competence1, 2);
		mission_test.addCompetenceReq(cr_test);
		mission_test.addCompetenceReq(new CompetenceRequirement(competences_csv.getByID("B.2."), 5));
		mission_test.affectEmployee(employe_test);
		mission_test.affectEmployee(employes_csv.getByID("2"));
		mission_test.affectEmployee(employes_csv.getByID("5"));
		
		// Ajout des compétences requises dans le csv
		ArrayList<CompetenceRequirement> unsaved_objects = mission_test.getCompReq();
		CSVObjects<CompetenceRequirement> compreq_csv = new CSVObjects<>(CompetenceRequirement.class, config);
		compreq_csv.addMany(unsaved_objects);
		
		CSVObjects<Mission> missions_csv = new CSVObjects<>(Mission.class, config);
		missions_csv.add(mission_test);
		
		System.out.println(missions_csv.getByID(mission_test.csvID()));
	}
	
}
