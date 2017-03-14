package navigation;

import java.awt.BorderLayout;
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

import csv.CSVObjects;
import csv.CSVToTable;
import csv.InvalidCSVException;
import csv.InvalidDataException;
import gui.Button;
import gui.Titre;
import models.Competence;
import models.Employee;

public class Competences extends JPanel implements MouseListener {

	Button	boutonNouveau;
	Button	boutonModifier;
	Button	boutonSupprimer;
	Button	boutonEnregistrer;
	Button	boutonAnnuler;
	JTable	listeCompetences;
	Vector	selectedCells	= new Vector<int[]>();

	JTextField	code;
	JTable		listeLangues;

	private static final long serialVersionUID = 1L;
	
	public Competences () 
    {   
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));

		this.listeCompetences = new JTable();
		try {
			listeCompetences = CSVToTable.Competences();
			listeCompetences.setFillsViewportHeight(true);
			listeCompetences.addMouseListener(this);
			JScrollPane js = new JScrollPane(listeCompetences);
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
		
		Titre titre = new Titre(" Détails de la compétence :");
		titre.setBounds(330, 10, 930, 20);
		add(titre);
		
		JLabel labelNom = new JLabel("Code :");
		labelNom.setBounds(350, 50, 150, 25);
		add(labelNom);
		
		JLabel labelCompetences = new JLabel("Liste des langues :");
		labelCompetences.setBounds(350, 80, 150, 25);
		add(labelCompetences);
		
		this.code = new JTextField();
		this.code.addMouseListener(this);
		this.code.setBounds(400, 50, 150, 25);
		add(this.code);
				
		this.listeLangues = new JTable();
		this.listeLangues.addMouseListener(this);
		this.listeLangues.setFillsViewportHeight(true);
		JScrollPane js = new JScrollPane(this.listeLangues);
		js.setVisible(true);
		js.setBounds(350, 110, 350, 350);
		add(js);
		
		ChargementConsultation();
	}
	
	public void ChargementConsultation() {
		this.code.setEditable(false);
		this.listeLangues.setEnabled(false);
		
		this.boutonEnregistrer.setVisible(false);
		this.boutonAnnuler.setVisible(false);
		this.boutonNouveau.setVisible(true);
		this.boutonModifier.setVisible(true);
		this.boutonSupprimer.setVisible(true);
		this.listeCompetences.setEnabled(true);
	}
	
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
	
	public void ChargementNouveau() {
	}
	
	public void Enregistrement() {
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
			int ligneSelectionne = this.listeCompetences.getSelectedRow();
			this.code.setText((listeCompetences.getValueAt(ligneSelectionne, 0)).toString());
			@SuppressWarnings("unchecked")
			TableModel dataModel = CSVToTable
					.LanguesCompetence((ArrayList<String>) this.listeCompetences.getValueAt(ligneSelectionne, 1))
					.getModel();
			this.listeLangues.setModel(dataModel);
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
