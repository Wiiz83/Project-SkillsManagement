package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import csv.CSVException;
import csv.CSVObjects;
import models.Cal;
import models.CompetenceRequirement;
import models.Mission;
import models.Status;
import java.util.Comparator;

public class Missions extends CSVRequests<Mission> {
	
	Missions(Data data) throws DataException {
		super(data, Mission.class);
	}
	
	@Override
	public void ajouter(Mission m) throws DataException {
		try {
			CSVObjects<CompetenceRequirement> compreq_csv = new CSVObjects<CompetenceRequirement>(
					CompetenceRequirement.class, data.getCSVConfig()
			);
			compreq_csv.addMany(m.getCompReq());
		} catch (CSVException e2) {
			throw new DataException(e2);
		}
		super.ajouter(m);
	}
	
	@Override
	public void supprimer(Mission m) throws DataException {
		try {
			CSVObjects<CompetenceRequirement> compreq_csv = new CSVObjects<CompetenceRequirement>(
					CompetenceRequirement.class, data.getCSVConfig()
			);
			if (m.getCompReq() != null) {
				for (CompetenceRequirement cr : m.getCompReq())
					if (cr.isAttached())
						compreq_csv.delete(cr);
			}
		} catch (CSVException e1) {
			throw new DataException(e1);
		}
		super.supprimer(m);
	}
	
	@Override
	public void modifier(Mission m) throws DataException {
		this.supprimer(m);
		this.ajouter(m);
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
	
	/**
	 * @return Missions en préparation qui commencent dans 15 jours
	 * @throws DataException
	 */
	public ArrayList<Mission> AlertesEmployesManquants() throws DataException {
		Comparator<Mission> comp = (m, n) -> m.getDateDebut().compareTo(m.getDateDebut());
		comp = comp.thenComparing((m, n) -> m.nbEmployesManquants() - n.nbEmployesManquants());
		ArrayList<Mission> alertes = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(Cal.today());
		cal.add(Calendar.DATE, 15);
		Date dateRetard = cal.getTime();
		try {
			alertes = filtrerTrier(
					m -> m.getStatus() == Status.PREPARATION && m.nbEmployesManquants() > 0
							&& m.getDateDebut().after(dateRetard),
					comp
			);
		} catch (DataException e) {
			throw new DataException(e);
		}
		return alertes;
	}
	
	/**
	 * @return Missions en cours et qui sont en retard
	 * @throws DataException
	 */
	public ArrayList<Mission> AlertesMissionsEnRetard() throws DataException {
		Comparator<Mission> comp = (m, n) -> m.getDateDebut().compareTo(m.getDateDebut());
		comp = comp.thenComparing((m, n) -> m.nbEmployesManquants() - n.nbEmployesManquants());
		ArrayList<Mission> alertes = new ArrayList<>();
		try {
			alertes = filtrerTrier(m -> m.getStatus() == Status.EN_COURS && m.enRetard(), comp);
		} catch (DataException e) {
			throw new DataException(e);
		}
		return alertes;
	}
	
}
