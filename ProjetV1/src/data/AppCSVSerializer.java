package data;

import models.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import csv.*;

/**
  *
 */
public class AppCSVSerializer implements CSVSerializer {
	private DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	
	public AppCSVSerializer() {
		super();
	}
	
	public CSVLine Serialize(Object o) {
		if (o.getClass() == Competence.class)
			return Competence((Competence) o);
		if (o.getClass() == Employee.class) {
			return Employee((Employee) o);
		}
		if (o.getClass() == CompetenceRequirement.class) {
			return CompetenceRequirement((CompetenceRequirement) o);
		}
		if (o.getClass() == Mission.class) {
			return Mission((Mission) o);
		}
		if (o.getClass() == Language.class) {
			return Language((Language) o);
		} else {
			throw new IllegalArgumentException("Object not serializable to CSV");
		}
	}
	
	private CSVLine Language(Language o) {
		CSVLine line = new CSVLine();
		line.add(o.csvID());
		line.add(o.getName());
		line.add(o.getCountry());
		return line;
	}
	
	private CSVLine Mission(Mission o) {
		CSVLine line = new CSVLine();
		line.add(o.csvID());
		line.add(o.getNomM());
		line.add(dateformat.format(o.getDateDebut()));
		line.add(Integer.toString(o.getDuree()));
		line.add(Integer.toString(o.getNbPersReq()));
		line.add(dateformat.format(o.getDateFin()));
		line.add(String.valueOf(o.getForcer_planification()));
		return line;
	}
	
	private CSVLine Competence(Competence o) {
		CSVLine line = new CSVLine();
		line.add(o.getCode().toString());
		line.addAll(o.getNames());
		return line;
	}
	
	private CSVLine Employee(Employee o) {
		CSVLine line = new CSVLine();
		line.add(o.getName());
		line.add(o.getLastName());
		line.add(dateformat.format(o.getEntryDate()));
		line.add(o.csvID());
		return line;
	}
	
	private CSVLine CompetenceRequirement(CompetenceRequirement o) {
		CSVLine line = new CSVLine();
		line.add(o.csvID());
		line.add(Integer.toString(o.getNbRequiredEmployees()));
		return line;
	}
	
}