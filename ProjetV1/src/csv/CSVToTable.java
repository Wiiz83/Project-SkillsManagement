package csv;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import csv.CSVObjects;
import csv.InvalidCSVException;
import csv.InvalidDataException;
import models.*;

public class CSVToTable {
	static SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public static JTable Employes()
			throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException {
		CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class);
		ArrayList<Employee> employes = employes_csv.getAll();
		
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
					System.out.println("JTable access ");
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
	
	public static JTable MissionsEncours()
			throws NumberFormatException, IOException, InvalidCSVException, InvalidDataException, ParseException {
		CSVObjects<Mission> missions_csv = new CSVObjects<>(Mission.class);
		ArrayList<Mission> missions = missions_csv.GetFiltered(m -> m.getStatus() == Status.EN_COURS);
		return Mission(missions);
	}
	
	public static JTable MissionsIntervalle(String strDateDebut, int nbJours)
			throws NumberFormatException, IOException, InvalidCSVException, InvalidDataException, ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDebut = dateformatter.parse(strDateDebut);
		Calendar c = Calendar.getInstance();
		c.setTime(dateDebut);
		c.add(Calendar.DATE, nbJours);
		CSVObjects<Mission> missions_csv = new CSVObjects<>(Mission.class);
		
		ArrayList<Mission> missions = missions_csv.GetFiltered(
				m -> m.getDateDebut().compareTo(dateDebut) > 0 && m.getDateDebut().compareTo(c.getTime()) < 0
		);
		return Mission(missions);
	}
	
	public static JTable Competences(ArrayList<Competence> competence)
			throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException {
		CSVObjects<Competence> competences_csv = new CSVObjects<>(Competence.class);
		
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
				return competence.size();
			}
			
			public Object getValueAt(int row, int col) {
				Competence comp = competence.get(row);
				
				switch (col) {
				case 0:
					return comp.getCode();
				case 1:
					return comp.getNames();
				default:
					System.out.println("JTable access ");
					break;
				}
				return comp;
			}
		};
		
		JTable table = new JTable(dataModel);
		
		for (int i = 2; i < dataModel.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMinWidth(0);
			table.getColumnModel().getColumn(i).setMaxWidth(0);
		}
		
		return table;
	}
	
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
	
}
