package navigation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
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
import models.Competence;
import models.Employee;

/**
 * Formulaire contenant tous les employés
 */
public class Personnel extends Formulaire {

	private static final long serialVersionUID = 1L;
	private Data data;
	private ArrayList<Competence> listCompEmp;
	private JTable JTablePersonnel;
	private GenericTableModel<Employee> mJTablePersonnel;
	private JTable JTableCompetences;
	private GenericTableModel<Competence> mJTableCompetences;
	private Button boutonEditComp;
	private JTextField nom;
	private JTextField prenom;
	private DatePicker datePicker;
	private HintTextField recherche;

	/**
	 * @param data
	 */
	public Personnel(Data data) {
		super();

		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));

		this.JTablePersonnel = new JTable();
		try {
			this.JTablePersonnel = JTables.Employes(data.Employes().tous());
			this.JTablePersonnel.setFillsViewportHeight(true);
			JScrollPane js = new JScrollPane(this.JTablePersonnel);
			js.setVisible(true);
			js.setBounds(10, 45, 300, 565);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();

		String indication = "Rechercher un nom ou prénom...";
		this.recherche = new HintTextField(indication);
		this.recherche.setBounds(10, 10, 300, 25);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(JTablePersonnel.getModel());
		this.recherche.getDocument().addDocumentListener(new RechercheJTable(recherche, indication, rowSorter));
		add(this.recherche);
		JTablePersonnel.setRowSorter(rowSorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		rowSorter.setSortKeys(sortKeys);

		Titre titre = new Titre(" Détails du salarié :");
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

		jpanelInfo.add(new JLabel("Nom :"));
		this.nom = new JTextField();
		jpanelInfo.add(this.nom);

		jpanelInfo.add(new JLabel("Prénom :"));
		this.prenom = new JTextField();
		jpanelInfo.add(this.prenom);

		jpanelInfo.add(new JLabel("Date d'entrée"));
		this.datePicker = new DatePicker();
		jpanelInfo.add(this.datePicker);

		panelPadding.add(jpanelInfo);
		this.add(panelPadding);

		JPanel panelPadding3 = new JPanel();
		panelPadding3.setBounds(660, 50, 590, 480);
		panelPadding3.setBackground(Color.WHITE);
		panelPadding3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des compétences",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

		JPanel panelCompetences = new JPanel(new BorderLayout());
		panelCompetences.setPreferredSize(new Dimension(480, 430));
		panelCompetences.setBackground(Color.WHITE);

		this.JTableCompetences = new JTable();
		this.JTableCompetences.setFillsViewportHeight(true);
		JScrollPane js = new JScrollPane(this.JTableCompetences);
		js.setVisible(true);
		panelCompetences.add(js);

		panelPadding3.add(panelCompetences);

		JPanel panelCompetencesButtons = new JPanel(new FlowLayout());

		panelCompetencesButtons.setPreferredSize(new Dimension(60, 100));
		panelCompetencesButtons.setBackground(Color.WHITE);

		this.boutonEditComp = new Button("/boutons/miniedit.png");
		panelCompetencesButtons.add(this.boutonEditComp);

		panelPadding3.add(panelCompetencesButtons);
		this.add(panelPadding3);

		JTablePersonnel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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

		this.boutonEditComp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ModifierCompetence();
			}
		});

		composantsEdition.add(this.boutonEditComp);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.prenom);
		composantsEdition.add(this.datePicker);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		composantsEdition.add(this.JTableCompetences);

		composantsConsultation.add(this.recherche);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTablePersonnel);

		super.ChargementConsultation();
	}

	/**
	 * Vide tous les champs du formulaire
	 */
	public void VideChamps() {
		this.nom.setText("");
		this.prenom.setText("");
		this.datePicker.setText("");
		this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(new ArrayList<Competence>())
				.getModel();
		this.JTableCompetences.setModel(this.mJTableCompetences);
		this.JTableCompetences.getColumnModel().getColumn(0).setPreferredWidth(200);
		this.JTableCompetences.getColumnModel().getColumn(1).setPreferredWidth(600);
	}

	/**
	 * @return l'employé séléctionné
	 */
	public Employee getSelected() {
		try {
			this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();
			return this.mJTablePersonnel
					.getRowObject(this.JTablePersonnel.convertRowIndexToModel(this.JTablePersonnel.getSelectedRow()));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Affichage des détails de la l'employé sélectionné
	 */
	public void AffichageSelection() {
		Employee EmployeSelect = getSelected();
		if (EmployeSelect != null) {
			this.nom.setText(EmployeSelect.getLastName());
			this.prenom.setText(EmployeSelect.getName());

			Date date = EmployeSelect.getEntryDate();
			Instant instant = date.toInstant();
			ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
			LocalDate d = zdt.toLocalDate();
			this.datePicker.setDate(d);

			this.listCompEmp = EmployeSelect.getCompetences();
			this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();
			this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(listCompEmp).getModel();
			this.JTableCompetences.setModel(this.mJTableCompetences);
			
			JTableCompetences.setAutoCreateRowSorter(true);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(JTableCompetences.getModel());
			JTableCompetences.setRowSorter(sorter);
			List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
			
			
			this.JTableCompetences.getColumnModel().getColumn(0).setPreferredWidth(200);
			this.JTableCompetences.getColumnModel().getColumn(1).setPreferredWidth(600);
		}
	}

	/**
	 * Création d'un nouvel employé
	 */
	public void Nouveau() {
		JTablePersonnel.getSelectionModel().clearSelection();
		VideChamps();
		super.ChargementModification();
		this.mode = "nouveau";
	}

	/**
	 * Enregistrement de l'employé
	 */
	public void Enregistrer() {
		if ((this.nom.getText().equals("")) || (this.prenom.getText().equals(""))
				|| (this.datePicker.getText().equals(""))) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Vous devez renseigner toutes les informations pour enregistrer.", "Informations non renseignés",
					JOptionPane.WARNING_MESSAGE);
		} else {
			ZonedDateTime zdt = this.datePicker.getDate().atStartOfDay(ZoneId.systemDefault());
			Instant instant = zdt.toInstant();
			java.util.Date DateEntree = java.util.Date.from(instant);

			try {
				switch (this.mode) {
				case "nouveau":
					Employee nouvEmp = new Employee(this.nom.getText(), this.prenom.getText(), DateEntree);
					data.Employes().ajouter(nouvEmp);
					ArrayList<Competence> listCompNouv = mJTableCompetences.getArraylist();
					nouvEmp.setCompetences(listCompNouv);
					data.Employes().modifier(nouvEmp);
					this.mJTablePersonnel.addRowObject(nouvEmp);
					this.mJTablePersonnel.fireTableDataChanged();
					ChargementConsultation();
					break;

				case "modification":
					if (getSelected() != null) {
						Employee empSelect = getSelected();
						empSelect.setLastName(this.nom.getText());
						empSelect.setName(this.prenom.getText());
						empSelect.setEntryDate(DateEntree);
						ArrayList<Competence> listComp = mJTableCompetences.getArraylist();
						empSelect.setCompetences(listComp);
						data.Employes().modifier(empSelect);
						this.mJTablePersonnel.fireTableDataChanged();
						ChargementConsultation();
					}
					break;
				}
			} catch (DataException e1) {
				System.out.println("Problème d'enregistrement: " + e1);
			}

		}
	}


	/**
	 * Modification de l'employé séléctionné
	 */
	public void Modifier() {
		if (getSelected() != null) {
			super.ChargementModification();
		}
	}


	/**
	 *  Annulation de toutes les modifications précédemment effectuées
	 */
	public void Annuler() {
		switch (this.mode) {
		case "nouveau":
			VideChamps();
			ChargementConsultation();
			break;

		case "modification":
			AffichageSelection();
			ChargementConsultation();
			/*
			 * if (getSelected() != null) { Employee EmployeSelect =
			 * getSelected(); this.nom.setText(EmployeSelect.getLastName());
			 * this.prenom.setText(EmployeSelect.getName()); LocalDate d =
			 * EmployeSelect.getEntryDate().toInstant().atZone(ZoneId.
			 * systemDefault()).toLocalDate(); this.datePicker.setDate(d);
			 * this.listCompEmp = EmployeSelect.getCompetences();
			 * this.mJTableCompetences = (GenericTableModel<Competence>)
			 * JTables.Competences(listCompEmp) .getModel();
			 * this.JTableCompetences.setModel(this.mJTableCompetences);
			 * this.JTableCompetences.getColumnModel().getColumn(0).
			 * setPreferredWidth(200);
			 * this.JTableCompetences.getColumnModel().getColumn(1).
			 * setPreferredWidth(600); ChargementConsultation(); }
			 */
			break;
		}
	}

	/**
	 * Suppression de l'employé séléctionné
	 */
	public void Supprimer() {
		try {
			if (getSelected() != null) {
				int n = JOptionPane.showConfirmDialog(new JFrame(), "Voulez vraiment supprimer cet employé ?",
						"Confirmation de suppression", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					Employee empSelect = getSelected();
					data.Employes().supprimer(empSelect);
					this.mJTablePersonnel.deleteRowObject(empSelect);
					this.mJTablePersonnel.fireTableDataChanged();
					VideChamps();
				}
			}
		} catch (DataException e2) {
			e2.printStackTrace();
		}
	}


	/**
	 * Modifier les compétences d'un employé
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

}
