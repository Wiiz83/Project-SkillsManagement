package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import csv.CSVEntity;

public class Mission extends MissionAbstract implements Cloneable {
	
	private static final long					serialVersionUID	= -6014131798059503343L;
	private ArrayList<CompetenceRequirement>	CompReq;
	protected Date								dateFinReelle;
	
	/**
	 * @param nomM
	 * @param dateDebut
	 * @param duree
	 * @param nbPersReq
	 */
	public Mission(String nomM, Date dateDebut, int duree, int nbPersReq) {
		super(nomM, dateDebut, duree, nbPersReq);
		this.CompReq = new ArrayList<>();
		setDateFinRelle(duree);
	}
	
	/**
	 * @param cr
	 * @return ajoute une compétence a la mission (true si réussi false si echec
	 *         de l'ajout
	 */
	public void setDateFinRelle(int duree) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dateDebut);
		cal.add(Calendar.DATE, duree);
		this.dateFinReelle = cal.getTime();
	}
	
	public void setDateFinRelle(Date date) {
		this.dateFinReelle = date;
	}
	
	public boolean enRetard() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dateDebut);
		cal.add(Calendar.DATE, duree);
		Date dateFinPrev = cal.getTime();
		return dateFinReelle.compareTo(dateFinPrev) >= 0;
	}
	

	public Date getDateFinReelle() {
		return dateFinReelle;
	}
	
	public boolean addCompetenceReq(CompetenceRequirement cr) {
		return CompReq.add(cr);
	}
	
	public boolean removeCompetenceReq(CompetenceRequirement c) {
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
	
	public void setCompReq(ArrayList<CompetenceRequirement> compR) {
		this.CompReq = compR;
	}
	
	@Override
	public String toString() {
		return "Mission [nomM=" + nom + ", id=" + id + ", AffEmp=" + AffEmp + ", CompReq=" + CompReq + "]";
	}
	
	@Override
	protected HashMap<Class<? extends CSVEntity>, ArrayList<? extends CSVEntity>> getReferencedObjects() {
		HashMap<Class<? extends CSVEntity>, ArrayList<? extends CSVEntity>> referencedObjects = new HashMap<>();
		referencedObjects.put(Employee.class, AffEmp);
		referencedObjects.put(CompetenceRequirement.class, CompReq);
		return referencedObjects;
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
	
	@Override
	public Object clone() {
		ArrayList<CompetenceRequirement> cr = new ArrayList<>();
		ArrayList<Employee> emp = new ArrayList<>();
		
		for (CompetenceRequirement s : CompReq)
			cr.add(s);
		for (Employee s : this.AffEmp)
			emp.add(s);
		
		Mission copy = null;
		try {
			copy = (Mission) super.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
		copy.setAffEmp(emp);
		copy.setCompReq(cr);
		return copy;
	}

	
}
