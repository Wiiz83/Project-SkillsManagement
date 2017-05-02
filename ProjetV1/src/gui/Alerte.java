package gui;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.Mission;

public class Alerte {
	String	type;
	String	desc;
	String 	nom;
	String	dateDebut;
	Mission mission;
	private SimpleDateFormat	MissionDateFormat	= new SimpleDateFormat("dd/MM/yyyy");
	
	public Alerte(String type, String desc) {
		super();
		this.type = type;
		this.desc = desc;
	}
	
	public Alerte(Mission M) {
		this.type = "Employés non affectés";
		this.nom = M.getNomM();
		this.dateDebut = MissionDateFormat.format(M.getDateDebut());
		this.mission = M;
	}
	
	public Mission getMission(){
		return mission;
	}
	
	public String getType() {
		return type;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getDateDebut() {
		return dateDebut;
	}
	
}