package navigation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import gui.Titre;
import models.Competence;
import models.Mission;

/**
 * Page "Missions" de l'application contenant la liste de toutes les missions
 * avec possibilité d'ajout, suppression et modification
 * 
 */
public class Missions extends JPanel implements MouseListener {
	
	Button						boutonNouveau;
	Button						boutonModifier;
	Button						boutonSupprimer;
	Button						boutonEnregistrer;
	Button						boutonAnnuler;
	Button						boutonAddComp;
	Button						boutonDeleteComp;
	Button						boutonAddEmp;
	Button						boutonDeleteEmp;
	JFormattedTextField			dateD;
	JFormattedTextField			duree;
	JFormattedTextField			nombre;
	JTable						listeMissions;
	Vector<int[]>				selectedCells		= new Vector<int[]>();
	int							IDSelect;
	JTextField					nom;
	JComboBox<String>			statut;
	JTable						competences;
	JTable						employes;
	private static final long	serialVersionUID	= 1L;
	
	Data data;
	
	public Missions(Data data) {
		
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
		labelNom.setBounds(350, 50, 150, 25);
		add(labelNom);
		
		JLabel labelDate = new JLabel("Nombre de personnes :");
		labelDate.setBounds(350, 80, 150, 25);
		add(labelDate);
		
		JLabel labelDebut = new JLabel("Date de début :");
		labelDebut.setBounds(800, 50, 150, 25);
		add(labelDebut);
		
		JLabel labelDuree = new JLabel("Durée :");
		labelDuree.setBounds(800, 80, 150, 25);
		add(labelDuree);
		
		JLabel labelCompetences = new JLabel("Liste des compétences :");
		labelCompetences.setBounds(350, 140, 150, 25);
		add(labelCompetences);
		
		JLabel labelEmployes = new JLabel("Liste des employés :");
		labelEmployes.setBounds(800, 140, 150, 25);
		add(labelEmployes);
		
		this.nom = new JTextField();
		this.nom.addMouseListener(this);
		this.nom.setBounds(500, 50, 150, 25);
		add(this.nom);
		
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatterNumber = new NumberFormatter(format);
		formatterNumber.setValueClass(Integer.class);
		formatterNumber.setMinimum(0);
		formatterNumber.setMaximum(Integer.MAX_VALUE);
		formatterNumber.setAllowsInvalid(false);
		formatterNumber.setCommitsOnValidEdit(true);
		this.nombre = new JFormattedTextField(formatterNumber);
		this.nombre.addMouseListener(this);
		this.nombre.setBounds(500, 80, 150, 25);
		add(this.nombre);
		
		MaskFormatter formatterDuree;
		try {
			formatterDuree = new MaskFormatter("##:##");
			formatterDuree.setPlaceholderCharacter('_');
			this.duree = new JFormattedTextField(formatterDuree);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.duree.addMouseListener(this);
		this.duree.setBounds(900, 80, 150, 25);
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
		this.dateD.setBounds(900, 50, 150, 25);
		add(this.dateD);
		
		this.competences = new JTable();
		this.competences.addMouseListener(this);
		this.competences.setFillsViewportHeight(true);
		JScrollPane jsComp = new JScrollPane(this.competences);
		jsComp.setVisible(true);
		jsComp.setBounds(350, 170, 350, 350);
		add(jsComp);
		
		this.employes = new JTable();
		this.employes.addMouseListener(this);
		this.employes.setFillsViewportHeight(true);
		JScrollPane jsEmp = new JScrollPane(this.employes);
		jsEmp.setVisible(true);
		jsEmp.setBounds(800, 170, 350, 350);
		add(jsEmp);
		
		this.boutonAddComp = new Button("/boutons/miniadd.png");
		this.boutonAddComp.setBounds(710, 170);
		this.boutonAddComp.addMouseListener(this);
		add(this.boutonAddComp);
		
		this.boutonDeleteComp = new Button("/boutons/minidelete.png");
		this.boutonDeleteComp.setBounds(710, 210);
		this.boutonDeleteComp.addMouseListener(this);
		add(this.boutonDeleteComp);
		
		this.boutonAddEmp = new Button("/boutons/miniadd.png");
		this.boutonAddEmp.setBounds(1160, 170);
		this.boutonAddEmp.addMouseListener(this);
		add(this.boutonAddEmp);
		
		this.boutonDeleteEmp = new Button("/boutons/minidelete.png");
		this.boutonDeleteEmp.setBounds(1160, 210);
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
	
	/**
	 * Mode Nouveau : - On vide tous les éléments contenus dans les champs du
	 * formulaire - Les éléments de la liste ne sont pas séléctionnables
	 */
	public void ChargementNouveau() {
		
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
				// ChargementSuppression();
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
			}
		}
		
		/**
		 * Actualisation des champs du formulaire TODO : Faire passer la
		 * référence de l'objet !
		 */
		if (e.getSource() instanceof JTable) {
			int ligneSelectionne = this.listeMissions.getSelectedRow();
			this.nom.setText((String) listeMissions.getValueAt(ligneSelectionne, 0));
			this.IDSelect = (int) listeMissions.getValueAt(ligneSelectionne, 3);
			@SuppressWarnings("unchecked")
			TableModel dataModel = JTables
					.Competences((ArrayList<Competence>) listeMissions.getValueAt(ligneSelectionne, 4)).getModel();
			this.competences.setModel(dataModel);
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
