package data;

import csv.CSVCache;
import csv.CSVException;

public class Test {
	public static void main(String[] args) throws CSVException {
		CSVCache cache = new CSVCache();
		Data data = new Data(cache);
		System.out.println(data.Competences().tous().size());
		System.out.println(data.Competences().manquantesEmploye("4").size());
		System.out.println(data.Employes().parID("4").getCompetences().size());
		System.out.println(data.Employes().tous());
	}
}
