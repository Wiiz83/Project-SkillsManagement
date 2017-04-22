package gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public abstract class GenericTableModel<E> extends AbstractTableModel {
	
	private static final long	serialVersionUID	= -3220481913022140265L;
	ArrayList<E>			objects;
	String[]					headers;
	
	public GenericTableModel(ArrayList<E> objects, String[] headers) {
		this.objects = objects;
		this.headers = headers;
	}
	
	public E getRowObject(int row) {
		return objects.get(row);
	}
	
	public void deleteRowObject(E e){
		objects.remove(e);
	}
	
	@Override
	public String getColumnName(int col) {
		return headers[col];
	}
	
	@Override
	public int getColumnCount() {
		return headers.length;
	}
	
	@Override
	public int getRowCount() {
		return objects.size();
	}
	
	//Fonction qui va s'occupper de remettre à jour tout le tableau et qui va mettre à jour aussi l'affichage
	public void setData(ArrayList<E> o){
		objects = o;
		super.fireTableDataChanged();
	}
	
}