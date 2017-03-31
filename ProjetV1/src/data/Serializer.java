package data;

import models.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import csv.*;

/**
 * Crée une ligne CSV à partir des attributs d'un objet
 *
 */
public class Serializer extends CSVSerializer {
	private DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	
	public Serializer() {
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
		} else {
			throw new IllegalArgumentException("Object not serializable to CSV");
		}
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
		line.add(o.getCompetence().csvID());
		line.add(Integer.toString(o.getNbRequiredEmployees()));
		return line;
	}
	
}
