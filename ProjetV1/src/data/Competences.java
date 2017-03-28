package data;

import java.util.ArrayList;

import csv.CSVException;
import models.Competence;

public class Competences extends Requests<Competence> {
	Competences(Data data) throws CSVException {
		super(data, Competence.class);
	}
	
	public ArrayList<Competence> manquantesEmploye(String IDEmploye) throws CSVException {
		ArrayList<Competence> compEmp = data.Employes().parID(IDEmploye).getCompetences();
		return csvobjects.getFiltered(c -> !compEmp.contains(c));
	}
}
