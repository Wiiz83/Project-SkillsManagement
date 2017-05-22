package gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Construction d'un TableModel personnalisé pour stocker une ArrayList d'objets
 * @param <E> le type d'objet 
 */
public abstract class GenericTableModel<E> extends AbstractTableModel {
	
	private static final long	serialVersionUID	= -3220481913022140265L;
	ArrayList<E>			objects;
	String[]					headers;
	
	/**
	 * @param objects l'arraylist d'objets
	 * @param headers les titres des colonnes 
	 */
	public GenericTableModel(ArrayList<E> objects, String[] headers) {
		this.objects = objects;
		this.headers = headers;
	}
	
	/**
	 * @param row la ligne du TableModel
	 * @return l'objet à cette ligne
	 */
	public E getRowObject(int row) {
		return objects.get(row);
	}
	
	/**
	 * @return l'arraylist d'objets 
	 */
	public ArrayList<E> getArraylist(){
		return objects;
	}
	
	/**
	 * @param e l'objet à supprimer du modèle
	 */
	public void deleteRowObject(E e){
		objects.remove(e);
	}
	
	/**
	 * @param e l'objet à ajouter au modèle
	 */
	public void addRowObject(E e){
		objects.add(e);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int col) {
		return headers[col];
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return headers.length;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return objects.size();
	}

	
}