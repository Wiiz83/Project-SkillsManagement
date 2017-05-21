package navigation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import gui.Alerte;
import gui.JTables;
import gui.Titre;
import models.Employee;
import models.Mission;
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
	Data						data;
	
	public Accueil(Data data) {
		this.data = data;
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
		 * JTable contenant les missions � venir sur un intervalle d�finit par
		 * l'utilisateur
		 */
		Titre titreMAV = new Titre(" Missions � venir dans le mois :");
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
		 * Diagramme cammenbert repr�sentant la r�partition des missions par
		 * leurs statuts
		 */
		Titre label3 = new Titre(" R�partition des missions par statut :");
		label3.setBounds(820, 10, 440, 20);
		add(label3);
		
		DefaultPieDataset datac = new DefaultPieDataset();
		
		try {
			int mPreparation = data.Missions().AvecStatus(Status.PREPARATION).size();
			int mEnCours = data.Missions().AvecStatus(Status.EN_COURS).size();
			int mPlanifiee = data.Missions().AvecStatus(Status.PLANIFIEE).size();
			int mTerminee = data.Missions().AvecStatus(Status.TERMINEE).size();
			
			datac.setValue("En cours", mEnCours);
			datac.setValue("En pr�paration", mPreparation);
			datac.setValue("Planifi�e", mPlanifiee);
			datac.setValue("Termin�e", mTerminee);
			
			JFreeChart chart = ChartFactory.createPieChart("", datac, false, true, false);
			
			ChartPanel CP = new ChartPanel(chart);
			CP.setBounds(820, 40, 440, 250);
			add(CP);
		} catch (DataException e) {
			e.printStackTrace();
		}
		
		
		ArrayList<Mission> alertes_emp_m = new ArrayList<>();
		ArrayList<Mission> alertes_retard = new ArrayList<>();
		try {
			alertes_emp_m = data.Missions().AlertesEmployesManquants();
			alertes_retard = data.Missions().AlertesMissionsEnRetard();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
		ArrayList<Alerte> alertes = new ArrayList<>();
		
		for (Mission m : alertes_emp_m)
			alertes.add(new Alerte(m,"Employ�s non affect�s"));
		for (Mission m : alertes_retard)
			alertes.add(new Alerte(m, "Mission en retard"));
			
		JTable	alertesJTable = JTables.Alertes(alertes);
		alertesJTable.setFillsViewportHeight(true);
		JScrollPane jsAlertes = new JScrollPane(alertesJTable);
		jsAlertes.setVisible(true);
		jsAlertes.setBounds(820, 340, 440, 250);
		add(jsAlertes);
		
		alertesJTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(alertesJTable.getValueAt(alertesJTable.getSelectedRow(), 1) == "Mission en retard"){
					int n = JOptionPane.showConfirmDialog(
							new JFrame(), "Cette mission est elle termin�e ?", "Confirmation de mission termin�e",
							JOptionPane.YES_NO_OPTION
					);
					if (n == JOptionPane.YES_OPTION) {
						// TODO : On met le statut en TERMINEE
						Mission m = (Mission) alertesJTable.getValueAt(alertesJTable.getSelectedRow(), 3);
						Date dateFin = new Date();
						m.setDateFinRelle(dateFin);
						DefaultTableModel dtm = (DefaultTableModel) alertesJTable.getModel();
						dtm.fireTableDataChanged();
						
					}
				}
			}
			
		});
		
		validate();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	
}
