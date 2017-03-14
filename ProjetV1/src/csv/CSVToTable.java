package csv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import csv.CSVObjects;
import csv.InvalidCSVException;
import csv.InvalidDataException;
import models.*;

public class CSVToTable {
	static SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public static JTable Employes(ArrayList<Employee> employes)
			throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException {
		
		@SuppressWarnings("serial")
		TableModel dataModel = new AbstractTableModel() {
			@Override
			public String getColumnName(int col) {
				String[] headers = { "Nom", "Prénom", "Date d'entrée", "ID", "Compétences" };
				return headers[col];
			}
			
			public int getColumnCount() {
				return 5;
			}
			
			public int getRowCount() {
				return employes.size();
			}
			
			public Object getValueAt(int row, int col) {
				Employee emp = employes.get(row);
				
				switch (col) {
				case 0:
					return emp.getLastName();
				case 1:
					return emp.getName();
				case 2:
					return dateformatter.format(emp.getEntryDate());
				case 3:
					return emp.getID();
				case 4:
					return emp.getCompetences();
				default:
					System.out.println("Employes_JTable access ");
					break;
				}
				
				return emp;
			}
		};
		
		JTable table = new JTable(dataModel);
		table.getColumnModel().getColumn(3).setMinWidth(0);
		table.getColumnModel().getColumn(3).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		
		return table;
	}
	
	public static JTable Employes()
			throws NumberFormatException, IOException, InvalidCSVException, InvalidDataException, ParseException {
		CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class);
		ArrayList<Employee> employes = employes_csv.getAll();
		return Employes(employes);
	}
	
	public static JTable CompetencesEmploye(ArrayList<Competence> list) {
		
		@SuppressWarnings("serial")
		TableModel dataModel = new AbstractTableModel() {
			public String getColumnName(int col) {
				String[] headers = { "Code", "FR", "EN" };
				return headers[col];
			}
			
			public int getColumnCount() {
				return Languages.size + 1;
			}
			
			public int getRowCount() {
				return list.size();
			}
			
			public Object getValueAt(int row, int col) {
				Competence comp = list.get(row);
				
				switch (col) {
				case 0:
					return comp.getCode();
				
				default:
					if (col <= Languages.size)
						return comp.getNames().get(col - 1);
					System.out.println("JTable access ");
					break;
				}
				return comp;
			}
		};
		JTable table = new JTable(dataModel);
		return table;
	}
	
	public static JTable CompetencesEmployeManquantes(String IDEmploye)
			throws NumberFormatException, IOException, InvalidCSVException, InvalidDataException, ParseException {
		CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class);
		Employee employe = employes_csv.getByID(IDEmploye);
		ArrayList<Competence> compEmp = employe.getCompetences();
		
		CSVObjects<Competence> comp_csv = new CSVObjects<>(Competence.class);
		ArrayList<Competence> autres = comp_csv.getFiltered(c -> !compEmp.contains(c));
		return CompetencesEmploye(autres);
	}
	
	public static JTable Competences()
			throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException {
		CSVObjects<Competence> competences_csv = new CSVObjects<>(Competence.class);
		ArrayList<Competence> competences = competences_csv.getAll();
		return CompetencesEmploye(competences);
	};
	
	public static JTable LanguesCompetence(ArrayList<String> list) {
		
		@SuppressWarnings("serial")
		TableModel dataModel = new AbstractTableModel() {
			public String getColumnName(int col) {
				String[] headers = { "FR", "EN" };
				return headers[col];
			}
			
			public int getColumnCount() {
				return Languages.size;
			}
			
			public int getRowCount() {
				return list.size();
			}
			
			public Object getValueAt(int row, int col) {
				String nom = list.get(row);
				
				switch (col) {
				case 0:
					return nom;
				default:
					System.out.println("JTable access ");
					break;
				}
				return nom;
			}
		};
		JTable table = new JTable(dataModel);
		return table;
	}
	
	public static JTable Mission(ArrayList<Mission> missions)
			throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException {
		
		@SuppressWarnings("serial")
		TableModel dataModel = new AbstractTableModel() {
			@Override
			public String getColumnName(int col) {
				String[] headers = { "Code", "Name", "Duration", "Status" };
				return headers[col];
			}
			
			public int getColumnCount() {
				return 3;
			}
			
			public int getRowCount() {
				return missions.size();
			}
			
			public Object getValueAt(int row, int col) {
				Mission mis = missions.get(row);
				switch (col) {
				case 0:
					return mis.getID();
				case 1:
					return mis.getNomM();
				case 2:
					return mis.getDuree();
				case 3:
					return mis.getStatus();
				default:
					System.out.println("JTable access ");
					break;
				}
				return missions;
			}
		};
		JTable table = new JTable(dataModel);
		return table;
	}
	
	public static JTable Mission()
			throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException {
		CSVObjects<Mission> missions_csv = new CSVObjects<>(Mission.class);
		ArrayList<Mission> missions = missions_csv.getAll();
		return Mission(missions);
	}
	
	public static JTable Mission(Predicate<Mission> filtre)
			throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException {
		CSVObjects<Mission> missions_csv = new CSVObjects<>(Mission.class);
		ArrayList<Mission> missions = missions_csv.getFiltered(filtre);
		return Mission(missions);
	}
	
	public static JTable MissionsAvecStatus(Status status)
			throws NumberFormatException, IOException, InvalidCSVException, InvalidDataException, ParseException {
		return Mission(m -> m.getStatus() == status);
	}
	
	public static JTable MissionsduMois()
			throws NumberFormatException, IOException, InvalidCSVException, InvalidDataException, ParseException {
		return MissionsIntervalle(Cal.today(), 30);
	}
	
	public static JTable MissionsIntervalle(Date dateDebut, int nbJours)
			throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(dateDebut);
		c.add(Calendar.DATE, nbJours);
		return Mission(m -> m.getDateDebut().compareTo(dateDebut) > 0 && m.getDateDebut().compareTo(c.getTime()) < 0);
	}
	
	public static JTable MissionsIntervalle(String strDateDebut, int nbJours)
			throws ParseException, NumberFormatException, IOException, InvalidCSVException, InvalidDataException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDebut = dateformatter.parse(strDateDebut);
		return MissionsIntervalle(dateDebut, nbJours);
	}
	
}
