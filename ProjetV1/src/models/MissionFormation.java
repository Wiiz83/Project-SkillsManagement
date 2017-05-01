package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import csv.CSVEntity;

public class MissionFormation extends MissionAbstract {
	
	private static final long		serialVersionUID	= 4395319872170806127L;
	private ArrayList<Competence>	Competences;
	
	public MissionFormation(String nomM, Date dateDebut, int duree, int nbPersReq) {
		super(nomM, dateDebut, duree, nbPersReq);
 	}
	
	public void addCompetence(Competence c) {
		Competences.add(c);
	}
	
	public ArrayList<Competence> getCompetences() {
		return Competences;
	}
	
	@Override
	public HashMap<Class<? extends CSVEntity>, ArrayList<String>> getReferencedObjectsIDS() {
		HashMap<Class<? extends CSVEntity>, ArrayList<String>> IDS = new HashMap<>();
		IDS.put(Employee.class, getIDS(AffEmp));
		IDS.put(Competence.class, getIDS(Competences));
		return IDS;
	}
	
	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		AffEmp = castArrayList(hashMap, Employee.class);
		Competences = castArrayList(hashMap, Competence.class);
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
		if (Competences == null) {
			if (other.Competences != null)
				return false;
		} else if (!Competences.equals(other.Competences))
			return false;
		return true;
	}
}
