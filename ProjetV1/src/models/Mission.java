package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import csv.CSVEntity;
import csv.InvalidDataException;

/**
 * @author David Classe Mission
 */
public class Mission extends MissionAbstract {
	private ArrayList<CompetenceRequirement> CompReq;

	/**
	 * @param nomM
	 * @param dateDebut
	 * @param duree
	 * @param nbPersReq
	 *            Classe Mission
	 */
	public Mission(String nomM, Date dateDebut, int duree, int nbPersReq) {
		super();
		this.nomM = nomM;
		this.dateDebut = dateDebut;
		this.duree = duree;
		this.nbPersReq = nbPersReq;
		this.AffEmp = new ArrayList<>();
		this.CompReq = new ArrayList<>();
		this.setDateFin(setDateFin(duree));
	}

	/**
	 * @return le statut actuel de la mission
	 */
	public Status getStatus() {

		if (Cal.today().compareTo(dateFin) > 0) {
			return Status.TERMINEE; // Vérification de si la mission est
									// terminée puis retour
		}
		if (AffEmp.size() < nbPersReq || forcer_planification)
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

	public void planifier() {
		this.forcer_planification = true;
	}

	public boolean estModifiable() {
		return (getStatus() == Status.PREPARATION || getStatus() == Status.PLANIFIEE); // retourne
																						// si
																						// oui
																						// ou
																						// non
																						// la
																						// mission
																						// est
																						// toujours
																						// modifiable
	}

	/**
	 * @param duree
	 * @return calcul la date de fin et l'instancie sur la mission
	 */
	public Date setDateFin(int duree) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dateDebut);
		cal.add(Calendar.DATE, duree);
		return cal.getTime();
	}

	/**
	 * @param cr
	 * @return ajoute une compétence a la mission (true si réussi false si echec
	 *         de l'ajout
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
	 *            Affectation d'un employé sur la mission
	 */
	public void affectEmployee(Employee e) {
		AffEmp.add(e);
	}

	public int getNbPersReq() {
		return nbPersReq;
	}

	public void setNbPersReq(int nbPersReq) {
		this.nbPersReq = nbPersReq;
	}

	@Override
	public String toString() {
		return "Mission [nomM=" + nomM + ", id=" + id + ", AffEmp=" + AffEmp + ", CompReq=" + CompReq + "]";
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

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public boolean getForcer_planification() {
		return forcer_planification;
	}

}
