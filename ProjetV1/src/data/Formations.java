package data;

import models.MissionFormation;

public class Formations extends CSVRequests<MissionFormation> {
	
	public Formations(Data data) throws DataException {
		super(data, MissionFormation.class);
	}
	
}
