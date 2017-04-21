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
import models.Employee;

/**
 * Page "Personnel" de l'application contenant la liste du personnel avec
 * possibilité d'ajout, suppression et modification
 * 
 * TODO ATTENTION : Implémentation différente de Compétences et Personnel -
 * Utilisation de la méthode AffichageListe() qui actualise la liste en cas de
 * modification
 * 
 */
public class Personnel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	
	Button							boutonEditComp;
	GestionListe 					gc;
	ArrayList<Competence> listCompEmp;
	
	Employee			empSelect;
	Button				boutonNouveau;
	Button				boutonModifier;
	Button				boutonSupprimer;
	Button				boutonEnregistrer;
	Button				boutonAnnuler;
	JTable				listePersonnel;
	Vector<int[]>		selectedCells		= new Vector<int[]>();
	JScrollPane			jsPersonnel;
	int					IDSelect;
	JTextField			nom;
	JTextField			prenom;
	JTextField			date;
	JTable				competences;
	TableModel			competencesModel;
	String				mode;
	private Data		data;
	SimpleDateFormat	GuiDateFormat		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat	EmployeeDateFormat	= new SimpleDateFormat("dd/MM/yyyy");
	
	public Personnel(Data data) {
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		/**
		 * Affichage de la liste gauche
		 */
		AffichageListe();
		
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
				
		ChargementConsultation();
	}
	
	/**
	 * Affiche la liste
	 */
	public void AffichageListe() {
		this.listePersonnel = new JTable();
		try {
			this.listePersonnel = JTables.Employes(data.Employes().tous());
			this.listePersonnel.setFillsViewportHeight(true);
			this.listePersonnel.addMouseListener(this);
			this.jsPersonnel = new JScrollPane(this.listePersonnel);
			this.jsPersonnel.setVisible(true);
			this.jsPersonnel.setBounds(10, 10, 300, 600);
			add(this.jsPersonnel);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime la liste et reinitialise l'ID séléctionné
	 */
	public void Reinitialiser() {
		remove(this.listePersonnel);
		remove(this.jsPersonnel);
		this.IDSelect = 0;
	}
	
	/**
	 * Mode Consultation : - Lecture des informations détaillés de l'élément
	 * sélectionné - Les éléments du formulaire ne sont pas modifiables
	 */
	public void ChargementConsultation() {
		this.nom.setEditable(false);
		this.prenom.setEditable(false);
		this.date.setEditable(false);
		//this.competences.setEnabled(false);
		
		this.boutonEnregistrer.setVisible(false);
		this.boutonAnnuler.setVisible(false);
		this.boutonNouveau.setVisible(true);
		this.boutonModifier.setVisible(true);
		this.boutonSupprimer.setVisible(true);
		this.listePersonnel.setEnabled(true);
		
		this.mode = "consultation";
	}
	
	/**
	 * Mode Modification : - Possibilité de modifier les informations détaillés
	 * de l'élément sélectionné - Les éléments de la liste ne sont pas
	 * séléctionnables
	 */
	public void ChargementModification() {
		this.nom.setEditable(true);
		this.prenom.setEditable(true);
		this.date.setEditable(true);
		this.competences.setEnabled(true);
		
		this.boutonEnregistrer.setVisible(true);
		this.boutonAnnuler.setVisible(true);
		this.boutonNouveau.setVisible(false);
		this.boutonModifier.setVisible(false);
		this.boutonSupprimer.setVisible(false);
		this.listePersonnel.setEnabled(false);
		
		this.mode = "modification";
	}
	
	/**
	 * Mode Nouveau : - On vide tous les éléments contenus dans les champs du
	 * formulaire - Les éléments de la liste ne sont pas séléctionnables
	 */
	public void ChargementNouveau() {
		this.nom.setText("");
		this.prenom.setText("");
		this.date.setText("");
		this.competencesModel = JTables.Competences(new ArrayList<Competence>()).getModel();
		this.competences.setModel(this.competencesModel);
		
		ChargementModification();
		this.mode = "nouveau";
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
			this.empSelect.setLastName(this.nom.getText());
			this.empSelect.setName(this.prenom.getText());
			this.empSelect.setEntryDate(this.date.getText(), "yyyy-MM-dd");
			data.Employes().modifier(this.empSelect);
			break;
		
		default:
			break;
		}
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
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			
			if (e.getSource().equals(this.boutonEditComp)) {				
				ProgramFrame.frame.setEnabled(false);
				try {
					ArrayList<Competence> listCompNonPoss = data.Competences().manquantesEmploye(Integer.toString(this.empSelect.getID()));
					TableModel compNonPossModel = JTables.Competences(listCompNonPoss).getModel();
					JTable compNonPoss = new JTable(compNonPossModel);
					JTable compPoss = new JTable(competencesModel);
					
					this.gc = new GestionListe(compPoss, compNonPoss);
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
				ChargementModification();
			}
			
			/**
			 * TODO Suppression d'un élément existant : doit demander la
			 * confirmation de l'utilisateur
			 */
			if (e.getSource().equals(this.boutonSupprimer)) {
				try {
					Employee emp = data.Employes().parID(Integer.toString(IDSelect));
					data.Employes().supprimer(emp);
					Reinitialiser();
					AffichageListe();
				} catch (DataException e1) {
					e1.printStackTrace();
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
			GenericTableModel<Employee> model = (GenericTableModel<Employee>) listePersonnel.getModel();
			this.empSelect = model.getRowObject(listePersonnel.convertRowIndexToModel(listePersonnel.getSelectedRow()));
			
			this.nom.setText(this.empSelect.getLastName());
			this.prenom.setText(this.empSelect.getName());
			this.date.setText(EmployeeDateFormat.format(this.empSelect.getEntryDate()));
			
			this.listCompEmp = this.empSelect.getCompetences();
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
