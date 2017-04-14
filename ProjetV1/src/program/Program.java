package program;

import javax.cache.Caching;
import javax.swing.SwingUtilities;

import csv.CSVConfig;
import csv.CSVException;
import data.Data;
import data.DataException;
import data.AppCSVConfig;
import data.AppCSVDataModel;
import data.AppCSVDeserializer;
import data.AppCSVSerializer;
import gui.ProgramFrame;

public class Program {
	
	/**
	 * Lancement du programme en appelant la méthode displayGUI de la classe
	 * ProgramFrame qui affiche la fenêtre
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 * @throws CSVException
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		{
			CSVConfig config = AppCSVConfig.getInstance();
			Data data;
			// Objet unique à passer à toutes les classes qui accèdent aux
			// données.
			try {
				data = new Data(config);
			} catch (DataException e) {
				System.out.println("Problème chargement des CSV" + e.getMessage());
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
