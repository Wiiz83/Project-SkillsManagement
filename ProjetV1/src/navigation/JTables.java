package navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import csv.CSVEntity;
import gui.GenericTableModel;
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
		String[] headers = { "ID", "Nom", "Durée", "Status" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Mission>(missions, headers) {
			
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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		return table;
	}
	
}
