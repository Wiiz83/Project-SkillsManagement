package navigation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import com.github.lgooddatepicker.components.DatePicker;
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
import models.CompetenceRequirement;
import models.Employee;
import models.Mission;
import models.Status;

/**
 * Page "Missions" de l'application contenant la liste de toutes les missions
 * avec possibilité d'ajout, suppression et modification
 * 
 */
public class Missions extends Formulaire {
	
	private static final long							serialVersionUID	= 1L;
	private Data										data;
	private Button										boutonAddComp;
	private Button										boutonEditComp;
	private Button										boutonDeleteComp;
	private Button										boutonAddEmp;
	private Button										boutonDeleteEmp;
	private JTextField									nom;
	private JComboBox<Status>							statut;
	private DatePicker									dateD;
	private JFormattedTextField							duree;
	private JFormattedTextField							nombre;
	private HintTextField								recherche;
	private JComboBox<String>							filtre;
	private JTable										JTableMissions;
	private GenericTableModel<Mission>					mJTableMissions;
	private JTable										JTableCompetences;
	private GenericTableModel<CompetenceRequirement>	mJTableCompetences;
	private JTable										JTableEmployes;
	private GenericTableModel<Employee>					mJTableEmployes;
	private Mission										missionEnCours;
	private JLabel 										dateDeFinPrevue;
	private JLabel 										dateDeFinReelle;
	private Mission										cloned;
	private NumberFormat numberFormat = NumberFormat.getNumberInstance();
	
	public Missions(Data data) {
		super();
		
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.JTableMissions = new JTable();
		try {
			JTableMissions = JTables.Missions(data.Missions().tous());
			JTableMissions.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(JTableMissions);
			js.setVisible(true);
			js.setBounds(10, 80, 300, 520);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTableMissions = (GenericTableModel<Mission>) this.JTableMissions.getModel();
		
		String indication = "Rechercher un nom de mission...";
		this.recherche = new HintTextField(indication);
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTableMissions.getModel());
		this.recherche.getDocument().addDocumentListener(new RechercheJTable(recherche, indication, rowSorter));
		add(this.recherche);
		
		this.filtre = new JComboBox<>();
		this.filtre.addItem("Toutes");
		this.filtre.addItem(Status.EN_COURS.toString());
		this.filtre.addItem(Status.PLANIFIEE.toString());
		this.filtre.addItem(Status.PREPARATION.toString());
		this.filtre.addItem(Status.TERMINEE.toString());
		this.filtre.setBounds(10, 45, 300, 25);
		this.filtre.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (filtre.getSelectedItem().toString() != "Toutes") {
							rowSorter.setRowFilter(RowFilter.regexFilter(filtre.getSelectedItem().toString(), 2));
						} else {
							rowSorter.setRowFilter(null);
						}
					}
				}
		);
		add(this.filtre);
		JTableMissions.setRowSorter(rowSorter);
		
		
		Titre titre = new Titre(" Détails de la mission :");
		titre.setBounds(330, 10, 930, 20);
		add(titre);
		
		
		JPanel panelPadding = new JPanel();
		panelPadding.setBounds(350, 50, 300, 480);
		panelPadding.setBackground(Color.WHITE);
		panelPadding.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informations", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));


		JPanel jpanelInfo = new JPanel(new GridLayout(15,1,0,4));		
		jpanelInfo.setPreferredSize(new Dimension(280, 450));
		jpanelInfo.setBackground(Color.WHITE);

        jpanelInfo.add(new JLabel("Nom : "));
    	this.nom = new JTextField();
        jpanelInfo.add(nom);
        
        jpanelInfo.add(new JLabel("Statut :"));
        this.statut = new JComboBox<>();
        jpanelInfo.add(statut);
        
        jpanelInfo.add(new JLabel("Personnes requises : "));
        this.nombre = new JFormattedTextField(numberFormat);
        jpanelInfo.add(nombre);
        
        jpanelInfo.add(new JLabel("Date de début : "));
        this.dateD = new DatePicker();
        jpanelInfo.add(dateD);
        
        jpanelInfo.add(new JLabel("Durée (en jours) :"));
        this.duree = new JFormattedTextField(numberFormat);
        jpanelInfo.add(duree);
		
		dateDeFinPrevue = new JLabel("");
		dateDeFinPrevue.setHorizontalAlignment(JLabel.CENTER);
		dateDeFinPrevue.setVerticalAlignment(JLabel.CENTER);
		jpanelInfo.add(dateDeFinPrevue);
		
		dateDeFinReelle = new JLabel("");
		dateDeFinReelle.setHorizontalAlignment(JLabel.CENTER);
		dateDeFinReelle.setVerticalAlignment(JLabel.CENTER);
		jpanelInfo.add(dateDeFinReelle);

        panelPadding.add(jpanelInfo);
		this.add(panelPadding);

		
		JPanel panelPadding3 = new JPanel();
		panelPadding3.setBounds(660, 50, 590, 240);
		panelPadding3.setBackground(Color.WHITE);
		panelPadding3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des compétences", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
		
		JPanel panelCompetences = new JPanel(new BorderLayout());		
		panelCompetences.setPreferredSize(new Dimension(480, 200));
		panelCompetences.setBackground(Color.WHITE);

		this.JTableCompetences = new JTable();
		this.JTableCompetences.setFillsViewportHeight(true);
		JScrollPane jsComp = new JScrollPane(this.JTableCompetences);
		jsComp.setVisible(true);
		panelCompetences.add(jsComp);
		
		panelPadding3.add(panelCompetences);
		
		JPanel panelCompetencesButtons = new JPanel(new FlowLayout());		
		panelCompetencesButtons.setPreferredSize(new Dimension(60, 100));
		panelCompetencesButtons.setBackground(Color.WHITE);

		this.boutonAddComp = new Button("/boutons/miniadd.png");
		this.boutonAddComp.setBounds(1160, 80);
		panelCompetencesButtons.add(this.boutonAddComp);
		
		this.boutonEditComp = new Button("/boutons/miniedit.png");
		this.boutonEditComp.setBounds(1160, 120);
		panelCompetencesButtons.add(this.boutonEditComp);
		
		this.boutonDeleteComp = new Button("/boutons/minidelete.png");
		this.boutonDeleteComp.setBounds(1160, 160);
		panelCompetencesButtons.add(this.boutonDeleteComp);
		
		panelPadding3.add(panelCompetencesButtons);
		
		
		this.add(panelPadding3);
		
		
		
		
		
		JPanel panelPadding2 = new JPanel();
		panelPadding2.setBounds(660, 290, 590, 240);
		panelPadding2.setBackground(Color.WHITE);
		panelPadding2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des employés", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
		
		JPanel panelEmployes = new JPanel(new BorderLayout());		
		panelEmployes.setPreferredSize(new Dimension(480, 200));
		panelEmployes.setBackground(Color.WHITE);

		this.JTableEmployes = new JTable();
		this.JTableEmployes.setFillsViewportHeight(true);
		JScrollPane jsEmp = new JScrollPane(this.JTableEmployes);
		jsEmp.setVisible(true);
		panelEmployes.add(jsEmp);
		

		panelPadding2.add(panelEmployes);
		
		JPanel panelEmployesButtons = new JPanel(new FlowLayout());		
		panelEmployesButtons.setPreferredSize(new Dimension(60, 100));
		panelEmployesButtons.setBackground(Color.WHITE);

		
		this.boutonAddEmp = new Button("/boutons/miniadd.png");
		panelEmployesButtons.add(this.boutonAddEmp);
		
		this.boutonDeleteEmp = new Button("/boutons/minidelete.png");
		panelEmployesButtons.add(this.boutonDeleteEmp);
		
		panelPadding2.add(panelEmployesButtons);
		
		
		this.add(panelPadding2);

		
		JTableMissions.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	AffichageSelection();
	        }
	    });
		
		this.boutonNouveau.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Nouveau();
					}
				}
		);
		
		this.boutonModifier.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Modifier();
					}
				}
		);
		
		this.boutonSupprimer.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Supprimer();
					}
				}
		);
		
		this.boutonEnregistrer.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Enregistrer();
					}
				}
		);
		
		this.boutonAnnuler.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Annuler();
					}
				}
		);
		
		this.boutonAddComp.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						AjouterCompetence();
					}
				}
		);
		
		this.boutonEditComp.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						ModifierCompetence();
					}
				}
		);
		
		this.boutonDeleteComp.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						SupprimerCompetence();
					}
				}
		);
		
		this.boutonAddEmp.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						AjouterEmploye();
					}
				}
		);
		
		this.boutonDeleteEmp.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						SupprimerEmploye();
					}
				}
		);
		
		JTableCompetences.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            if (JTableCompetences.getSelectedRow() > -1) {
	            	boutonEditComp.setVisible(true);
	            } else {
	            	boutonEditComp.setVisible(false);
	            }
	        }
	    });
		
		boutonEditComp.setVisible(false);

		
		composantsEdition.add(this.JTableEmployes);
		composantsEdition.add(this.JTableCompetences);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.statut);
		composantsEdition.add(this.dateD);
		composantsEdition.add(this.duree);
		composantsEdition.add(this.nombre);
		composantsEdition.add(this.boutonAddComp);
		composantsEdition.add(this.boutonDeleteComp);
		composantsEdition.add(this.boutonAddEmp);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		composantsEdition.add(this.boutonDeleteEmp);
		
		composantsConsultation.add(this.recherche);
		composantsConsultation.add(this.filtre);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTableMissions);
		
		super.ChargementConsultation();
	}
	
	/*
	 * Vide tous les champs du formulaire
	 */
	public void VideChamps() {
		this.nom.setText("");
		this.dateD.setText("");
		this.duree.setText("");
		dateDeFinPrevue.setText("");
		dateDeFinReelle.setText("");
		this.nombre.setText("");
		statut.removeAllItems();
		statut.addItem(Status.PREPARATION);
		this.mJTableEmployes = (GenericTableModel<Employee>) JTables.Employes(new ArrayList<Employee>()).getModel();
		this.JTableEmployes.setModel(mJTableEmployes);
		this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) JTables.CompetencesRequises(new ArrayList<CompetenceRequirement>()).getModel();
		this.JTableCompetences.setModel(mJTableCompetences);
	}
	
	/*
	 * Renvoi la mission séléctionnée
	 */
	public Mission getMissionSelected() {
		try {
			this.mJTableMissions = (GenericTableModel<Mission>) this.JTableMissions.getModel();
			return this.mJTableMissions
					.getRowObject(this.JTableMissions.convertRowIndexToModel(this.JTableMissions.getSelectedRow()));
		} catch (Exception e) {
			return null;
		}
	}
	
	public Mission getMissionEnCours() {
		return missionEnCours;
	}
	
	/*
	 * Renvoi la compétence séléctionnée
	 */
	public CompetenceRequirement getCompetenceSelected() {
		try {
			this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) this.JTableCompetences.getModel();
			return this.mJTableCompetences.getRowObject(
					this.JTableCompetences.convertRowIndexToModel(this.JTableCompetences.getSelectedRow())
			);
		} catch (Exception e) {
			return null;
		}
	}
	

	/*
	 * Renvoi l'employé séléctionné
	 */
	public Employee getEmployeSelected() {
		try {
			this.mJTableEmployes = (GenericTableModel<Employee>) this.JTableEmployes.getModel();
			return this.mJTableEmployes
					.getRowObject(this.JTableEmployes.convertRowIndexToModel(this.JTableEmployes.getSelectedRow()));
		} catch (Exception e) {
			return null;
		}
	}
	
	void updateComboBox() {
		statut.removeAllItems();
		if (missionEnCours == null)
			return;
		statut.addItem(missionEnCours.getStatus());
		if (missionEnCours.getStatus() == Status.PREPARATION)
			statut.addItem(Status.PLANIFIEE);
	}
	
	private void updateMissionStatus() {
		Status selected = (Status) statut.getSelectedItem();
		if (selected == Status.PLANIFIEE && missionEnCours.getStatus() == Status.PREPARATION)
			missionEnCours.planifier();
	}
	
	public void updateCompReq() {
		ArrayList<CompetenceRequirement> listCompMiss = missionEnCours.getCompReq();
		this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) JTables.CompetencesRequises(listCompMiss).getModel();
		this.JTableCompetences.setModel(mJTableCompetences);
		this.mJTableCompetences.fireTableDataChanged();
	}
	
	/*
	 * Affichage des détails de la mission sélectionnée
	 */
	public void AffichageSelection() {
		missionEnCours = getMissionSelected();
		
		
		if (missionEnCours != null) {
			this.nom.setText(missionEnCours.getNomM());
			
			Date date =  missionEnCours.getDateDebut();
			Instant instant = date.toInstant();
			ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
			LocalDate d = zdt.toLocalDate();
			this.dateD.setDate(d);

			this.duree.setText(Integer.toString(missionEnCours.getDuree()));
			this.nombre.setText(Integer.toString(missionEnCours.getNbPersReq()));
			this.statut.setSelectedItem(missionEnCours.getStatus());

			if(missionEnCours.getStatus() == Status.TERMINEE){
				this.dateDeFinReelle.setVisible(true);
			    SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd MMM yyyy");
			    String sdateDeFinReelle = formatter.format(missionEnCours.getDateFinReelle());
			    dateDeFinReelle.setText("Date de fin réelle le " + sdateDeFinReelle);
			} else {
				this.dateDeFinReelle.setVisible(false);
			}
		    SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd MMM yyyy");
		    String dateFinPrevue = formatter.format(missionEnCours.getDateFin());
			dateDeFinPrevue.setText("Date de fin prévue le " + dateFinPrevue);
			
			ArrayList<CompetenceRequirement> listCompMiss = missionEnCours.getCompReq();
			TableModel tmComp = JTables.CompetencesRequises(listCompMiss).getModel();
			this.JTableCompetences.setModel(tmComp);
			
			ArrayList<Employee> listEmpMiss = missionEnCours.getAffEmp();
			TableModel tmEmp = JTables.Employes(listEmpMiss).getModel();
			this.JTableEmployes.setModel(tmEmp);
			
			this.mJTableEmployes = (GenericTableModel<Employee>) this.JTableEmployes.getModel();
			this.mJTableCompetences = (GenericTableModel<CompetenceRequirement>) this.JTableCompetences.getModel();
			
			updateComboBox();
		}
	}
	
	/*
	 * NOUVEAU : Création d'une nouvelle mission
	 */
	public void Nouveau() {
		JTableMissions.getSelectionModel().clearSelection();
		this.missionEnCours = new Mission("", models.Cal.today(), 0, 0);
		VideChamps();
		ChargementModification();
		this.mode = "nouveau";
	}
	
	/*
	 * ENREGISTRER : Enregistrement de la mission
	 */
	public void Enregistrer() {
		if ((this.dateD.getText().equals("")) || (this.duree.getText().equals("")) || (this.nombre.getText().equals(""))
				|| (this.nom.getText().equals(""))) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez renseigner toutes les informations pour enregistrer.",
					"Informations non renseignés", JOptionPane.WARNING_MESSAGE
			);
		} else {
			
			ZonedDateTime zdt = this.dateD.getDate().atStartOfDay(ZoneId.systemDefault());
			Instant instant = zdt.toInstant();
			java.util.Date dateD = java.util.Date.from(instant);
			
			String strDuree = this.duree.getText().replaceAll("\\D+", "");
			int duree = Integer.parseInt(strDuree);
			
			String strNombre = this.nombre.getText().replaceAll("\\D+", "");
			int nb = Integer.parseInt(strNombre);
			
			String nom = this.nom.getText();
			ArrayList<Employee> presentEmployes = mJTableEmployes.getArraylist();
			ArrayList<CompetenceRequirement> presentCompetences = mJTableCompetences.getArraylist();

			missionEnCours.setDateDebut(dateD);
			missionEnCours.setDuree(duree);
			missionEnCours.setNbPersReq(nb);
			missionEnCours.setNomM(nom);
			missionEnCours.setAffEmp(presentEmployes);
			missionEnCours.setCompReq(presentCompetences);
			
			updateMissionStatus();
			
			switch (this.mode) {
			case "nouveau":
				try {
					missionEnCours.resetDateFinReelle();
					data.Missions().ajouter(missionEnCours);
					this.mJTableMissions.addRowObject(missionEnCours);
					this.mJTableMissions.fireTableDataChanged();
					super.ChargementConsultation();
				} catch (DataException e) {
					e.printStackTrace();
				}
				break;
			
			case "modification":
				try {
					data.Missions().modifier(missionEnCours);
					this.mJTableMissions.fireTableDataChanged();
					super.ChargementConsultation();
				} catch (DataException e) {
					e.printStackTrace();
				}
				break;
			}
		
			updateComboBox();
		}
	}
	
	/*
	 * MODIFIER : Modification de l'employé séléctionné
	 */
	public void Modifier() {
		this.missionEnCours = getMissionSelected();
		
	 
		if (missionEnCours != null) {
			cloned = (Mission) missionEnCours.clone();
			if (missionEnCours.estModifiable()) {
				super.ChargementModification();
			} else if (missionEnCours.getStatus() == Status.EN_COURS) {

					DatePicker dp = new DatePicker();
					String message ="La mission est en cours : elle n'est donc plus modifiable. \n Si elle est terminée, veuillez entrer la date de fin :";
					Object[] params = {message,dp};
					int n = JOptionPane.showConfirmDialog(null,params,"Cette mission est elle terminée ?", JOptionPane.YES_NO_OPTION);
					
					if (n == JOptionPane.YES_OPTION) {
			
						Date DateDeFin = java.sql.Date.valueOf(dp.getDate());
						Date DateDeDebut = missionEnCours.getDateDebut();
						
						// Date Début après Date Fin
				        if (DateDeDebut.compareTo(DateDeFin) > 0) {
							JOptionPane.showMessageDialog(new JFrame(), "La date de fin doit être supérieure ou égale à la date de début.","Date invalide", JOptionPane.WARNING_MESSAGE);
							Modifier();
				        } else if (DateDeDebut.compareTo(DateDeFin) <= 0) {
				        	missionEnCours.setDateFinRelle(DateDeFin);
				        	try {
								data.Missions().modifier(missionEnCours);
								this.mJTableMissions.fireTableDataChanged();
								updateMissionStatus();
								updateComboBox();
								VideChamps();
							} catch (DataException e) {
								e.printStackTrace();
							}
				        } else {
							JOptionPane.showMessageDialog(new JFrame(), "La date de fin réelle est invalide.","Date invalide", JOptionPane.WARNING_MESSAGE);
							Modifier();
				        }
					}
				
			} else if (missionEnCours.getStatus() == Status.TERMINEE) {
				JOptionPane.showMessageDialog(
						new JFrame(), "La mission est terminée : elle n'est donc plus modifiable.", "Mission terminée",
						JOptionPane.WARNING_MESSAGE
				);
			}
		}
	}
	
	/*
	 * ANNULER : Annulation de toutes les modifications précédemment effectuées
	 */
	public void Annuler() {
		this.missionEnCours = null;
		updateComboBox();
		
		switch (this.mode) {
		case "nouveau":
			VideChamps();
			ChargementConsultation();
			break;
		
		case "modification":
			Mission missSelect = getMissionSelected();
			missSelect.setCompReq(cloned.getCompReq());
			missSelect.setAffEmp(cloned.getAffEmp());
			if (missSelect != null) {
				AffichageSelection();
				ChargementConsultation();
			}
			break;
		}
	}
	
	/*
	 * SUPPRIMER : Suppression de l'employé séléctionné
	 */
	public void Supprimer() {
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
	
	public void AjouterCompetence() {
		JFrame frame;
		try {
			ProgramFrame.getFrame().setEnabled(false);
			frame = new MissionsAddCompetence(data, this.missionEnCours, this);
			frame.setTitle("Ajout de compétences");
			frame.setResizable(false);
			try {
				Image iconImage = ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
				frame.setIconImage(iconImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        frame.addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent we) {
	            	ProgramFrame.getFrame().setEnabled(true);
	            	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        }
		    });
			frame.pack();
			frame.setVisible(true);
		} catch (HeadlessException | DataException e1) {
			e1.printStackTrace();
		}
	}
	
	public void ModifierCompetence() {
		CompetenceRequirement compSelect = getCompetenceSelected();
		if (compSelect != null) {
			int rowIndex = this.JTableCompetences.getSelectedRow();
			ProgramFrame.getFrame().setEnabled(false);
			MissionsEditCompetence fel = new MissionsEditCompetence(rowIndex, JTableCompetences, compSelect, this);
			fel.displayGUI();
		}
	}
	
	public void SupprimerCompetence() {
		CompetenceRequirement compSelect = getCompetenceSelected();
		if (compSelect != null) {
			int n = JOptionPane.showConfirmDialog(
					new JFrame(), "Voulez vraiment supprimer cette compétence ?", "Confirmation de suppression",
					JOptionPane.YES_NO_OPTION
			);
			if (n == JOptionPane.YES_OPTION) {
				missionEnCours.removeCompetenceReq(compSelect);
				this.mJTableCompetences.deleteRowObject(compSelect);
				this.mJTableCompetences.fireTableDataChanged();
				updateComboBox();
			}
		}
	}
	
	public void AjouterEmploye() {
		ArrayList<Employee> absentEmployes;
		try {
			absentEmployes = data.Employes().tous();
			ArrayList<Employee> presentEmployes = mJTableEmployes.getArraylist();
			for (Employee emp : presentEmployes) {
				if (absentEmployes.contains(emp)) {
					absentEmployes.remove(emp);
				}
			}
			ProgramFrame.getFrame().setEnabled(false);
			missionEnCours.setAffEmp(presentEmployes);
			MissionsAddEmploye fel = new MissionsAddEmploye(missionEnCours, absentEmployes, this, mJTableEmployes);
			fel.displayGUI();
			
		} catch (DataException e) {
			e.printStackTrace();
		}
	}
	
	public void SupprimerEmploye() {
		Employee emp = (Employee) mJTableEmployes.getRowObject(JTableEmployes.getSelectedRow());
		missionEnCours.removeEmployee(emp);
		mJTableEmployes.fireTableDataChanged();
	}
	
}
