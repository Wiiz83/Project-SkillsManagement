package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import csv.CSVEntity;

public class MissionFormation extends MissionAbstract {
	
	public MissionFormation(String nomM, Date dateDebut, int duree, int nbPersReq) {
		super(nomM, dateDebut, duree, nbPersReq);
	}
	
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 4395319872170806127L;
	private ArrayList<Competence>	Comp;
	
	@Override
	public HashMap<Class<? extends CSVEntity>, ArrayList<String>> getReferencedObjectsIDS() {
		HashMap<Class<? extends CSVEntity>, ArrayList<String>> IDS = new HashMap<>();
		IDS.put(Employee.class, getIDS(AffEmp));
		IDS.put(Competence.class, getIDS(Comp));
		return IDS;
	}
	
	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		AffEmp = castArrayList(hashMap, Employee.class);
		Comp = castArrayList(hashMap, Competence.class);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MissionFormation))
			return false;
		MissionFormation other = (MissionFormation) obj;
		if (Comp == null) {
			if (other.Comp != null)
				return false;
		} else if (!Comp.equals(other.Comp))
			return false;
		return true;
	}
}
