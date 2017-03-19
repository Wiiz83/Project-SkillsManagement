package program;

import java.io.IOException;
import javax.swing.SwingUtilities;
import java.text.ParseException;
import csv.*;
import csv.InvalidDataException;
import gui.ProgramFrame;

public class Program {
	
	/**
	 * Lancement du programme en appelant la m�thode displayGUI de la classe ProgramFrame qui affiche la fen�tre 
	 */
	public static void main(String[] args) throws IOException, InvalidCSVException, InvalidDataException, ParseException, CSVUpdateException {
		{	
			SwingUtilities.invokeLater(
					new Runnable() {
						public void run() {
							new ProgramFrame().displayGUI();
						}
					}
			);
		}
	}
}
