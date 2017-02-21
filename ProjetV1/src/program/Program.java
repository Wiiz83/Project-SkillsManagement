package program;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import db.*;
import gui.ProgramFrame;
import models.*;

public class Program {

	public static void main(String[] args) {
	
		Toolkit tk = Toolkit.getDefaultToolkit();
			
	    SwingUtilities.invokeLater(new Runnable()
	    {
	            public void run()
	            {
	                new ProgramFrame().displayGUI("Blokus");
	            }
	    });
		
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
