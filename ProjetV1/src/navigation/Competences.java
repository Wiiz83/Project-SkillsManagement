package navigation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import csv.CSVException;
import csv.InvalidDataException;
import data.Data;
import data.DataException;
import gui.Button;
import gui.GenericTableModel;
import gui.Titre;
import models.Competence;
import models.CompetenceCode;
import models.Employee;

/**
 * Page "Comp�tences" de l'application contenant la liste de toutes les
 * comp�tences avec possibilit� d'ajout, suppression et modification
 * 
 */
public class Competences extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	Competence		compSelect;
	JTable					listeCompetences;
	JScrollPane			jsCompetences;
	Vector<int[]>	selectedCells	= new Vector<int[]>();
	String					mode;
	private Data		data;
	int					IDSelect;
	
	Button					boutonNouveau;
	Button					boutonModifier;
	Button					boutonSupprimer;
	Button					boutonEnregistrer;
	Button					boutonAnnuler;
	Button					boutonAddLangue;
	Button					boutonDeleteLangue;
	JTextField			code;
	JTable					listeLangues;
	TableModel			modelLangues;

	public Competences(Data data) {
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		/**
		 * Affichage de la liste gauche
		 */
		AffichageListe();

		
		/**
		 * Cr�ation et positionnement des boutons de gestion de formulaire
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
		 * Cr�ation et positionnement des �l�ments du formulaire
		 */
		Titre titre = new Titre(" D�tails de la comp�tence :");
		titre.setBounds(330, 10, 930, 20);
		add(titre);
		
		JLabel labelNom = new JLabel("Code :");
		labelNom.setBounds(350, 60, 150, 25);
		add(labelNom);
		
		JLabel labelCompetences = new JLabel("Liste des langues :");
		labelCompetences.setBounds(350, 100, 150, 25);
		add(labelCompetences);
		
		this.code = new JTextField();
		this.code.addMouseListener(this);
		this.code.setBounds(400, 60, 150, 25);
		add(this.code);
		
		this.listeLangues = new JTable();
		this.listeLangues.addMouseListener(this);
		this.listeLangues.setFillsViewportHeight(true);
		JScrollPane jsLangues = new JScrollPane(this.listeLangues);
		jsLangues.setVisible(true);
		jsLangues.setBounds(350, 130, 750, 400);
		add(jsLangues);
		
		this.boutonAddLangue = new Button("/boutons/miniadd.png");
		this.boutonAddLangue.setBounds(710, 170);
		this.boutonAddLangue.addMouseListener(this);
		add(this.boutonAddLangue);
		
		this.boutonDeleteLangue = new Button("/boutons/minidelete.png");
		this.boutonDeleteLangue.setBounds(710, 210);
		this.boutonDeleteLangue.addMouseListener(this);
		add(this.boutonDeleteLangue);
		
		ChargementConsultation();
	}
	
	/**
	 * Affiche la liste
	 */
	public void AffichageListe() {
		this.listeCompetences = new JTable();
		try {
			this.listeCompetences = JTables.Competences(data.Competences().tous());
			this.listeCompetences.setFillsViewportHeight(true);
			this.listeCompetences.addMouseListener(this);
			this.jsCompetences = new JScrollPane(this.listeCompetences);
			this.jsCompetences.setVisible(true);
			this.jsCompetences.setBounds(10, 10, 300, 600);
			add(this.jsCompetences);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprime la liste et reinitialise l'ID s�l�ctionn�
	 */
	public void Reinitialiser() {
		remove(this.listeCompetences);
		remove(this.jsCompetences);
		this.IDSelect = 0;
	}
	
	/**
	 * Mode Consultation : - Lecture des informations d�taill�s de l'�l�ment
	 * s�lectionn� - Les �l�ments du formulaire ne sont pas modifiables
	 */
	public void ChargementConsultation() {
		this.code.setEditable(false);
		this.listeLangues.setEnabled(false);
		
		this.boutonEnregistrer.setVisible(false);
		this.boutonAnnuler.setVisible(false);
		this.boutonNouveau.setVisible(true);
		this.boutonModifier.setVisible(true);
		this.boutonSupprimer.setVisible(true);
		this.listeCompetences.setEnabled(true);
		
		this.mode = "consultation";
	}
	
	/**
	 * Mode Modification : - Possibilit� de modifier les informations d�taill�s
	 * de l'�l�ment s�lectionn� - Les �l�ments de la liste ne sont pas
	 * s�l�ctionnables
	 */
	public void ChargementModification() {
		this.code.setEditable(true);
		this.listeLangues.setEnabled(true);
		
		this.boutonEnregistrer.setVisible(true);
		this.boutonAnnuler.setVisible(true);
		this.boutonNouveau.setVisible(false);
		this.boutonModifier.setVisible(false);
		this.boutonSupprimer.setVisible(false);
		this.listeCompetences.setEnabled(false);
	}
	
	/**
	 * Mode Nouveau : - On vide tous les �l�ments contenus dans les champs du
	 * formulaire - Les �l�ments de la liste ne sont pas s�l�ctionnables
	 */
	public void ChargementNouveau() {
		this.code.setText("");

		/*
		 *  TODO : Cr�ation de la liste de langue a partir d'une arraylist
		 *  this.modelLangues = JTables.Competences(new ArrayList<Competence>()).getModel();
		 *  this.competences.setModel(this.competencesModel);
		 */

		this.mode = "nouveau";		
		ChargementModification();
	}
	
	/**
	 * Enregistrement des informations dans le cas d'une modification ou d'un
	 * ajout
	 * 
	 * @throws ParseException
	 * @throws InvalidDataException 
	 * @throws CSVException
	 */
	public void Enregistrement() throws ParseException, DataException, InvalidDataException {
		
		CompetenceCode compCode = new CompetenceCode(this.code.getText());
		
		switch (this.mode) {
		case "nouveau":

			/*
			 * TODO : Mettre Arraylist de langue dans le constructeur 
			 * Competence nouvComp = new Competence(compCode, names)
			 * data.Competences().ajouter(nouvComp);	
			 */
			break;
		
		case "modification":
			this.compSelect.setCode(compCode);
		
			/*
			 *  TODO : Arraylist de langues
			 *  this.compSelect.setNames(names);
			 */
			data.Competences().modifier(this.compSelect);
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
	 * c'est un �l�ment de la liste
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			
			/**
			 * TODO Formulaire pr�t � l'ajout d'un nouvel �l�ment
			 */
			if (e.getSource().equals(this.boutonNouveau)) {
				ChargementNouveau();
			}
			
			/**
			 * Formulaire pr�t � la modification d'un �l�ment existant
			 */
			if (e.getSource().equals(this.boutonModifier)) {
				ChargementModification();
			}
			
			/**
			 * TODO Suppression d'un �l�ment existant : doit demander la
			 * confirmation de l'utilisateur
			 */
			if (e.getSource().equals(this.boutonSupprimer)) {
				try {
					Competence comp = data.Competences().parID(Integer.toString(this.IDSelect));
					data.Competences().supprimer(comp);
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
			 * TODO On enregistre les modifications ou le nouvel �l�ment
			 */
			if (e.getSource().equals(this.boutonEnregistrer)) {
				try {
					Enregistrement();
				} catch (ParseException e1) {
					System.out.println("Format incorrect: " + e1);
				} catch (DataException e1) {
					System.out.println("Probl�me d'enregistrement: " + e1);
				} catch (InvalidDataException e1) {
					System.out.println("Donn�e incorrecte: " + e1);
				}
				ChargementConsultation();
			}
		}
		
		/**
		 * Actualisation des champs du formulaire TODO : Faire passer la
		 * r�f�rence de l'objet !
		 */
		if (e.getSource() instanceof JTable) {
			GenericTableModel<Competence> model = (GenericTableModel<Competence>) this.listeCompetences.getModel();
			this.compSelect = model.getRowObject(this.listeCompetences.convertRowIndexToModel(listeCompetences.getSelectedRow()));
			
			this.code.setText(this.compSelect.getCode().toString());
			
			ArrayList<String> listLanguesComp = this.compSelect.getNames();
			/*
			 * TODO : faire une methode pour les langues dans la classe JTables
			 * this.modelLangues = JTables.Competences(listCompEmp).getModel();
			 * 	this.competences.setModel(this.competencesModel);
			 */


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
