package csv;

import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;

public class CSVModel {
	
	private String				RELATIVEPATH	= "./fichiers_projet/";
	private CSVMetadataMapper	metadata;
	
	public CSVModel() {
		metadata = new CSVMetadataMapper();
		metadata.AddAssociationOrEntity(Employee.class, null, RELATIVEPATH + "liste_personnel.csv", true, 3);
		metadata.AddAssociationOrEntity(Competence.class, null, RELATIVEPATH + "liste_competences.csv", false, 0);
		metadata.AddAssociationOrEntity(
				CompetenceRequirement.class, null, RELATIVEPATH + "competences_req.csv", true, 0
		);
		metadata.AddAssociationOrEntity(Mission.class, null, RELATIVEPATH + "liste_missions.csv", true, 0);
		
		metadata.AddAssociationOrEntity(
				Employee.class, Competence.class, RELATIVEPATH + "competences_personnel.csv", true, 0
		);
		
		metadata.AddAssociationOrEntity(
				Mission.class, Employee.class, RELATIVEPATH + "missions_personnel.csv", true, 0
		);
		
		metadata.AddAssociationOrEntity(
				Mission.class, CompetenceRequirement.class, RELATIVEPATH + "missions_competences.csv", true, 0
		);
		
	}
	
	public CSVMetadataMapper getMetadata() {
		return metadata;
	}
}
