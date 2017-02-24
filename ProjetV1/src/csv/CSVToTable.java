package csv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
				String[] headers = { "Nom", "Pr�nom", "Date d'entr�e" };
				return headers[col];
			}
			
			public int getColumnCount() {
				return 3;
			}
			
			public int getRowCount() {
				return employes.size();
			}
			
			public Object getValueAt(int row, int col) {
				Employee emp = employes.get(row);
				switch (col) {
				case 1:
					return emp.getName();
				
				case 0:
					return emp.getLastName();
				case 2:
					return dateformatter.format(emp.getEntryDate());
				default:
					System.out.println("JTable access ");
					break;
				}
				return emp;
			}
		};
		JTable table = new JTable(dataModel);
		
		return table;
	}
}