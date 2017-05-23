package navigation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

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
import models.Competence;
import models.Employee;
import models.Mission;
import models.MissionFormation;
import models.Status;

/**
 * Formulaire contenant toutes les missions de formation
 */
public class Formations extends Formulaire {

	private static final long serialVersionUID = 1L;
	private Data data;
	private Button boutonEditEmp;
	private Button boutonEditComp;
	private JTextField nom;
	private JComboBox<Status> statut;
	private DatePicker dateD;
	private JFormattedTextField duree;
	private JFormattedTextField nombre;
	private HintTextField recherche;
	private JComboBox<String> filtre;
	private JTable JTableFormations;
	private GenericTableModel<MissionFormation> mJTableFormations;
	private JTable JTableCompetences;
	private GenericTableModel<Competence> mJTableCompetences;
	private JTable JTableEmployes;
	private GenericTableModel<Employee> mJTableEmployes;
	private NumberFormat numberFormat = NumberFormat.getNumberInstance();
	private MissionFormation formationEnCours;
	private JLabel dateDeFinPrevue;
	private JLabel dateDeFinReelle;

	/**
	 * @param data
	 */
	public Formations(Data data) {
		super();

		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));

		this.JTableFormations = new JTable();
		try {
			JTableFormations = JTables.Missions(data.Formations().tous());
			JTableFormations.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(JTableFormations);
			js.setVisible(true);
			js.setBounds(10, 80, 300, 520);
			add(js);
			this.mJTableFormations = (GenericTableModel<MissionFormation>) this.JTableFormations.getModel();
		} catch (DataException e) {
			e.printStackTrace();
		}

		String indication = "Rechercher un nom de formation..";
		this.recherche = new HintTextField(indication);
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTableFormations.getModel());
		this.recherche.getDocument().addDocumentListener(new RechercheJTable(recherche, indication, rowSorter));
		add(this.recherche);
		
		JTableFormations.setRowSorter(rowSorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		rowSorter.setSortKeys(sortKeys);

		this.filtre = new JComboBox<>();
		this.filtre.addItem("Toutes");
		this.filtre.addItem(Status.EN_COURS.toString());
		this.filtre.addItem(Status.PLANIFIEE.toString());
		this.filtre.addItem(Status.PREPARATION.toString());
		this.filtre.addItem(Status.TERMINEE.toString());
		this.filtre.setBounds(10, 45, 300, 25);
		this.filtre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filtre.getSelectedItem().toString() != "Tous") {
					rowSorter.setRowFilter(RowFilter.regexFilter(filtre.getSelectedItem().toString(), 2));
				}
			}
		});
		add(this.filtre);
		JTableFormations.setRowSorter(rowSorter);

		Titre titre = new Titre(" Détails de la formation :");
		titre.setBounds(330, 10, 930, 20);
		add(titre);

		JPanel panelPadding = new JPanel();
		panelPadding.setBounds(350, 50, 300, 480);
		panelPadding.setBackground(Color.WHITE);
		panelPadding.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informations",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

		JPanel jpanelInfo = new JPanel(new GridLayout(15, 1, 0, 4));
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
		panelPadding3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des compétences",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

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

		this.boutonEditComp = new Button("/boutons/miniedit.png");
		panelCompetencesButtons.add(this.boutonEditComp);

		panelPadding3.add(panelCompetencesButtons);

		this.add(panelPadding3);

		JPanel panelPadding2 = new JPanel();
		panelPadding2.setBounds(660, 290, 590, 240);
		panelPadding2.setBackground(Color.WHITE);
		panelPadding2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des employés",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

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

		this.boutonEditEmp = new Button("/boutons/miniedit.png");
		panelEmployesButtons.add(this.boutonEditEmp);

		panelPadding2.add(panelEmployesButtons);

		this.add(panelPadding2);

		JTableFormations.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				AffichageSelection();
			}
		});

		this.boutonNouveau.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Nouveau();
			}
		});

		this.boutonModifier.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Modifier();
			}
		});

		this.boutonSupprimer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Supprimer();
			}
		});

		this.boutonEnregistrer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Enregistrer();
			}
		});

		this.boutonAnnuler.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Annuler();
			}
		});

		JTableFormations.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				AffichageSelection();
			}
		});

		this.boutonEditComp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ModifierCompetence();
			}
		});

		this.boutonEditEmp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ModifierEmploye();
			}
		});

		composantsEdition.add(this.JTableEmployes);
		composantsEdition.add(this.JTableCompetences);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.statut);
		composantsEdition.add(this.dateD);
		composantsEdition.add(this.duree);
		composantsEdition.add(this.nombre);
		composantsEdition.add(this.boutonEditComp);
		composantsEdition.add(this.boutonEditEmp);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);

		composantsConsultation.add(this.recherche);
		composantsConsultation.add(this.filtre);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTableFormations);

		super.ChargementConsultation();

	}

	/**
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
		this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(new ArrayList<Competence>())
				.getModel();
		this.JTableCompetences.setModel(mJTableCompetences);
	}

	/**
	 * @return la formation séléctionnée
	 */
	public MissionFormation getFormationSelected() {
		try {
			this.mJTableFormations = (GenericTableModel<MissionFormation>) JTableFormations.getModel();
			return mJTableFormations
					.getRowObject(this.JTableFormations.convertRowIndexToModel(this.JTableFormations.getSelectedRow()));
		} catch (Exception e) {
			return null;
		}
	}
	
	void updateComboBox() {
		statut.removeAllItems();
		if (formationEnCours == null)
			return;
		statut.addItem(formationEnCours.getStatus());
		if (formationEnCours.getStatus() == Status.PREPARATION)
			statut.addItem(Status.PLANIFIEE);
	}
	
	private void updateMissionStatus() {
		Status selected = (Status) statut.getSelectedItem();
		if (selected == Status.PLANIFIEE && formationEnCours.getStatus() == Status.PREPARATION)
			formationEnCours.planifier();
	}

	/**
	 * Affichage des détails de la mission sélectionnée
	 */
	public void AffichageSelection() {
		formationEnCours = getFormationSelected();

		if (formationEnCours != null) {
			this.nom.setText(formationEnCours.getNomM());

			Date date = formationEnCours.getDateDebut();
			Instant instant = date.toInstant();
			ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
			LocalDate d = zdt.toLocalDate();
			this.dateD.setDate(d);

			this.duree.setText(Integer.toString(formationEnCours.getDuree()));
			this.nombre.setText(Integer.toString(formationEnCours.getNbPersReq()));

			this.statut.setSelectedItem(formationEnCours.getStatus());

			if(formationEnCours.getDateFinReelle() != formationEnCours.getDateFin()){
				this.dateDeFinReelle.setVisible(true);
			    SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd MMM yyyy");
			    String sdateDeFinReelle = formatter.format(formationEnCours.getDateFinReelle());
			    dateDeFinReelle.setText("Date de fin réelle le " + sdateDeFinReelle);
			} else {
				this.dateDeFinReelle.setVisible(false);
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd MMM yyyy");
			String dateFinPrevue = formatter.format(formationEnCours.getDateFin());
			dateDeFinPrevue.setText("Date de fin prévue le " + dateFinPrevue);

			ArrayList<Competence> listCompMiss = formationEnCours.getCompetences();
			this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(listCompMiss).getModel();
			this.JTableCompetences.setModel(mJTableCompetences);

			ArrayList<Employee> listEmpMiss = formationEnCours.getAffEmp();
			this.mJTableEmployes = (GenericTableModel<Employee>) JTables.Employes(listEmpMiss).getModel();
			this.JTableEmployes.setModel(mJTableEmployes);
			
			JTableEmployes.setAutoCreateRowSorter(true);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(JTableEmployes.getModel());
			JTableEmployes.setRowSorter(sorter);
			List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
			
			JTableCompetences.setAutoCreateRowSorter(true);
			TableRowSorter<TableModel> sorter2 = new TableRowSorter<TableModel>(JTableCompetences.getModel());
			JTableCompetences.setRowSorter(sorter2);
			List<RowSorter.SortKey> sortKeys2 = new ArrayList<>(25);
			sortKeys2.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sorter2.setSortKeys(sortKeys2);
			
			updateComboBox();
		}
	}

	/**
	 * Création d'une nouvelle mission
	 */
	public void Nouveau() {
		JTableFormations.getSelectionModel().clearSelection();
		this.formationEnCours = new MissionFormation("", models.Cal.today(), 0, 0);
		VideChamps();
		super.ChargementModification();
		this.mode = "nouveau";
	}

	/**
	 * Enregistrement de la mission
	 */
	public void Enregistrer() {
		if ((this.dateD.getText().equals("")) || (this.duree.getText().equals("")) || (this.nombre.getText().equals(""))
				|| (this.nom.getText().equals(""))) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Vous devez renseigner toutes les informations pour enregistrer.", "Informations non renseignés",
					JOptionPane.WARNING_MESSAGE);
		} else {

			
			ZonedDateTime zdt = this.dateD.getDate().atStartOfDay(ZoneId.systemDefault());
			Instant instant = zdt.toInstant();
			java.util.Date dateD = java.util.Date.from(instant);

			String strDuree = duree.getText().replaceAll("\\D+", "");
			int duree = Integer.parseInt(strDuree);
			String strNombre = nombre.getText().replaceAll("\\D+", "");
			int nb = Integer.parseInt(strNombre);
			String nom = this.nom.getText();

			ArrayList<Employee> presentEmployes = mJTableEmployes.getArraylist();
			ArrayList<Competence> presentCompetences = mJTableCompetences.getArraylist();

			formationEnCours.setDateDebut(dateD);
			formationEnCours.setDuree(duree);
			formationEnCours.setNbPersReq(nb);
			formationEnCours.setNomM(nom);
			formationEnCours.setAffEmp(presentEmployes);
			formationEnCours.setCompetences(presentCompetences);
			
			updateMissionStatus();

			switch (this.mode) {
			case "nouveau":
				try {
					formationEnCours.resetDateFinReelle();
					data.Formations().ajouter(formationEnCours);
					mJTableFormations.addRowObject(formationEnCours);
					mJTableFormations.fireTableDataChanged();
					super.ChargementConsultation();
				} catch (DataException e) {
					e.printStackTrace();
				}
				break;

			case "modification":
				try {
					data.Formations().modifier(formationEnCours);
					mJTableFormations.fireTableDataChanged();
					super.ChargementConsultation();
				} catch (DataException e) {
					e.printStackTrace();
				}
				break;
			}
			
			
			updateComboBox();
		}
	}

	/**
	 * Modification de la formation séléctionnée
	 */
	public void Modifier() {
		this.formationEnCours = getFormationSelected();
		if (formationEnCours != null) {
			if (formationEnCours.estModifiable()) {
				super.ChargementModification();
			} else if (formationEnCours.getStatus() == Status.EN_COURS) {

				DatePicker dp = new DatePicker();
				String message ="La formation est en cours : elle n'est donc plus modifiable. \n Veuillez entrer sa date de fin réelle :";
				Object[] params = {message,dp};
				int n = JOptionPane.showConfirmDialog(null,params,"Modification de la de fin réelle", JOptionPane.YES_NO_OPTION);
				
				if (n == JOptionPane.YES_OPTION) {
		
					Date DateDeFin = java.sql.Date.valueOf(dp.getDate());
					Date DateDeDebut = formationEnCours.getDateDebut();
					
					// Date Début après Date Fin
			        if (DateDeDebut.compareTo(DateDeFin) > 0) {
						JOptionPane.showMessageDialog(new JFrame(), "La date de fin doit être supérieure ou égale à la date de début.","Date invalide", JOptionPane.WARNING_MESSAGE);
						Modifier();
			        } else if (DateDeDebut.compareTo(DateDeFin) <= 0) {
			        	formationEnCours.setDateFinRelle(DateDeFin);
			        	try {
							data.Formations().modifier(formationEnCours);
							this.mJTableFormations.fireTableDataChanged();
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
			
		} else if (formationEnCours.getStatus() == Status.TERMINEE) {
			JOptionPane.showMessageDialog(
					new JFrame(), "La formation est terminée : elle n'est donc plus modifiable.", "Formation terminée",
					JOptionPane.WARNING_MESSAGE
			);
		}
		}
	}


	/**
	 * Annulation de toutes les modifications précédemment effectuées
	 */
	public void Annuler() {
		this.formationEnCours = null;
		VideChamps();
		ChargementConsultation();
		updateComboBox();
	}

	/**
	 * Suppression de l'employé séléctionné
	 */
	public void Supprimer() {
		MissionFormation formationEnCours = getFormationSelected();
		if (formationEnCours != null) {
			int n = JOptionPane.showConfirmDialog(new JFrame(), "Voulez vraiment supprimer cette mission ?",
					"Confirmation de suppression", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				try {
					data.Formations().supprimer(formationEnCours);
					this.mJTableFormations.deleteRowObject(formationEnCours);
					this.mJTableFormations.fireTableDataChanged();
					VideChamps();
				} catch (DataException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Modifier les compétences
	 */
	public void ModifierCompetence() {
		ProgramFrame.getFrame().setEnabled(false);
		try {
			ArrayList<Competence> listCompNonPoss = data.Competences().Autres(mJTableCompetences.getArraylist());
			GenericTableModel<Competence> compNonPossModel = (GenericTableModel<Competence>) JTables
					.Competences(listCompNonPoss).getModel();
			JTable compNonPoss = new JTable(compNonPossModel);
			JTable compPoss = new JTable(mJTableCompetences);
			PersonnelEditCompetence gestionListe = new PersonnelEditCompetence(compPoss, compNonPoss,
					mJTableCompetences, compNonPossModel);
			gestionListe.displayGUI();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Modifier les employés
	 */
	public void ModifierEmploye() {
		try {
			ProgramFrame.getFrame().setEnabled(false);
			ArrayList<Employee> listCompNonPoss = data.Employes().Autres(mJTableEmployes.getArraylist());
			GenericTableModel<Employee> compNonPossModel = (GenericTableModel<Employee>) JTables
					.Employes(listCompNonPoss).getModel();
			JTable compNonPoss = new JTable(compNonPossModel);
			JTable compPoss = new JTable(mJTableEmployes);
			FormationsEditEmploye gestionListe = new FormationsEditEmploye(compPoss, compNonPoss, mJTableEmployes,
					compNonPossModel);
			gestionListe.displayGUI();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
	}
	

}
