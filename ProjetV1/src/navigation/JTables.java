package navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import gui.GenericTableModel;
import models.*;

/**
 * Permet de convertir une liste d'objets en JTable
 *
 */
public class JTables {
	
	public static JTable Employes(ArrayList<Employee> employes) {
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
		String[] headers = { "Nom", "Prénom", "Date d'entrée", "ID", "Compétences" };
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
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		
		return table;
	}
	
	public static JTable Competences(ArrayList<Competence> competences) {
		
		String[] headers = { "Code", "FR", "EN" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Competence>(competences, headers) {
			
			public Object getValueAt(int row, int col) {
				Competence comp = competences.get(row);
				
				switch (col) {
				case 0:
					return comp.getCode();
				
				default:
					if (col <= Languages.size)
						return comp.getNames().get(col - 1);
					else
						System.out.println("Competences JTable access ");
					break;
				}
				return comp;
			}
		};
		
		JTable table = new JTable(dataModel);
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
		return table;
	}
	
}
