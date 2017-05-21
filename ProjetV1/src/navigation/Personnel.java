package navigation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import com.github.lgooddatepicker.components.DatePicker;

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
import models.Mission;

/**
 * Page "Personnel" de l'application contenant la liste du personnel avec
 * possibilité d'ajout, suppression et modification
 * 
 */
public class Personnel extends Formulaire {
	
	private static final long							serialVersionUID	= 1L;
	private Data												data;
	private ArrayList<Competence>					listCompEmp;
	private JTable											JTablePersonnel;
	private GenericTableModel<Employee>		mJTablePersonnel;
	private JTable											JTableCompetences;
	private GenericTableModel<Competence>	mJTableCompetences;
	private Button											boutonEditComp;
	private JTextField										nom;
	private JTextField										prenom;
	private DatePicker 										datePicker;
	private HintTextField 									recherche;
	private SimpleDateFormat	EmployeeDateFormat	= new SimpleDateFormat("dd/MM/yyyy");

	public Personnel(Data data) {
		super();
		
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.JTablePersonnel = new JTable();
		try {
			this.JTablePersonnel = JTables.Employes(data.Employes().tous());
			this.JTablePersonnel.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(this.JTablePersonnel);
			js.setVisible(true);
			js.setBounds(10, 45, 300, 565);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();
		
		String indication =  "Rechercher un nom ou prénom...";
		this.recherche = new HintTextField(indication); 
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTablePersonnel.getModel());
		this.recherche.getDocument().addDocumentListener(new RechercheJTable(recherche, indication, rowSorter));
		add(this.recherche);		
		JTablePersonnel.setRowSorter(rowSorter);
		
		Titre titre = new Titre(" Détails du salarié :");
		titre.setBounds(330, 10, 930, 20);
		add(titre);
		
		JLabel labelNom = new JLabel("Nom :");
		labelNom.setBounds(350, 50, 150, 25);
		add(labelNom);
		
		JLabel labelPrenom = new JLabel("Prénom :");
		labelPrenom.setBounds(350, 80, 150, 25);
		add(labelPrenom);
		
		JLabel labelDate = new JLabel("Date d'entrée :");
		labelDate.setBounds(350, 110, 150, 25);
		add(labelDate);
		
		JLabel labelCompetences = new JLabel("Liste des compétences :");
		labelCompetences.setBounds(350, 140, 150, 25);
		add(labelCompetences);
		
		this.nom = new JTextField();
		this.nom.setBounds(450, 50, 150, 25);
		add(this.nom);
		
		this.prenom = new JTextField();
		this.prenom.setBounds(450, 80, 150, 25);
		add(this.prenom);

		this.datePicker = new DatePicker();
		this.datePicker.setBounds(450, 110, 180, 25);
		add(datePicker);
		
		this.JTableCompetences = new JTable();
		this.JTableCompetences.setFillsViewportHeight(true);
		JScrollPane js = new JScrollPane(this.JTableCompetences);
		js.setVisible(true);
		js.setBounds(350, 170, 800, 350);
		add(js);
		
		this.boutonEditComp = new Button("/boutons/miniedit.png");
		this.boutonEditComp.setBounds(1160, 170);
		add(this.boutonEditComp);
		
		
		this.JTablePersonnel.addMouseListener(new MouseAdapter() {
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
		
		this.boutonEditComp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				ModifierCompetence();
			}
		});

		composantsEdition.add(this.boutonEditComp);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.prenom);
		composantsEdition.add(this.datePicker);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		composantsEdition.add(this.JTableCompetences);
		
		composantsConsultation.add(this.recherche);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTablePersonnel);
		
		super.ChargementConsultation();
	}
	
	/*
	 * Vide tous les champs du formulaire
	 */
	public void VideChamps() {
		this.nom.setText("");
		this.prenom.setText("");
		this.datePicker.setText("");
		this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(new ArrayList<Competence>())
				.getModel();
		this.JTableCompetences.setModel(this.mJTableCompetences);
		this.JTableCompetences.getColumnModel().getColumn(0).setPreferredWidth(200);
		this.JTableCompetences.getColumnModel().getColumn(1).setPreferredWidth(600);
	}
	
	/*
	 * Renvoi l'employé séléctionné
	 */
	public Employee getSelected() {
		try {
			this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();
			return this.mJTablePersonnel
					.getRowObject(this.JTablePersonnel.convertRowIndexToModel(this.JTablePersonnel.getSelectedRow()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez sélectionner un employé pour réaliser cette action.",
					"Employé non séléctionné", JOptionPane.WARNING_MESSAGE
			);
			return null;
		}
	}
	
	
	/*
	 * Affichage des détails de l'employé sélectionné
	 */
	public void AffichageSelection(){
		Employee EmployeSelect = getSelected();
		if (EmployeSelect != null) {
			this.nom.setText(EmployeSelect.getLastName());
			this.prenom.setText(EmployeSelect.getName());

			Date date = EmployeSelect.getEntryDate();
			Instant instant = date.toInstant();
			ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
			LocalDate d = zdt.toLocalDate();
			this.datePicker.setDate(d);
			
			this.listCompEmp = EmployeSelect.getCompetences();
			this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();
			this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(listCompEmp).getModel();
			this.JTableCompetences.setModel(this.mJTableCompetences);
			this.JTableCompetences.getColumnModel().getColumn(0).setPreferredWidth(200);
			this.JTableCompetences.getColumnModel().getColumn(1).setPreferredWidth(600);
		}
	}
	
	
	/*
	 * NOUVEAU : Création d'un nouvel employé
	 */
	public void Nouveau(){
		VideChamps();
		super.ChargementModification();
		this.mode = "nouveau";
	}
	
	/*
	 * ENREGISTRER : Enregistrement de l'employé
	 */
	public void Enregistrer(){
		if((this.nom.getText().equals("")) || (this.prenom.getText().equals("")) || (this.datePicker.getText().equals(""))){
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez renseigner toutes les informations pour enregistrer.",
					"Informations non renseignés", JOptionPane.WARNING_MESSAGE
			);
		} else {
			ZonedDateTime zdt = this.datePicker.getDate().atStartOfDay(ZoneId.systemDefault());
			Instant instant = zdt.toInstant();
			java.util.Date DateEntree = java.util.Date.from(instant);
			
			try {
				switch (this.mode) {
				case "nouveau":
					Employee nouvEmp = new Employee(this.nom.getText(), this.prenom.getText(), DateEntree);
					data.Employes().ajouter(nouvEmp);
					ArrayList<Competence> listCompNouv = mJTableCompetences.getArraylist();
					nouvEmp.setCompetences(listCompNouv);
					data.Employes().modifier(nouvEmp);
					this.mJTablePersonnel.addRowObject(nouvEmp);
					this.mJTablePersonnel.fireTableDataChanged();
					ChargementConsultation();
					break;
				
				case "modification":
					if (getSelected() != null) {
						Employee empSelect = getSelected();
						empSelect.setLastName(this.nom.getText());
						empSelect.setName(this.prenom.getText());
						empSelect.setEntryDate(DateEntree);
						ArrayList<Competence> listComp = mJTableCompetences.getArraylist();
						empSelect.setCompetences(listComp);
						data.Employes().modifier(empSelect);
						this.mJTablePersonnel.fireTableDataChanged();
						ChargementConsultation();
					}
					break;
				}
			} catch (DataException e1) {
				System.out.println("Problème d'enregistrement: " + e1);
			}
			
		}
	}
	
	/*
	 * MODIFIER : Modification de l'employé séléctionné
	 */
	public void Modifier(){
		if (getSelected() != null) {
			super.ChargementModification();
		}
	}
	
	
	/*
	 * ANNULER : Annulation de toutes les modifications précédemment effectuées 
	 */
	public void Annuler(){
		switch (this.mode) {
		case "nouveau":
			VideChamps();
			ChargementConsultation();
		break;
		
		case "modification":
			AffichageSelection();
			ChargementConsultation();
			/*
			if (getSelected() != null) {
				Employee EmployeSelect = getSelected();
				this.nom.setText(EmployeSelect.getLastName());
				this.prenom.setText(EmployeSelect.getName());
				LocalDate d = EmployeSelect.getEntryDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				this.datePicker.setDate(d);
				this.listCompEmp = EmployeSelect.getCompetences();
				this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(listCompEmp)
						.getModel();
				this.JTableCompetences.setModel(this.mJTableCompetences);
				this.JTableCompetences.getColumnModel().getColumn(0).setPreferredWidth(200);
				this.JTableCompetences.getColumnModel().getColumn(1).setPreferredWidth(600);
				ChargementConsultation();
			}*/
			break;
		}
	}
	
	/*
	 * SUPPRIMER : Suppression de l'employé  séléctionné
	 */
	public void Supprimer(){
		try {
			if (getSelected() != null) {
				int n = JOptionPane.showConfirmDialog(
						new JFrame(), "Voulez vraiment supprimer cet employé ?", "Confirmation de suppression",
						JOptionPane.YES_NO_OPTION
				);
				if (n == JOptionPane.YES_OPTION) {
					Employee empSelect = getSelected();
					data.Employes().supprimer(empSelect);
					this.mJTablePersonnel.deleteRowObject(empSelect);
					this.mJTablePersonnel.fireTableDataChanged();
					VideChamps();
				}
			}
		} catch (DataException e2) {
			e2.printStackTrace();
		}
	}
	
	/*
	 * Modifier les compétences d'un employé
	 */
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


	
}
