package navigation;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import data.Data;
import data.DataException;
import gui.Button;
import gui.Formulaire;
import gui.GenericTableModel;
import gui.HintTextField;
import gui.JTables;
import gui.ProgramFrame;
import gui.RechercheJTable;
import gui.Titre;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;
import models.Recommendation;
import models.Status;

/**
 * Page "Missions" de l'application contenant la liste de toutes les missions
 * avec possibilité d'ajout, suppression et modification
 * 
 */
public class Missions extends Formulaire {
	
	private static final long												serialVersionUID	= 1L;
	private Data																	data;
	private Button																boutonAddComp;
	private Button																boutonEditComp;
	private Button																boutonDeleteComp;
	private Button																boutonAddEmp;
	private Button																boutonAddEmpRecom;
	private Button																boutonMissionTermine;
	private JTextField															nom;
	private JComboBox<Status>											statut;
	private JFormattedTextField											dateD;
	private JFormattedTextField											duree;
	private JFormattedTextField											nombre;
	private HintTextField														recherche;
	private JComboBox<String>											filtre;
	private JTable																JTableMissions;
	private GenericTableModel<Mission>								mJTableMissions;
	private JTable																JTableCompetences;
	private GenericTableModel<CompetenceRequirement>	mJTableCompetences;
	private JTable																JTableEmployes;
	private GenericTableModel<Employee>							mJTableEmployes;
	private SimpleDateFormat	MissionDateFormat				= new SimpleDateFormat("dd/MM/yyyy");
	private Mission																missionEnCours;

	public Missions(Data data) {
		super();
		
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.JTableMissions = new JTable();
		try {
			JTableMissions = JTables.Missions(data.Missions().tous());
			JTableMissions.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(JTableMissions);
			js.setVisible(true);
			js.setBounds(10, 80, 300, 520);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTableMissions = (GenericTableModel<Mission>) this.JTableMissions.getModel();
		
		String indication = "Rechercher un nom de mission...";
		this.recherche = new HintTextField(indication);
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTableMissions.getModel());
		this.recherche.getDocument().addDocumentListener(new RechercheJTable(recherche, indication, rowSorter));
		add(this.recherche);
		
		this.filtre = new JComboBox<>();
		this.filtre.addItem("Toutes");
		this.filtre.addItem(Status.EN_COURS.toString());
		this.filtre.addItem(Status.PLANIFIEE.toString());
		this.filtre.addItem(Status.PREPARATION.toString());
		this.filtre.addItem(Status.TERMINEE.toString());
		this.filtre.setBounds(10, 45, 300, 25);
		this.filtre.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (filtre.getSelectedItem().toString() != "Tous") {
							rowSorter.setRowFilter(RowFilter.regexFilter(filtre.getSelectedItem().toString(), 2));
						}
					}
				}
		);
		add(this.filtre);
		JTableMissions.setRowSorter(rowSorter);
		
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
		labelCompetences.setBounds(650, 50, 150, 25);
		add(labelCompetences);
		
		JLabel labelEmployes = new JLabel("Liste des employés :");
		labelEmployes.setBounds(650, 250, 150, 25);
		add(labelEmployes);
		
		this.nom = new JTextField();
		this.nom.setBounds(400, 60, 200, 25);
		add(this.nom);
		
		this.statut = new JComboBox<>();
		this.statut.setBounds(400, 100, 200, 25);
		add(this.statut);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		this.duree = new JFormattedTextField(numberFormat);
		this.duree.setBounds(450, 220, 150, 25);
		add(this.duree);
		
		this.nombre = new JFormattedTextField(numberFormat);
		this.nombre.setBounds(480, 140, 120, 25);
		add(nombre);

		MaskFormatter formatterDate;
		try {
			formatterDate = new MaskFormatter("##/##/####");
			formatterDate.setPlaceholderCharacter('_');
			this.dateD = new JFormattedTextField(formatterDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.dateD.setBounds(440, 180, 160, 25);
		add(this.dateD);
		
		this.JTableCompetences = new JTable();

		this.JTableCompetences.setFillsViewportHeight(true);
		JScrollPane jsComp = new JScrollPane(this.JTableCompetences);
		jsComp.setVisible(true);
		jsComp.setBounds(650, 80, 500, 150);
		add(jsComp);
		
		this.JTableEmployes = new JTable();
		this.JTableEmployes.setFillsViewportHeight(true);
		JScrollPane jsEmp = new JScrollPane(this.JTableEmployes);
		jsEmp.setVisible(true);
		jsEmp.setBounds(650, 280, 500, 200);
		add(jsEmp);
		
		this.boutonAddComp = new Button("/boutons/miniadd.png");
		this.boutonAddComp.setBounds(1160, 80);
		add(this.boutonAddComp);
		
		this.boutonEditComp = new Button("/boutons/miniedit.png");
		this.boutonEditComp.setBounds(1160, 120);
		add(this.boutonEditComp);
		
		this.boutonDeleteComp = new Button("/boutons/minidelete.png");
		this.boutonDeleteComp.setBounds(1160, 160);
		add(this.boutonDeleteComp);
		
		this.boutonAddEmp = new Button("/boutons/miniedit.png");
		this.boutonAddEmp.setBounds(1160, 280);
		add(this.boutonAddEmp);
		
		this.boutonAddEmpRecom = new Button("/boutons/minirecommandation.png");
		this.boutonAddEmpRecom.setBounds(1160, 320);
		add(this.boutonAddEmpRecom);
		
		
		this.JTableMissions.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				AffichageSelection();
			}
		});
		
		this.boutonNouveau.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				Nouveau();
			}
		});
		
		this.boutonModifier.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				Modifier();
			}
		});
		
		this.boutonSupprimer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				Supprimer();
			}
		});
		
		this.boutonEnregistrer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				Enregistrer();
			}
		});
		
		this.boutonAnnuler.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				Annuler();
			}
		});
		
		this.boutonAddComp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				AjouterCompetence();
			}
		});
		
		this.boutonEditComp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				ModifierCompetence();
			}
		});
		
		this.boutonDeleteComp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				SupprimerCompetence();
			}
		});
		
		this.boutonAddEmp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				AjouterEmploye();
			}
		});
		
		this.boutonAddEmpRecom.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				AjouterEmployeRecommande();
			}
		});
		
		composantsEdition.add(this.JTableEmployes);
		composantsEdition.add(this.JTableCompetences);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.statut);
		composantsEdition.add(this.dateD);
		composantsEdition.add(this.duree);
		composantsEdition.add(this.nombre);
		composantsEdition.add(this.boutonAddComp);
		composantsEdition.add(this.boutonDeleteComp);
		composantsEdition.add(this.boutonEditComp);
		composantsEdition.add(this.boutonAddEmp);
		composantsEdition.add(this.boutonAddEmpRecom);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTableMissions);
		
		super.ChargementConsultation();
	}
	
	/*
	 * Vide tous les champs du formulaire
	 */
	public void VideChamps() {
		this.nom.setText("");
		this.dateD.setText("");
		this.duree.setText("");
		this.nombre.setText("");
		this.statut.setSelectedIndex(0);
		this.mJTableEmployes = (GenericTableModel<Employee>) JTables.Employes(new ArrayList<Employee>()).getModel();
		this.JTableEmployes.setModel(mJTableEmployes);
		this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) JTables
				.CompetencesRequises(new ArrayList<CompetenceRequirement>()).getModel();
		this.JTableCompetences.setModel(mJTableCompetences);
	}
	
	
	/*
	 * Renvoi la mission séléctionnée
	 */
	public Mission getMissionSelected() {
		try {
			this.mJTableMissions = (GenericTableModel<Mission>) this.JTableMissions.getModel();
			return this.mJTableMissions
					.getRowObject(this.JTableMissions.convertRowIndexToModel(this.JTableMissions.getSelectedRow()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez sélectionner une mission pour réaliser cette action.",
					"Mission non séléctionnée", JOptionPane.WARNING_MESSAGE
			);
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Renvoi la compétence séléctionnée
	 */
	public CompetenceRequirement getCompetenceSelected() {
		try {
			this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) this.JTableCompetences.getModel();
			return this.mJTableCompetences.getRowObject(
					this.JTableCompetences.convertRowIndexToModel(this.JTableCompetences.getSelectedRow())
			);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez sélectionner une compétence pour réaliser cette action.",
					"Compétence non séléctionnée", JOptionPane.WARNING_MESSAGE
			);
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Renvoi l'employé séléctionné
	 */
	public Employee getEmployeSelected() {
		try {
			this.mJTableEmployes = (GenericTableModel<Employee>) this.JTableEmployes.getModel();
			return this.mJTableEmployes
					.getRowObject(this.JTableEmployes.convertRowIndexToModel(this.JTableEmployes.getSelectedRow()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez sélectionner un employé pour réaliser cette action.",
					"Employé non séléctionné", JOptionPane.WARNING_MESSAGE
			);
			e.printStackTrace();
			return null;
		}
	}

	// TODO : Appeler à chaque modification de champ et affectation d'employés ou compétences req.
	void updateComboBox() {
		Mission mission = getMissionSelected();
		statut.removeAllItems();
		if (mission == null)
			return;
		statut.addItem(mission.getStatus());
		if (mission.getStatus() == Status.PREPARATION)
			statut.addItem(Status.PLANIFIEE);
	}
	
	// TODO: Appeler avant l'enregistrement
	private void updateMissionStatus() {
		Status selected = (Status) statut.getSelectedItem();
		Mission mission = getMissionSelected();
		if (selected == Status.PLANIFIEE && mission.getStatus() == Status.PREPARATION)
			mission.planifier();
		if (selected == Status.PREPARATION && mission.getForcer_planification()
				&& mission.getStatus() == Status.PLANIFIEE)
			mission.deplanifier();
	}
	
	
	public void updateCompReq() {
		mJTableCompetences.fireTableDataChanged();
	}
	
	
	/*
	 * Affichage des détails de la mission sélectionnée
	 */
	public void AffichageSelection(){
		Mission missSelect = getMissionSelected();
		
		if (missSelect != null) {
			this.nom.setText(missSelect.getNomM());
			this.dateD.setText(MissionDateFormat.format(missSelect.getDateDebut()));
			this.duree.setText(Integer.toString(missSelect.getDuree()));
			this.nombre.setText(Integer.toString(missSelect.getNbPersReq()));
			this.statut.setSelectedItem(missSelect.getStatus());
			
			ArrayList<CompetenceRequirement> listCompMiss = missSelect.getCompReq();
			TableModel tmComp = JTables.CompetencesRequises(listCompMiss).getModel();
			this.JTableCompetences.setModel(tmComp);
			
			ArrayList<Employee> listEmpMiss = missSelect.getAffEmp();
			TableModel tmEmp = JTables.Employes(listEmpMiss).getModel();
			this.JTableEmployes.setModel(tmEmp);
			
			this.mJTableEmployes = (GenericTableModel<Employee>) this.JTableEmployes.getModel();
			this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) this.JTableCompetences.getModel();
			
			updateComboBox();
		}
	}
	
	
	/*
	 * NOUVEAU : Création d'une nouvelle mission
	 */
	public void Nouveau(){
		this.missionEnCours = new Mission("", null, 0, 0);
		VideChamps();
		ChargementModification();
		this.mode = "nouveau";
	}
	
	/*
	 * TODO ENREGISTRER : Enregistrement de la mission
	 */
	public void Enregistrer(){
		if ((this.dateD.getText().equals("")) || (this.duree.getText().equals(""))
				|| (this.nombre.getText().equals("")) || (this.nom.getText().equals(""))) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez renseigner toutes les informations pour enregistrer.",
					"Informations non renseignés", JOptionPane.WARNING_MESSAGE
			);
		} else {

			updateMissionStatus();

			Date dateD = new Date(this.dateD.getText());
			String strDuree = this.duree.getText().replaceAll("\\D+","");
			int duree = Integer.parseInt(strDuree);
			String strNombre = this.nombre.getText().replaceAll("\\D+","");
			int nb = Integer.parseInt(strNombre);
			String nom = this.nom.getText();
			
			Mission mission = new Mission(nom, dateD, duree, nb);
			ChargementConsultation();
			}
		}
		
	
	/*
	 * MODIFIER : Modification de l'employé séléctionné
	 */
	public void Modifier(){
		this.missionEnCours = getMissionSelected();
		if (missionEnCours != null) {
			if (missionEnCours.estModifiable()) {
				super.ChargementModification();
			} else if (missionEnCours.getStatus() == Status.EN_COURS) {
				JOptionPane.showMessageDialog(
						new JFrame(), "La mission est en cours : elle n'est donc plus modifiable.",
						"Mission en cours", JOptionPane.WARNING_MESSAGE
				);
			} else if (missionEnCours.getStatus() == Status.TERMINEE) {
				JOptionPane.showMessageDialog(
						new JFrame(), "La mission est terminée : elle n'est donc plus modifiable.",
						"Mission terminée", JOptionPane.WARNING_MESSAGE
				);
			}
		}
	}
	
	/*
	 * ANNULER : Annulation de toutes les modifications précédemment effectuées 
	 */
	public void Annuler(){
		this.missionEnCours = null;
		
		switch (this.mode) {
			case "nouveau":
				VideChamps();
				ChargementConsultation();
				break;
			
			case "modification":
				Mission missSelect = getMissionSelected();
				if (missSelect != null) {
					this.nom.setText(missSelect.getNomM());
					this.dateD.setText(MissionDateFormat.format(missSelect.getDateDebut()));
					this.duree.setText(Integer.toString(missSelect.getDuree()));
					this.nombre.setText(Integer.toString(missSelect.getNbPersReq()));
					this.statut.setSelectedItem(missSelect.getStatus());
					
					ArrayList<CompetenceRequirement> listCompMiss = missSelect.getCompReq();
					TableModel tmComp = JTables.CompetencesRequises(listCompMiss).getModel();
					this.JTableCompetences.setModel(tmComp);
					
					ArrayList<Employee> listEmpMiss = missSelect.getAffEmp();
					TableModel tmEmp = JTables.Employes(listEmpMiss).getModel();
					this.JTableEmployes.setModel(tmEmp);
					ChargementConsultation();
				}
				break;
		}
	}
	
	/*
	 * SUPPRIMER : Suppression de l'employé  séléctionné
	 */
	public void Supprimer(){
		Mission missSelect = getMissionSelected();
		if (missSelect != null) {
			int n = JOptionPane.showConfirmDialog(
					new JFrame(), "Voulez vraiment supprimer cette mission ?", "Confirmation de suppression",
					JOptionPane.YES_NO_OPTION
			);
			if (n == JOptionPane.YES_OPTION) {
				try {
					data.Missions().supprimer(missSelect);
					this.mJTableMissions.deleteRowObject(missSelect);
					this.mJTableMissions.fireTableDataChanged();
					VideChamps();
				} catch (DataException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	public void AjouterCompetence(){
		JFrame frame;
		try {
			frame = new MissionsAddCompetence(data, this.missionEnCours, this);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		} catch (HeadlessException | DataException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void ModifierCompetence(){
		CompetenceRequirement compSelect = getCompetenceSelected();
		if (compSelect != null) {
			int rowIndex = this.JTableCompetences.getSelectedRow();
			MissionsEditCompetence fel = new MissionsEditCompetence(rowIndex, JTableCompetences, compSelect, this);
			fel.displayGUI();
		}
	}
	
	public void SupprimerCompetence(){
		CompetenceRequirement compSelect = getCompetenceSelected();
		if (compSelect != null) {
			int n = JOptionPane.showConfirmDialog(new JFrame(), "Voulez vraiment supprimer cette compétence ?","Confirmation de suppression", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				missionEnCours.removeCompetenceReq(compSelect);
				this.mJTableCompetences.deleteRowObject(compSelect);
				this.mJTableCompetences.fireTableDataChanged();
				updateComboBox();
			}
		}
	}
	
	public void AjouterEmploye(){
		ProgramFrame.getFrame().setEnabled(false);
		ArrayList<Employee> listEmpNonPoss;
		try {
			listEmpNonPoss = data.Employes().Autres(mJTableEmployes.getArraylist());
			GenericTableModel<Employee> empNonPossModel = (GenericTableModel<Employee>) JTables.Employes(listEmpNonPoss).getModel();
			JTable empNonPoss = new JTable(empNonPossModel);
			JTable empPoss = new JTable(mJTableEmployes);
			MissionsEditEmploye gestionListe = new MissionsEditEmploye(empPoss, empNonPoss, mJTableEmployes, empNonPossModel, this, missionEnCours);
			gestionListe.displayGUI();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
	}
	
	public void AjouterEmployeRecommande(){
		ProgramFrame.getFrame().setEnabled(false);
		try {
			ArrayList<Employee> absentEmployes = data.Employes().tous();
			ArrayList<Employee> presentEmployes = mJTableEmployes.getArraylist();
			
			for (Employee emp : presentEmployes) {
				if(absentEmployes.contains(emp)){
					absentEmployes.remove(emp);
				}
			}
			missionEnCours.setAffEmp(presentEmployes);
			Recommendation recommandation  = new Recommendation(missionEnCours, absentEmployes);
			recommandation.setRecommendations();
			
			ArrayList<Employee> listEmpNonPoss = recommandation.getEmpRec();
			JTable empNonPoss = JTables.Recommendation(recommandation);
			TableModel empNonPossModel = empNonPoss.getModel();
			JTable empPoss = new JTable(mJTableEmployes);
			MissionsEditEmployeRecommande gestionListe = new MissionsEditEmployeRecommande(empPoss, empNonPoss, mJTableEmployes, empNonPossModel, this);
			gestionListe.displayGUI();
			
		} catch (DataException e1) {
			e1.printStackTrace();
		}
	}
}
