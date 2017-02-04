package models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Mission {
	private String nomMission;
	private Date dateDebut;
	private int duree;
	private static int id;
	private Status status;
	private int nbPersReq;
	private ArrayList<Competence> listCompReq;
	private ArrayList<Employee> listEmployee;//Affectés
	private int nbCompPres[];
	
	
	public Mission(String nomMission, Date dateDebut, int duree) {
		super();
		this.nomMission = nomMission;
		this.dateDebut = dateDebut;
		this.duree = duree;
		this.status = status.PREPARATION;
		this.id++;
	}

	
	
	public String getNomMission() {
		return nomMission;
	}


	public void setNomMission(String nomMission) {
		this.nomMission = nomMission;
	}


	public int getNbPersReq() {
		return nbPersReq;
	}


	public void setNbPersReq(int nbPersReq) {
		this.nbPersReq = nbPersReq;
	}



	
	public void addEmp(Employee e){
		this.listEmployee.add(e);
	}
	
	public void remEmp(Employee e){
		this.listEmployee.remove(e);
	}
	
//	public void MAJ(){
//		setNbEmpAff(this.listEmployee.size());
//		for(Employee e : this.listEmployee){
//			for(Competence c : e.getCompetences()){
//				this.nbCompPres[c.ID()]++;
//			}
//		}
//		
//	}
	
	
}
