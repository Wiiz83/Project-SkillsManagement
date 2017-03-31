package navigation;

import java.awt.HeadlessException;
import java.util.ArrayList;

import javax.swing.JFrame;

import csv.CSVEntity;
import csv.CSVException;
import data.Data;
import data.Requests;

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
	
	void saveChanges() throws CSVException {
		@SuppressWarnings("unchecked")
		Requests<E> req = new Requests<E>(data, (Class<E>) entity.getClass());
		req.modifier(entity);
	}
}
