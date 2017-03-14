package models;

import java.util.Calendar;
import java.util.Date;

// Calendrier
public class Cal {
	
	public static Date today() {
		Calendar today = Calendar.getInstance();
		today.clear(Calendar.HOUR);
		today.clear(Calendar.MINUTE);
		today.clear(Calendar.SECOND);
		
		return today.getTime();
	}
	
}
