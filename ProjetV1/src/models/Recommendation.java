package models;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import csv.CSVObjects;
import csv.InvalidCSVException;
import csv.InvalidDataException;

public class Recommendation {
	
	private static int recID;
	private Mission misToRec;
	private ArrayList<Employee> empRec;
	private ArrayList<CompetenceRequirement> misCompReq;
	private ArrayList<Employee> empAff;
	private ArrayList<Employee> empToRec;
	
	
	/**
	 * @param misToRec (La mission qui est concernée par les recommendations
	 */
	public Recommendation(Mission misToRec) {
		super();
		this.recID++;
		this.misToRec = misToRec;
		this.misCompReq = misToRec.getCompReq();
		this.empAff = misToRec.getAffEmp();
	}
	
	/**
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws InvalidCSVException
	 * @throws InvalidDataException
	 * @throws ParseException
	 * Calcul toutes les recommendations de la mission en fonction des besoins
	 */
	public void setRecommendations() throws IOException, NumberFormatException, InvalidCSVException, InvalidDataException, ParseException{
		CSVObjects<Employee> employee_csv = new CSVObjects<>(Employee.class);
		empRec = employee_csv.getAll();
		for(Employee e : empRec){
			for(Competence c : e.getCompetences()){
				if(checkCompMission(c) != 0){
					if(!this.empToRec.contains(e))
					this.empToRec.add(e);
				}
			}
		}
	}
	
	/**
	 * @return la liste des employés recommendés pour la mission
	 */
	public ArrayList<Employee> GetEmpToRec(){
		return this.empToRec;
	}
	
	/**
	 * Efface tout les employés déjà présent sur la mission des recommendations
	 */
	public void deleteAff(){
		for(Employee e : empRec){
			for(Employee e2 : empAff){
				if(e == e2){
					empRec.remove(e);
				}
			}
		}
	}
	
	/**
	 * @param Compétence a vérifier concernant la mission
	 * @return Le nombre de d'employés nécéssaires ayant cette compétence sur le projet
	 */
	public int checkCompMission(Competence c){
		int nbEmpReqComp = 0;
		for(CompetenceRequirement cr : this.misCompReq){
			if(cr.getCompetence() == c){
				nbEmpReqComp = cr.getNbRequiredEmployees();
			}
		}
		for(Employee e : this.empAff){
			for(Competence cEmp : e.getCompetences()){
				if(cEmp == c){
					nbEmpReqComp--;
				}
			}
		}
		return nbEmpReqComp;
	}
	
}
