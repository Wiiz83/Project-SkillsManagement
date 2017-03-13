package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

/**
 * @author David
 * Classe Mission
 */
public class Mission extends CSVEntity {
	private String								nomM;
	private Date								dateDebut;
	private Date								dateFin;
	private int									duree;
	private int									nbPersReq;
	private int									id	= -1;
	private ArrayList<Employee>					AffEmp;
	private ArrayList<CompetenceRequirement>	CompReq;
	private Status								Status;
	
	/**
	 * @param nomM
	 * @param dateDebut
	 * @param duree
	 * @param nbPersReq
	 * Classe Mission
	 */
	public Mission(String nomM, Date dateDebut, int duree, int nbPersReq) {
		super();
		this.nomM = nomM;
		this.dateDebut = dateDebut;
		this.duree = duree;
		this.nbPersReq = nbPersReq;
		this.AffEmp = new ArrayList<>();
		this.CompReq = new ArrayList<>();
		this.Status = Status.PLANIFIEE;
		this.dateFin = setDateFin(duree);
	}
	
	/**
	 * @return le statut actuel de la mission
	 */
	public Status getStatus() {
		return Status;
	}
	
	/**
	 * @param duree
	 * @return calcul la date de fin et l'instancie sur la mission
	 */
	public Date setDateFin(int duree){
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dateDebut);
		cal.add(Calendar.DATE, duree);
		return cal.getTime();
	}

	public void setStatus(Status status) {
		Status = status;
	}

	/**
	 * @param cr
	 * @return ajoute une compétence a la mission (true si réussi false si echec de l'ajout
	 */
	public boolean addCompetenceReq(CompetenceRequirement cr) {
		return CompReq.add(cr);
	}
	
	/**
	 * @return l'arraylist des compétences requises sur le projet
	 */
	public ArrayList<CompetenceRequirement> getCompReq() {
		return CompReq;
	}
	
	/**
	 * @param e
	 * Affectation d'un employé sur la mission
	 */
	public void affectEmployee(Employee e) {
		AffEmp.add(e);
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
	
	public int getNbPersReq() {
		return nbPersReq;
	}
	
	public void setNbPersReq(int nbPersReq) {
		this.nbPersReq = nbPersReq;
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
	
	@Override
	public String toString() {
		return "Mission [nomM=" + nomM + ", id=" + id + ", AffEmp=" + AffEmp + ", CompReq=" + CompReq + "]";
	}
	
	@Override
	public String csvID() {
		return Integer.toString(id);
	}
	
	public void setCsvID(String iD) throws InvalidDataException {
		try {
			setID(Integer.parseInt(iD));
		} catch (NumberFormatException e) {
			throw new InvalidDataException(e);
		}
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
	
}
