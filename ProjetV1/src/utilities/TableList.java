package utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableList extends JComponent {
    JTextArea output;
    JList list; 
    JTable table;
    String newline = "\n";
    ListSelectionModel listSelectionModel;
    
    public TableList() {

         String[] columnNames = { "French", "Spanish", "Italian" };
         String[][] tableData = {{"un",     "uno",     "uno"     },
                                 {"deux",   "dos",     "due"     },
                                 {"trois",  "tres",    "tre"     },
                                 { "quatre", "cuatro",  "quattro"},
                                 { "cinq",   "cinco",   "cinque" },
                                 { "six",    "seis",    "sei"    },
                                 { "sept",   "siete",   "sette"  } };

         table = new JTable(tableData, columnNames);
         listSelectionModel = table.getSelectionModel();
	 	 table.setSelectionModel(listSelectionModel);
 	
    }
    
    
    
}


