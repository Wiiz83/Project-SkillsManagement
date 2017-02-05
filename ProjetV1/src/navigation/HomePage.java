package navigation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

public class HomePage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int POS_X	= 488;
	
	/**
	 * Constructeur
	 */
	public HomePage() {
		super();
		
        
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

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

	}

	

	
	

}

