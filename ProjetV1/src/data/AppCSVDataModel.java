package data;

import csv.CSVModel;
import models.*;

public class AppCSVDataModel extends CSVModel {
	
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
		Metadata().AddAssociationOrEntity(
				CompetenceRequirement.class, Competence.class, RELATIVEPATH + "compreq_competences.csv", true, 0
		);
		
		Metadata().AddAssociationOrEntity(Language.class, null, RELATIVEPATH + "langues.csv", true, 0);
		Metadata().AddAssociationOrEntity(MissionFormation.class, null, RELATIVEPATH + "liste_formations.csv", true, 0);
		Metadata().AddAssociationOrEntity(
				MissionFormation.class, Competence.class, RELATIVEPATH + "formations_comp.csv", true, 0
		);
		Metadata().AddAssociationOrEntity(
				MissionFormation.class, Employee.class, RELATIVEPATH + "formations_personnel.csv", true, 0
		);
	}
}
