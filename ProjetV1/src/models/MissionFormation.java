package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import csv.CSVEntity;

public class MissionFormation extends MissionAbstract {
	
	private static final long		serialVersionUID	= 4395319872170806127L;
	private ArrayList<Competence>	Competences;
	
	@Override
	public Status getStatus() {
		
		if (Cal.today().compareTo(getDateFin()) > 0) {
			this.giveCompetences();
			return Status.TERMINEE; // V�rification de si la mission est
									// termin�e puis retour
		}
		if (AffEmp.size() >= nbPersReq || forcer_planification)
			if (Cal.today().compareTo(dateDebut) > 0) {
				return Status.EN_COURS; // V�rification de si la mission est
										// commenc�e puis retour
			} else {
				return Status.PLANIFIEE; // V�rification de si la mission est
											// planifi�e mais non commenc�e puis
											// retour
			}
		else
			return Status.PREPARATION; // Si aucun des �tats pr�c�dents ne
										// correspond alors la mission est
										// encore en pr�paration
	}
	
	public MissionFormation(String nomM, Date dateDebut, int duree, int nbPersReq) {
		super(nomM, dateDebut, duree, nbPersReq);
 	}
	
	public void addCompetence(Competence c) {
		Competences.add(c);
	}
	
	public ArrayList<Competence> getCompetences() {
		return Competences;
	}
	
	public void setCompetences(ArrayList<Competence> comp) {
		this.Competences = comp;
	}
	
	@Override
	protected HashMap<Class<? extends CSVEntity>, ArrayList<? extends CSVEntity>> getReferencedObjects() {
		HashMap<Class<? extends CSVEntity>, ArrayList<? extends CSVEntity>> referencedObjects = new HashMap<>();
		referencedObjects.put(Employee.class, AffEmp);
		referencedObjects.put(Competence.class, Competences);
		return referencedObjects;
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
	
	
	/**
	 * M�thode pour attribution des comp�tences aux employ�s a la fin de la formation
	 * on check les employ�s, ensuite les comp�tences, si l'employ� ne l'a pas d�j� on lui ajoute
	 */
	public void giveCompetences(){
		for(Employee e : this.getAffEmp()){
			for(Competence c : this.getCompetences()){
				if(!e.getCompetences().contains(c)){
					e.addCompetence(c);
				}
			}
		}
	}
}
