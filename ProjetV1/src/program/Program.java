package program;

import java.io.IOException;

import javax.swing.SwingUtilities;

import db.*;
import gui.Window;
import models.*;

public class Program {

	public static void main(String[] args) {
	
		Window window = new Window("Blokus");
		SwingUtilities.invokeLater(window);
		
		
	/*
	CSVDocument csv = new CSVDocument("./fichiers_projet/liste_competences.csv");
	CSVLine line = null;
	Competence c= null;
	try {
		line = csv.getAll().get(0);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	try {
		c = CSVDeserializer.Deserialize(line, Competence.class);
	} catch (InvalidCSVException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvalidDataException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(c);	*/
		
		
	}

}
