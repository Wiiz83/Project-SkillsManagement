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
	 * Lancement du programme en appelant la méthode displayGUI de la classe
	 * ProgramFrame qui affiche la fenêtre
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
			// Objet unique à passer à toutes les classes qui accèdent aux
			// données.
			try {
				data = new Data(new AppCSVConfig());
				giveCompetences(data);
			} catch (DataException e) {
				System.out.println("Problème chargement des CSV" + e.getMessage());
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
	 * Méthode pour attribution des compétences aux employés a la fin de la
	 * formation on check les employés, ensuite les compétences, si l'employé ne
	 * l'a pas déjà on lui ajoute
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
