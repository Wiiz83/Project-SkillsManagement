package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import csv.CSVEntity;

/**
 * Classe Mission
 */
public class Mission extends MissionAbstract {
	/**
	 * 
	 */
	private static final long					serialVersionUID	= -6014131798059503343L;
	private ArrayList<CompetenceRequirement>	CompReq;
	
	/**
	 * @param nomM
	 * @param dateDebut
	 * @param duree
	 * @param nbPersReq
	 */
	public Mission(String nomM, Date dateDebut, int duree, int nbPersReq) {
		super(nomM, dateDebut, duree, nbPersReq);
		this.CompReq = new ArrayList<>();
	}
	
	/**
	 * @param cr
	 * @return ajoute une compétence a la mission (true si réussi false si echec
	 *         de l'ajout
	 */
	public boolean addCompetenceReq(CompetenceRequirement cr) {
		return CompReq.add(cr);
	}
	
	public boolean removeCompetenceReq(Competence c) {
		for (CompetenceRequirement cr : CompReq)
			if (cr.getCompetence().equals(c))
				return CompReq.remove(cr);
		return false;
	}
	
	/**
	 * @return l'arraylist des compétences requises sur le projet
	 */
	public ArrayList<CompetenceRequirement> getCompReq() {
		return CompReq;
	}
	
	@Override
	public String toString() {
		return "Mission [nomM=" + nom + ", id=" + id + ", AffEmp=" + AffEmp + ", CompReq=" + CompReq + "]";
	}
	
	@Override
	public HashMap<Class<? extends CSVEntity>, ArrayList<String>> getReferencedObjectsIDS() {
		HashMap<Class<? extends CSVEntity>, ArrayList<String>> IDS = new HashMap<>();
		IDS.put(Employee.class, getIDS(AffEmp));
		IDS.put(CompetenceRequirement.class, getIDS(CompReq));
		return IDS;
	}
	
	@Override
	public void setReferencedObjects(HashMap<Class<? extends CSVEntity>, ArrayList<Object>> hashMap) {
		AffEmp = castArrayList(hashMap, Employee.class);
		CompReq = castArrayList(hashMap, CompetenceRequirement.class);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Mission))
			return false;
		Mission other = (Mission) obj;
		if (CompReq == null) {
			if (other.CompReq != null)
				return false;
		} else if (!CompReq.equals(other.CompReq))
			return false;
		return true;
	}
	
}
