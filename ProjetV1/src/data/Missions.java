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

public class Missions extends CSVRequests<Mission> {
	
	Missions(Data data) throws DataException {
		super(data, Mission.class);
	}
	
	public Mission parID(int ID) throws DataException {
		return parID(Integer.toString(ID));
	}
	
	public ArrayList<Mission> AvecStatus(Status status) throws DataException {
		try {
			return csvobjects.getFiltered(m -> m.getStatus() == status);
		} catch (CSVException e) {
			throw new DataException(e);
		}
	}
	
	public ArrayList<Mission> duMois() throws DataException {
		return dansIntervalle(Cal.today(), 30);
	}
	
	public ArrayList<Mission> dansIntervalle(Date dateDebut, int nbJours) throws DataException {
		Calendar c = Calendar.getInstance();
		c.setTime(dateDebut);
		c.add(Calendar.DATE, nbJours);
		try {
			return csvobjects.getFiltered(
					m -> m.getDateDebut().compareTo(dateDebut) > 0 && m.getDateDebut().compareTo(c.getTime()) < 0
			);
		} catch (CSVException e) {
			throw new DataException(e);
		}
	}
	
	public ArrayList<Mission> dansIntervalle(String strDateDebut, int nbJours) throws DataException {
		SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDebut;
		try {
			dateDebut = dateformatter.parse(strDateDebut);
		} catch (ParseException e) {
			throw new DataException(e);
		}
		return dansIntervalle(dateDebut, nbJours);
	}
	
}
