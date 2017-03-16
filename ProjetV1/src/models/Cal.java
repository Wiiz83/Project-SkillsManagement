package models;

import java.util.Calendar;
import java.util.Date;

public class Cal {
	
	/**
	 * @return La date d'aujourd'hui ou une autre date pour les tests
	 */
	public static Date today() {
		Calendar today = Calendar.getInstance();
		today.clear(Calendar.HOUR);
		today.clear(Calendar.MINUTE);
		today.clear(Calendar.SECOND);
		
		return today.getTime();
	}
	
}
