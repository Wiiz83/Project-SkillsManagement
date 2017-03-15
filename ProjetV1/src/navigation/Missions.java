package navigation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import csv.CSVException;
import gui.Button;
import gui.Titre;
import models.Competence;

public class Missions extends JPanel implements MouseListener {
	
	Button	boutonNouveau;
	Button	boutonModifier;
	Button	boutonSupprimer;
	Button	boutonEnregistrer;
	Button	boutonAnnuler;
	JTable	listeMissions;
	Vector	selectedCells	= new Vector<int[]>();
	
	int					IDSelect;
	JTextField			nom;
	JComboBox<String>	statut;
	
	JTable competences;
	
	private static final long serialVersionUID = 1L;
	
	public Missions() {
		
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.listeMissions = new JTable();
		try {
			listeMissions = JTableRequests.toutesLesMissions();
			listeMissions.setFillsViewportHeight(true);
			listeMissions.addMouseListener(this);
			JScrollPane js = new JScrollPane(listeMissions);
			js.setVisible(true);
			js.setBounds(10, 10, 300, 600);
			add(js);
			
		} catch (CSVException e) {
			e.printStackTrace();
		}
		
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
		
		this.competences = new JTable();
		this.competences.addMouseListener(this);
		this.competences.setFillsViewportHeight(true);
		JScrollPane js = new JScrollPane(this.competences);
		js.setVisible(true);
		js.setBounds(350, 170, 350, 350);
		add(js);
		
		ChargementConsultation();
	}
	
	public void ChargementConsultation() {
		this.nom.setEditable(false);
		this.competences.setEnabled(false);
		
		this.boutonEnregistrer.setVisible(false);
		this.boutonAnnuler.setVisible(false);
		this.boutonNouveau.setVisible(true);
		this.boutonModifier.setVisible(true);
		this.boutonSupprimer.setVisible(true);
		this.listeMissions.setEnabled(true);
	}
	
	public void ChargementModification() {
		this.nom.setEditable(true);
		this.competences.setEnabled(true);
		
		this.boutonEnregistrer.setVisible(true);
		this.boutonAnnuler.setVisible(true);
		this.boutonNouveau.setVisible(false);
		this.boutonModifier.setVisible(false);
		this.boutonSupprimer.setVisible(false);
		this.listeMissions.setEnabled(false);
	}
	
	public void ChargementNouveau() {
		
	}
	
	public void Enregistrement() {
		// this.IDSelect = (int) listePersonnel.getValueAt(ligneSelectionne, 3);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		batch.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		batch.setColor(Color.WHITE);
		batch.fillRect(330, 40, 930, 510);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			if (e.getSource().equals(this.boutonNouveau)) {
				ChargementNouveau();
			}
			if (e.getSource().equals(this.boutonModifier)) {
				ChargementModification();
			}
			if (e.getSource().equals(this.boutonSupprimer)) {
				// ChargementSuppression();
			}
			if (e.getSource().equals(this.boutonAnnuler)) {
				ChargementConsultation();
			}
			if (e.getSource().equals(this.boutonEnregistrer)) {
				// enregistrer
				Enregistrement();
				ChargementConsultation();
			}
		}
		
		if (e.getSource() instanceof JTable) {
			int ligneSelectionne = this.listeMissions.getSelectedRow();
			this.nom.setText((String) listeMissions.getValueAt(ligneSelectionne, 0));
			this.IDSelect = (int) listeMissions.getValueAt(ligneSelectionne, 3);
			@SuppressWarnings("unchecked")
			TableModel dataModel = ListToJTable
					.competences((ArrayList<Competence>) listeMissions.getValueAt(ligneSelectionne, 4)).getModel();
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
