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
			return Status.TERMINEE; // Vérification de si la mission est
									// terminée puis retour
		}
		if (AffEmp.size() >= nbPersReq || forcer_planification)
			if (Cal.today().compareTo(dateDebut) > 0) {
				return Status.EN_COURS; // Vérification de si la mission est
										// commencée puis retour
			} else {
				return Status.PLANIFIEE; // Vérification de si la mission est
											// planifiée mais non commencée puis
											// retour
			}
		else
			return Status.PREPARATION; // Si aucun des états précédents ne
										// correspond alors la mission est
										// encore en préparation
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
	 * Méthode pour attribution des compétences aux employés a la fin de la formation
	 * on check les employés, ensuite les compétences, si l'employé ne l'a pas déjà on lui ajoute
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
