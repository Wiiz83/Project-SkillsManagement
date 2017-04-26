package navigation;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import csv.CSVEntity;
import data.Data;
import data.DataException;
import data.IRequete;
import gui.JTables;
import data.CSVRequests;

public class MissionsAddCompetence<E extends CSVEntity, N extends CSVEntity> extends JFrame {

	private static final long	serialVersionUID	= -379824080943045915L;
	private Data				data;
	private E					entity;
	private Class<N>			nEntityClass;
	
	private JTable		dissocTable;
	private JScrollPane	jScrollPane1;
	private JButton		AddButton;
	
	public MissionsAddCompetence(Data data, E entity, Class<N> nEntityClass) throws HeadlessException, DataException {
		super();
		this.data = data;
		this.entity = entity;
		this.nEntityClass = nEntityClass;
		loadData();
		initComponents();
	}
	
	private void loadData() throws DataException {
		
		CSVRequests<N> nEntitiesCSV;
		ArrayList<String> associatedEntitesIDS = entity.getReferencedObjectsIDS().get(nEntityClass);
		nEntitiesCSV = new CSVRequests<>(data, nEntityClass);
		
		dissocTable = JTables.getTable(
				nEntityClass, nEntitiesCSV.filtrer(n -> !associatedEntitesIDS.contains(n.csvID()))
		);
	}
	
	private void initComponents() {
		
		jScrollPane1 = new javax.swing.JScrollPane();
		AddButton = new JButton();
		AddButton.setText("Ajouter");
		AddButton.addActionListener(
				new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						AddButtonActionPerformed(evt);
					}
				}
		);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		jScrollPane1.setViewportView(dissocTable);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup().addContainerGap().addComponent(
																jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
																338, javax.swing.GroupLayout.PREFERRED_SIZE
														)
												).addGroup(
														layout.createSequentialGroup().addGap(135, 135, 135)
																.addComponent(AddButton)
												)
								).addContainerGap(15, Short.MAX_VALUE)
				)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addComponent(
												jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE
										).addGap(18, 18, 18).addComponent(AddButton).addGap(18, 18, 18)
						)
		);
		
		dissocTable.setEnabled(true);
		pack();
	}
	
	private void AddButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
	}
	
	void saveChanges() throws DataException {
		@SuppressWarnings("unchecked")
		IRequete<E> req = new CSVRequests<E>(data, (Class<E>) entity.getClass());
		req.modifier(entity);
	}
}
