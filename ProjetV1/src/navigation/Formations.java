package navigation;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import models.Competence;
import models.Employee;
import models.MissionFormation;
import models.Status;

public class Formations  extends Formulaire {

	private static final long												serialVersionUID	= 1L;
	private Data																	data;
	private Button																boutonEditEmp;
	private Button																boutonEditComp;
	private JTextField															nom;
	private JComboBox<Status>											statut;
	private JFormattedTextField											dateD;
	private JFormattedTextField											duree;
	private JFormattedTextField											nombre;
	private HintTextField														recherche;
	private JComboBox<String>											filtre;
	private JTable																JTableFormations;
	private GenericTableModel<MissionFormation>    			mJTableFormations;
	private JTable																JTableCompetences;
	private GenericTableModel<Competence>  					mJTableCompetences;
	private JTable																JTableEmployes;
	private GenericTableModel<Employee>  						mJTableEmployes;
	private SimpleDateFormat	FormationDateFormat				= new SimpleDateFormat("dd/MM/yyyy");
	private MissionFormation												formationEnCours;

	
	public Formations(Data data) {
	super();
		
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.JTableFormations = new JTable();
		try {
			JTableFormations = JTables.Missions(data.Formations().tous());
			JTableFormations.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(JTableFormations);
			js.setVisible(true);
			js.setBounds(10, 80, 300, 520);
			add(js);
			this.mJTableFormations = (GenericTableModel<MissionFormation>) this.JTableFormations.getModel();
		} catch (DataException e) {
			e.printStackTrace();
		}
		
		String indication = "Rechercher un nom de formation..";
		this.recherche = new HintTextField(indication);
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTableFormations.getModel());
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
		JTableFormations.setRowSorter(rowSorter);
		
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
		
		this.boutonEditComp = new Button("/boutons/miniedit.png");
		this.boutonEditComp.setBounds(1160, 80);
		add(this.boutonEditComp);

		this.boutonEditEmp = new Button("/boutons/miniedit.png");
		this.boutonEditEmp.setBounds(1160, 280);
		add(this.boutonEditEmp);

		JTableFormations.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
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
		
		JTableFormations.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	AffichageSelection();
	        }
	    });
		
		this.boutonEditComp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				ModifierCompetence();
			}
		});

		this.boutonEditEmp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				ModifierEmploye();
			}
		});

		
		composantsEdition.add(this.JTableEmployes);
		composantsEdition.add(this.JTableCompetences);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.statut);
		composantsEdition.add(this.dateD);
		composantsEdition.add(this.duree);
		composantsEdition.add(this.nombre);
		composantsEdition.add(this.boutonEditComp);
		composantsEdition.add(this.boutonEditEmp);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		
		composantsConsultation.add(this.recherche);
		composantsConsultation.add(this.filtre);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTableFormations);
		
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
		this.statut.addItem(Status.PREPARATION);
		this.mJTableEmployes = (GenericTableModel<Employee>) JTables.Employes(new ArrayList<Employee>()).getModel();
		this.JTableEmployes.setModel(mJTableEmployes);
		this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(new ArrayList<Competence>()).getModel();
		this.JTableCompetences.setModel(mJTableCompetences);
	}
	
	/*
	 * Renvoi la formation séléctionnée
	 */
	public MissionFormation getFormationSelected() {
		try {
			this.mJTableFormations = (GenericTableModel<MissionFormation>) JTableFormations.getModel();
			return mJTableFormations.getRowObject(this.JTableFormations.convertRowIndexToModel(this.JTableFormations.getSelectedRow()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Vous devez sélectionner une formation pour réaliser cette action.","Formation non séléctionnée", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}
	
	
	/*
	 * Affichage des détails de la mission sélectionnée
	 */
	public void AffichageSelection(){
		MissionFormation missSelect = getFormationSelected();
		
		if (missSelect != null) {
			this.nom.setText(missSelect.getNomM());
			this.dateD.setText(FormationDateFormat.format(missSelect.getDateDebut()));
			this.duree.setText(Integer.toString(missSelect.getDuree()));
			this.nombre.setText(Integer.toString(missSelect.getNbPersReq()));
			this.statut.setSelectedItem(missSelect.getStatus());
			
			ArrayList<Competence> listCompMiss = missSelect.getCompetences();
			this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(listCompMiss).getModel();
			this.JTableCompetences.setModel(mJTableCompetences);
			
			ArrayList<Employee> listEmpMiss = missSelect.getAffEmp();
			this.mJTableEmployes = (GenericTableModel<Employee>) JTables.Employes(listEmpMiss).getModel();
			this.JTableEmployes.setModel(mJTableEmployes);
		}
	}
	
	/*
	 * NOUVEAU : Création d'une nouvelle mission
	 */
	public void Nouveau(){
		this.formationEnCours = new MissionFormation("", models.Cal.today(), 0, 0);
		VideChamps();
		super.ChargementModification();
		this.mode = "nouveau";
	}
	
	/*
	 * ENREGISTRER : Enregistrement de la mission
	 */
	public void Enregistrer(){
		if ((this.dateD.getText().equals("")) || (this.duree.getText().equals(""))
				|| (this.nombre.getText().equals("")) || (this.nom.getText().equals(""))) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez renseigner toutes les informations pour enregistrer.",
					"Informations non renseignés", JOptionPane.WARNING_MESSAGE
			);
		} else {

			Date dateD = new Date(this.dateD.getText());
			String strDuree = duree.getText().replaceAll("\\D+","");
			int duree = Integer.parseInt(strDuree);
			String strNombre = nombre.getText().replaceAll("\\D+","");
			int nb = Integer.parseInt(strNombre);
			String nom = this.nom.getText();
			
			ArrayList<Employee> presentEmployes = mJTableEmployes.getArraylist();
			ArrayList<Competence> presentCompetences = mJTableCompetences.getArraylist();
			
			formationEnCours.setDateDebut(dateD);
			formationEnCours.setDuree(duree);
			formationEnCours.setNbPersReq(nb);
			formationEnCours.setNomM(nom);
			formationEnCours.setAffEmp(presentEmployes);
			formationEnCours.setCompetences(presentCompetences);
			
			switch (this.mode) {
			case "nouveau":
				try {
					data.Formations().ajouter(formationEnCours);
					mJTableFormations.addRowObject(formationEnCours);
					mJTableFormations.fireTableDataChanged();
					super.ChargementConsultation();
				} catch (DataException e) {
					e.printStackTrace();
				}
				break;
			
			case "modification":
				try {
					data.Formations().modifier(formationEnCours);
					mJTableFormations.fireTableDataChanged();
					super.ChargementConsultation();
				} catch (DataException e) {
					e.printStackTrace();
				}
				break;
		}
			}
		}
		
	
	/*
	 * MODIFIER : Modification de l'employé séléctionné
	 */
	public void Modifier(){
		this.formationEnCours = getFormationSelected();
		if (formationEnCours != null) {
			if (formationEnCours.estModifiable()) {
				super.ChargementModification();
			} else if (formationEnCours.getStatus() == Status.EN_COURS) {
				JOptionPane.showMessageDialog(
						new JFrame(), "La mission est en cours : elle n'est donc plus modifiable.",
						"Mission en cours", JOptionPane.WARNING_MESSAGE
				);
			} else if (formationEnCours.getStatus() == Status.TERMINEE) {
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
		this.formationEnCours = null;
		VideChamps();
		ChargementConsultation();
	}
	
	/*
	 * SUPPRIMER : Suppression de l'employé  séléctionné
	 */
	public void Supprimer(){
		MissionFormation formationEnCours = getFormationSelected();
		if (formationEnCours != null) {
			int n = JOptionPane.showConfirmDialog(
					new JFrame(), "Voulez vraiment supprimer cette mission ?", "Confirmation de suppression",
					JOptionPane.YES_NO_OPTION
			);
			if (n == JOptionPane.YES_OPTION) {
				try {
					data.Formations().supprimer(formationEnCours);
					this.mJTableFormations.deleteRowObject(formationEnCours);
					this.mJTableFormations.fireTableDataChanged();
					VideChamps();
				} catch (DataException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	public void ModifierCompetence(){
		ProgramFrame.getFrame().setEnabled(false);
		try {
			ArrayList<Competence> listCompNonPoss = data.Competences().Autres(mJTableCompetences.getArraylist());
			GenericTableModel<Competence> compNonPossModel = (GenericTableModel<Competence>) JTables.Competences(listCompNonPoss).getModel();
			JTable compNonPoss = new JTable(compNonPossModel);
			JTable compPoss = new JTable(mJTableCompetences);
			PersonnelEditCompetence gestionListe = new PersonnelEditCompetence(compPoss, compNonPoss, mJTableCompetences, compNonPossModel);
			gestionListe.displayGUI();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
	}
	
	public void ModifierEmploye(){
		ProgramFrame.getFrame().setEnabled(false);
		try {
			ArrayList<Employee> listCompNonPoss = data.Employes().Autres(mJTableEmployes.getArraylist());
			GenericTableModel<Employee> compNonPossModel = (GenericTableModel<Employee>) JTables.Employes(listCompNonPoss).getModel();
			JTable compNonPoss = new JTable(compNonPossModel);
			JTable compPoss = new JTable(mJTableEmployes);
			FormationsEditEmploye gestionListe = new FormationsEditEmploye(compPoss, compNonPoss, mJTableEmployes, compNonPossModel);
			gestionListe.displayGUI();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
	}
}
