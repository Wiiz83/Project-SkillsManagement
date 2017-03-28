package program;

import javax.swing.SwingUtilities;

import csv.CSVCache;
import csv.CSVException;
import data.Data;
import gui.ProgramFrame;

public class Program {
	
	/**
	 * Lancement du programme en appelant la m�thode displayGUI de la classe
	 * ProgramFrame qui affiche la fen�tre
	 * 
	 * @throws CSVException
	 */
	public static void main(String[] args) {
		{
			CSVCache cache = new CSVCache();
			Data data;
			try {
				data = new Data(cache);
			} catch (CSVException e) {
				System.out.println("Probl�me chargement des CSV");
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
