package navigation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import csv.InvalidDataException;
import data.Data;
import data.DataException;
import gui.Button;
import gui.Formulaire;
import gui.GenericTableModel;
import gui.JTables;
import gui.Titre;
import models.Competence;
import models.CompetenceCode;
import models.Language;

/**
 * Page "Compétences" de l'application contenant la liste de toutes les
 * compétences avec possibilité d'ajout, suppression et modification
 * 
 */
public class Competences extends Formulaire implements MouseListener {
	
	private static final long	serialVersionUID	= 1L;
	private Data				data;
	
	JTable							JTableCompetences;
	GenericTableModel<Competence>	mJTableCompetences;
	
	JTextField code;
	
	JTable		JTableLangues;
	TableModel	mJTableLangues;
	
	Button	boutonNouveau;
	Button	boutonModifier;
	Button	boutonSupprimer;
	Button	boutonEnregistrer;
	Button	boutonAnnuler;
	Button	boutonEditLangue;
	
	ArrayList<Language> langues;
	
	public Competences(Data data) {
		this.data = data;
		try {
			langues = data.Langues().tous();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.JTableCompetences = new JTable();
		try {
			this.JTableCompetences = JTables.Competences(data.Competences().tous());
			this.JTableCompetences.setFillsViewportHeight(true);
			this.JTableCompetences.addMouseListener(this);
			JScrollPane jsCompetences = new JScrollPane(this.JTableCompetences);
			jsCompetences.setVisible(true);
			jsCompetences.setBounds(10, 10, 300, 600);
			add(jsCompetences);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTableCompetences = (GenericTableModel<Competence>) this.JTableCompetences.getModel();
		
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
		labelNom.setBounds(350, 60, 150, 25);
		add(labelNom);
		
		JLabel labelCompetences = new JLabel("Liste des langues :");
		labelCompetences.setBounds(350, 100, 150, 25);
		add(labelCompetences);
		
		this.code = new JTextField();
		this.code.addMouseListener(this);
		this.code.setBounds(400, 60, 150, 25);
		add(this.code);
		
		this.JTableLangues = new JTable();
		this.JTableLangues.addMouseListener(this);
		this.JTableLangues.setFillsViewportHeight(true);
		JScrollPane jsLangues = new JScrollPane(this.JTableLangues);
		jsLangues.setVisible(true);
		jsLangues.setBounds(350, 130, 350, 400);
		add(jsLangues);
		
		this.boutonEditLangue = new Button("/boutons/miniedit.png");
		this.boutonEditLangue.setBounds(710, 130);
		this.boutonEditLangue.addMouseListener(this);
		add(this.boutonEditLangue);
		
		composantsEdition.add(this.boutonEditLangue);
		composantsEdition.add(this.code);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		composantsEdition.add(this.JTableLangues);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTableCompetences);
		
		super.ChargementConsultation();
	}
	
	public void VideChamps() {
		this.code.setText("");
		this.JTableLangues.setModel(JTables.LanguesCompetence(null, langues).getModel());
	}
	
	public Competence getCompetenceSelected() {
		try {
			this.mJTableCompetences = (GenericTableModel<Competence>) this.JTableCompetences.getModel();
			return this.mJTableCompetences.getRowObject(
					this.JTableCompetences.convertRowIndexToModel(this.JTableCompetences.getSelectedRow())
			);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez sélectionner une compétence pour réaliser cette action.",
					"Compétence non séléctionnée", JOptionPane.WARNING_MESSAGE
			);
			e.printStackTrace();
			return null;
		}
	}
	
	public void ActualisationChamps() {
		if (getCompetenceSelected() != null) {
			Competence compSelect = getCompetenceSelected();
			ArrayList<Language> ListeLangues;
			try {
				ListeLangues = data.Langues().tous();
				TableModel TableLangues = JTables.LanguesCompetence(compSelect, ListeLangues).getModel();
				this.JTableLangues.setModel(TableLangues);
			} catch (DataException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			
			if (e.getSource().equals(this.boutonEditLangue)) {
				if (getCompetenceSelected() != null) {
					Competence compSelect = getCompetenceSelected();
					int rowIndex = JTableLangues.getSelectedRow();
					CompetencesEditLangue fel = new CompetencesEditLangue(rowIndex, JTableLangues, compSelect, this);
					fel.displayGUI();
				}
			}
			
			if (e.getSource().equals(this.boutonNouveau)) {
				VideChamps();
				super.ChargementModification();
				this.mode = "nouveau";
			}
			
			if (e.getSource().equals(this.boutonModifier)) {
				if (getCompetenceSelected() != null) {
					super.ChargementModification();
					this.code.setEnabled(false);
				}
			}
			
			if (e.getSource().equals(this.boutonSupprimer)) {
				try {
					if (getCompetenceSelected() != null) {
						int n = JOptionPane.showConfirmDialog(
								new JFrame(), "Voulez vraiment supprimer cette compétence ?",
								"Confirmation de suppression", JOptionPane.YES_NO_OPTION
						);
						if (n == JOptionPane.YES_OPTION) {
							Competence compSelect = getCompetenceSelected();
							data.Competences().supprimer(compSelect);
							this.mJTableCompetences.deleteRowObject(compSelect);
							this.mJTableCompetences.fireTableDataChanged();
							VideChamps();
						}
					}
				} catch (DataException e2) {
					e2.printStackTrace();
				}
			}
			
			if (e.getSource().equals(this.boutonAnnuler)) {
				switch (this.mode) {
				case "nouveau":
					VideChamps();
					ChargementConsultation();
					break;
				
				case "modification":
					if (getCompetenceSelected() != null) {
						Competence CompSelect = getCompetenceSelected();
						this.code.setText(CompSelect.getCode().toString());
						
						ArrayList<Language> ListeLangues;
						try {
							ListeLangues = data.Langues().tous();
							TableModel mJTableLangues = JTables.LanguesCompetence(CompSelect, ListeLangues).getModel();
							this.JTableLangues.setModel(mJTableLangues);
						} catch (DataException e1) {
							e1.printStackTrace();
						}
						ChargementConsultation();
					}
					break;
				}
			}
			
			if (e.getSource().equals(this.boutonEnregistrer)) {
				try {
					switch (this.mode) {
					case "nouveau":
						ArrayList<String> noms = new ArrayList<>();
						int nb_langues = langues.size();
						for (int i = 0; i < nb_langues; i++)
							noms.add((String) JTableLangues.getModel().getValueAt(i, 1));
						Competence c = null;
						try {
							c = new Competence(this.code.getText(), noms);
						} catch (InvalidDataException e2) {
							e2.printStackTrace();
							return;
						}
						
						data.Competences().ajouter(c);
						this.mJTableCompetences.addRowObject(c);
						this.mJTableCompetences.fireTableDataChanged();
						
						ChargementConsultation();
						break;
					
					case "modification":
						if (getCompetenceSelected() != null) {
							Competence compSelect = getCompetenceSelected();
							CompetenceCode cc;
							try {
								cc = new CompetenceCode(this.code.getText());
								compSelect.setCode(cc);
								data.Competences().modifier(compSelect);
								this.mJTableCompetences.fireTableDataChanged();
								ChargementConsultation();
							} catch (InvalidDataException e1) {
								e1.printStackTrace();
							}
						}
						break;
					}
				} catch (DataException e1) {
					System.out.println("Problème d'enregistrement: ");
					e1.printStackTrace();
				}
			}
		}
		
		if (e.getSource() instanceof JTable) {
			
			if (e.getSource().equals(this.JTableCompetences)) {
				Competence CompSelect = getCompetenceSelected();
				this.code.setText(CompSelect.getCode().toString());
				
				ArrayList<Language> ListeLangues;
				try {
					ListeLangues = data.Langues().tous();
					this.mJTableLangues = JTables.LanguesCompetence(CompSelect, ListeLangues).getModel();
					this.JTableLangues.setModel(mJTableLangues);
				} catch (DataException e1) {
					e1.printStackTrace();
				}
			}
			
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
