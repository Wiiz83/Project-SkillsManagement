package navigation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
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
import gui.RechercheJTable;
import gui.Titre;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;
import models.MissionFormation;
import models.Status;

public class Formations  extends Formulaire {

	private static final long												serialVersionUID	= 1L;
	private Data																	data;
	private Button																boutonAddComp;
	private Button																boutonEditComp;
	private Button																boutonDeleteComp;
	private Button																boutonAddEmp;
	private Button																boutonDeleteEmp;
	private Button																boutonMissionTermine;
	private JTextField															nom;
	private JComboBox<Status>											statut;
	private JFormattedTextField											dateD;
	private JFormattedTextField											duree;
	private JFormattedTextField											nombre;
	private HintTextField														recherche;
	private JComboBox<String>											filtre;
	private JTable																JTableFormations;
	private JTable																JTableCompetences;
	private JTable																JTableEmployes;
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
			JTableFormations = JTables.Missions(data.Missions().tous());
			JTableFormations.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(JTableFormations);
			js.setVisible(true);
			js.setBounds(10, 80, 300, 520);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.JTableFormations = (GenericTableModel<MissionFormation>) this.JTableFormations.getModel();
		
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
		
		this.boutonMissionTermine = new Button("/boutons/missionterminee.png");
		this.boutonMissionTermine.setBounds(400, 400);
		this.boutonMissionTermine.setVisible(false);
		add(this.boutonMissionTermine);
		
		this.boutonAddComp = new Button("/boutons/miniadd.png");
		this.boutonAddComp.setBounds(1160, 80);
		add(this.boutonAddComp);
		
		this.boutonEditComp = new Button("/boutons/miniedit.png");
		this.boutonEditComp.setBounds(1160, 120);
		add(this.boutonEditComp);
		
		this.boutonDeleteComp = new Button("/boutons/minidelete.png");
		this.boutonDeleteComp.setBounds(1160, 160);
		add(this.boutonDeleteComp);
		
		this.boutonAddEmp = new Button("/boutons/miniadd.png");
		this.boutonAddEmp.setBounds(1160, 280);
		add(this.boutonAddEmp);
		
		this.boutonDeleteEmp = new Button("/boutons/minidelete.png");
		this.boutonDeleteEmp.setBounds(1160, 320);
		add(this.boutonDeleteEmp);
		
		this.boutonDeleteEmp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
			/* TODO
				MissionTerminee();
				*/
			}
		});
		
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
		
		this.boutonDeleteEmp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				SupprimerEmploye();
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
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		composantsEdition.add(this.boutonDeleteEmp);
		
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTableFormations);
		
		super.ChargementConsultation();
		
	}
}
