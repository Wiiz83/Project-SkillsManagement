package navigation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import gui.ProgramFrame;

public class Accueil extends JPanel  {
	    ListSelectionModel listSelectionModel;
	    JPanel missionsEnCours;
	    JPanel missionsTempsIntervalle;

	    public Accueil() 
	    {
		    setOpaque(false);
			setLayout(null);
			setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
			
			JLabel label = new JLabel(" Missions en cours :");
			label.setOpaque(true);
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Calibri", Font.BOLD, 14));
			label.setBackground(new Color(0, 72, 136));
		 	label.setBounds(10, 10, 800, 20);
			add(label);
			
			String [] header={"name","age"};
		    String [][] data={{"akash","20"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"}};
		    DefaultTableModel model = new DefaultTableModel(data,header);
		    JTable table = new JTable(model);
		    table.setPreferredScrollableViewportSize(new Dimension(450,63));
		    table.setFillsViewportHeight(true);
		    JScrollPane js=new JScrollPane(table);
		    js.setVisible(true);
		    js.setBounds(10, 40, 800, 250);
		  	add(js);
		  	
			JLabel label2 = new JLabel(" Missions à venir dans le mois :");
			label2.setOpaque(true);
			label2.setForeground(Color.WHITE);
			label2.setFont(new Font("Calibri", Font.BOLD, 14));
			label2.setBackground(new Color(0, 72, 136));
			label2.setBounds(10, 310, 800, 20);
			add(label2);
			
			String [] header2={"name","age"};
		    String [][] data2={{"akash","20"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"}};
		    DefaultTableModel model2 = new DefaultTableModel(data2,header2);
		    JTable table2 = new JTable(model2);
		    table2.setPreferredScrollableViewportSize(new Dimension(450,63));
		    table2.setFillsViewportHeight(true);
		    JScrollPane js2=new JScrollPane(table2);
		    js2.setVisible(true);
		    js2.setBounds(10, 340, 800, 250);
		  	add(js2);
		
	    }
}
