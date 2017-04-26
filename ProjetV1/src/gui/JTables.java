package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import csv.CSVEntity;
import models.*;

/**
 * Permet de convertir une liste d'objets en JTable
 *
 */

public class JTables {
	
	@SuppressWarnings("unchecked")
	public static <E extends CSVEntity> JTable getTable(Class<? extends CSVEntity> c, ArrayList<E> data) {
		
		if (c == Competence.class)
			return Competences((ArrayList<Competence>) data);
		if (c == Employee.class) {
			return Employes((ArrayList<Employee>) data);
		}
		if (c == Mission.class) {
			return Missions((ArrayList<Mission>) data);
		}
		if (c == CompetenceRequirement.class) {
			return CompetencesRequises((ArrayList<CompetenceRequirement>) data);
		} else {
			return null;
		}
	}
	
	public static JTable Employes(ArrayList<Employee> employes) {
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
		String[] headers = { "Nom", "Prénom", "Date d'entrée" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Employee>(employes, headers) {
			public Object getValueAt(int row, int col) {
				Employee emp = employes.get(row);
				
				switch (col) {
				case 0:
					return emp.getLastName();
				case 1:
					return emp.getName();
				case 2:
					return dateformatter.format(emp.getEntryDate());
				default:
					System.out.println("Employes_JTable access ");
					break;
				}
				
				return emp;
			}
		};
		
		JTable table = new JTable(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		return table;
	}
	
	public static JTable Competences(ArrayList<Competence> competences) {
		
		String[] headers = { "Code", "FR" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Competence>(competences, headers) {
			
			public Object getValueAt(int row, int col) {
				Competence comp = competences.get(row);
				
				switch (col) {
				case 0:
					return comp.getCode();
				case 1:
					return comp.getNames().get(1);
				default:
					System.out.println("Competences JTable access ");
					break;
				}
				return comp;
			}
		};
		
		JTable table = new JTable(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		return table;
	}
	
	public static JTable CompetencesRequises(ArrayList<CompetenceRequirement> CompReq) {
		String[] headers = { "Code", "FR", "Employés requis" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<CompetenceRequirement>(CompReq, headers) {
			
			public Object getValueAt(int row, int col) {
				CompetenceRequirement cr = CompReq.get(row);
				Competence comp = cr.getCompetence();
				
				switch (col) {
				case 0:
					return comp.getCode();
				case 1:
					return comp.getNames().get(1);
				case 2:
					return cr.getNbRequiredEmployees();
				default:
					System.out.println("CompetenceRequirement JTable access ");
					break;
				}
				return comp;
			}
		};
		
		JTable table = new JTable(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		return table;
	}
	
	public static JTable Missions(ArrayList<Mission> missions) {
		String[] headers = { "Nom", "Durée", "Statut" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Mission>(missions, headers) {
			
			public Object getValueAt(int row, int col) {
				Mission mis = missions.get(row);
				switch (col) {
				case 0:
					return mis.getNomM();
				case 1:
					return mis.getDuree();
				case 2:
					return mis.getStatus();
				default:
					System.out.println("Missions JTable access ");
					break;
				}
				return missions;
			}
		};
		JTable table = new JTable(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		return table;
	}
	
	public static JTable LanguesCompetence(Competence competence, ArrayList<Language> langues) {
		
		String[] headers = { "Pays", "Libellé" };
		@SuppressWarnings("serial")
		DefaultTableModel dataModel = new DefaultTableModel() {
			@Override
			public String getColumnName(int col) {
				return headers[col];
			}
			
			@Override
			public Object getValueAt(int row, int col) {
				
				switch (col) {
				case 0:
					return langues.get(row).getCountry();
				case 1: {
					if (competence == null)
						return "";
					if (competence.getNames().get(row) == null)
						return "";
					return competence.getNames().get(row);
				}
				default:
					System.out.println("LanguesCompetence JTable access ");
					break;
				}
				return null;
			}
			
			@Override
			public int getColumnCount() {
				return 2;
			}
			
			@Override
			public int getRowCount() {
				return langues.size();
			}
		};
		
		JTable table = new JTable(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		
		return table;
	}
	
	public static JTable Alertes(ArrayList<Alerte> Alertes) {
		String[] headers = { "Alerte", "Détails" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Alerte>(Alertes, headers) {
			
			@Override
			public int getColumnCount() {
				return 2;
			}
			
			@Override
			public int getRowCount() {
				return Alertes.size();
			}
			
			@Override
			public Object getValueAt(int row, int col) {
				switch (col) {
				case 0:
					return Alertes.get(row).getType();
				case 1:
					return Alertes.get(row).getDesc();
				default:
					System.out.println("Alertes JTable access ");
					break;
				}
				return null;
			}
			
		};
		JTable table = new JTable(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		return table;
	}
	
	public static JTable Recommendation(ArrayList<Recommendation> Rec) {
		String[] headers = { "Nom", "Durée", "Statut" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Recommendation>(Rec, headers) {
			
			public Object getValueAt(int row, int col) {
				Recommendation rec = Rec.get(row);
				switch (col) {
				case 0:
					return rec.getEmpRec();
				case 1:
					return rec.getRLevel();
				default:
					System.out.println("Missions JTable access ");
					break;
				}
				return Rec;
			}
		};
		JTable table = new JTable(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		return table;
	}
	
}
