package navigation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;
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
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;
import models.Recommendation;
import models.Status;

/**
 * Page "Missions" de l'application contenant la liste de toutes les missions
 * avec possibilité d'ajout, suppression et modification
 * 
 */
public class Missions extends Formulaire implements MouseListener {
	
	private static final long	serialVersionUID	= 1L;
	private Data				data;
	
	Button	boutonNouveau;
	Button	boutonModifier;
	Button	boutonSupprimer;
	Button	boutonEnregistrer;
	Button	boutonAnnuler;
	Button	boutonAddComp;
	Button	boutonEditComp;
	Button	boutonDeleteComp;
	Button	boutonAddEmp;
	Button	boutonAddEmpRecom;
	Button  missionTermine;
	
	JTable						JTableMissions;
	GenericTableModel<Mission>	mJTableMissions;
	
	JTable										JTableCompetences;
	GenericTableModel<CompetenceRequirement>	mJTableCompetences;
	
	JTable						JTableEmployes;
	GenericTableModel<Employee>	mJTableEmployes;
	
	JTextField			nom;
	JComboBox<Status>	statut;
	JFormattedTextField	dateD;
	JFormattedTextField	duree;
	JFormattedTextField	nombre;
	
	SimpleDateFormat	GuiDateFormat		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat	MissionDateFormat	= new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat	MissionDureeFormat	= new SimpleDateFormat("dd/MM/yyyy");
	
	 HintTextField recherche;
	 JComboBox<String>	filtre;
	
	public Missions(Data data) {
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.JTableMissions = new JTable();
		try {
			JTableMissions = JTables.Missions(data.Missions().tous());
			JTableMissions.setFillsViewportHeight(true);
			JTableMissions.addMouseListener(this);
			JScrollPane js = new JScrollPane(JTableMissions);
			js.setVisible(true);
			js.setBounds(10, 80, 300, 520);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTableMissions = (GenericTableModel<Mission>) this.JTableMissions.getModel();
		
		
		String indication =  "Rechercher un nom de mission...";
		this.recherche = new HintTextField(indication); 
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTableMissions.getModel());
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

		
		this.filtre = new JComboBox<>();
		this.filtre.addItem("Toutes");
		this.filtre.addItem(Status.EN_COURS.toString());
		this.filtre.addItem(Status.PLANIFIEE.toString());
		this.filtre.addItem(Status.PREPARATION.toString());
		this.filtre.addItem(Status.TERMINEE.toString());
		this.filtre.setBounds(10, 45, 300, 25);
		this.filtre.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(filtre.getSelectedItem().toString() != "Tous"){
			    	rowSorter.setRowFilter(RowFilter.regexFilter(filtre.getSelectedItem().toString(), 2));
		    	}
		    }
		});
		add(this.filtre);
		
		JTableMissions.setRowSorter(rowSorter);
		
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
		
		Titre titre = new Titre(" Détails de la mission :");
		titre.setBounds(330, 10, 930, 20);
		add(titre);
		
		JLabel labelNom = new JLabel("Nom :");
		labelNom.setBounds(350, 60, 150, 25);
		add(labelNom);
		
		JLabel labelStatut = new JLabel("Statut :");
		labelStatut.setBounds(350, 100, 150, 25);
		add(labelStatut);
		
		JLabel labelNbPersonnes = new JLabel("Personnes requises :");
		labelNbPersonnes.setBounds(350, 140, 150, 25);
		add(labelNbPersonnes);
		
		JLabel labelDebut = new JLabel("Date de début :");
		labelDebut.setBounds(350, 180, 150, 25);
		add(labelDebut);
		
		JLabel labelDuree = new JLabel("Durée (en jours) :");
		labelDuree.setBounds(350, 220, 150, 25);
		add(labelDuree);
		
		JLabel labelCompetences = new JLabel("Liste des compétences :");
		labelCompetences.setBounds(650, 50, 150, 25);
		add(labelCompetences);
		
		JLabel labelEmployes = new JLabel("Liste des employés :");
		labelEmployes.setBounds(650, 250, 150, 25);
		add(labelEmployes);
		
		this.nom = new JTextField();
		this.nom.addMouseListener(this);
		this.nom.setBounds(400, 60, 150, 25);
		add(this.nom);
		
		this.statut = new JComboBox<>(Status.values());
		this.statut.addMouseListener(this);
		this.statut.setBounds(400, 100, 150, 25);
		add(this.statut);
		
		MaskFormatter formatterNombre;
		try {
			formatterNombre = new MaskFormatter("####");
			this.nombre = new JFormattedTextField(formatterNombre);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		this.nombre.addMouseListener(this);
		this.nombre.setBounds(480, 140, 70, 25);
		add(nombre);
		
		MaskFormatter formatterDuree;
		try {
			formatterDuree = new MaskFormatter("####");
			this.duree = new JFormattedTextField(formatterDuree);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		this.duree.addMouseListener(this);
		this.duree.setBounds(450, 220, 100, 25);
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
		this.dateD.setBounds(440, 180, 110, 25);
		add(this.dateD);
		
		this.JTableCompetences = new JTable();
		this.JTableCompetences.addMouseListener(this);
		this.JTableCompetences.setFillsViewportHeight(true);
		JScrollPane jsComp = new JScrollPane(this.JTableCompetences);
		jsComp.setVisible(true);
		jsComp.setBounds(650, 80, 500, 150);
		add(jsComp);
		
		this.JTableEmployes = new JTable();
		this.JTableEmployes.addMouseListener(this);
		this.JTableEmployes.setFillsViewportHeight(true);
		JScrollPane jsEmp = new JScrollPane(this.JTableEmployes);
		jsEmp.setVisible(true);
		jsEmp.setBounds(650, 280, 500, 200);
		add(jsEmp);
		
		this.boutonAddComp = new Button("/boutons/miniadd.png");
		this.boutonAddComp.setBounds(1160, 80);
		this.boutonAddComp.addMouseListener(this);
		add(this.boutonAddComp);
		
		this.boutonEditComp = new Button("/boutons/miniedit.png");
		this.boutonEditComp.setBounds(1160, 120);
		this.boutonEditComp.addMouseListener(this);
		add(this.boutonEditComp);
		
		this.boutonDeleteComp = new Button("/boutons/minidelete.png");
		this.boutonDeleteComp.setBounds(1160, 160);
		this.boutonDeleteComp.addMouseListener(this);
		add(this.boutonDeleteComp);
		
		this.boutonAddEmp = new Button("/boutons/miniedit.png");
		this.boutonAddEmp.setBounds(1160, 280);
		this.boutonAddEmp.addMouseListener(this);
		add(this.boutonAddEmp);
		
		this.boutonAddEmpRecom = new Button("/boutons/minirecommandation.png");
		this.boutonAddEmpRecom.setBounds(1160, 320);
		this.boutonAddEmpRecom.addMouseListener(this);
		add(this.boutonAddEmpRecom);
		
		composantsEdition.add(this.JTableEmployes);
		composantsEdition.add(this.JTableCompetences);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.statut);
		composantsEdition.add(this.dateD);
		composantsEdition.add(this.duree);
		composantsEdition.add(this.nombre);
		composantsEdition.add(this.boutonAddComp);
		composantsEdition.add(this.boutonDeleteComp);
		composantsEdition.add(this.boutonEditComp);
		composantsEdition.add(this.boutonAddEmp);
		composantsEdition.add(this.boutonAddEmpRecom);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTableMissions);
		
		super.ChargementConsultation();
	}
	
	public void VideChamps() {
		this.nom.setText("");
		this.dateD.setText("");
		this.duree.setText("");
		this.nombre.setText("");
		this.statut.setSelectedIndex(0);
		this.mJTableEmployes = (GenericTableModel<Employee>) JTables.Employes(new ArrayList<Employee>()).getModel();
		this.JTableEmployes.setModel(mJTableEmployes);
		this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) JTables
				.CompetencesRequises(new ArrayList<CompetenceRequirement>()).getModel();
		this.JTableCompetences.setModel(mJTableCompetences);
	}
	
	public Mission getMissionSelected() {
		try {
			this.mJTableMissions = (GenericTableModel<Mission>) this.JTableMissions.getModel();
			return this.mJTableMissions
					.getRowObject(this.JTableMissions.convertRowIndexToModel(this.JTableMissions.getSelectedRow()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez sélectionner une mission pour réaliser cette action.",
					"Mission non séléctionnée", JOptionPane.WARNING_MESSAGE
			);
			e.printStackTrace();
			return null;
		}
	}
	
	public CompetenceRequirement getCompetenceSelected() {
		try {
			this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) this.JTableCompetences.getModel();
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
	
	public Employee getEmployeSelected() {
		try {
			this.mJTableEmployes = (GenericTableModel<Employee>) this.JTableEmployes.getModel();
			return this.mJTableEmployes
					.getRowObject(this.JTableEmployes.convertRowIndexToModel(this.JTableEmployes.getSelectedRow()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez sélectionner un employé pour réaliser cette action.",
					"Employé non séléctionné", JOptionPane.WARNING_MESSAGE
			);
			e.printStackTrace();
			return null;
		}
	}
	
	// TODO : Appeler à chaque modification de champ et affectation d'employés
	// ou compétences req.
	private void updateComboBox() {
		Mission mission = getMissionSelected();
		statut.removeAllItems();
		if (mission == null)
			return;
		statut.addItem(mission.getStatus());
		if (mission.getStatus() == Status.PREPARATION)
			statut.addItem(Status.PLANIFIEE);
	}
	
	// TODO: Appler avant l'enregistrement
	private void updateMissionStatus() {
		Status selected = (Status) statut.getSelectedItem();
		Mission mission = getMissionSelected();
		if (selected == Status.PLANIFIEE && mission.getStatus() == Status.PREPARATION)
			mission.planifier();
		if (selected == Status.PREPARATION && mission.getForcer_planification()
				&& mission.getStatus() == Status.PLANIFIEE)
			mission.deplanifier();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			if (e.getSource().equals(this.boutonAddComp)) {
				Mission missSelect = getMissionSelected();
				if (missSelect != null) {
					JFrame frame;
					try {
						frame = new MissionsAddCompetence<Mission, Employee>(data, getMissionSelected(), Employee.class);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.pack();
						frame.setVisible(true);
					} catch (HeadlessException | DataException e1) {
						e1.printStackTrace();
					}
				}
			}
			
			if (e.getSource().equals(this.boutonEditComp)) {
				CompetenceRequirement compSelect = getCompetenceSelected();
				if (compSelect != null) {
					int rowIndex = this.JTableCompetences.getSelectedRow();
					MissionsEditCompetence fel = new MissionsEditCompetence(
							rowIndex, JTableCompetences, compSelect, this
					);
					fel.displayGUI();
				}
			}
			
			if (e.getSource().equals(this.boutonDeleteComp)) {
				Mission missSelect = getMissionSelected();
				if (missSelect != null) {
					CompetenceRequirement compSelect = getCompetenceSelected();
					if (compSelect != null) {
						int n = JOptionPane.showConfirmDialog(
								new JFrame(), "Voulez vraiment supprimer cette compétence ?", "Confirmation de suppression",
								JOptionPane.YES_NO_OPTION
						);
						if (n == JOptionPane.YES_OPTION) {
							missSelect.removeCompetenceReq(compSelect);
							this.mJTableCompetences.deleteRowObject(compSelect);
							this.mJTableCompetences.fireTableDataChanged();
							VideChamps();
						}
					}
				}
			}
			
			if (e.getSource().equals(this.boutonAddEmp)) {
				ProgramFrame.getFrame().setEnabled(false);
				switch (this.mode) {
				case "nouveau":
					try {
						ArrayList<Employee> listEmpNonPoss = data.Employes().tous();
						GenericTableModel<Employee> empNonPossModel = (GenericTableModel<Employee>) JTables.Employes(listEmpNonPoss).getModel();
						JTable empNonPoss = new JTable(empNonPossModel);
						JTable empPoss = new JTable(mJTableEmployes);
						PersonnelEditCompetence gestionListe = new PersonnelEditCompetence(empPoss, empNonPoss, mJTableEmployes, empNonPossModel);
						gestionListe.displayGUI();
					} catch (DataException e1) {
						e1.printStackTrace();
					}
					break;
				
				case "modification":
					Mission missSelect = getMissionSelected();
					if (missSelect != null) {

							// ArrayList<Employee> listEmpNonPoss = data.Missions();
							//
							// Récuperer les Employés manquants
							// GenericTableModel<Employee> empNonPossModel = (GenericTableModel<Employee>) JTables.Employes(listEmpNonPoss).getModel();
							// JTable empNonPoss = new JTable(empNonPossModel);
							
							/*
							 * 
							 * 							JTable empPoss = new JTable(mJTableEmployes);
									PersonnelEditCompetence gestionListe = new PersonnelEditCompetence(empPoss, empNonPoss, mJTableEmployes, empNonPossModel);
									gestionListe.displayGUI();
							 * 
							 * 
							 */

							/*
							 * 							GenericTableModel<Competence> compNonPossModel = (GenericTableModel<Competence>) JTables.Competences(listCompNonPoss).getModel();	 
							JTable compNonPoss = new JTable(compNonPossModel);
							JTable compPoss = new JTable(mJTableCompetences);
							PersonnelEditCompetence gestionListe = new PersonnelEditCompetence(compPoss, compNonPoss, mJTableCompetences, compNonPossModel);
							gestionListe.displayGUI();
							 * 
							 * 
							 */

					}
					break;
				}
			}
			
			if (e.getSource().equals(this.boutonAddEmpRecom)) {
				ProgramFrame.getFrame().setEnabled(false);
				switch (this.mode) {
				case "nouveau":
					break;
				
				case "modification":
					Mission missSelect = getMissionSelected();
					if (missSelect != null) {

					}
					break;
				}
				
			}
			
			if (e.getSource().equals(this.boutonNouveau)) {
				VideChamps();
				ChargementModification();
				this.mode = "nouveau";
			}
			
			/**
			 * Formulaire prêt à la modification d'un élément existant
			 */
			if (e.getSource().equals(this.boutonModifier)) {
				Mission missSelect = getMissionSelected();
				if (missSelect != null) {
					if(missSelect.estModifiable()){
						super.ChargementModification();
					} else if(missSelect.getStatus() == Status.EN_COURS) {
						JOptionPane.showMessageDialog(
								new JFrame(), "La mission est en cours : elle n'est donc plus modifiable.",
								"Mission en cours", JOptionPane.WARNING_MESSAGE
						);
					} else if(missSelect.getStatus() == Status.TERMINEE) {
						JOptionPane.showMessageDialog(
								new JFrame(), "La mission est terminée : elle n'est donc plus modifiable.",
								"Mission terminée", JOptionPane.WARNING_MESSAGE
						);
					}
					
				}
			}
			
			/**
			 * Suppression d'un élément existant : doit demander la
			 * confirmation de l'utilisateur
			 */
			if (e.getSource().equals(this.boutonSupprimer)) {
				Mission missSelect = getMissionSelected();
				if (missSelect != null) {
					int n = JOptionPane.showConfirmDialog(
							new JFrame(), "Voulez vraiment supprimer cette mission ?", "Confirmation de suppression",
							JOptionPane.YES_NO_OPTION
					);
					if (n == JOptionPane.YES_OPTION) {
						try {
							data.Missions().supprimer(missSelect);
							this.mJTableMissions.deleteRowObject(missSelect);
							this.mJTableMissions.fireTableDataChanged();
							VideChamps();
						} catch (DataException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			
			/**
			 * On annule toutes les modifications faites par l'utilisateur depuis l'activation du mode modification / du mode nouveau
			 */
			if (e.getSource().equals(this.boutonAnnuler)) {
				switch (this.mode) {
				case "nouveau":
					VideChamps();
					ChargementConsultation();
				break;
				
				case "modification":
					Mission missSelect = getMissionSelected();
					if (missSelect != null) {
						this.nom.setText(missSelect.getNomM());
						this.dateD.setText(MissionDateFormat.format(missSelect.getDateDebut()));
						this.duree.setText(Integer.toString(missSelect.getDuree()));
						this.nombre.setText(Integer.toString(missSelect.getNbPersReq()));
						this.statut.setSelectedItem(missSelect.getStatus());
						
						ArrayList<CompetenceRequirement> listCompMiss = missSelect.getCompReq();
						TableModel tmComp = JTables.CompetencesRequises(listCompMiss).getModel();
						this.JTableCompetences.setModel(tmComp);
						
						ArrayList<Employee> listEmpMiss = missSelect.getAffEmp();
						TableModel tmEmp = JTables.Employes(listEmpMiss).getModel();
						this.JTableEmployes.setModel(tmEmp);
						ChargementConsultation();
					}
					break;
				}
			}
			
			/**
			 * TODO On enregistre les modifications ou le nouvel élément
			 */
			if (e.getSource().equals(this.boutonEnregistrer)) {
				if((this.dateD.getText().equals("")) ||  (this.duree.getText().equals("")) || (this.nombre.getText().equals(""))  ||  (this.nom.getText().equals(""))){
					JOptionPane.showMessageDialog(
							new JFrame(), "Vous devez renseigner toutes les informations pour enregistrer.",
							"Informations non renseignés", JOptionPane.WARNING_MESSAGE
					);
				} else {
				
				Date dateD = new Date(this.dateD.getText());
				int duree = Integer.parseInt(this.duree.getText());
				int nb = Integer.parseInt(this.nombre.getText());
				String nom = this.nom.getText();
				
				Mission mission = new Mission(nom, dateD, duree, nb);
				ChargementConsultation();
				
				switch (this.mode) {
				case "nouveau":
					/*
					 * Mission nouvEmp = new Mission(this.nom.getText(),
					 * this.dateD.getText(), this.duree.getText(),
					 * this.nombre.getText());
					 * 
					 * 
					 * if (this.JTableEmployes != null &&
					 * this.JTableEmployes.getModel() != null) {
					 * ArrayList<Employee> listEmp = new ArrayList<Object>;
					 * 
					 * for(int row = 0; row < table.getRowCount(); row++) {
					 * for(int column = 0; column = table.getColumnCount();
					 * column++) { list.add(table.getValueAt(row, column)); } }
					 * 
					 * nouvEmp.setAffEmp(affEmp); }
					 * 
					 * if(){
					 * 
					 * }
					 * 
					 * data.Missions().ajouter(nouvEmp); break;
					 * 
					 * case "modification":
					 * /*this.empSelect.setLastName(this.nom.getText());
					 * this.empSelect.setName(this.prenom.getText());
					 * this.empSelect.setEntryDate(this.date.getText(),
					 * "yyyy-MM-dd"); data.Employes().modifier(this.empSelect);
					 */
					break;
				
				default:
					break;
				}
			}
			}
		}
		
		/**
		 * Actualisation des champs du formulaire
		 */
		if (e.getSource() instanceof JTable) {
			Mission missSelect = getMissionSelected();
			
			this.nom.setText(missSelect.getNomM());
			this.dateD.setText(MissionDateFormat.format(missSelect.getDateDebut()));
			this.duree.setText(Integer.toString(missSelect.getDuree()));
			this.nombre.setText(Integer.toString(missSelect.getNbPersReq()));
			this.statut.setSelectedItem(missSelect.getStatus());
			
			ArrayList<CompetenceRequirement> listCompMiss = missSelect.getCompReq();
			TableModel tmComp = JTables.CompetencesRequises(listCompMiss).getModel();
			this.JTableCompetences.setModel(tmComp);
			
			ArrayList<Employee> listEmpMiss = missSelect.getAffEmp();
			TableModel tmEmp = JTables.Employes(listEmpMiss).getModel();
			this.JTableEmployes.setModel(tmEmp);
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
