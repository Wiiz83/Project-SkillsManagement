package program;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.text.ParseException;
import java.util.ArrayList;

import csv.*;
import csv.InvalidDataException;
import gui.ProgramFrame;
import models.*;

public class Program {
	
	public static Cursor NOT_ALLOWED_CURSOR = null;
	public static Cursor LEFT_POINTER_CURSOR = null;
	public static Cursor GRAB_CURSOR = null;
	public static Cursor GRABBED_CURSOR = null;
	public static Cursor POINTING_HAND_CURSOR = null;
    static SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
	
	/*
	 * check if refrenced entity is attached . if y & no id ini doc then throw
	 * csvexception
	 */
	public static void main(String[] args) throws IOException, InvalidCSVException, InvalidDataException, ParseException, CSVUpdateException {
		 {
	
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		try {
			BufferedImage cursorNotAllowedImage = ImageIO.read(Program.class.getClass().getResource("/cursors/not_allowed2.png"));
			NOT_ALLOWED_CURSOR = tk.createCustomCursor(cursorNotAllowedImage, new Point(7,7), "Not Allowed");
			
			BufferedImage cursorGrabImage = ImageIO.read(Program.class.getClass().getResource("/cursors/grab.png"));
			GRAB_CURSOR = tk.createCustomCursor(cursorGrabImage, new Point(7,8), "Grab");
			
			BufferedImage cursorGrabbingImage = ImageIO.read(Program.class.getClass().getResource("/cursors/grabbing.png"));
			GRABBED_CURSOR = tk.createCustomCursor(cursorGrabbingImage, new Point(7,8), "Grabbing");
			
			BufferedImage cursorLeftPointerImage = ImageIO.read(Program.class.getClass().getResource("/cursors/left_ptr.png"));
			LEFT_POINTER_CURSOR = tk.createCustomCursor(cursorLeftPointerImage, new Point(0,0), "Pointer");

			BufferedImage cursorPointingHandImage = ImageIO.read(Program.class.getClass().getResource("/cursors/pointing_hand.png"));
			POINTING_HAND_CURSOR = tk.createCustomCursor(cursorPointingHandImage, new Point(4,0), "Pointing Hand");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
			
	    SwingUtilities.invokeLater(new Runnable()
	    {
	            public void run()
	            {
	                new ProgramFrame().displayGUI();
	            }
	    });



				
	    for (int i = 0; i < 10000; i++) {
	    	// Charger toutes les compétences
	    	CSVObjects<Competence> competences_csv = new CSVObjects<>(Competence.class); // Accès
	    	// au
	    	// CSV
	    	Competence competence1 = competences_csv.getByID("A.1.");
	    	Competence competence2 = competences_csv.getByID("C.1.");
	    	System.out.println(competence1);

	    	// Ajout d'un employé
	    	Employee employe_test = new Employee("Employé_test", "_test", dateformatter.parse("11/11/2011"));
	    	employe_test.addCompetence(competence1);
	    	employe_test.addCompetence(competence2);

	    	CSVObjects<Employee> employes_csv = new CSVObjects<>(Employee.class);
	    	employes_csv.add(employe_test);
	    	String genereated_id = employe_test.csvID();

	    	System.out.println(employes_csv.getByID(genereated_id));

	    	// Ajout d'une mission
	    	Mission mission_test = new Mission("Mission_test", dateformatter.parse("08/08/2018"), 20, 9);
	    	CompetenceRequirement cr_test = new CompetenceRequirement(competence1, 2);
	    	mission_test.addCompetenceReq(cr_test);
	    	mission_test.addCompetenceReq(new CompetenceRequirement(competences_csv.getByID("B.2."), 5));
	    	mission_test.affectEmployee(employe_test);
	    	mission_test.affectEmployee(employes_csv.getByID("2"));
	    	mission_test.affectEmployee(employes_csv.getByID("5"));

	    	// Ajout des compétences requises dans le csv
	    	ArrayList<CompetenceRequirement> unsaved_objects = mission_test.getCompReq();
	    	CSVObjects<CompetenceRequirement> compreq_csv = new CSVObjects<>(CompetenceRequirement.class);
	    	compreq_csv.addMany(unsaved_objects);

	    	CSVObjects<Mission> missions_csv = new CSVObjects<>(Mission.class);
	    	missions_csv.add(mission_test);

	    	System.out.println(missions_csv.getByID(mission_test.csvID()));
	    }
		 }

	    
	    
	}

}


