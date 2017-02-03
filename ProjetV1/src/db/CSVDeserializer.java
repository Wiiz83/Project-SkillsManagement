package db;


import models.*;

public class CSVDeserializer {
	private static CompetenceCode CompetenceCode(CSVLine line) throws InvalidDataException{
		return new CompetenceCode(line.get(0));
	}
	public static Competence Competence (CSVLine line) throws InvalidCSVException, InvalidDataException {
		if (line.size()!=Languages.size+1)
			throw new InvalidCSVException();
		CompetenceCode code = CompetenceCode(line);
		line.remove(0);
		return new Competence(code,line);
	}
}
