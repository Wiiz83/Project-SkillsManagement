package data;

import csv.CSVConfig;
import csv.CSVException;

public class Data {
	CSVConfig			config;
	private Employes	employes;
	private Missions	missions;
	private Competences	competences;
	
	public Data(CSVConfig config) throws CSVException {
		this.config = config;
		this.employes = new Employes(this);
		this.missions = new Missions(this);
		this.competences = new Competences(this);
		
	}
	
	public CSVConfig getConfig() {
		return config;
	}
	
	public Employes Employes() {
		return employes;
	}
	
	public Missions Missions() {
		return missions;
	}
	
	public Competences Competences() {
		return competences;
	}
	
}
