package navigation;

import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import csv.CSVToTable;
import csv.InvalidCSVException;
import csv.InvalidDataException;


public class Personnel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public Personnel()  {   
	    setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		

		JTable table = new JTable();		
		try {
			table = CSVToTable.Employes();
			table.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(table);
		    js.setVisible(true);
		    js.setBounds(10, 10, 800, 20);
		    
		} catch (NumberFormatException | IOException | InvalidCSVException | InvalidDataException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
