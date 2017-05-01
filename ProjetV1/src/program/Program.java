package program;

import javax.swing.SwingUtilities;

import csv.CSVException;
import data.Data;
import data.DataException;
import data.AppCSVConfig;
import gui.ProgramFrame;

public class Program {
	
	/**
	 * 
	 * 
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
			Data data;
			// Objet unique à passer à toutes les classes qui accèdent aux
			// données.
			try {
				data = new Data(new AppCSVConfig());
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
