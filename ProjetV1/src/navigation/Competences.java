package navigation;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
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
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import csv.InvalidDataException;
import data.Data;
import data.DataException;
import gui.Button;
import gui.Formulaire;
import gui.GenericTableModel;
import gui.HintTextField;
import gui.JTables;
import gui.ProgramFrame;
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
	
	Button				boutonNouveau;
	Button				boutonModifier;
	Button				boutonSupprimer;
	Button				boutonEnregistrer;
	Button				boutonAnnuler;
	Button				boutonEditLangue;
	Competence			cloned;
	ArrayList<Language>	langues;
	
	HintTextField recherche;
	Competence tempComp;
	
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
			JScrollPane js = new JScrollPane(this.JTableCompetences);
			js.setVisible(true);
			js.setBounds(10, 45, 300, 565);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTableCompetences = (GenericTableModel<Competence>) this.JTableCompetences.getModel();
		this.JTableCompetences.getColumnModel().getColumn(0).setPreferredWidth(75);
		this.JTableCompetences.getColumnModel().getColumn(1).setPreferredWidth(225);
		
		String indication =  "Rechercher un code ou un libellé...";
		this.recherche = new HintTextField(indication); 
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTableCompetences.getModel());
		
		this.recherche.getDocument().addDocumentListener(new DocumentListener(){

	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                String text = recherche.getText();

	                if (text.equals(indication)) {
	                    rowSorter.setRowFilter(null);
	                } else {
	                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	                }
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                String text = recherche.getText();

	                if (text.equals(indication)) {
	                    rowSorter.setRowFilter(null);
	                } else {
	                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	                }
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	            }

	        });
		add(this.recherche);
		JTableCompetences.setRowSorter(rowSorter);
		
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
		jsLangues.setBounds(350, 130, 800, 400);
		add(jsLangues);
		
		this.boutonEditLangue = new Button("/boutons/miniedit.png");
		this.boutonEditLangue.setBounds(1160, 130);
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
		this.JTableLangues.getColumnModel().getColumn(0).setPreferredWidth(200);
		this.JTableLangues.getColumnModel().getColumn(1).setPreferredWidth(600);
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
	
	
	public int getLangueSelected() {
		try {
			return JTableLangues.getSelectedRow();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Vous devez sélectionner un libellé pour réaliser cette action.","Libellé non séléctionné", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return -1;
		}
	}
	
	public void ActualisationChamps() {
		
	ArrayList<Language> ListeLangues;
		switch (this.mode) {
			case "nouveau":
				try {
					ListeLangues = data.Langues().tous();
					TableModel TableLangues = JTables.LanguesCompetence(tempComp, ListeLangues).getModel();
					this.JTableLangues.setModel(TableLangues);
					this.JTableLangues.getColumnModel().getColumn(0).setPreferredWidth(200);
					this.JTableLangues.getColumnModel().getColumn(1).setPreferredWidth(600);
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				break;
			
			case "modification":
				Competence CompSelect = getCompetenceSelected();
				if (CompSelect != null) {
					try {
						ListeLangues = data.Langues().tous();
						TableModel TableLangues = JTables.LanguesCompetence(CompSelect, ListeLangues).getModel();
						this.JTableLangues.setModel(TableLangues);
						this.JTableLangues.getColumnModel().getColumn(0).setPreferredWidth(200);
						this.JTableLangues.getColumnModel().getColumn(1).setPreferredWidth(600);
					} catch (DataException e1) {
						e1.printStackTrace();
					}
				}
				break;
		}
	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			
			if (e.getSource().equals(this.boutonEditLangue)) {
				
				int rowIndex = JTableLangues.getSelectedRow();
				if(rowIndex > -1){
					switch (this.mode) {
					case "nouveau":
						CompetencesEditLangue felnouv = new CompetencesEditLangue(rowIndex, JTableLangues, this.tempComp, this);
						felnouv.displayGUI();
						break;
					
					case "modification":
						Competence CompSelect = getCompetenceSelected();
						if (CompSelect != null) {
							CompetencesEditLangue fel = new CompetencesEditLangue(rowIndex, JTableLangues, CompSelect, this);
							fel.displayGUI();
						}
						break;
					}
				}
			}
			
			if (e.getSource().equals(this.boutonNouveau)) {
				ArrayList<String> liste = new ArrayList<>();
				for(int i = 0; i<langues.size(); i++){
					liste.add("");
				}
				this.tempComp = new Competence((CompetenceCode) null, liste);
				VideChamps();
				super.ChargementModification();
				this.mode = "nouveau";
			}
			
			if (e.getSource().equals(this.boutonModifier)) {
				if (getCompetenceSelected() != null) {
					cloned = (Competence) getCompetenceSelected().clone();
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
						CompSelect.setNames(cloned.getNames());
						TableModel mJTableLangues = JTables.LanguesCompetence(CompSelect, langues).getModel();
						this.JTableLangues.setModel(mJTableLangues);
						this.JTableLangues.getColumnModel().getColumn(0).setPreferredWidth(200);
						this.JTableLangues.getColumnModel().getColumn(1).setPreferredWidth(600);
						ChargementConsultation();
					}
					break;
				}
			}
			
			if (e.getSource().equals(this.boutonEnregistrer)) {
				if((this.code.getText().equals(""))){
					JOptionPane.showMessageDialog(
							new JFrame(), "Vous devez renseigner toutes les informations pour enregistrer.",
							"Informations non renseignés", JOptionPane.WARNING_MESSAGE
					);
				} else {
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
					this.JTableLangues.getColumnModel().getColumn(0).setPreferredWidth(200);
					this.JTableLangues.getColumnModel().getColumn(1).setPreferredWidth(600);
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
