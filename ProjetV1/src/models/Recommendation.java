package models;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import csv.CSVConfig;
import csv.CSVException;
import csv.CSVObjects;
import data.Data;
import data.DataException;

/**
 * @author David Classe Recommendation
 */
public class Recommendation {
	
	private static int							recID; //id du groupe de recommendations
	private Mission								misToRec; //Mission qui a besoin des recommendations
	private ArrayList<Employee>					empRec; //ArrayList des employ�s � tester pour les recommendations
	private ArrayList<CompetenceRequirement>	misCompReq; //ArrayList des comp�tences requises dans la missions
	private ArrayList<Employee>					empAff; //ArrayList affect�s � la mission
	private ArrayList<Employee>					empToRec; //ArrayList des employ�s recommend�s
	private int[]								RLevel; //Tableau des niveaux de recommendations par employ�s
	
	/**
	 * @param misToRec  La mission qui est concern�e par les recommendations
	 */	
	public Recommendation(Mission misToRec, ArrayList<Employee> emp) {
		super();
		Recommendation.recID++;
		this.misToRec = misToRec;
		this.misCompReq = misToRec.getCompReq();
		this.empRec = emp;
		this.empAff = this.misToRec.getAffEmp();
	}
	
	/**
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ParseException
	 *             Calcul toutes les recommendations de la mission en fonction
	 *             des besoins
	 * @throws DataException
	 */
	public void setRecommendations() throws DataException {
		for (Employee e : empRec) { // Boucles et conditions pour v�rifier si l'employ� est recommendable et si oui l'ajoute
			this.RLevel[e.getID()] = 0;
			for (Competence c : e.getCompetences()) {
				if (checkCompMission(c) != 0) {
					if (!this.empToRec.contains(e))
						this.empToRec.add(e);
					this.RLevel[e.getID()]++;
				}
			}
		}
	}
	
	/**
	 * @return la liste des employ�s recommend�s pour la mission
	 */
	public ArrayList<Employee> GetEmpToRec() {
		return this.empToRec;
	}
	
	/**
	 * Efface tout les employ�s d�j� pr�sent sur la mission des recommendations
	 */
	public void deleteAff() {
		for (Employee e : empRec) {
			for (Employee e2 : empAff) {
				if (e == e2) {
					empRec.remove(e);
				}
			}
		}
	}
	
	/**
	 * @param Comp�tence
	 *            a v�rifier concernant la mission
	 * @return Le nombre de d'employ�s n�c�ssaires ayant cette comp�tence sur le
	 *         projet
	 */
	public int checkCompMission(Competence c) {
		int nbEmpReqComp = 0;
		for (CompetenceRequirement cr : this.misCompReq) {
			if (cr.getCompetence() == c) {
				nbEmpReqComp = cr.getNbRequiredEmployees(); // On r�cup�re le
															// nombre d'employ�s
															// requis pour la
															// comp�tence
			}
		}
		for (Employee e : this.empAff) {
			for (Competence cEmp : e.getCompetences()) {
				if (cEmp == c) {
					nbEmpReqComp--; // On d�cr�mente le compteur pour avoir au final le nombre d'employ�s encore requis
				}
			}
		}
		return nbEmpReqComp; // On retourne le nombre d'employ� en manque sur la comp�tence
	}
	
	public int getLevel(int idEmp){
		
		return 0;
	}
	
	public ArrayList<Employee> getEmpRec() {
		return empRec;
	}
	
	public int[] getRLevel() {
		return RLevel;
	}
	
	public void setRLevel(int[] rLevel) {
		RLevel = rLevel;
	}
	
}
