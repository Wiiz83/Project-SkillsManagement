package navigation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import gui.Alerte;
import gui.JTables;
import gui.Titre;
import models.Mission;
import models.Status;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.github.lgooddatepicker.components.DatePicker;

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
	private JTable				alertesJTable;
	
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
		
			
		this.alertesJTable = JTables.Alertes(alertes);
		
		alertesJTable.setFillsViewportHeight(true);
		JScrollPane jsAlertes = new JScrollPane(alertesJTable);
		jsAlertes.setVisible(true);
		jsAlertes.setBounds(820, 340, 440, 250);
		add(jsAlertes);
		
		this.alertesJTable.addMouseListener(new MouseAdapter() {
			   public void mousePressed(MouseEvent me) {
			        if (me.getClickCount() == 2) {
						if(alertesJTable.getValueAt(alertesJTable.getSelectedRow(), 0) == "Mission en retard"){
							PopUpMissionEnRetard();
						}
			        }
			    }
			});
		validate();
	}

	
	public void PopUpMissionEnRetard(){
		DatePicker dp = new DatePicker();
		String message ="Veuillez entrer la date de fin r�elle de la mission :";
		Object[] params = {message,dp};
		int n = JOptionPane.showConfirmDialog(null,params,"Cette mission est elle termin�e ?", JOptionPane.YES_NO_OPTION);
		
		if (n == JOptionPane.YES_OPTION) {

			if(dp.getDate() == null){
				JOptionPane.showMessageDialog(new JFrame(), "La date de fin r�elle est invalide.","Date invalide", JOptionPane.WARNING_MESSAGE);
				PopUpMissionEnRetard();
			} else {
				
				Mission missionEnCours = (Mission) this.alertesJTable.getValueAt(alertesJTable.getSelectedRow(), 3);
				
				ZonedDateTime zdt = dp.getDate().atStartOfDay(ZoneId.systemDefault());
				Instant instant = zdt.toInstant();
				java.util.Date DateDeFin = java.util.Date.from(instant);

				Date DateDeDebut = missionEnCours.getDateDebut();
				
				// Date D�but apr�s Date Fin
		        if (DateDeDebut.compareTo(DateDeFin) > 0) {
					JOptionPane.showMessageDialog(new JFrame(), "La date de fin doit �tre sup�rieure ou �gale � la date de d�but.","Date invalide", JOptionPane.WARNING_MESSAGE);
					PopUpMissionEnRetard();
		        } else if (DateDeDebut.compareTo(DateDeFin) <= 0) {	        	
		        	missionEnCours.setDateFinRelle(DateDeFin);
		        	try {
						data.Missions().modifier(missionEnCours);
						
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
						
						TableModel TableAlertes = JTables.Alertes(alertes).getModel();
						this.alertesJTable.setModel(TableAlertes);
						alertesJTable.getColumnModel().getColumn(3).setMinWidth(0);
						alertesJTable.getColumnModel().getColumn(3).setMaxWidth(0);
						alertesJTable.getColumnModel().getColumn(3).setWidth(0);
						
					} catch (DataException e) {
						e.printStackTrace();
					}
		        } else {
					JOptionPane.showMessageDialog(new JFrame(), "La date de fin r�elle est invalide.","Date invalide", JOptionPane.WARNING_MESSAGE);
					PopUpMissionEnRetard();
		        }
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	
}
