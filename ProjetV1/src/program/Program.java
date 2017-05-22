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
import models.Competence;
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
				giveCompetences(data);
			} catch (DataException e) {
				System.out.println("Probl�me chargement des CSV" + e.getMessage());
				JOptionPane.showMessageDialog(
						new JFrame(), "Erreur lors de la lecture des fichiers CSV.",
						"Erreur CSV", JOptionPane.WARNING_MESSAGE
				);
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
	
	/**
	 * M�thode pour attribution des comp�tences aux employ�s a la fin de la
	 * formation on check les employ�s, ensuite les comp�tences, si l'employ� ne
	 * l'a pas d�j� on lui ajoute
	 * 
	 * @throws DataException
	 */
	
	private static void giveCompetences(Data data) throws DataException {
		ArrayList<MissionFormation> listeFormationsTerminee = data.Formations().filtrer(o -> o.getStatus() == Status.TERMINEE);
		for (MissionFormation formation : listeFormationsTerminee) {
			for(Employee e : formation.getAffEmp()){
				for(Competence c : formation.getCompetences()){
					if(!e.getCompetences().contains(c)){
						e.addCompetence(c);
						data.Employes().modifier(e);
					}
				}
			}
		}
		
	}
}
