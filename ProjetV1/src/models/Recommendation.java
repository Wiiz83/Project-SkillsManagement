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
	private ArrayList<Employee>					empRec; //ArrayList des employés à tester pour les recommendations
	private ArrayList<CompetenceRequirement>	misCompReq; //ArrayList des compétences requises dans la missions
	private ArrayList<Employee>					empAff; //ArrayList affectés à la mission
	private ArrayList<Employee>					empToRec; //ArrayList des employés recommendés
	private int[]								RLevel; //Tableau des niveaux de recommendations par employés
	
	/**
	 * @param misToRec  La mission qui est concernée par les recommendations
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
		for (Employee e : empRec) { // Boucles et conditions pour vérifier si l'employé est recommendable et si oui l'ajoute
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
	 * @return la liste des employés recommendés pour la mission
	 */
	public ArrayList<Employee> GetEmpToRec() {
		return this.empToRec;
	}
	
	/**
	 * Efface tout les employés déjà présent sur la mission des recommendations
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
	 * @param Compétence
	 *            a vérifier concernant la mission
	 * @return Le nombre de d'employés nécéssaires ayant cette compétence sur le
	 *         projet
	 */
	public int checkCompMission(Competence c) {
		int nbEmpReqComp = 0;
		for (CompetenceRequirement cr : this.misCompReq) {
			if (cr.getCompetence() == c) {
				nbEmpReqComp = cr.getNbRequiredEmployees(); // On récupère le
															// nombre d'employés
															// requis pour la
															// compétence
			}
		}
		for (Employee e : this.empAff) {
			for (Competence cEmp : e.getCompetences()) {
				if (cEmp == c) {
					nbEmpReqComp--; // On décrémente le compteur pour avoir au final le nombre d'employés encore requis
				}
			}
		}
		return nbEmpReqComp; // On retourne le nombre d'employé en manque sur la compétence
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
