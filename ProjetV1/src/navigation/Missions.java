package navigation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import data.Data;
import data.DataException;
import gui.Button;
import gui.GenericTableModel;
import gui.Titre;
import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;
import models.Status;

/**
 * Page "Missions" de l'application contenant la liste de toutes les missions
 * avec possibilité d'ajout, suppression et modification
 * 
 */
public class Missions extends JPanel implements MouseListener {
	
	private static final long	serialVersionUID	= 1L;
	private Data 				data;
	
	Button						boutonNouveau;
	Button						boutonModifier;
	Button						boutonSupprimer;
	Button						boutonEnregistrer;
	Button						boutonAnnuler;
	Button						boutonAddComp;
	Button						boutonDeleteComp;
	Button						boutonAddEmp;
	Button						boutonDeleteEmp;
	JFormattedTextField	dateD;
	JFormattedTextField	duree;
	JFormattedTextField	nombre;
	JTable						listeMissions;
	GenericTableModel<Mission> modelMissions;
	/*
	Mission						missSelect;
	int							IDSelect;
	*/
	JTextField					nom;
	JComboBox<String>	statut;
	JTable						competences;
	JTable						employes;
	SimpleDateFormat		GuiDateFormat		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat		MissionDateFormat	= new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat		MissionDureeFormat	= new SimpleDateFormat("dd/MM/yyyy");
	String						mode;

	
	public Missions(Data data) {
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		/**
		 * JTable contenant toutes les missions
		 */
		this.listeMissions = new JTable();
		try {
			listeMissions = JTables.Missions(data.Missions().tous());
			listeMissions.setFillsViewportHeight(true);
			listeMissions.addMouseListener(this);
			JScrollPane js = new JScrollPane(listeMissions);
			js.setVisible(true);
			js.setBounds(10, 10, 300, 600);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.modelMissions = (GenericTableModel<Mission>) this.listeMissions.getModel();
		
		/**
		 * Création et positionnement des boutons de gestion de formulaire
		 */
		this.boutonNouveau = new Button("/boutons/nouveau.png");
		this.boutonNouveau.setBounds(330, 560);
		this.boutonNouveau.addMouseListener(this);
		this.boutonModifier = new Button("/boutons/modifier.png");
		this.boutonModifier.setBounds(510, 560);
		this.boutonModifier.addMouseListener(this);
		this.boutonSupprimer = new Button("/boutons/supprimer.png");
		this.boutonSupprimer.setBounds(690, 560);
		this.boutonSupprimer.addMouseListener(this);
		this.boutonEnregistrer = new Button("/boutons/enregistrer.png");
		this.boutonEnregistrer.setBounds(885, 560);
		this.boutonEnregistrer.addMouseListener(this);
		this.boutonAnnuler = new Button("/boutons/annuler.png");
		this.boutonAnnuler.setBounds(1100, 560);
		this.boutonAnnuler.addMouseListener(this);
		add(this.boutonNouveau);
		add(this.boutonModifier);
		add(this.boutonSupprimer);
		add(this.boutonEnregistrer);
		add(this.boutonAnnuler);
		
		/**
		 * Création et positionnement des éléments du formulaire
		 */
		Titre titre = new Titre(" Détails de la mission :");
		titre.setBounds(330, 10, 930, 20);
		add(titre);
		
		JLabel labelNom = new JLabel("Nom :");
		labelNom.setBounds(350, 60, 150, 25);
		add(labelNom);
		
		JLabel labelStatut = new JLabel("Statut :");
		labelStatut.setBounds(350, 100, 150, 25);
		add(labelStatut);
		
		JLabel labelNbPersonnes = new JLabel("Personnes requises :");
		labelNbPersonnes.setBounds(350, 140, 150, 25);
		add(labelNbPersonnes);
		
		JLabel labelDebut = new JLabel("Date de début :");
		labelDebut.setBounds(350, 180, 150, 25);
		add(labelDebut);
		
		JLabel labelDuree = new JLabel("Durée (en jours) :");
		labelDuree.setBounds(350, 220, 150, 25);
		add(labelDuree);
		
		JLabel labelCompetences = new JLabel("Liste des compétences :");
		labelCompetences.setBounds(650, 60, 150, 25);
		add(labelCompetences);
		
		JLabel labelEmployes = new JLabel("Liste des employés :");
		labelEmployes.setBounds(650, 250, 150, 25);
		add(labelEmployes);
		
		this.nom = new JTextField();
		this.nom.addMouseListener(this);
		this.nom.setBounds(400, 60, 150, 25);
		add(this.nom);
		
		this.statut = new JComboBox(Status.values());
		this.statut.addMouseListener(this);
		this.statut.setBounds(400, 100, 150, 25);
		add(this.statut);
		
		MaskFormatter formatterNombre;
		try {
			formatterNombre = new MaskFormatter("####");
			this.nombre = new JFormattedTextField(formatterNombre);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		this.nombre.addMouseListener(this);
		this.nombre.setBounds(480, 140, 70, 25);
		add(nombre);
		

		MaskFormatter formatterDuree;
		try {
			formatterDuree = new MaskFormatter("####");
			this.duree = new JFormattedTextField(formatterDuree);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.duree.addMouseListener(this);
		this.duree.setBounds(450, 220, 100, 25);
		add(this.duree);
		
		MaskFormatter formatterDate;
		try {
			formatterDate = new MaskFormatter("##/##/##");
			formatterDate.setPlaceholderCharacter('_');
			this.dateD = new JFormattedTextField(formatterDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.dateD.addMouseListener(this);
		this.dateD.setBounds(440, 180, 110, 25);
		add(this.dateD);
		
		this.competences = new JTable();
		this.competences.addMouseListener(this);
		this.competences.setFillsViewportHeight(true);
		JScrollPane jsComp = new JScrollPane(this.competences);
		jsComp.setVisible(true);
		jsComp.setBounds(650, 90, 500, 150);
		add(jsComp);
		
		this.employes = new JTable();
		this.employes.addMouseListener(this);
		this.employes.setFillsViewportHeight(true);
		JScrollPane jsEmp = new JScrollPane(this.employes);
		jsEmp.setVisible(true);
		jsEmp.setBounds(650, 280, 500, 200);
		add(jsEmp);
		
		this.boutonAddComp = new Button("/boutons/miniadd.png");
		this.boutonAddComp.setBounds(1160, 90);
		this.boutonAddComp.addMouseListener(this);
		add(this.boutonAddComp);
		
		this.boutonDeleteComp = new Button("/boutons/minidelete.png");
		this.boutonDeleteComp.setBounds(1160, 130);
		this.boutonDeleteComp.addMouseListener(this);
		add(this.boutonDeleteComp);
		
		this.boutonAddEmp = new Button("/boutons/miniadd.png");
		this.boutonAddEmp.setBounds(1160, 280); 
		this.boutonAddEmp.addMouseListener(this);
		add(this.boutonAddEmp);
		
		this.boutonDeleteEmp = new Button("/boutons/minidelete.png");
		this.boutonDeleteEmp.setBounds(1160, 320); 
		this.boutonDeleteEmp.addMouseListener(this);
		add(this.boutonDeleteEmp);
		
		ChargementConsultation();
	}
	
	/**
	 * Mode Consultation : - Lecture des informations détaillés de l'élément
	 * sélectionné - Les éléments du formulaire ne sont pas modifiables
	 */
	public void ChargementConsultation() {
		this.nom.setEditable(false);
		this.duree.setEditable(false);
		this.nombre.setEditable(false);
		this.dateD.setEditable(false);
		this.competences.setEnabled(false);
		this.employes.setEnabled(false);
		this.statut.setEnabled(false);
		
		this.boutonAddComp.setVisible(false);
		this.boutonAddEmp.setVisible(false);
		this.boutonDeleteComp.setVisible(false);
		this.boutonDeleteEmp.setVisible(false);
		
		this.boutonEnregistrer.setVisible(false);
		this.boutonAnnuler.setVisible(false);
		this.boutonNouveau.setVisible(true);
		this.boutonModifier.setVisible(true);
		this.boutonSupprimer.setVisible(true);
		this.listeMissions.setEnabled(true);
	}
	
	/**
	 * Mode Modification : - Possibilité de modifier les informations détaillés
	 * de l'élément sélectionné - Les éléments de la liste ne sont pas
	 * séléctionnables
	 */
	public void ChargementModification() {
		this.nom.setEditable(true);
		this.duree.setEditable(true);
		this.nombre.setEditable(true);
		this.dateD.setEditable(true);
		this.competences.setEnabled(true);
		this.employes.setEnabled(true);
		this.statut.setEnabled(true);
		
		this.boutonAddComp.setVisible(true);
		this.boutonAddEmp.setVisible(true);
		this.boutonDeleteComp.setVisible(true);
		this.boutonDeleteEmp.setVisible(true);
		
		this.boutonEnregistrer.setVisible(true);
		this.boutonAnnuler.setVisible(true);
		this.boutonNouveau.setVisible(false);
		this.boutonModifier.setVisible(false);
		this.boutonSupprimer.setVisible(false);
		this.listeMissions.setEnabled(false);
	}
	
	public void VideChamps(){
		this.nom.setText("");
		this.dateD.setText("");
		this.duree.setText("");
		this.nombre.setText("");
		this.statut.setSelectedIndex(0);
		
		TableModel resetTMEmp = JTables.Employes(new ArrayList<Employee>()).getModel();
		this.employes.setModel(resetTMEmp);
		
		TableModel resetTMComp = JTables.CompetencesRequises(new ArrayList<CompetenceRequirement>()).getModel();
		this.competences.setModel(resetTMComp);
	}
	
	/**
	 * Dessine le fond blanc du formulaire
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(Color.WHITE);
		batch.fillRect(330, 40, 930, 510);
	}
	
	/**
	 * Clic sur la souris : - Si c'est un bouton de gestion du formulaire - Si
	 * c'est un élément de la liste
	 */
 	public Mission getSelected() {
		this.modelMissions = (GenericTableModel<Mission>) this.listeMissions.getModel();
		return this.modelMissions.getRowObject(this.listeMissions.convertRowIndexToModel(this.listeMissions.getSelectedRow()));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			if (e.getSource().equals(this.boutonAddComp)) {
				
				JFrame frame = null;
				try {
					frame = new AdderJFrame<Mission, Employee>(data, getSelected(), Employee.class);
				} catch (HeadlessException | DataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();

				frame.setVisible(true);
				
			}
			
			/**
			 * TODO Formulaire prêt à l'ajout d'un nouvel élément
			 */
			if (e.getSource().equals(this.boutonNouveau)) {
				VideChamps();
				ChargementModification();
				this.mode = "nouveau";
			}
			
			/**
			 * Formulaire prêt à la modification d'un élément existant
			 */
			if (e.getSource().equals(this.boutonModifier)) {
				ChargementModification();
			}
			
			/**
			 * TODO Suppression d'un élément existant : doit demander la
			 * confirmation de l'utilisateur
			 */
			if (e.getSource().equals(this.boutonSupprimer)) {

					try {
						Mission missSelect = getSelected();
						data.Missions().supprimer(missSelect);
	 					this.modelMissions.deleteRowObject(missSelect);
	 					this.modelMissions.fireTableDataChanged();
						VideChamps();
					} catch (DataException e2) {
						e2.printStackTrace();
					}
			}
			
			/**
			 * TODO On annule toutes les modifications faites par l'utilisateur
			 * depuis l'activation du mode modification / du mode nouveau
			 */
			if (e.getSource().equals(this.boutonAnnuler)) {
				ChargementConsultation();
			}
			
			/**
			 * TODO On enregistre les modifications ou le nouvel élément
			 */
			if (e.getSource().equals(this.boutonEnregistrer)) {
				
				Date dateD = new Date(this.dateD.getText());
				int duree = Integer.parseInt(this.duree.getText());
				int nb = Integer.parseInt(this.nombre.getText());
				
				Mission mission = new Mission(this.nom.getText(), dateD, duree, nb);
				ChargementConsultation();
				
				switch (this.mode) {
					case "nouveau":
						/*
						Mission nouvEmp = new Mission(this.nom.getText(), this.dateD.getText(), this.duree.getText(), this.nombre.getText());
						
						
				        if (this.employes != null && this.employes.getModel() != null) {
				        	ArrayList<Employee> listEmp = new ArrayList<Object>;

				        	for(int row = 0; row < table.getRowCount(); row++) {
				        	    for(int column = 0; column = table.getColumnCount(); column++) {
				        	      list.add(table.getValueAt(row, column));  
				        	    }
				        	}
				        	
				        	nouvEmp.setAffEmp(affEmp);
				        }
							
						if(){
							
						}

						data.Missions().ajouter(nouvEmp);
						break;
					
					case "modification":
						/*this.empSelect.setLastName(this.nom.getText());
						this.empSelect.setName(this.prenom.getText());
						this.empSelect.setEntryDate(this.date.getText(), "yyyy-MM-dd");
						data.Employes().modifier(this.empSelect);*/
						break;
					
					default:
						break;
				}
			}
		}
		
		/**
		 * Actualisation des champs du formulaire
		 */
		if (e.getSource() instanceof JTable) {
			Mission missSelect = getSelected();
			
			this.nom.setText(missSelect.getNomM());
			this.dateD.setText(MissionDateFormat.format(missSelect.getDateDebut()));
			this.duree.setText(Integer.toString(missSelect.getDuree()));
			this.nombre.setText(Integer.toString(missSelect.getNbPersReq()));
			this.statut.setSelectedItem(missSelect.getStatus());
			
			ArrayList<CompetenceRequirement> listCompMiss = missSelect.getCompReq();
			TableModel tmComp = JTables.CompetencesRequises(listCompMiss).getModel();
			this.competences.setModel(tmComp);

			ArrayList<Employee> listEmpMiss = missSelect.getAffEmp();
			TableModel tmEmp = JTables.Employes(listEmpMiss).getModel();
			this.employes.setModel(tmEmp);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
