package navigation;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import gui.ProgramFrame;

public class HomePanel extends JPanel  {
	   private JButton jcomp4;
	    private JPanel contentPane;
	    private JPanel choiceBox;

	    public HomePanel() 
	    {
	        setOpaque(true);
	        //setBackground(Color.RED.darker().darker());
	        String[] columnNames = { "French", "Spanish", "Italian" };
	        String[][] tableData = {{"un",     "uno",     "uno"     },
	                                {"deux",   "dos",     "due"     },
	                                {"trois",  "tres",    "tre"     },
	                                { "quatre", "cuatro",  "quattro"},
	                                { "cinq",   "cinco",   "cinque" },
	                                { "six",    "seis",    "sei"    },
	                                { "sept",   "siete",   "sette"  } };
			
	        //Create and set up the content pane.
	        JTable table = new JTable(tableData, columnNames);
	        add(table);
	        
	        
	    }
}
