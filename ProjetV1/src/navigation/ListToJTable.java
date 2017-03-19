package navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import models.*;

/**
 * Permet de convertir une liste d'objets en JTable
 *
 */
public class ListToJTable {
	static SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public static JTable employes(ArrayList<Employee> employes) {
		
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
	
	public static JTable competences(ArrayList<Competence> list) {
		
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

	public static JTable LanguesCompetence(ArrayList<String> list) {
		
		@SuppressWarnings("serial")
		TableModel dataModel = new AbstractTableModel() {
			public String getColumnName(int col) {
				String[] headers = { "Libellé" };
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
	
	public static JTable missions(ArrayList<Mission> missions) {
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
	
}
