package data;

import models.Language;

public class Langues extends CSVRequests<Language> {
	
	public Langues(Data data) throws DataException {
		super(data, Language.class);
	}
	
}
