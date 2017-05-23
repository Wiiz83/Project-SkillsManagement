package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import models.*;

/**
 * Permet de convertir une liste d'objets en JTable
 */
public class JTables {
	
	
	/**
	 * @param employes la liste des employés
	 * @return la jtable
	 */
	public static JTable Employes(ArrayList<Employee> employes) {
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
		String[] headers = { "Nom", "Prénom", "Date d'entrée" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Employee>(employes, headers) {
			
			  public boolean isCellEditable(int rowIndex, int mColIndex) {
			        return false;
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
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		return table;
	}
	
	/**
	 * @param competences la liste des compétences
	 * @return la jtable
	 */
	public static JTable Competences(ArrayList<Competence> competences) {
		
		String[] headers = { "Code", "FR" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Competence>(competences, headers) {
			
			  public boolean isCellEditable(int rowIndex, int mColIndex) {
			        return false;
			  }
			
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
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(225);
		return table;
	}
	
	/**
	 * @param CompReq la liste des compétences requises
	 * @return la jtable
	 */
	public static JTable CompetencesRequises(ArrayList<CompetenceRequirement> CompReq) {
		String[] headers = { "Code", "FR", "Employés requis" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<CompetenceRequirement>(CompReq, headers) {
			
			  public boolean isCellEditable(int rowIndex, int mColIndex) {
			        return false;
			  }
			
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
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		return table;
	}
	
	/**
	 * @param missions la liste de missions
	 * @return la jtable
	 */
	public static <E extends MissionAbstract> JTable Missions(ArrayList<E> missions) {
		String[] headers = { "Nom", "Date de début", "Statut" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<E>(missions, headers) {
			
			  public boolean isCellEditable(int rowIndex, int mColIndex) {
			        return false;
			  }
			
			public Object getValueAt(int row, int col) {
				E mis = missions.get(row);
				switch (col) {
				case 0:
					return mis.getNomM();
				case 1:
					return mis.getDateDebutToString();
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
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		return table;
	}
	
	

	
	/**
	 * @param competence la compétence 
	 * @param langues la liste des libellés de la compétence
	 * @return la jtable
	 */
	public static JTable LanguesCompetence(Competence competence, ArrayList<Language> langues) {
		
		String[] headers = { "Pays", "Libellé" };
		@SuppressWarnings("serial")
		TableModel dataModel = new DefaultTableModel() {
			
			  public boolean isCellEditable(int rowIndex, int mColIndex) {
			        return false;
			  }
			
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
		table.getRowSorter().toggleSortOrder(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);
		return table;
	}
	
	/**
	 * @param Alertes la liste des alertes
	 * @return la jtable
	 */
	public static JTable Alertes(ArrayList<Alerte> Alertes) {
		String[] headers = { "Type d'alerte", "Nom de mission", "Date de début", "Mission" };
		@SuppressWarnings("serial")
		TableModel dataModel = new GenericTableModel<Alerte>(Alertes, headers) {
			
			  public boolean isCellEditable(int rowIndex, int mColIndex) {
			        return false;
			  }
			
			@Override
			public int getColumnCount() {
				return 4;
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
					return Alertes.get(row).getNom();
				case 2:
					return Alertes.get(row).getDateDebut();
				case 3:
					return Alertes.get(row).getMission();
				default:
					System.out.println("Alertes JTable access ");
					break;
				}
				return null;
			}
			
		};
		JTable table = new JTable(dataModel);
		table.getColumnModel().getColumn(3).setMinWidth(0);
		table.getColumnModel().getColumn(3).setMaxWidth(0);
		table.getColumnModel().getColumn(3).setWidth(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		
		return table;
	}
	
	/**
	 * @param recom la liste des recommandations
	 * @return la jtable
	 */
	public static JTable Recommendation(Recommendation recom) {
		
		String[] headers = { "Nom", "Prénom", "Recommandation", "Employé"};
		@SuppressWarnings("serial")
		DefaultTableModel dataModel = new DefaultTableModel() {
			
			  public boolean isCellEditable(int rowIndex, int mColIndex) {
			        return false;
			  }
			
			@Override
			public String getColumnName(int col) {
				return headers[col];
			}
			
			@Override
			public Object getValueAt(int row, int col) {
				
				switch (col) {
				case 0:
					return recom.getEmpRec().get(row).getLastName();
				case 1: 
					return recom.getEmpRec().get(row).getName();
				case 2: 
					return recom.getLevel(recom.getEmpRec().get(row).getID())+ " %";
				case 3: 
					return recom.getEmpRec().get(row);
				default:
					System.out.println("LanguesCompetence JTable access ");
					break;
				}
				return null;
			}
			
			@Override
			public int getColumnCount() {
				return 4;
			}
			
			@Override
			public int getRowCount() {
				return recom.getEmpRec().size();
			}
		};
		
		JTable table = new JTable(dataModel);
		table.getColumnModel().getColumn(3).setMinWidth(0);
		table.getColumnModel().getColumn(3).setMaxWidth(0);
		table.getColumnModel().getColumn(3).setWidth(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		
		return table;
	}
	
}
