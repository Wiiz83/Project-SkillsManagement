package program;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import csv.CSVException;
import data.Data;
import data.DataException;
import data.AppCSVConfig;
import gui.ProgramFrame;
import models.Employee;
import models.MissionFormation;
import models.Status;

public class Program {
	
	/**
	 * Lancement du programme en appelant la m�thode displayGUI de la classe
	 * ProgramFrame qui affiche la fen�tre
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws DataException 
	 * 
	 * @throws CSVException
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, DataException {
		{
			Data data;
			// Objet unique � passer � toutes les classes qui acc�dent aux
			// donn�es.
			try {
				data = new Data(new AppCSVConfig());
			} catch (DataException e) {
				System.out.println("Probl�me chargement des CSV" + e.getMessage());
				JOptionPane.showMessageDialog(
						new JFrame(), "Erreur lors de la lecture des fichiers CSV.",
						"Erreur CSV", JOptionPane.WARNING_MESSAGE
				);
				e.printStackTrace();
				return;
			}
			
			ArrayList<MissionFormation> listeFormationsTerminee = data.Formations().filtrer(o -> o.getStatus() == Status.TERMINEE);
			for (MissionFormation formation : listeFormationsTerminee) {
				formation.giveCompetences();
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
