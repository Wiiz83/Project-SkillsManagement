package navigation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import data.Data;
import data.DataException;
import gui.Button;
import gui.GenericTableModel;
import gui.ProgramFrame;
import gui.Titre;
import models.Competence;
import models.Employee;

/**
 * Page "Personnel" de l'application contenant la liste du personnel avec
 * possibilité d'ajout, suppression et modification
 * 
 */
public class Personnel extends Page implements MouseListener {
	private static final long	serialVersionUID	= 1L;
	private Data				data;
	
	GestionListe			gc;
	ArrayList<Competence>	listCompEmp;
	
	JTable						JTablePersonnel;
	GenericTableModel<Employee>	mJTablePersonnel;
	
	JTable							JTableCompetences;
	GenericTableModel<Competence>	mJTableCompetences;
	
	Button	boutonNouveau;
	Button	boutonModifier;
	Button	boutonSupprimer;
	Button	boutonEnregistrer;
	Button	boutonAnnuler;
	Button	boutonEditComp;
	
	JTextField	nom;
	JTextField	prenom;
	JTextField	date;
	
	SimpleDateFormat	GuiDateFormat		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat	EmployeeDateFormat	= new SimpleDateFormat("dd/MM/yyyy");
	
	public Personnel(Data data) {
		this.data = data;
		setOpaque(false);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
		this.JTablePersonnel = new JTable();
		try {
			this.JTablePersonnel = JTables.Employes(data.Employes().tous());
			this.JTablePersonnel.setFillsViewportHeight(true);
			this.JTablePersonnel.addMouseListener(this);
			JScrollPane js = new JScrollPane(this.JTablePersonnel);
			js.setVisible(true);
			js.setBounds(10, 10, 300, 600);
			add(js);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();
		
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
		
		MaskFormatter formatterDate;
		try {
			formatterDate = new MaskFormatter("##/##/####");
			formatterDate.setPlaceholderCharacter('_');
			this.date = new JFormattedTextField(formatterDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.date.addMouseListener(this);
		this.date.setBounds(450, 110, 150, 25);
		add(this.date);
		
		this.JTableCompetences = new JTable();
		this.JTableCompetences.addMouseListener(this);
		this.JTableCompetences.setFillsViewportHeight(true);
		JScrollPane js = new JScrollPane(this.JTableCompetences);
		js.setVisible(true);
		js.setBounds(350, 170, 350, 350);
		add(js);
		// this.mJTableCompetences = (GenericTableModel<Competence>)
		// this.JTableCompetences.getModel();
		
		this.boutonEditComp = new Button("/boutons/miniedit.png");
		this.boutonEditComp.setBounds(710, 170);
		this.boutonEditComp.addMouseListener(this);
		add(this.boutonEditComp);
		
		composantsEdition.add(this.boutonEditComp);
		composantsEdition.add(this.nom);
		composantsEdition.add(this.prenom);
		composantsEdition.add(this.date);
		composantsEdition.add(this.boutonEnregistrer);
		composantsEdition.add(this.boutonAnnuler);
		
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonModifier);
		composantsConsultation.add(this.boutonNouveau);
		composantsConsultation.add(this.boutonSupprimer);
		composantsConsultation.add(this.JTablePersonnel);
		
		super.ChargementConsultation();
	}
	
	public void VideChamps() {
		this.nom.setText("");
		this.prenom.setText("");
		this.date.setText("");
		this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(new ArrayList<Competence>())
				.getModel();
		this.JTableCompetences.setModel(this.mJTableCompetences);
	}
	
	public Employee getSelected() {
		try {
			this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();
			return this.mJTablePersonnel
					.getRowObject(this.JTablePersonnel.convertRowIndexToModel(this.JTablePersonnel.getSelectedRow()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					new JFrame(), "Vous devez sélectionner un employé pour réaliser cette action.",
					"Employé non séléctionné", JOptionPane.WARNING_MESSAGE
			);
			return null;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof Button) {
			
			if (e.getSource().equals(this.boutonEditComp)) {
				ProgramFrame.frame.setEnabled(false);
				switch (this.mode) {
					case "nouveau":
						try {
							ArrayList<Competence> listCompNonPoss = data.Competences().tous();
							GenericTableModel<Competence> compNonPossModel = (GenericTableModel<Competence>) JTables.Competences(listCompNonPoss).getModel();
							JTable compNonPoss = new JTable(compNonPossModel);
							JTable compPoss = new JTable(mJTableCompetences);
							this.gc = new GestionListe(compPoss, compNonPoss, mJTableCompetences, compNonPossModel);
							this.gc.displayGUI();
						} catch (DataException e1) {
							e1.printStackTrace();
						}
						break;
					
					case "modification":
						try {
							if(getSelected() != null){
								Employee empSelect = getSelected();
								ArrayList<Competence> listCompNonPoss = data.Competences().manquantesEmploye(Integer.toString(empSelect.getID()));
								GenericTableModel<Competence> compNonPossModel = (GenericTableModel<Competence>) JTables.Competences(listCompNonPoss).getModel();	 
								JTable compNonPoss = new JTable(compNonPossModel);
								JTable compPoss = new JTable(mJTableCompetences);
								this.gc = new GestionListe(compPoss, compNonPoss, mJTableCompetences, compNonPossModel);
								this.gc.displayGUI();
							}
						} catch (DataException e1) {
							e1.printStackTrace();
						}
						break;
				}
			}
			
			/**
			 * Formulaire prêt à l'ajout d'un nouvel élément
			 */
			if (e.getSource().equals(this.boutonNouveau)) {
				VideChamps();
				super.ChargementModification();
				this.mode = "nouveau";
			}
			
			/**
			 * Formulaire prêt à la modification d'un élément existant
			 */
			if (e.getSource().equals(this.boutonModifier)) {
				if (getSelected() != null) {
					super.ChargementModification();
				}
			}
			
			/**
			 * Suppression d'un élément existant : doit demander la confirmation
			 * de l'utilisateur
			 */
			if (e.getSource().equals(this.boutonSupprimer)) {
				try {
					if (getSelected() != null) {
						int n = JOptionPane.showConfirmDialog(
								new JFrame(), "Voulez vraiment supprimer cet employé ?", "Confirmation de suppression",
								JOptionPane.YES_NO_OPTION
						);
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
			 * On annule toutes les modifications faites par l'utilisateur
			 * depuis l'activation du mode modification ou nouveau
			 */
			if (e.getSource().equals(this.boutonAnnuler)) {
				
				switch (this.mode) {
				case "nouveau":
					VideChamps();
					break;
				
				case "modification":
					if (getSelected() != null) {
						Employee EmployeSelect = getSelected();
						this.nom.setText(EmployeSelect.getLastName());
						this.prenom.setText(EmployeSelect.getName());
						this.date.setText(EmployeeDateFormat.format(EmployeSelect.getEntryDate()));
						this.listCompEmp = EmployeSelect.getCompetences();
						this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(listCompEmp)
								.getModel();
						this.JTableCompetences.setModel(this.mJTableCompetences);
					}
					break;
				
				default:
					break;
				}
				ChargementConsultation();
			}
			
			/**
			 * TODO On enregistre les modifications ou le nouvel élément
			 */
			if (e.getSource().equals(this.boutonEnregistrer)) {
				try {
					switch (this.mode) {
					case "nouveau":
						Employee nouvEmp = new Employee(this.nom.getText(), this.prenom.getText(), this.date.getText());
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
							empSelect.setEntryDate(this.date.getText(), "dd/MM/yyyy");
							ArrayList<Competence> listComp = mJTableCompetences.getArraylist();
							empSelect.setCompetences(listComp);
							data.Employes().modifier(empSelect);
							this.mJTablePersonnel.fireTableDataChanged();
							ChargementConsultation();
						}
						break;
					}
				} catch (ParseException e1) {
					System.out.println("Format incorrect: " + e1);
				} catch (DataException e1) {
					System.out.println("Problème d'enregistrement: " + e1);
				}

			}
		}
		
		/**
		 * Actualisation des champs du formulaire
		 */
		if (e.getSource() instanceof JTable) {
			if (getSelected() != null) {
				Employee EmployeSelect = getSelected();
				
				this.nom.setText(EmployeSelect.getLastName());
				this.prenom.setText(EmployeSelect.getName());
				this.date.setText(EmployeeDateFormat.format(EmployeSelect.getEntryDate()));
				
				this.listCompEmp = EmployeSelect.getCompetences();
				this.mJTablePersonnel = (GenericTableModel<Employee>) this.JTablePersonnel.getModel();
				
				this.mJTableCompetences = (GenericTableModel<Competence>) JTables.Competences(listCompEmp).getModel();
				
				// this.mJTableCompetences = (GenericTableModel<Competence>)
				// this.JTableCompetences.getModel();
				
				this.JTableCompetences.setModel(this.mJTableCompetences);
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
