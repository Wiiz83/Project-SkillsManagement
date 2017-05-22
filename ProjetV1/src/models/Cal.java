package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Cal {

	/**
	 * @return La date d'aujourd'hui ou une autre date pour les tests
	 * @throws IOException 
	 */
	public static Date today(){
		Calendar today = Calendar.getInstance();
		FileReader file;
		try {
			file = new FileReader("./ressources/fichiers/date.txt");
			BufferedReader bf = new BufferedReader(file); //Test sur le fichier pour les tests
			today.clear(Calendar.HOUR);
			today.clear(Calendar.MINUTE);
			today.clear(Calendar.SECOND); //On supprimes les heures, minutes, secondes
			String st;
			try {
				st = bf.readLine(); //On lit la ligne 1 du fichier
			} catch (IOException e) {
				return today.getTime();
			}
			if(st != null){			
				
				String[] Split = new String[3];
				Split = st.split(";"); //On récupère les valeurs (année, mois, jour) du fichier
				today.set(Integer.parseInt(Split[0]), Integer.parseInt(Split[1]), Integer.parseInt(Split[2]));
				
				return today.getTime(); //Return de la valeur du calendar
			}else{
				return today.getTime(); //Return de la valeur du calendar
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return today.getTime(); //Si erreur on retourne la date d'aujourd'hui
		}
				
	}

}
