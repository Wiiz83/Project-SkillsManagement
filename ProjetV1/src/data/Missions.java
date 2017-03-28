package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import csv.CSVException;

import models.Cal;
import models.Mission;
import models.Status;

public class Missions extends Requests<Mission> {
	
	Missions(Data data) throws CSVException {
		super(data, Mission.class);
	}
	
	public ArrayList<Mission> AvecStatus(Status status) throws CSVException {
		return csvobjects.getFiltered(m -> m.getStatus() == status);
	}
	
	public ArrayList<Mission> duMois() throws CSVException {
		return dansIntervalle(Cal.today(), 30);
	}
	
	public ArrayList<Mission> dansIntervalle(Date dateDebut, int nbJours) throws CSVException {
		Calendar c = Calendar.getInstance();
		c.setTime(dateDebut);
		c.add(Calendar.DATE, nbJours);
		return csvobjects.getFiltered(
				m -> m.getDateDebut().compareTo(dateDebut) > 0 && m.getDateDebut().compareTo(c.getTime()) < 0
		);
	}
	
	public ArrayList<Mission> dansIntervalle(String strDateDebut, int nbJours) throws CSVException, ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDebut = dateformatter.parse(strDateDebut);
		return dansIntervalle(dateDebut, nbJours);
	}
	
}
