package csv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import models.*;

/**
 * Crée une ligne CSV à partir des attributs d'un objet
 *
 */
public class CSVSerializer {
	private static DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static CSVLine Serialize(Object o) {
		if (o.getClass() == Competence.class)
			return CSVSerializer.Competence((Competence) o);
		if (o.getClass() == Employee.class) {
			return CSVSerializer.Employee((Employee) o);
		}
		if (o.getClass() == CompetenceRequirement.class) {
			return CSVSerializer.CompetenceRequirement((CompetenceRequirement) o);
		}
		if (o.getClass() == Mission.class) {
			return CSVSerializer.Mission((Mission) o);
		} else {
			throw new IllegalArgumentException("Object not serializable to CSV");
		}
	}
	
	private static CSVLine Mission(Mission o) {
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
	
	private static CSVLine Competence(Competence o) {
		CSVLine line = new CSVLine();
		line.add(o.getCode().toString());
		line.addAll(o.getNames());
		return line;
	}
	
	private static CSVLine Employee(Employee o) {
		CSVLine line = new CSVLine();
		line.add(o.getName());
		line.add(o.getLastName());
		line.add(dateformat.format(o.getEntryDate()));
		line.add(o.csvID());
		return line;
	}
	
	private static CSVLine CompetenceRequirement(CompetenceRequirement o) {
		CSVLine line = new CSVLine();
		line.add(o.csvID());
		line.add(o.getCompetence().csvID());
		line.add(Integer.toString(o.getNbRequiredEmployees()));
		return line;
	}
	
}
