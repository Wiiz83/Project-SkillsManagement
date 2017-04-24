package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		Date d = dateformatter.parse("24/05/2017");
		
		Mission m = new Mission("test", d, 10, 0);
		Mission m2 = new Mission("test", d, 10, 5);
		System.out.println(m.getStatus());
		System.out.println(m2.getStatus());
	}
}
