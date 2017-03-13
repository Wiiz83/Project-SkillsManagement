package navigation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
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

import csv.CSVToTable;
import csv.InvalidCSVException;
import csv.InvalidDataException;
import gui.Button;
import gui.Titre;
import models.Competence;

public class Missions extends JPanel implements MouseListener {
	Button	boutonNouveau;
	Button	boutonModifier;
	Button	boutonSupprimer;
	Button	boutonEnregistrer;
	Button	boutonAnnuler;
	JTable	listePersonnel;
	Vector	selectedCells	= new Vector<int[]>();
	
	int			IDSelect;
	JTextField	nom;
	JTextField	prenom;
	JTextField	date;
	JTable		competences;
	
	private static final long serialVersionUID = 1L;
	
	
	public Missions() 
    {   

		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.listePersonnel = new JTable();
		try {
			listePersonnel = CSVToTable.Mission();
			listePersonnel.setFillsViewportHeight(true);
			listePersonnel.addMouseListener(this);
			JScrollPane js = new JScrollPane(listePersonnel);
			js.setVisible(true);
			js.setBounds(10, 10, 300, 600);
			add(js);
			
		} catch (NumberFormatException | IOException | InvalidCSVException | InvalidDataException | ParseException e) {
			// TODO Auto-generated catch block
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
		
		this.prenom = new JTextField();
		this.prenom.addMouseListener(this);
		this.prenom.setBounds(450, 80, 150, 25);
		add(this.prenom);
		
		this.date = new JTextField();
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
		
		ChargementConsultation();
	}
	
	public void ChargementConsultation() {
		this.nom.setEditable(false);
		this.prenom.setEditable(false);
		this.date.setEditable(false);
		this.competences.setEnabled(false);
		
		this.boutonEnregistrer.setVisible(false);
		this.boutonAnnuler.setVisible(false);
		this.boutonNouveau.setVisible(true);
		this.boutonModifier.setVisible(true);
		this.boutonSupprimer.setVisible(true);
		this.listePersonnel.setEnabled(true);
	}
	
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
			// Employee employeSelect = new Employe();
			
			int ligneSelectionne = this.listePersonnel.getSelectedRow();
			this.nom.setText((String) listePersonnel.getValueAt(ligneSelectionne, 0));
			this.prenom.setText((String) listePersonnel.getValueAt(ligneSelectionne, 1));
			this.date.setText((String) listePersonnel.getValueAt(ligneSelectionne, 2));
			this.IDSelect = (int) listePersonnel.getValueAt(ligneSelectionne, 3);
			@SuppressWarnings("unchecked")
			TableModel dataModel = CSVToTable
					.CompetencesEmploye((ArrayList<Competence>) listePersonnel.getValueAt(ligneSelectionne, 4))
					.getModel();
			
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
