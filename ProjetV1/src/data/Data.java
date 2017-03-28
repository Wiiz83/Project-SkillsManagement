package data;

import csv.CSVCache;
import csv.CSVException;

public class Data {
	private CSVCache	cache;
	private Employes	employes;
	private Missions	missions;
	private Competences	competences;
	
	public Data(CSVCache cache) throws CSVException {
		this.cache = cache;
		this.employes = new Employes(this);
		this.missions = new Missions(this);
		this.competences = new Competences(this);
	}
	
	CSVCache getCache() {
		return cache;
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
