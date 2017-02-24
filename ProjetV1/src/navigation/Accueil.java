package navigation;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import gui.Table;
import gui.Titre;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Accueil extends JPanel  {
	
		private static final long serialVersionUID = 1L;
		ListSelectionModel listSelectionModel;
	    JPanel missionsEnCours;
	    JPanel missionsTempsIntervalle;
	    
	    String [] headerTableMEC;
	    String [][] dataTableMEC;
	    String [] headerTableMAV;
	    String [][] dataTableMAV;

	    public Accueil() 
	    {
		    setOpaque(false);
			setLayout(null);
			setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
			
		    /*
		     *  MISSION EN COURS
		     */
			Titre titreMEC = new Titre(" Missions en cours :");
			titreMEC.setBounds(10, 10, 800, 20);
			add(titreMEC);
			
		    DefaultTableModel modelMEC = new DefaultTableModel(dataTableMEC,headerTableMEC);
		    Table tableMEC = new Table(modelMEC,10, 40, 800, 250);
		  	add(tableMEC.getJScrollPane());
		    
		    /*
		     *  MISSION A VENIR 
		     */
		  	Titre titreMAV = new Titre(" Missions à venir dans le mois :");
		  	titreMAV.setBounds(10, 310, 800, 20);
			add(titreMAV);

		    DefaultTableModel modelMAV = new DefaultTableModel(dataTableMAV,headerTableMAV);
		    Table tableMAV = new Table(modelMAV,10, 340, 800, 250);
		  	add(tableMAV.getJScrollPane());

		    /*
		     *  CAMEMBERT
		     */
		  	Titre label3 = new Titre(" Répartition des missions par statut :");
			label3.setBounds(820, 10, 440, 20);
			add(label3);
		  	
		  	DefaultPieDataset datac = new DefaultPieDataset();
		  	datac.setValue("Category 1", 43.2);
		  	datac.setValue("Category 2", 27.9);
		  	datac.setValue("Category 3", 79.5);
		  	JFreeChart chart = ChartFactory.createPieChart("",	datac,  false, true, false 	);

		  	ChartPanel CP = new ChartPanel(chart);
		  	CP.setBounds(820, 40, 440, 250);
		  	add(CP);
		  	validate();
		  	
		
	    }
}
