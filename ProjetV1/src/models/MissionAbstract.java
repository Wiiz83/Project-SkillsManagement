package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import csv.CSVEntity;
import csv.InvalidDataException;

public abstract class MissionAbstract extends CSVEntity {
	/**
	 * 
	 */
	private static final long		serialVersionUID		= 6150557649409166209L;
	protected String				nom;
	protected Date					dateDebut;
	protected int					duree;
	protected int					nbPersReq;
	protected int					id						= -1;
	protected ArrayList<Employee>	AffEmp;
	protected boolean				forcer_planification	= false;
	protected Date					dateFinReelle;
	
	public MissionAbstract(String nomM, Date dateDebut, int duree, int nbPersReq) {
		this.nom = nomM;
		this.dateDebut = dateDebut;
		this.duree = duree;
		this.nbPersReq = nbPersReq;
		this.AffEmp = new ArrayList<>();
		setDateFinRelle(duree);
		
	}
	
	/**
	 * @return le statut actuel de la mission
	 */
	public Status getStatus() {
		
		if (Cal.today().compareTo(getDateFinReelle()) > 0) {
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
	
	public Date getDateFin() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dateDebut);
		cal.add(Calendar.DATE, duree);
		return cal.getTime();
	}
	
	public int nbEmployesManquants() {
		return nbPersReq - AffEmp.size();
	}
	
	public void planifier() {
		this.forcer_planification = true;
	}
	
	public void deplanifier() {
		this.forcer_planification = false;
	}
	
	/**
	 * @param duree
	 * @return calcul la date de fin et l'instancie sur la mission
	 */
	
	public boolean estModifiable() {
		return (getStatus() == Status.PREPARATION || getStatus() == Status.PLANIFIEE);
		// retourne si oui ou non la mission est toujours modifiable
	}
	
	public void affectEmployee(Employee e) {
		AffEmp.add(e);
	}
	
	public void removeEmployee(Employee e) {
		AffEmp.remove(e);
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
		return nom;
	}
	
	public void setNomM(String nomM) {
		this.nom = nomM;
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
	
	public boolean getForcer_planification() {
		return forcer_planification;
	}
	
	public int getNbPersReq() {
		return nbPersReq;
	}
	
	public void setNbPersReq(int nbPersReq) {
		this.nbPersReq = nbPersReq;
	}
	
	@Override
	public String csvID() {
		return Integer.toString(id);
	}
	
	@Override
	public void setCsvID(String iD) throws InvalidDataException {
		try {
			setID(Integer.parseInt(iD));
		} catch (NumberFormatException e) {
			throw new InvalidDataException(e);
		}
	}
	
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
 
	

	
}
