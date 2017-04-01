package navigation;

import java.awt.HeadlessException;
import java.util.ArrayList;

import javax.swing.JFrame;

import csv.CSVEntity;
import data.Data;
import data.DataException;
import data.IRequete;
import data.CSVRequests;

public abstract class AssociationJFrame<E extends CSVEntity, N extends CSVEntity> extends JFrame {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -379824080943045915L;
	private Data				data;
	private E					entity;
	private Class<N>			nEntityClass;
	
	public AssociationJFrame(Data data, E entity, Class<N> nEntityClass) throws HeadlessException {
		super();
		this.data = data;
		this.entity = entity;
		this.nEntityClass = nEntityClass;
	}
	
	abstract ArrayList<N> getAssociatedEntities();
	
	abstract void AssociateEntity(N nEntity);
	
	abstract void DissociateEntity(N nEntity);
	
	void saveChanges() throws DataException {
		@SuppressWarnings("unchecked")
		IRequete<E> req = new CSVRequests<E>(data, (Class<E>) entity.getClass());
		req.modifier(entity);
	}
}
