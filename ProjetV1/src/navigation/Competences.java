package navigation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import gui.RechercheJTable;
import gui.Titre;
import models.Competence;
import models.CompetenceCode;
import models.Language;

/**
 * Page "Compétences" de l'application contenant la liste de toutes les compétences avec possibilité d'ajout, suppression et modification
 */
public class Competences extends Formulaire {
	
	private static final long							serialVersionUID	= 1L;
	private Data												data;
	private JTable											JTableCompetences;
	private GenericTableModel<Competence>	mJTableCompetences;
	private JTextField 										code;
	private JTable											JTableLangues;
	private TableModel									mJTableLangues;
	private Button											boutonEditLangue;
	private Competence									cloned;
	private ArrayList<Language>						langues;
	private HintTextField 									recherche;
	private Competence 									tempComp;
	
	public Competences(Data data) {
		super();
		
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		try {
			langues = data.Langues().tous();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
		
		this.JTableCompetences = new JTable();
		try {
			this.JTableCompetences = JTables.Competences(data.Competences().tous());
			this.JTableCompetences.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(this.JTableCompetences);
			js.setVisible(true);
			js.setBounds(10, 45, 300, 565);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTableCompetences = (GenericTableModel<Competence>) this.JTableCompetences.getModel();
		
		String indication =  "Rechercher un code ou un libellé...";
		this.recherche = new HintTextField(indication); 
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTableCompetences.getModel());
		this.recherche.getDocument().addDocumentListener(new RechercheJTable(recherche, indication, rowSorter));
		add(this.recherche);
		JTableCompetences.setRowSorter(rowSorter);
		
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
		this.code.setBounds(400, 60, 150, 25);
		add(this.code);
		
		this.JTableLangues = new JTable();
		this.JTableLangues.setFillsViewportHeight(true);
		JScrollPane jsLangues = new JScrollPane(this.JTableLangues);
		jsLangues.setVisible(true);
		jsLangues.setBounds(350, 130, 800, 400);
		add(jsLangues);
		
		this.boutonEditLangue = new Button("/boutons/miniedit.png");
		this.boutonEditLangue.setBounds(1160, 130);
		add(this.boutonEditLangue);
		
		
		JTableCompetences.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
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
		
		this.boutonEditLangue.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				ModifierLibelle();
			}
		});
		
		this.JTableLangues.addMouseListener(new MouseAdapter() {
		   public void mousePressed(MouseEvent me) {
		        if (me.getClickCount() == 2) {
		        	ModifierLibelle();
		        }
		    }
		});
		
		JTableLangues.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            if (JTableLangues.getSelectedRow() > -1) {
	            	boutonEditLangue.setVisible(true);
	            } else {
	            	boutonEditLangue.setVisible(false);
	            }
	        }
	    });
		
		boutonEditLangue.setVisible(false);
		
		composantsEdition.add(this.code);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		composantsEdition.add(this.JTableLangues);
		
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTableCompetences);
		composantsConsultation.add(this.recherche);
		
		super.ChargementConsultation();
	}
	
	/*
	 * Vide tous les champs du formulaire
	 */
	public void VideChamps() {
		this.code.setText("");
		this.JTableLangues.setModel(JTables.LanguesCompetence(null, langues).getModel());
	}
	
	/*
	 * Renvoi la compétence séléctionnée 
	 */
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
	
	/*
	 * Renvoi la ligne de la langue séléctionnée 
	 */
	public int getLangueSelected() {
		try {
			return JTableLangues.getSelectedRow();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Vous devez sélectionner un libellé pour réaliser cette action.","Libellé non séléctionné", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return -1;
		}
	}
	
	/*
	 * Actualise la JTable des langues après une modification 
	 */
	public void ActualisationChamps() {
		
	ArrayList<Language> ListeLangues;
		switch (this.mode) {
			case "nouveau":
				try {
					ListeLangues = data.Langues().tous();
					TableModel TableLangues = JTables.LanguesCompetence(tempComp, ListeLangues).getModel();
					this.JTableLangues.setModel(TableLangues);
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
					} catch (DataException e1) {
						e1.printStackTrace();
					}
				}
				break;
		}
	}
	
	/*
	 * Affichage des détails de la compétence sélectionnée 
	 */
	public void AffichageSelection(){
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

	/*
	 * NOUVEAU : Création d'une nouvelle compétence
	 */
	public void Nouveau(){
		ArrayList<String> liste = new ArrayList<>();
		for(int i = 0; i<langues.size(); i++){
			liste.add("");
		}
		this.tempComp = new Competence((CompetenceCode) null, liste);
		VideChamps();
		super.ChargementModification();
		this.mode = "nouveau";
	}
	
	/*
	 * ENREGISTRER : Enregistrement de la compétence 
	 */
	public void Enregistrer(){
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
	
	/*
	 * MODIFIER : Modification de la compétence séléctionnée 
	 */
	public void Modifier(){
		if (getCompetenceSelected() != null) {
			cloned = (Competence) getCompetenceSelected().clone();
			super.ChargementModification();
			this.code.setEnabled(false);
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
			if (getCompetenceSelected() != null) {
				Competence CompSelect = getCompetenceSelected();
				this.code.setText(CompSelect.getCode().toString());
				CompSelect.setNames(cloned.getNames());
				TableModel mJTableLangues = JTables.LanguesCompetence(CompSelect, langues).getModel();
				this.JTableLangues.setModel(mJTableLangues);
				ChargementConsultation();
			}
			break;
		}
	}
	
	/*
	 * SUPPRIMER : Suppression de la compétence séléctionnée 
	 */
	public void Supprimer(){
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
	
	/*
	 * Modifier le libellé d'une compétence 
	 */
	public void ModifierLibelle(){
		int rowIndex = JTableLangues.getSelectedRow();
		if(rowIndex > -1){
			ProgramFrame.getFrame().setEnabled(false);
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
}
