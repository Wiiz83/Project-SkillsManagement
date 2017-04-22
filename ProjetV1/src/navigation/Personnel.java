package navigation;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import csv.CSVException;
import data.Data;
import data.DataException;
import gui.Button;
import gui.GenericTableModel;
import gui.ProgramFrame;
import gui.Titre;
import models.Competence;
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;

/**
 * Page "Personnel" de l'application contenant la liste du personnel avec
 * possibilité d'ajout, suppression et modification
 * 
 * TODO ATTENTION : Implémentation différente de Compétences et Personnel -
 * Utilisation de la méthode AffichageListe() qui actualise la liste en cas de
 * modification
 * 
 */
public class Personnel extends Page implements MouseListener {
	private static final long serialVersionUID = 1L;
	private Data		data;

	GestionListe 					gc;
	ArrayList<Competence> listCompEmp;
	
	JTable									listePersonnel;
	GenericTableModel<Employee> modelPersonnel;
	
	Button				boutonNouveau;
	Button				boutonModifier;
	Button				boutonSupprimer;
	Button				boutonEnregistrer;
	Button				boutonAnnuler;
	Button				boutonEditComp;
	
	JTextField			nom;
	JTextField			prenom;
	JTextField			date;
	JTable				competences;
	TableModel		competencesModel;

	SimpleDateFormat	GuiDateFormat		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat	EmployeeDateFormat	= new SimpleDateFormat("dd/MM/yyyy");
	
	public Personnel(Data data) {
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.listePersonnel = new JTable();
		try {
			this.listePersonnel = JTables.Employes(data.Employes().tous());
			this.listePersonnel.setFillsViewportHeight(true);
			this.listePersonnel.addMouseListener(this);
			JScrollPane js  = new JScrollPane(this.listePersonnel);
			js.setVisible(true);
			js.setBounds(10, 10, 300, 600);
			add(js);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.modelPersonnel = (GenericTableModel<Employee>) this.listePersonnel.getModel();
		
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
		this.nom.addMouseListener(this);
		this.nom.setBounds(450, 50, 150, 25);
		add(this.nom);
		
		this.prenom = new JTextField();
		this.prenom.addMouseListener(this);
		this.prenom.setBounds(450, 80, 150, 25);
		add(this.prenom);
		
		MaskFormatter formatterDate;
		try {
			formatterDate = new MaskFormatter("##/##/####");
			formatterDate.setPlaceholderCharacter('_');
			this.date = new JFormattedTextField(formatterDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.date.addMouseListener(this);
		this.date.setBounds(450, 110, 150, 25);
		add(this.date);
		
		this.competences = new JTable();
		this.competences.addMouseListener(this);
		this.competences.setFillsViewportHeight(true);
		JScrollPane js = new JScrollPane(this.competences);
		js.setVisible(true);
		js.setBounds(350, 170, 350, 350);
		add(js);
		
		this.boutonEditComp = new Button("/boutons/miniedit.png");
		this.boutonEditComp.setBounds(710, 170);
		this.boutonEditComp.addMouseListener(this);
		add(this.boutonEditComp);
		
		composantsEdition.add(this.boutonEditComp);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.prenom);
		composantsEdition.add(this.date);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.listePersonnel);

		super.ChargementConsultation();
	}
	
	/**
	 * Mode Nouveau : On vide tous les éléments contenus dans les champs du formulaire
	 */
	public void ChargementNouveau() {
		VideChamps();
		super.ChargementModification();
		this.mode = "nouveau";
	}
	
	public void VideChamps(){
		this.nom.setText("");
		this.prenom.setText("");
		this.date.setText("");
		this.competencesModel = JTables.Competences(new ArrayList<Competence>()).getModel();
		this.competences.setModel(this.competencesModel);
	}
	
 	public Employee getSelected() {
		this.modelPersonnel = (GenericTableModel<Employee>) this.listePersonnel.getModel();
		return this.modelPersonnel.getRowObject(this.listePersonnel.convertRowIndexToModel(this.listePersonnel.getSelectedRow()));
	}

	/**
	 * Enregistrement des informations dans le cas d'une modification ou d'un
	 * ajout
	 * 
	 * @throws ParseException
	 * @throws CSVException
	 */
	public void Enregistrement() throws ParseException, DataException {
		
		switch (this.mode) {
		case "nouveau":
			Employee nouvEmp = new Employee(this.nom.getText(), this.prenom.getText(), this.date.getText());
			data.Employes().ajouter(nouvEmp);
			break;
		
		case "modification":
			Employee empSelect = getSelected();
			empSelect.setLastName(this.nom.getText());
			empSelect.setName(this.prenom.getText());
			empSelect.setEntryDate(this.date.getText(), "yyyy-MM-dd");
			data.Employes().modifier(empSelect);
			break;
		
		default:
			break;
		}
	}
	
	/**
	 * Clic sur la souris : - Si c'est un bouton de gestion du formulaire - Si
	 * c'est un élément de la liste
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			
			if (e.getSource().equals(this.boutonEditComp)) {				
				ProgramFrame.frame.setEnabled(false);
				try {
					Employee empSelect = getSelected();
					ArrayList<Competence> listCompNonPoss = data.Competences().manquantesEmploye(Integer.toString(empSelect.getID()));
					TableModel compNonPossModel = JTables.Competences(listCompNonPoss).getModel();
					JTable compNonPoss = new JTable(compNonPossModel);
					JTable compPoss = new JTable(competencesModel);
					
					this.gc = new GestionListe(compPoss, compNonPoss, competencesModel, compNonPossModel);
					this.gc.displayGUI();
					
				} catch (DataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			
			/**
			 * TODO Formulaire prêt à l'ajout d'un nouvel élément
			 */
			if (e.getSource().equals(this.boutonNouveau)) {
				ChargementNouveau();
			}
			
			/**
			 * Formulaire prêt à la modification d'un élément existant
			 */
			if (e.getSource().equals(this.boutonModifier)) {
				super.ChargementModification();
			}
			
			/**
			 * TODO Suppression d'un élément existant : doit demander la
			 * confirmation de l'utilisateur
			 */
			if (e.getSource().equals(this.boutonSupprimer)) {
				try {
					Employee empSelect = getSelected();
					data.Employes().supprimer(empSelect);
 					this.modelPersonnel.deleteRowObject(empSelect);
 					this.modelPersonnel.fireTableDataChanged();
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
				try {
					Enregistrement();
				} catch (ParseException e1) {
					System.out.println("Format incorrect: " + e1);
				} catch (DataException e1) {
					System.out.println("Problème d'enregistrement: " + e1);
				}
				ChargementConsultation();
			}
		}
		
		/**
		 * Actualisation des champs du formulaire TODO : Faire passer la
		 * référence de l'objet !
		 */
		if (e.getSource() instanceof JTable) {
			Employee EmployeSelect = getSelected();
			
			this.nom.setText(EmployeSelect.getLastName());
			this.prenom.setText(EmployeSelect.getName());
			this.date.setText(EmployeeDateFormat.format(EmployeSelect.getEntryDate()));
			
			this.listCompEmp = EmployeSelect.getCompetences();
			this.competencesModel = JTables.Competences(listCompEmp).getModel();
			this.competences.setModel(this.competencesModel);
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
