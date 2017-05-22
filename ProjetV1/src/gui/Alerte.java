package gui;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.Mission;

/**
 *  Une alerte correspond à une mission en retard ou qui n'a pas tous ses employés affectés 
 */
public class Alerte {
	String	type;
	String	desc;
	String 	nom;
	String	dateDebut;
	Mission mission;
	private SimpleDateFormat	MissionDateFormat	= new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * @param type le type d'alerte 
	 * @param desc les détails de l'alerte
	 */
	public Alerte(String type, String desc) {
		super();
		this.type = type;
		this.desc = desc;
	}
	
	/**
	 * @param M la mission concerné
	 * @param type le type d'alerte 
	 */
	public Alerte(Mission M, String type) {
		this.type = type;
		this.nom = M.getNomM();
		this.dateDebut = MissionDateFormat.format(M.getDateDebut());
		this.mission = M;
	}
	
	/**
	 * @return la mission 
	 */
	public Mission getMission(){
		return mission;
	}
	
	/**
	 * @return le type d'alerte
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return le nom de la mission
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * @return la date de début de la mission
	 */
	public String getDateDebut() {
		return dateDebut;
	}
	
}