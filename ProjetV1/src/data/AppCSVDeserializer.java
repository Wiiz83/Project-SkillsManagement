package data;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import csv.CSVConfig;
import csv.CSVDeserializer;
import csv.CSVEntity;
import csv.CSVException;
import csv.CSVLine;
import csv.CSVObjects;
import csv.InvalidCSVException;
import csv.InvalidDataException;
import models.Competence;
import models.CompetenceCode;
import models.CompetenceRequirement;
import models.Employee;
import models.Languages;
import models.Mission;

public class AppCSVDeserializer implements CSVDeserializer {
	SimpleDateFormat	dateformatter	= new SimpleDateFormat("dd/MM/yyyy");
	CSVConfig			csvconfig;
	
	public AppCSVDeserializer(CSVConfig csvconfig) {
		super();
		this.csvconfig = csvconfig;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E extends CSVEntity> E Deserialize(CSVLine line, Class<?> c) throws IOException, CSVException {
		try {
			if (c == Competence.class)
				return (E) Competence(line);
			if (c == Employee.class) {
				return (E) Employee(line);
			}
			if (c == Mission.class) {
				return (E) Mission(line);
			}
			if (c == CompetenceRequirement.class) {
				return (E) CompetenceRequirement(line);
			} else {
				throw new IllegalArgumentException("Object not deserializable");
			}
		} catch (NumberFormatException | ParseException e) {
			throw new InvalidDataException(e);
		}
	}
	
	private CompetenceRequirement CompetenceRequirement(CSVLine line)
			throws ParseException, NumberFormatException, IOException, CSVException {
		CompetenceRequirement cr = new CompetenceRequirement(Integer.parseInt(line.get(1)));
		cr.setCsvID(line.get(0));
		return cr;
	}
	
	private CompetenceCode CompetenceCode(CSVLine line) throws InvalidDataException {
		return new CompetenceCode(line.get(0));
	}
	
	private Competence Competence(CSVLine line) throws InvalidCSVException, InvalidDataException {
		if (line.size() != Languages.size + 1)
			throw new InvalidCSVException();
		CompetenceCode code = CompetenceCode(line);
		line.remove(0);
		return new Competence(code, line);
	}
	
	private Employee Employee(CSVLine line) throws InvalidCSVException, InvalidDataException {
		if (line.size() != 4) {
			throw new InvalidCSVException();
		}
		Employee e;
		try {
			e = new Employee(line.get(0), line.get(1), line.get(2), line.get(3));
		} catch (ParseException e1) {
			throw new InvalidDataException(e1);
		}
		return e;
	}
	
	private Mission Mission(CSVLine line) throws NumberFormatException, ParseException, InvalidDataException {
		Mission m = new Mission(
				line.get(1), dateformatter.parse(line.get(2)), Integer.parseInt(line.get(3)),
				Integer.parseInt(line.get(4))
		);
		m.setCsvID(line.get(0));
		if (line.get(6).equals("true"))
			m.planifier();
		m.setDateFin(dateformatter.parse(line.get(5)));
		return m;
	}
	
}