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
			file = new FileReader("date.txt");
			BufferedReader bf = new BufferedReader(file);
			today.clear(Calendar.HOUR);
			today.clear(Calendar.MINUTE);
			today.clear(Calendar.SECOND);
			String st;
			try {
				st = bf.readLine();
			} catch (IOException e) {
				return today.getTime();
			}
			if(st != null){			
				
				String[] Split = new String[3];
				Split = st.split(";");
				today.set(Integer.parseInt(Split[1]), Integer.parseInt(Split[2]), Integer.parseInt(Split[3]));
				
				return today.getTime();
			}else{
				return today.getTime();
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return today.getTime();
		}
				
	}

}
