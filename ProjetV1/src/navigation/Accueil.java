package navigation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import gui.Titre;
import models.Status;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import data.Data;
import data.DataException;

/**
 * Page d'accueil de l'application
 */

public class Accueil extends JPanel {
	
	private static final long	serialVersionUID	= 1L;
	ListSelectionModel			listSelectionModel;
	JPanel						missionsEnCours;
	JPanel						missionsTempsIntervalle;
	
	public Accueil(Data data) {
		
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		/**
		 * JTable contenant les missions en cours
		 */
		Titre titreMEC = new Titre(" Missions en cours :");
		titreMEC.setBounds(10, 10, 800, 20);
		add(titreMEC);
		
		JTable tableMEC = new JTable();
		try {
			tableMEC = JTables.Missions(data.Missions().AvecStatus(Status.EN_COURS));
		} catch (DataException e) {
			e.printStackTrace();
		}
		tableMEC.setFillsViewportHeight(true);
		JScrollPane jsMEC = new JScrollPane(tableMEC);
		jsMEC.setVisible(true);
		jsMEC.setBounds(10, 40, 800, 250);
		add(jsMEC);
		
		/**
		 * JTable contenant les missions à venir sur un intervalle définit par
		 * l'utilisateur
		 */
		Titre titreMAV = new Titre(" Missions à venir dans le mois :");
		titreMAV.setBounds(10, 310, 800, 20);
		add(titreMAV);
		
		JTable tableMAV = new JTable();
		try {
			tableMAV = JTables.Missions(data.Missions().duMois());
		} catch (DataException e) {
			e.printStackTrace();
		}
		tableMAV.setFillsViewportHeight(true);
		JScrollPane jsMAV = new JScrollPane(tableMAV);
		jsMAV.setVisible(true);
		jsMAV.setBounds(10, 340, 800, 250);
		add(jsMAV);
		
		Titre label4 = new Titre(" Alertes :");
		label4.setBounds(820, 310, 440, 20);
		add(label4);
		
		/**
		 * Diagramme cammenbert représentant la répartition des missions par
		 * leurs statuts
		 */
		Titre label3 = new Titre(" Répartition des missions par statut :");
		label3.setBounds(820, 10, 440, 20);
		add(label3);
		
		DefaultPieDataset datac = new DefaultPieDataset();
		
		try {
			int mPreparation = data.Missions().AvecStatus(Status.PREPARATION).size();
			int mEnCours = data.Missions().AvecStatus(Status.EN_COURS).size();
			int mPlanifiee = data.Missions().AvecStatus(Status.PLANIFIEE).size();
			int mTerminee = data.Missions().AvecStatus(Status.TERMINEE).size();
			int mWarning = data.Missions().AvecStatus(Status.WARNING).size();
			
			datac.setValue("En cours", mEnCours);
			datac.setValue("En préparation", mPreparation);
			datac.setValue("Planifiée", mPlanifiee);
			datac.setValue("Terminée", mTerminee);
			datac.setValue("Alertée", mWarning);
			
			JFreeChart chart = ChartFactory.createPieChart("", datac, false, true, false);
			
			ChartPanel CP = new ChartPanel(chart);
			CP.setBounds(820, 40, 440, 250);
			add(CP);
		} catch (DataException e) {
			e.printStackTrace();
		}
		
		validate();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(Color.WHITE);
		batch.fillRect(820, 340, 440, 250);
	}
	
}
