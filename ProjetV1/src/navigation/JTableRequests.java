package navigation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

import javax.swing.JTable;

import csv.CSVException;
import csv.CSVObjects;
import models.*;

public class JTableRequests {
	
	///////// Requetes Employé
	public static JTable tousLesEmployes() throws CSVException {
		CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class);
		ArrayList<Employee> employes = employes_csv.getAll();
		return ListToJTable.employes(employes);
	}
	
	public static JTable CompetencesEmployeManquantes(String IDEmploye) throws CSVException {
		CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class);
		Employee employe = employes_csv.getByID(IDEmploye);
		ArrayList<Competence> compEmp = employe.getCompetences();
		
		CSVObjects<Competence> comp_csv = new CSVObjects<>(Competence.class);
		ArrayList<Competence> autres = comp_csv.getFiltered(c -> !compEmp.contains(c));
		return ListToJTable.competences(autres);
	}
	
	///////// Requetes Competence
	
	public static JTable toutesLesCompetences() throws CSVException {
		CSVObjects<Competence> competences_csv = new CSVObjects<>(Competence.class);
		ArrayList<Competence> competences = competences_csv.getAll();
		return ListToJTable.competences(competences);
	};
	
	///////// Requetes Mission
	
	public static JTable toutesLesMissions() throws CSVException {
		CSVObjects<Mission> missions_csv = new CSVObjects<>(Mission.class);
		ArrayList<Mission> missions = missions_csv.getAll();
		return ListToJTable.missions(missions);
	}
	
	private static JTable Mission(Predicate<Mission> filtre) throws CSVException {
		CSVObjects<Mission> missions_csv = new CSVObjects<>(Mission.class);
		ArrayList<Mission> missions = missions_csv.getFiltered(filtre);
		return ListToJTable.missions(missions);
	}
	
	public static JTable MissionsAvecStatus(Status status) throws CSVException {
		return Mission(m -> m.getStatus() == status);
	}
	
	public static JTable MissionsduMois() throws CSVException {
		return MissionsIntervalle(Cal.today(), 30);
	}
	
	public static JTable MissionsIntervalle(Date dateDebut, int nbJours) throws CSVException {
		Calendar c = Calendar.getInstance();
		c.setTime(dateDebut);
		c.add(Calendar.DATE, nbJours);
		return Mission(m -> m.getDateDebut().compareTo(dateDebut) > 0 && m.getDateDebut().compareTo(c.getTime()) < 0);
	}
	
	public static JTable MissionsIntervalle(String strDateDebut, int nbJours) throws CSVException, ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDebut = dateformatter.parse(strDateDebut);
		return MissionsIntervalle(dateDebut, nbJours);
	}
	
}
