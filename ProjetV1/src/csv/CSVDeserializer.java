package csv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.*;

public class CSVDeserializer {
	static SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * Crée un objet à partir de sa classe et sa représentation dans un fichier
	 * CSV
	 * 
	 * @param line
	 * @param c
	 * @return
	 * @throws InvalidCSVException
	 * @throws InvalidDataException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <E extends CSVEntity> E Deserialize(CSVLine line, Class<?> c)
			throws InvalidCSVException, InvalidDataException, IOException {
		try {
			if (c == Competence.class)
				return (E) CSVDeserializer.Competence(line);
			if (c == Employee.class) {
				return (E) CSVDeserializer.Employee(line);
			}
			if (c == Mission.class) {
				return (E) CSVDeserializer.Mission(line);
			}
			if (c == CompetenceRequirement.class) {
				return (E) CSVDeserializer.CompetenceRequirement(line);
			} else {
				throw new IllegalArgumentException("Object not deserializable");
			}
		} catch (NumberFormatException | ParseException e) {
			throw new InvalidDataException(e);
		}
	}
	
	private static CompetenceRequirement CompetenceRequirement(CSVLine line)
			throws ParseException, NumberFormatException, InvalidCSVException, InvalidDataException, IOException {
		CSVObjects<Competence> compreq = new CSVObjects<>(Competence.class);
		return new CompetenceRequirement(compreq.getByID(line.get(1)), Integer.parseInt(line.get(0)));
	}
	
	private static CompetenceCode CompetenceCode(CSVLine line) throws InvalidDataException {
		return new CompetenceCode(line.get(0));
	}
	
	private static Competence Competence(CSVLine line) throws InvalidCSVException, InvalidDataException {
		if (line.size() != Languages.size + 1)
			throw new InvalidCSVException();
		CompetenceCode code = CompetenceCode(line);
		line.remove(0);
		return new Competence(code, line);
	}
	
	private static Employee Employee(CSVLine line) throws InvalidCSVException, InvalidDataException {
		if (line.size() != 4) {
			throw new InvalidCSVException();
		}
		Employee e = new Employee(line.get(0), line.get(1), line.get(2), line.get(3));
		return e;
	}
	
	private static Mission Mission(CSVLine line) throws NumberFormatException, ParseException, InvalidDataException {
		Mission m = new Mission(
				line.get(1), dateformatter.parse(line.get(2)), Integer.parseInt(line.get(3)),
				Integer.parseInt(line.get(4))
		);
		m.setCsvID(line.get(0));
		return m;
	}
	
}
