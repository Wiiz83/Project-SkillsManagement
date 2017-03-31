package program;

import javax.cache.Caching;
import javax.swing.SwingUtilities;

import csv.CSVConfig;
import csv.CSVException;
import data.Data;
import data.DataModel;
import data.Deserializer;
import data.Serializer;
import gui.ProgramFrame;

public class Program {
	
	/**
	 * Lancement du programme en appelant la m�thode displayGUI de la classe
	 * ProgramFrame qui affiche la fen�tre
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 * @throws CSVException
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		{
			CSVConfig config = new CSVConfig(
					Caching.getCachingProvider().getCacheManager(), DataModel.class, Deserializer.class,
					Serializer.class
			);
			Data data;
			// Objet unique � passer � toutes les classes qui acc�dent aux
			// donn�es.
			try {
				data = new Data(config);
			} catch (CSVException e) {
				System.out.println("Probl�me chargement des CSV" + e.getMessage());
				e.printStackTrace();
				return;
			}
			SwingUtilities.invokeLater(
					new Runnable() {
						public void run() {
							new ProgramFrame().displayGUI(data);
						}
					}
			);
		}
	}
}
