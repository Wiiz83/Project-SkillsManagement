package data;

import java.util.ArrayList;

import csv.CSVException;
import models.Competence;

public class Competences extends CSVRequests<Competence> {
	Competences(Data data) throws DataException {
		super(data, Competence.class);
	}
	
	public ArrayList<Competence> manquantesEmploye(String IDEmploye) throws DataException {
		ArrayList<Competence> compEmp;
		compEmp = data.Employes().parID(IDEmploye).getCompetences();
		try {
			return csvobjects.getFiltered(c -> !compEmp.contains(c));
		} catch (CSVException e) {
			throw new DataException(e);
		}
	}
}
