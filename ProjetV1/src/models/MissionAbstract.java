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
			return Status.TERMINEE; // V�rification de si la mission est
									// termin�e puis retour
		} else if (AffEmp.size() < nbPersReq || forcer_planification)
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
	
}
