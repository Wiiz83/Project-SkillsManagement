package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import csv.CSVEntity;

public abstract class MissionAbstract extends CSVEntity {
	/**
	 * 
	 */
	private static final long		serialVersionUID		= 6150557649409166209L;
	protected String				nomM;
	protected Date					dateDebut;
	protected Date					dateFin;
	protected int					duree;
	protected int					nbPersReq;
	protected int					id						= -1;
	protected ArrayList<Employee>	AffEmp;
	protected boolean				forcer_planification	= false;
	
	public Status getStatus() {
		
		if (Cal.today().compareTo(dateFin) > 0) {
			return Status.TERMINEE; // Vérification de si la mission est
									// terminée puis retour
		} else if (AffEmp.size() < nbPersReq || forcer_planification)
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
	
	public boolean estModifiable() {
		return (getStatus() == Status.PREPARATION || getStatus() == Status.PLANIFIEE);
		// retourne si oui ou non la mission est toujours modifiable
	}
	
	public Date setDateFin(int duree) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dateDebut);
		cal.add(Calendar.DATE, duree);
		return cal.getTime();
	}
	
	public void affectEmployee(Employee e) {
		AffEmp.add(e);
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public ArrayList<Employee> getAffEmp() {
		return AffEmp;
	}
	
	public void setAffEmp(ArrayList<Employee> affEmp) {
		AffEmp = affEmp;
	}
	
	////////// getters - setters
	public String getNomM() {
		return nomM;
	}
	
	public void setNomM(String nomM) {
		this.nomM = nomM;
	}
	
	public Date getDateDebut() {
		return dateDebut;
	}
	
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	
	public int getDuree() {
		return duree;
	}
	
	public void setDuree(int duree) {
		this.duree = duree;
	}
	
	@Override
	public String csvID() {
		return Integer.toString(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MissionAbstract))
			return false;
		MissionAbstract other = (MissionAbstract) obj;
		if (AffEmp == null) {
			if (other.AffEmp != null)
				return false;
		} else if (!AffEmp.equals(other.AffEmp))
			return false;
		if (dateDebut == null) {
			if (other.dateDebut != null)
				return false;
		} else if (!dateDebut.equals(other.dateDebut))
			return false;
		if (dateFin == null) {
			if (other.dateFin != null)
				return false;
		} else if (!dateFin.equals(other.dateFin))
			return false;
		if (duree != other.duree)
			return false;
		if (forcer_planification != other.forcer_planification)
			return false;
		if (id != other.id)
			return false;
		if (nbPersReq != other.nbPersReq)
			return false;
		if (nomM == null) {
			if (other.nomM != null)
				return false;
		} else if (!nomM.equals(other.nomM))
			return false;
		return true;
	}
	
}
