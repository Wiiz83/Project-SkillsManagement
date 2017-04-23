package navigation;

import java.awt.HeadlessException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import csv.CSVEntity;
import data.Data;
import data.DataException;
import data.IRequete;
import data.CSVRequests;

public class AssociationJFrame<E extends CSVEntity, N extends CSVEntity> extends JFrame {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -379824080943045915L;
	private Data				data;
	private E					entity;
	private Class<N>			nEntityClass;
	
	private JTable		assocTable;
	private JTable		dissocTable;
	private JScrollPane	jScrollPane1;
	private JScrollPane	jScrollPane2;
	private JButton		toLeftB;
	private JButton		toRightB;;
	
	public AssociationJFrame(Data data, E entity, Class<N> nEntityClass) throws HeadlessException, DataException {
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
		
		assocTable = JTables.getTable(
				nEntityClass, nEntitiesCSV.filtrer(n -> associatedEntitesIDS.contains(n.csvID()))
		);
		
		dissocTable = JTables.getTable(
				nEntityClass, nEntitiesCSV.filtrer(n -> !associatedEntitesIDS.contains(n.csvID()))
		);
	}
	
	private void initComponents() {
		
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		toRightB = new javax.swing.JButton();
		toLeftB = new javax.swing.JButton();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		jScrollPane1.setViewportView(dissocTable);
		
		jScrollPane2.setViewportView(assocTable);
		
		toRightB.setText(">>");
		toRightB.addActionListener(
				new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						toRightBActionPerformed(evt);
					}
				}
		);
		
		toLeftB.setText("<<");
		toLeftB.addActionListener(
				new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						toLeftBActionPerformed(evt);
					}
				}
		);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addGap(71, 71, 71)
										.addComponent(
												jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258,
												javax.swing.GroupLayout.PREFERRED_SIZE
										).addGap(18, 18, 18)
										.addGroup(
												layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(toLeftB).addComponent(toRightB)
										).addGap(18, 18, 18)
										.addComponent(
												jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282,
												javax.swing.GroupLayout.PREFERRED_SIZE
										).addContainerGap(66, Short.MAX_VALUE)
						)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(
												layout.createParallelGroup(
														javax.swing.GroupLayout.Alignment.TRAILING, false
												).addComponent(
														jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355,
														Short.MAX_VALUE
												).addComponent(
														jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
														Short.MAX_VALUE
												)
										).addGap(21, 21, 21)
						).addGroup(
								layout.createSequentialGroup().addGap(163, 163, 163).addComponent(toRightB)
										.addGap(18, 18, 18).addComponent(toLeftB).addContainerGap(197, Short.MAX_VALUE)
						)
		);
		assocTable.setEnabled(true);
		dissocTable.setEnabled(true);
		pack();
	}
	
	private void toLeftBActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}
	
	private void toRightBActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}
	// abstract ArrayList<N> getAssociatedEntities();
	
	// abstract void AssociateEntity(N nEntity);
	
	// abstract void DissociateEntity(N nEntity);
	
	void saveChanges() throws DataException {
		@SuppressWarnings("unchecked")
		IRequete<E> req = new CSVRequests<E>(data, (Class<E>) entity.getClass());
		req.modifier(entity);
	}
}
