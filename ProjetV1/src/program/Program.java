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
		 }  
	}
}


