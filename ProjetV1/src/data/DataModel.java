package data;

import csv.CSVModel;
import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;

public class DataModel extends CSVModel {
	
	@Override
	public void defineModel() {
		String RELATIVEPATH = "./ressources/fichiers/";
		
		Metadata().AddAssociationOrEntity(Employee.class, null, RELATIVEPATH + "liste_personnel.csv", true, 3);
		Metadata().AddAssociationOrEntity(Competence.class, null, RELATIVEPATH + "liste_competences.csv", false, 0);
		Metadata().AddAssociationOrEntity(
				CompetenceRequirement.class, null, RELATIVEPATH + "competences_req.csv", true, 0
		);
		Metadata().AddAssociationOrEntity(Mission.class, null, RELATIVEPATH + "liste_missions.csv", true, 0);
		Metadata().AddAssociationOrEntity(
				Employee.class, Competence.class, RELATIVEPATH + "competences_personnel.csv", true, 0
		);
		Metadata().AddAssociationOrEntity(
				Mission.class, Employee.class, RELATIVEPATH + "missions_personnel.csv", true, 0
		);
		Metadata().AddAssociationOrEntity(
				Mission.class, CompetenceRequirement.class, RELATIVEPATH + "missions_competences.csv", true, 0
		);
	}
}
