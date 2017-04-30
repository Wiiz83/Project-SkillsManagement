package navigation;

import java.awt.HeadlessException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import data.Data;
import data.DataException;
import gui.JTables;
import models.*;

/**
 * JFame pour ajouter des compétences requises pour une mission.
 * 
 * @param data
 * @param mission
 * @throws HeadlessException
 * @throws DataException
 */

public class MissionsAddCompetence extends JFrame {
	
	private static final long	serialVersionUID	= -379824080943045915L;
	private Data				data;
	
	private JTable				dissocTable;
	private JScrollPane			jScrollPane1;
	private JButton				AddButton;
	private JTextField			nbEmployes;
	private javax.swing.JLabel	nbEmpLable;
	
	private Mission mission;
	
	public MissionsAddCompetence(Data data, Mission mission) throws HeadlessException, DataException {
		super();
		this.data = data;
		this.mission = mission;
		loadData();
		initComponents();
	}
	
	private void loadData() throws DataException {
		ArrayList<String> ids_competencesMission = new ArrayList<>();
		for (CompetenceRequirement cr : mission.getCompReq())
			ids_competencesMission.add(cr.getCompetence().csvID());
		ArrayList<Competence> dissocCompetences = data.Competences()
				.filtrer(c -> !ids_competencesMission.contains(c.csvID()));
		dissocTable = JTables.Competences(dissocCompetences);
	}
	
	private void initComponents() {
		nbEmpLable = new JLabel();
		
		jScrollPane1 = new javax.swing.JScrollPane();
		AddButton = new JButton();
		AddButton.setText("Ajouter");
		AddButton.addActionListener(
				new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						AjoutCompReq();
					}
				}
		);
		this.nbEmployes = new JTextField();
		
		jScrollPane1.setViewportView(dissocTable);
		nbEmployes.addActionListener(
				new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
					}
				}
		);
		
		nbEmpLable.setText("Nombre d'employés requis: ");
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						layout.createSequentialGroup().addContainerGap().addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(
												jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338,
												javax.swing.GroupLayout.PREFERRED_SIZE
										)
										.addGroup(
												layout.createSequentialGroup().addComponent(nbEmpLable)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED
														)
														.addGroup(
																layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING
																).addComponent(AddButton).addComponent(
																		nbEmployes,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 69,
																		javax.swing.GroupLayout.PREFERRED_SIZE
																)
														)
										)
						).addContainerGap(15, Short.MAX_VALUE)
				)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						layout.createSequentialGroup().addContainerGap()
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
								.addGap(14, 14, 14)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														nbEmployes, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE
												).addComponent(nbEmpLable)
								).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(AddButton).addGap(6, 6, 6)
				)
		);
		
		dissocTable.setEnabled(true);
		pack();
	}
	
	private void AjoutCompReq() {
	}
}
